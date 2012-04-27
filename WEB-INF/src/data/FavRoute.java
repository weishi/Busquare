package data;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class FavRoute {
	private int id;
	private int uid;
	private int bid;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
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
