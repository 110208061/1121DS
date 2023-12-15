import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.sql.*;


public class VolleyballReservationPage extends JFrame implements NextBack{
	private JPanel infoPanel, introPanel, choosePanel, btnPanel, datePanel ;
	private JButton[] btns = new JButton[10];
	private JButton[] chooseBtns = new JButton[2];
	private JButton next, back, reset;
	private JLabel info1, info2, info3, mon, tue, wed, thr, fri, intro1, intro2;
	private int count = 0;
	private int StringCount = 0;
	private int[] dates = new int[2];
	
	
	private User user;
	
	public VolleyballReservationPage(User user) {
		this.user = user;
		
		setTitle("登記時段");
		setLayout(new FlowLayout());
		setSize(900, 550);
		
		createLabel();
		createBtns();
		Next();
		Back();
		createResetBtn();
		createPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		
		
		
		
	}
	
	public void createLabel() {
			
		info1 = new JLabel("所屬科系： " + user.getDept());
		info1.setPreferredSize(new Dimension(80, 30));
		info2 = new JLabel("所屬隊伍： " + user.getTeam());
		info2.setPreferredSize(new Dimension(80, 30));
		info3 = new JLabel("學號： " + user.getID());
		info3.setPreferredSize(new Dimension(80, 30));

	}
	
	public void createBtns() {
		//說明規則
		intro1 = new JLabel("請確認以上填寫資訊是否正確，並選擇'最多2個'想要的時段:");
		intro1.setForeground(Color.RED);
		intro1.setPreferredSize(new Dimension(600, 50));
		
		intro2 = new JLabel("你正在填寫 6/19 - 6/23 的場地申請");
		intro2.setForeground(Color.RED);
		intro2.setPreferredSize(new Dimension(600, 50));
		
		
		//建立button
		for(int i = 0; i < 10; i++) {
			JButton btn = new JButton();
			//設定button文字
			if(i < 5) {
				btn.setText(" 早上(0830-1200)");
			}else if(i >= 5) {
				btn.setText(" 晚上(1830-2130)");
			}
			
			
			//ActionListener
			class ButtonListener implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					
					if(count >= 2){
						JOptionPane.showMessageDialog(null, "最多只能選兩個時段！", "提示", JOptionPane.ERROR_MESSAGE);
					}
					
					if(count < 2) {
						chooseBtns[count] = btn;
						btn.setEnabled(false);
						count++;
						int num = 0;
						for(int a = 0; a<10; a++) {
							if(btns[a].equals(btn)) {
								num = a;
							}
						}
						
						if(num%5 == 0) {
							if(num<5) {
								dates[StringCount] = 0;
								StringCount++;
							}else {
								dates[StringCount] = 5;
								StringCount++;
							}
							
							
						}else if(num%5 == 1) {
							if(num<5) {
								dates[StringCount] = 1;
								StringCount++;
							}else {
								dates[StringCount] = 6;
								StringCount++;
							}
							
						}else if(num%5 == 2) {
							if(num<5) {
								dates[StringCount] = 2;
								StringCount++;
							}else {
								dates[StringCount] = 7;
								StringCount++;
							}
							
						}else if(num%5 == 3) {
							if(num<5) {
								dates[StringCount] = 3;
								StringCount++;
							}else {
								dates[StringCount] = 8;
								StringCount++;
							}
							
						}else {
							if(num<5) {
								dates[StringCount] = 4;
								StringCount++;
							}else {
								dates[StringCount] = 9;
								StringCount++;
							}
							
						}
					}
					
						
					
				}
			}
			
			btn.setSize(50, 50);
			btns[i] = btn;
			btn.addActionListener(new ButtonListener());
			
		}
		checkUsed();
		
		
		
		mon = new JLabel("Mon.");
		mon.setPreferredSize(new Dimension(50, 20));
		tue = new JLabel("Tue.");
		tue.setPreferredSize(new Dimension(50, 20));
		wed = new JLabel("Wed.");
		wed.setPreferredSize(new Dimension(50, 20));
		thr = new JLabel("Thr.");
		thr.setPreferredSize(new Dimension(50, 20));
		fri = new JLabel("Fri.");
		fri.setPreferredSize(new Dimension(50, 20));
		
	}
	
	public void checkUsed() {
	//Database 若有該時段已經有隊伍了，就顯示不能按
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "110208061";
		String url = server + database + "?useSSL=false";
		String username = "110208061";
		String password = "d0v8b";
						
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connectd"); 
							
			Statement stat = conn.createStatement();
			String query;
			boolean success;
							
			query = "SELECT TimeId FROM User";
			success = stat.execute(query);
			if(success) {
						
				ResultSet result = stat.getResultSet();
				ResultSetMetaData metaData = result.getMetaData();
				int columnCount = metaData.getColumnCount();
						
				while(result.next()) {
					String timeID = result.getString(columnCount);
							
					for(int i=0; i<10; i++) {
						String n = i + "";
						if(timeID.equals(n)) {
							btns[i].setEnabled(false);
						}
					}
				}
								
								
			}
							
		} catch (SQLException v) {
					v.printStackTrace();
		}
	}
	
	
	



	@Override
	public void Next() {
		next = new JButton("送出申請");
		next.setSize(50, 20);
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "場地租借申請已送出", "成功", JOptionPane.INFORMATION_MESSAGE);
				
				//連接到DATA BASE
				String server = "jdbc:mysql://140.119.19.73:3315/";
				String database = "110208061";
				String url = server + database + "?useSSL=false";
				String username = "110208061";
				String password = "d0v8b";
						
				try (Connection conn = DriverManager.getConnection(url, username, password)) {
					System.out.println("2. DB Connectd"); 
					
					Statement stat = conn.createStatement();
					String query;
					boolean sucess;
					
					//確認之前是否有填過
					query = "SELECT Department, Team FROM User";
					sucess = stat.execute(query);
					if(sucess) {
						ResultSet result = stat.getResultSet();
						ResultSetMetaData metaData = result.getMetaData();
						int columnCount = metaData.getColumnCount();
						String teamInfo = "";
						String userDept = user.getDept();
						String userTeam = user.getTeam();
						String deleQuery;
						
						while (result.next()) {
							for (int i = 1; i <= columnCount; i++) {
								 teamInfo += result.getString(i);
								 //Q:teamInfo不會包含學號和時段嗎
							}
							
							//刪除已填過的隊伍
							if(teamInfo.equals((userDept + userTeam))) {
								deleQuery = String.format("DELETE FROM User WHERE Department = '%s' AND Team = '%s'", userDept, userTeam);
								stat.execute(deleQuery);
								teamInfo = "";
								break;
							}else {
								teamInfo = "";
							}
						}
					}
					
					//新增隊伍
					String addQuery;
					int c = count-2;
					for(int i = 1; i<=count; i++) {
						int timeID = dates[c];
						c++;
						
						addQuery = String.format("INSERT INTO User (UserId, Department, Team, TimeId) VALUES (%s, '%s', '%s', %s)", user.getID(), user.getDept(), user.getTeam(), timeID);
						stat.execute(addQuery);
						
					}


							
				} catch (SQLException a) {
					a.printStackTrace();
				}
				
				dispose();
				HomePage home = new HomePage();
				home.setVisible(true);
			}
		});

		
	}

	@Override
	public void Back() {
		back = new JButton("回到填寫資訊頁面");
		back.setSize(50, 20);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				InformationPage infoPage = new InformationPage();
				infoPage.setVisible(true);
			}
		});

	}
	
	public void createResetBtn() {
		reset = new JButton("重新選擇");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i<10; i++) {
					btns[i].setEnabled(true);
				}
				StringCount = 0;
				count = 0;
				for(int a = 0; a<2; a++) {
					dates = new int[2];
				}
				
				checkUsed();
			}
		});
		
	}
	
	public void createPanel() {
		//返回&下一步按鈕
		btnPanel = new JPanel();
		btnPanel.setSize(250, 100);
		btnPanel.add(reset);
		btnPanel.add(back);
		btnPanel.add(next);
		
		//基本資訊
		infoPanel = new JPanel(new GridLayout(1, 3));
		infoPanel.setSize(250, 30);
		infoPanel.add(info1);
		infoPanel.add(info2);
		infoPanel.add(info3);
		
		//規則簡介
		introPanel = new JPanel(new GridLayout(2, 1));
		introPanel.setSize(200, 30);
		introPanel.add(intro1);
		introPanel.add(intro2);
		
		JPanel infoIntro = new JPanel(new GridLayout(2, 1));
		infoIntro.setSize(250, 50);
		infoIntro.add(infoPanel);
		infoIntro.add(introPanel);
		
		//星期幾
		datePanel = new JPanel(new GridLayout(1, 5));
		datePanel.setSize(250, 50);
		datePanel.add(mon);
		datePanel.add(tue);
		datePanel.add(wed);
		datePanel.add(thr);
		datePanel.add(fri);
		
		//選日期按鈕
		choosePanel = new JPanel(new GridLayout(2, 5));
		choosePanel.setSize(250, 100);
		for(int i = 0; i<10; i++) {choosePanel.add(btns[i]);}
		
		JPanel chooseDate = new JPanel(new GridLayout(3, 1));
		chooseDate.setSize(250, 150);
		chooseDate.add(datePanel);
		chooseDate.add(choosePanel);
		
		
		JPanel mix2 = new JPanel(new GridLayout(3, 1));
		mix2.setSize(250, 200);
		mix2.add(infoIntro);
		mix2.add(chooseDate);
		mix2.add(btnPanel);
		
		add(mix2);
        setLocationRelativeTo(null);
	}
	

}
