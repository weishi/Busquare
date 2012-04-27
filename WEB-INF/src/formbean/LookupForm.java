/* Wei Shi, weishi, 15437, 2012/3/18 */
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LookupForm extends FormBean {
	static int MAX_ACCU = 1000;
	private String action;
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

		if (!action.equals("Locate nearest busstop")) {
			errors.add("Invalid action");
			return errors;
		}

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
			errors.add("Location inaccurate. Must be within " + MAX_ACCU + " m");
			return errors;
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
