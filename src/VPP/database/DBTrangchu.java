package VPP.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class DBTrangchu {
    public static double layGiaTri(Connection conn, String sql) throws Exception {
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        }
        return 0;
    }

    public static void laytkhd(Connection conn, String time, double[] dt) throws Exception {
        // Lấy tổng doanh thu mọi thời đại
        double totalRevenue = layGiaTri(conn, "SELECT COALESCE(SUM(TongTien), 0) AS total FROM HoaDon");

        double doanhThuHomNay = 0;
        double doanhThuTuan = 0;
        double doanhThuThang = 0;

        if (time != null && time.trim().length() > 0) {
            // SQL Server: Dùng CAST AS DATE và GETDATE() thay cho DATE() và CURDATE()
            String sqlToday = "SELECT COALESCE(SUM(TongTien), 0) AS total "
                    + "FROM HoaDon WHERE CAST(" + time + " AS DATE) = CAST(GETDATE() AS DATE)";

            // SQL Server: Dùng DATEPART(week, ...) và DATEPART(year, ...) thay cho YEARWEEK()
            String sqlWeek = "SELECT COALESCE(SUM(TongTien), 0) AS total "
                    + "FROM HoaDon WHERE DATEPART(week, " + time + ") = DATEPART(week, GETDATE()) "
                    + "AND DATEPART(year, " + time + ") = DATEPART(year, GETDATE())";

            // SQL Server: Dùng MONTH() và YEAR() kết hợp GETDATE()
            String sqlMonth = "SELECT COALESCE(SUM(TongTien), 0) AS total "
                    + "FROM HoaDon WHERE YEAR(" + time + ") = YEAR(GETDATE()) "
                    + "AND MONTH(" + time + ") = MONTH(GETDATE())";

            doanhThuHomNay = layGiaTri(conn, sqlToday);
            doanhThuTuan = layGiaTri(conn, sqlWeek);
            doanhThuThang = layGiaTri(conn, sqlMonth);
        } else {
            // Nếu không có cột thời gian truyền vào, mặc định lấy tổng (tùy vào logic nghiệp vụ của bạn)
            doanhThuHomNay = totalRevenue;
            doanhThuTuan = totalRevenue;
            doanhThuThang = totalRevenue;
        }

        dt[0] = doanhThuHomNay;
        dt[1] = doanhThuTuan;
        dt[2] = doanhThuThang;
    }

    public static long csumHD(Connection conn) throws Exception {
        // COUNT(*) trong SQL Server trả về INT hoặc BIGINT tùy số lượng, dùng layGiaTri vẫn ổn
        return (long) layGiaTri(conn, "SELECT COUNT(*) AS total FROM HoaDon");
    }

    public static void doDuLieuProducts(DefaultTableModel model) throws Exception {
        model.setRowCount(0);

        // Đảm bảo tên bảng là SanPham nếu bạn đã đổi tên từ Products ở các bước trước
        String sql = "SELECT maSP, tenSP, soluongSP, giaSP FROM SanPham ORDER BY maSP";

        try (Connection conn = ketnoidb.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String ma = rs.getString("maSP");
                String ten = rs.getString("tenSP");
                int soLuong = rs.getInt("soluongSP");
                long gia = rs.getLong("giaSP"); 

                model.addRow(new Object[]{ma, ten, soLuong, gia});
            }
        }
    }
}