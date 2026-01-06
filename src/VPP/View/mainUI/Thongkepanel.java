package VPP.View.mainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class Thongkepanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JLabel lblDoanhThu, lblSoLuong, lblHoaDon;
    private JButton btnThongKe;
    private JComboBox<String> cboLoaiThongKe;
    private JTextField txtGiaTri;

    private final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Thongkepanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel jLabel1 = new JLabel("QL Thống Kê");
        jLabel1.setFont(new Font("Segoe UI Black", Font.BOLD, 36));
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        // Bảng dữ liệu
        String[] columnNames = {"Mã đơn", "Ngày (dd-MM-yyyy)", "Sản phẩm", "Số lượng", "Đơn giá", "Thành tiền"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Bộ lọc
        JPanel filterPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        cboLoaiThongKe = new JComboBox<>(new String[]{"Ngày", "Tháng", "Năm"});
        txtGiaTri = new JTextField();
        btnThongKe = new JButton("Thống kê");

        filterPanel.add(new JLabel("Loại thống kê:"));
        filterPanel.add(cboLoaiThongKe);
        filterPanel.add(new JLabel("Giá trị (Ngày: dd-MM-yyyy | Tháng: MM-yyyy | Năm: yyyy):"));
        filterPanel.add(txtGiaTri);

        // Kết quả
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblDoanhThu = new JLabel("Tổng doanh thu: 0");
        lblSoLuong = new JLabel("Tổng số lượng: 0");
        lblHoaDon = new JLabel("Số hóa đơn: 0");

        resultPanel.add(lblDoanhThu);
        resultPanel.add(lblSoLuong);
        resultPanel.add(lblHoaDon);

        // Thêm vào panel chính
        add(jLabel1, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(filterPanel, BorderLayout.NORTH);
        bottomPanel.add(btnThongKe, BorderLayout.CENTER);
        bottomPanel.add(resultPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện nút thống kê
        btnThongKe.addActionListener(this::handleThongKe);

        // Dữ liệu mẫu
        themDuLieuMau();
    }

    private void themDuLieuMau() {
        model.addRow(new Object[]{"001", "31-12-2025", "Bút Bi", 10, 10000, 10 * 10000});
        model.addRow(new Object[]{"002", "02-01-2026", "Vở", 5, 15000, 5 * 15000});
        model.addRow(new Object[]{"003", "03-01-2026", "Bút Chì", 8, 7000, 8 * 7000});
    }

    private void handleThongKe(ActionEvent e) {
        String loai = String.valueOf(cboLoaiThongKe.getSelectedItem());
        String giaTri = txtGiaTri.getText().trim();

        double tongDoanhThu = 0;
        int tongSoLuong = 0;
        int soHoaDon = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            String ngayStr = String.valueOf(model.getValueAt(i, 1));
            LocalDate ngayBan = LocalDate.parse(ngayStr, DATE_FMT);

            boolean match = false;
            switch (loai) {
                case "Ngày":
                    LocalDate ngayFilter = LocalDate.parse(giaTri, DATE_FMT);
                    match = ngayBan.equals(ngayFilter);
                    break;
                case "Tháng":
                    YearMonth thangFilter = YearMonth.parse(giaTri, DateTimeFormatter.ofPattern("MM-yyyy"));
                    match = YearMonth.from(ngayBan).equals(thangFilter);
                    break;
                case "Năm":
                    int namFilter = Integer.parseInt(giaTri);
                    match = ngayBan.getYear() == namFilter;
                    break;
            }

            if (match) {
                tongSoLuong += Integer.parseInt(model.getValueAt(i, 3).toString());
                tongDoanhThu += Double.parseDouble(model.getValueAt(i, 5).toString());
                soHoaDon++;
            }
        }

        lblDoanhThu.setText("Tổng doanh thu: " + tongDoanhThu);
        lblSoLuong.setText("Tổng số lượng: " + tongSoLuong);
        lblHoaDon.setText("Số hóa đơn: " + soHoaDon);
    }
}
