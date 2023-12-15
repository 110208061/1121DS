
public class User {
	private String Dept;
	private String Team;
	private String ID;
	
	public User(String dep, String Team, String ID) {
		this.Dept = dep;
		this.Team = Team;
		this.ID = ID;
	}
	
	public String getDept() {
		return Dept;
	}
	public String getTeam() {
		return Team;
	}
	public String getID() {
		return ID;
	}

}
