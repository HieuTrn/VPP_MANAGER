package VPP.View.mainUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainUI extends javax.swing.JFrame {

    
    private JPanel pnhientai;
    private JPanel contentPanel;
    private JPanel dashboardPanel;
    
    
    private JPanel nut_trangchu;
    private JPanel nut_sp;
    private JPanel nut_pos;
    private JPanel nut_hdon;
    private JPanel nut_thongke;
    private JPanel nut_about;

    
    private JPanel cDTN;
    private JPanel CDTT;
    private JPanel CDTThang;
    private JPanel CTHH;
    
    
    private JTable jTable1;

    public MainUI() {
        initComponents();
        pnhientai = contentPanel;
        
        
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    }

    
    public void chuyenPanel(JPanel panelMoi) {
        if (panelMoi != null) {
            pnhientai.removeAll();
            pnhientai.setLayout(new BorderLayout());
            pnhientai.add(panelMoi, BorderLayout.CENTER);
            pnhientai.revalidate();
            pnhientai.repaint();
        }
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lí bán hàng");
        
        
        getContentPane().setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(222, 120, 255));
        topPanel.setPreferredSize(new Dimension(100, 60)); // Chiều cao 60px
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        
        JPanel topLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        topLeft.setOpaque(false);
        
        JLabel lblAvatar = new JLabel();
        try {
            lblAvatar.setIcon(new ImageIcon(getClass().getResource("/VPP/image/account_circle_24dp_E3E3E3_FILL0_wght400_GRAD0_opsz24.png")));
        } catch (Exception e) {} 
        
        JLabel lblHello = new JLabel("Chào, Admin");
        lblHello.setForeground(Color.WHITE);
        lblHello.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        topLeft.add(lblAvatar);
        topLeft.add(lblHello);

        
        JLabel lblTitle = new JLabel("Phần Mềm Quản Lí Bán Đồ Văn Phòng Phẩm", SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));

        topPanel.add(topLeft, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);


        
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(222, 120, 255));
        sidePanel.setPreferredSize(new Dimension(220, 0)); // Rộng 220px, cao tự động
        sidePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
        // Sử dụng GridLayout 1 cột, nhiều dòng, khoảng cách 0
        sidePanel.setLayout(new GridLayout(10, 1, 0, 5)); 

        
        nut_trangchu = createSideButton("Trang Chủ", "/VPP/image/home_app_logo_32dp_E3E3E3_FILL0_wght400_GRAD0_opsz40.png");
        nut_sp = createSideButton("QL Sản Phẩm", "/VPP/image/package_2_32dp_E3E3E3_FILL0_wght400_GRAD0_opsz40.png");
        nut_pos = createSideButton("Bán Hàng (POS)", "/VPP/image/shopping_bag_32dp_E3E3E3_FILL0_wght400_GRAD0_opsz40.png");
        nut_hdon = createSideButton("QL Hoá Đơn", "/VPP/image/receipt_32dp_E3E3E3_FILL0_wght400_GRAD0_opsz40.png");
        nut_thongke = createSideButton("Thống Kê", "/VPP/image/leaderboard_32dp_E3E3E3_FILL0_wght500_GRAD200_opsz40.png");
        nut_about = createSideButton("Liên Hệ", "/VPP/image/info_32dp_E3E3E3_FILL0_wght400_GRAD0_opsz40.png");

        
        nut_trangchu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { chuyenPanel(dashboardPanel); }
        });
        nut_sp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { chuyenPanel(new Sanphampanel()); } 
        });
        nut_pos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { chuyenPanel(new Banhangpanel()); }
        });
        nut_hdon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { chuyenPanel(new Hoadonpanel()); }
        });
        nut_thongke.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { chuyenPanel(new Thongkepanel()); }
        });
        nut_about.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { chuyenPanel(new lienhepanel()); }
        });

        
        JPanel spacer = new JPanel(); spacer.setOpaque(false);
        sidePanel.add(spacer); 
        sidePanel.add(nut_trangchu);
        sidePanel.add(nut_sp);
        sidePanel.add(nut_pos);
        sidePanel.add(nut_hdon);
        sidePanel.add(nut_thongke);
        sidePanel.add(nut_about);
        
        sidePanel.add(new JLabel()); 
        sidePanel.add(new JLabel());


        
        contentPanel = new JPanel(new BorderLayout());
        
        
        dashboardPanel = new JPanel(new BorderLayout(20, 20)); 
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        
        JPanel statsContainer = new JPanel(new GridLayout(1, 4, 20, 0)); 
        statsContainer.setPreferredSize(new Dimension(0, 120)); 

        
        cDTN = Taosttc("Doanh Thu Hôm Nay", "0", new Color(255, 255, 204));
        CDTT = Taosttc("Doanh Thu Tuần Này", "0", new Color(255, 204, 204));
        CDTThang = Taosttc("Doanh Thu Tháng Này", "0", new Color(204, 255, 153));
        CTHH = Taosttc("Tổng Số Hoá đơn", "0", new Color(204, 255, 204));

        statsContainer.add(cDTN);
        statsContainer.add(CDTT);
        statsContainer.add(CDTThang);
        statsContainer.add(CTHH);

        dashboardPanel.add(statsContainer, BorderLayout.NORTH);

        
        jTable1 = new JTable();
        jTable1.setModel(new DefaultTableModel(
            new Object [][] {
                { "001", "Bút Bi", "1", "10,000" },
                { "002", "Vở", "1", "15,000" },
                { "003", "Bút Chì", "1", "7,000" }
            },
            new String [] { "Mã SP", "Tên SP", "Số Lượng", "Giá" }
        ));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        dashboardPanel.add(scrollPane, BorderLayout.CENTER);

        
        contentPanel.add(dashboardPanel, BorderLayout.CENTER);

        
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(sidePanel, BorderLayout.WEST);
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    


    private JPanel createSideButton(String text, String iconPath) {
        JPanel btn = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        btn.setBackground(new Color(222, 120, 255));
        btn.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lbl.setForeground(Color.WHITE);
        
        try {
            lbl.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        } catch (Exception e) {
            //
        }
        
        btn.add(lbl);

        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setOpaque(true);
                btn.setBackground(new Color(192, 192, 192)); 
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(222, 120, 255)); 
            }
        });

        return btn;
    }

    
    private JPanel Taosttc(String title, String value, Color bg) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
        lblValue.setForeground(new Color(222, 120, 255));
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        
        return card;
    }
}