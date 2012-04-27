package data;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class RouteStop {
	private int id;
	private int sid;
	private int bid;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
