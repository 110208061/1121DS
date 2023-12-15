import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
	// Layout
    private JButton btnQuery;
    private JButton btnApply;
    private JLabel lblWelcome, lblWarning;
    private JPanel btnPanel, allPanel;
    
    public HomePage() {
    	
        setTitle("政大運動性社團佔場系統");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createLabel();
        createButton();
        createPanel();
    }
    public void createLabel() {
    	   // 設置中間的方框
        lblWelcome = new JLabel("歡迎進入 政大運動性社團佔場系統", SwingConstants.CENTER);
        lblWarning = new JLabel("請注意：若您重複填寫，上一次登記記錄將被覆蓋掉", SwingConstants.CENTER);
		lblWarning.setForeground(Color.RED);
        lblWelcome.setFont(new Font("標楷體", Font.PLAIN, 24));

      
    }
    public void createButton() {
    	  // 設置兩個按鈕
        btnQuery = new JButton("查詢分發結果");
        btnApply = new JButton("申請場地租借");

        // 按下「查詢分發結果」按鈕事件處理
        btnQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// 進入結果頁：ResultPage
                ResultPage resultPage = new ResultPage();
                resultPage.setVisible(true);
                dispose();
            }
        });

        // 按下「申請場地租借」按鈕事件處理
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 進入第二頁：InformationPage
                InformationPage infoPage = new InformationPage();
                infoPage.setVisible(true);
                dispose();
            }
        });
    }
    public void createPanel() {
        // 設置按鈕的佈局
        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.add(btnQuery);
        btnPanel.add(btnApply);

        // 設置整體的佈局
        setSize(400,300);
        allPanel = new JPanel(new GridLayout(3,1));
        allPanel.setSize(300,300);
        allPanel.add(lblWarning);
        allPanel.add(lblWelcome);
        allPanel.add(btnPanel);
        
        add(allPanel);
        setLocationRelativeTo(null);
    }
}