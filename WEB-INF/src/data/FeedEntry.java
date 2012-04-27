package data;

public class FeedEntry {
	public User user;
	public Report report;
	public Route route;

	public FeedEntry(User user, Report report, Route route) {
		super();
		this.user = user;
		this.report = report;
		this.route = route;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
}
