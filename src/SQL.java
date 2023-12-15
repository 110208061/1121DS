import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
	
	
	//直接 return SQL 指令的 Method
	public String select(String searchItem,String itemName, String item,String table) {
		return String.format("SELECT %s FROM %s where %s = '%s'",searchItem,table,itemName,item);
	}
	
	public String select(String searchItem,String itemName, int item,String table) {
		return String.format("SELECT %s FROM %s where %s = %d",searchItem,table,itemName,item);
	}
	
	public String selectByTwo(String searchItem,String itemName1, int item1,String itemName2, String item2,String table) {
		return String.format("SELECT %s FROM %s where %s = %d AND %s = '%s'",searchItem,table,itemName1,item1,itemName2,item2);
	}
	
	public String selectByTwo(String searchItem,String itemName1, int item1,String itemName2, int item2,String table) {
		return String.format("SELECT %s FROM %s where %s = %d AND %s = %s",searchItem,table,itemName1,item1,itemName2,item2);
	}
	
	public String selectByTwoOrder(String searchItem,String itemName1, int item1,String itemName2, int item2,String orderName,String table) {
		return String.format("SELECT %s FROM %s where %s = %d AND %s = %s ORDER BY %s",searchItem,table,itemName1,item1,itemName2,item2,orderName);
	}
	
	public String selectByTwoOrder(String searchItem,String itemName1, int item1,String itemName2, String item2,String orderName,String table) {
		return String.format("SELECT %s FROM %s where %s = %d AND %s = '%s' ORDER BY %s",searchItem,table,itemName1,item1,itemName2,item2,orderName);
	}
	
	public String ifExist(String itemName, String item, String table) {
		return String.format("SELECT 1 FROM %s WHERE %s = '%s' LIMIT 1",table,itemName,item);
	}
	
	public String ifExist(String itemName, int item, String table) {
		return String.format("SELECT 1 FROM %s WHERE %s = %s LIMIT 1",table,itemName,item);
	}
	
	public String ifExistByTwo(String itemName1, int item1, String itemName2, String item2,String table) {
		return String.format("SELECT 1 FROM %s WHERE %s = %s AND %s = '%s' LIMIT 1",table,itemName1,item1,itemName2,item2);
	}
	
	public String insertUser(String name,String password,int id) {
		return String.format("INSERT INTO User (Name,ID,Password) VALUES ('%s',%d,'%s')",name,id,password);
	}
	
	public String insertSemester(String name, String beginDate, String endDate, String introduction,int id) {
		return String.format("INSERT INTO Semester(Name, BeginDate, EndDate, Introduction, UserID) VALUES ('%s','%s','%s','%s',%d)",name,beginDate,endDate,introduction,id);
	}
	
	public String updateSemester(String name, String beginDate, String endDate, String introduction,int semesterID) {
		return String.format("UPDATE Semester SET Name='%s', BeginDate='%s',EndDate='%s',Introduction='%s' WHERE SemesterID = %d",name,beginDate,endDate,introduction,semesterID);
	}
	
	public String insertSchedule(String name,String introduction, int beginTime, int endTime, String dayOfWeek, String location,int semesterid) {
		return String.format("INSERT INTO Schedule(Name, Introduction, SemesterID, DayOfWeek, Location, BeginTime, EndTime) VALUES ('%s','%s',%s,%s,'%s',%s,%s)",name,introduction,semesterid,dayOfWeek,location,beginTime,endTime);
	}
	
	public String updateSchedule(String name,String introduction, int beginTime, int endTime, String dayOfWeek, String location,int scheduleid) {
		return String.format("UPDATE Schedule SET Name='%s',Introduction='%s',DayOfWeek= %s ,Location='%s',BeginTime=%s,EndTime=%s WHERE ScheduleID = %s",name,introduction,dayOfWeek,location,beginTime,endTime,scheduleid);
	}
	
	public String insertTask(String name,String introduction, String beginDate, String endDate, String cTime, String bTime,String location,int scheduleID, int semesterID,int timer) {
		return String.format("INSERT INTO Task(Name, Introduction, ScheduleID, BeginDate, EndDate, Timer, isFinished, ConcentrateTime, BreakTime, SemesterID, Location) VALUES('%s','%s',%s,'%s','%s',%s,0,'%s','%s',%s,'%s')", name,introduction,scheduleID,beginDate,endDate,timer,cTime,bTime,semesterID,location);
	}
	
	public String updateTask(String name,String introduction, String beginDate, String endDate, String cTime, String bTime,String location,int scheduleID, int taskID,int timer) {
		return String.format("UPDATE Task SET Name='%s',Introduction='%s',ScheduleID='%s',BeginDate='%s',EndDate='%s',Timer=%s,isFinished=0,ConcentrateTime='%s',BreakTime='%s',Location='%s' WHERE TaskID=%s", name,introduction,scheduleID,beginDate,endDate,timer,cTime,bTime,location,taskID);
	}
	
	public String updateTaskFinished(int taskID) {
		return String.format("UPDATE Task SET isFinished=1 WHERE TaskID=%s", taskID);
	}
	
	public String delete(String itemName,int item,String table) {
		return String.format("DELETE FROM %s WHERE %s = %s",table,itemName,item);
	}
	
	
	
	//在parameter 輸入要執行的 Query，即會回傳 ResultSet。
	//可與上面的那些 method搭配使用，也可以自己寫Query
	public static ResultSet executeQuery(String Query) {
		
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "110208061";
		String url = server + database + "?useSSL=false";
		String username = "110208061";
		String password = "d0v8b";
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connectd"); 
							
			Statement stat = conn.createStatement();
			boolean success;

			success = stat.execute(Query);
			if(success) {
						
				ResultSet result = stat.getResultSet();
				return result;
				/* 得到 ResultSet 之後，可以開始處理資料了！
				
				ResultSetMetaData metaData = result.getMetaData();
				int columnCount = metaData.getColumnCount();
						
				while(result.next()) {
					
					看你要對結果做什麼
					
				}
				*/				
								
			}
							
		} catch (SQLException v) {
					v.printStackTrace();
		}
		
		return null;
	}
	
		
	//parameter 輸入要執行的 Command，即會執行 （若要進行Select，不要用這個Method
	//這個method 只用於 update, delete.... 等功能
	
	public void executeCommand(String Command) {
		
		String server = "jdbc:mysql://140.119.19.73:3315/";
		String database = "110208061";
		String url = server + database + "?useSSL=false";
		String username = "110208061";
		String password = "d0v8b";
		
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			System.out.println("DB Connectd"); 
							
			Statement stat = conn.createStatement();
			boolean success;

			success = stat.execute(Command);
			if(success) {
				System.out.println("Success");
			}
							
		} catch (SQLException v) {
					v.printStackTrace();
		}
	}
	
}