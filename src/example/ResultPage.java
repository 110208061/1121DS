import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.sql.*;

//這個 Page 展示「排球場」的分配結果
public class ResultPage extends JFrame implements NextBack {
	
	private JButton backbtn;
	private JLabel mon, tue, wed, thr, fri, selectType, timeInfo1, timeInfo2;
	private JComboBox<String> types = new JComboBox<String>();
	private ArrayList<JLabel> volleyballLabels = new ArrayList<JLabel>();
	private ArrayList<JLabel> basketballLabels = new ArrayList<JLabel>();
	private int[] volleyballTimeIds = new int[10];
	private int[] basketballTimeIds = new int[10];
	
	public ResultPage() {
		setTitle("結果查詢");
		setLayout(new FlowLayout());
		setSize(600, 300);
		
		createCombo();
		createLabel();
		createButton();
		createLayout();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}


	
	public void createLabel() {
		//設置固定的Labels
		mon = new JLabel("Mon.");
		tue = new JLabel("Tue.");
		wed = new JLabel("Web.");
		thr = new JLabel("Thu.");
		fri = new JLabel("Fri.");
		selectType = new JLabel("請選擇欲查詢的球場: ");
		selectType.setPreferredSize(new Dimension(130,30));
		
		timeInfo1 = new JLabel("早 : 08:30 - 12:00");
		timeInfo1.setPreferredSize(new Dimension(200,30));
		timeInfo2 = new JLabel("晚 : 18:30 - 21:30");
		timeInfo2.setPreferredSize(new Dimension(200,30));
		
		//設置會因分發結果變化的Labels，並準備兩種球類的TimeId[] 和 ArrayList<Label>
    	String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "110208061";
		String url = server + database + "?useSSL=false";
		String username = "110208061";
		String password = "d0v8b";
		
		//先把兩種球類，每一個時段的TimeId都設為100，Label都設為「尚無預約」
		for (int i=0; i<10; i++) {
			volleyballTimeIds[i] = 100;
			basketballTimeIds[i] = 100;
			volleyballLabels.add(new JLabel("尚無預約"));
			basketballLabels.add(new JLabel("尚無預約"));
		}

		//連接資料庫
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connectd");
			
			Statement stat = conn.createStatement();
			String query;
			boolean success;
			
		//查詢已登記的「系」和「時段編號」
			query = String.format("SELECT Department, TimeId FROM User");
			success = stat.execute(query);
			
		//把有登記的「系」和「時段編號」替代掉原本放的「尚無預約」和「100」（若該時段無人登記，仍然是尚無預約和100）
			if(success) {
				ResultSet result = stat.getResultSet();
				while(result.next()) {
					int Id = result.getInt("TimeId");
					String dept = result.getString("Department");
					
					for (int i=0; i<10; i++) {
						if (Id == i) {
							volleyballTimeIds[i] = Id;
							volleyballLabels.set(i,new JLabel(dept));
						}
					}
					for (int i=10; i<20; i++) {
						if (Id == i) {
							basketballTimeIds[i-10] = Id;
							basketballLabels.add(i-10 ,new JLabel(dept));
						}
					}	
				}
				

				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//如果使用者選到籃球場，就跳轉到BasketballResultPage
	public void createCombo() {
		types = new JComboBox<String>();
		types.addItem("排球場");
		types.addItem("籃球場");
		types.setSelectedItem(0);
		types.setPreferredSize(new Dimension(70,30));
		
		types.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				if (types.getSelectedItem().toString().equals("籃球場")) {
		               	BasketballResultPage bas = new BasketballResultPage();
		                bas.setVisible(true);
		                dispose();
        		}

			}
		});
	}
	
	

	public void createButton() {
		backbtn = new JButton("回到上一頁");
		backbtn.setPreferredSize(new Dimension(200,30));
		
		//按下「回到上一頁」會發生的事
		backbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Back();
			}
		});
		
	}
	
	
	
	public void createLayout() {
		setTitle("分發結果查詢");
		
		// infoPane : 設置上方的「時間說明」與「選擇球場類型」
		JPanel timePane = new JPanel(new GridLayout(2,1));
		timePane.setSize(200,100);
		timePane.add(timeInfo1);
		timePane.add(timeInfo2);
		
		JPanel choosePane  = new JPanel(new GridLayout(1,2));
		choosePane.setSize(200,100);
		choosePane.add(selectType);
		choosePane.add(types);
		
		JPanel infoPane = new JPanel(new FlowLayout());
		infoPane.setSize(500,100);
		infoPane.add(timePane);
		infoPane.add(choosePane);

		
		//設置中央的「星期標籤」和「分發結果」
			//「星期標籤」
		JPanel weekPane = new JPanel(new GridLayout(1,5));
		weekPane.setSize(500,100);
		weekPane.add(mon);
		weekPane.add(tue);
		weekPane.add(wed);
		weekPane.add(thr);
		weekPane.add(fri);
		
			//「分發結果」
		JPanel resultPane = new JPanel(new GridLayout(2,5));
		resultPane.setSize(600,500);

			//把 VolleyballLabell 內的標籤都放上來
		for (int i=0; i<10; i++) {
			resultPane.add(volleyballLabels.get(i));
		}
		
			// 放到同一個Pane
		JPanel middlePane = new JPanel(new GridLayout(3,1));
		middlePane.add(weekPane);
		middlePane.add(resultPane);
		middlePane.setSize(600,200);
		
		//按鈕Pane
		JPanel btnPane = new JPanel();
		btnPane.add(backbtn);
		btnPane.setSize(200,40);		
		
		//把整個頁面排版
		JPanel overallPane = new JPanel(new GridLayout(3,1));
		overallPane.setSize(800,300);
		overallPane.add(infoPane);
		overallPane.add(middlePane);
		overallPane.add(btnPane);
		
		add(overallPane);
        setLocationRelativeTo(null);
		
	}
	
	



	@Override
	public void Next() {
		
	}

	@Override
	public void Back() {
		dispose();
		HomePage home = new HomePage();
		home.setVisible(true);
	}

}