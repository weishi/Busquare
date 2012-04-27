package data;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class Route {
	private int id;
	private String routeName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String busName) {
		this.routeName = busName;
	}

}
