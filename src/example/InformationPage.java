import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationPage extends JFrame implements NextBack{
    private JComboBox<String> cboDept;
    private JComboBox<String> cboTeam;
    private JTextField txtStudentID;
    private JButton btnBack;
    private JButton btnNext;
    private VolleyballReservationPage nextPage;
    private User user;
    private HomePage lastPage = new HomePage() ;
    private String sports = "";

	
    public InformationPage() {
        setTitle("填寫資訊");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);

        createCombo();
        createButton();
        createLayout();
    }
    private void createCombo() {
        // 設置系所下拉式選單
        String[] deptOptions = {"01 資管系", "02 經濟系", "03 金融系", "04 企管系", "05 財政系"};
        cboDept = new JComboBox<>(deptOptions);

        cboDept.setPreferredSize(new Dimension(120,30));

        // 設置運動隊伍下拉式選單
        String[] teamOptions = {"排球", "籃球"};
        cboTeam = new JComboBox<>(teamOptions);
        cboTeam.setPreferredSize(new Dimension(120,30));
        // 設置學號欄位
        txtStudentID = new JTextField(9);
    }
	@Override
	public void Next() {
    	setVisible(false);
    	if(sports.equals("排球")) {
    		nextPage = new VolleyballReservationPage(user);
        	nextPage.setVisible(true);
    	}else {
    		BasketballReservationPage basketball = new BasketballReservationPage(user);
    		basketball.setVisible(true);
    	}
    	
	}
	@Override
	public void Back() {
    	setVisible(false);
    	lastPage.setVisible(true);
	}
    private void createButton() {
    	
    	  btnBack = new JButton("回到第一頁");
          btnNext = new JButton("下一步");
          btnBack.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  // 取得使用者填寫的資訊 
              		Back();
              }
          });
          
          btnNext.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  // 取得使用者填寫的資訊
                  String selectedDept = cboDept.getSelectedItem().toString();
                  String selectedTeam = cboTeam.getSelectedItem().toString();
                  sports = selectedTeam;
                  String studentID = txtStudentID.getText();

                  // 進行資訊驗證
                  if (studentID.length()!=9) {
                      JOptionPane.showMessageDialog(null, "請輸入學號9碼", "錯誤", JOptionPane.ERROR_MESSAGE);
                  } else {
                  	try {
                  	int idnum = Integer.parseInt(studentID);
                  	user =  new User(selectedDept,selectedTeam,studentID);
                    // 進入第三頁：ReservationPage
     
                  	Next();
                  	}
                  	catch(NumberFormatException exception) {
                  		 JOptionPane.showMessageDialog(null, "學號必須是數字", "錯誤", JOptionPane.ERROR_MESSAGE);
                  	}
                  }
          
              
              }
          });

    }
    private void createLayout() {
        JPanel hPanel = new JPanel();
        hPanel.add(new JLabel("請填寫以下資訊"));
        hPanel.setPreferredSize(new Dimension(500, 40));
        
        JPanel dPanel = new JPanel();
        dPanel.add(new JLabel("系所："));
        dPanel.add(cboDept);
        dPanel.setPreferredSize(new Dimension(500, 40));
        
        JPanel tPanel = new JPanel();
        tPanel.add(new JLabel("球隊："));
        tPanel.add(cboTeam);
        tPanel.setPreferredSize(new Dimension(500, 40));
        
        JPanel iPanel = new JPanel();
        iPanel.add(new JLabel("學號："));
        iPanel.add(txtStudentID);
        iPanel.setPreferredSize(new Dimension(500, 40));
        
        // 設置按鈕的佈局
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.add(btnBack);
        btnPanel.add(btnNext);
        
        JPanel allPanel = new JPanel();
        allPanel.add(hPanel);
        allPanel.add(dPanel);
        allPanel.add(tPanel);
        allPanel.add(iPanel);
        allPanel.add(btnPanel);
        
        // 設置整體的佈局
        setLayout(new BorderLayout(40, 40));
        getContentPane().add(allPanel, BorderLayout.CENTER); 
        setLocationRelativeTo(null);
    }

}
   
   
