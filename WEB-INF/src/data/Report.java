package data;

import java.util.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class Report {
	private int id;
	private int uid;
	private int bid;
	private Date timestamp;
	private String comment;
	private float longitude;
	private float latitude;
	private int flagUser;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getFlagUser() {
		return flagUser;
	}

	public void setFlagUser(int flagUser) {
		this.flagUser = flagUser;
	}

}
