/* Wei Shi, weishi, 15437, 2012/3/18 */
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LocationForm extends FormBean {
	static int MAX_ACCU = 1000;
	private String action;
	private String reportID;
	private String comment;
	private String geolong;
	private String geolat;
	private String geoaccu;
	private String routeID;

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}

	public String getReportID() {
		return reportID;
	}

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getGeolong() {
		return geolong;
	}

	public void setGeolong(String geolong) {
		this.geolong = geolong;
	}

	public String getGeolat() {
		return geolat;
	}

	public void setGeolat(String geolat) {
		this.geolat = geolat;
	}

	public String getGeoaccu() {
		return geoaccu;
	}

	public void setGeoaccu(String geoaccu) {
		this.geoaccu = geoaccu;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (action == null) {
			errors.add("Action is required");
			return errors;
		}

		if (!action.equals("report") && !action.equals("remove")
				&& !action.equals("flag")) {
			errors.add("Invalid action");
			return errors;
		}
		if (action.equals("remove") || action.equals("flag")) {
			if (reportID == null || reportID.length() == 0) {
				errors.add("Missing report id");
				return errors;
			}
			if (reportID.matches(".*[<>\"].*")) {
				errors.add("ReportID may not contain angle brackets or quotes");
			}
			if (!checkInt(reportID, errors)) {
				return errors;
			}
		} else {
			if (routeID == null || routeID.length() == 0) {
				errors.add("Missing bus route");
				return errors;
			}
			if (!checkInt(routeID, errors)) {
				return errors;
			}

			if (!checkCoordinate(geolong, errors)) {
				return errors;
			}
			if (!checkCoordinate(geolat, errors)) {
				return errors;
			}
			if (!checkCoordinate(geoaccu, errors)) {
				return errors;
			}
			/* Reject inaccurate report */
			if (Float.parseFloat(geoaccu) > MAX_ACCU) {
				errors.add("Location inaccurate. Must be within " + MAX_ACCU
						+ " m");
				return errors;
			}

			if (comment != null && comment.matches(".*[<>\"].*")) {
				errors.add("Comment may not contain angle brackets or quotes");
				return errors;
			}
			if (comment.length() > 140) {
				errors.add("Comment must be less than 140 chars");
				return errors;
			}

		}

		return errors;
	}

	private boolean checkCoordinate(String coor, List<String> errors) {
		if (coor == null || coor.length() == 0) {
			errors.add("Missing coordinates");
			return false;
		}
		if (coor.matches(".*[<>\"].*")) {
			errors.add("Coordinate may not contain angle brackets or quotes");
			return false;
		}
		try {
			Float.parseFloat(coor);
		} catch (NumberFormatException e) {
			errors.add("Invalid coordinates");
			return false;
		}
		return true;
	}

	private boolean checkInt(String str, List<String> errors) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			errors.add("Invalid input. Must be integer.");
			return false;
		}
		return true;
	}
}
