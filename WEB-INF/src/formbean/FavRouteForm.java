package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class FavRouteForm extends FormBean {
	private String action;
	private String routeID;

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
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
		if (!action.equals("bookmark") && !action.equals("remove")) {
			errors.add("Invalid action");
			return errors;
		}
		if (routeID == null || routeID.length() == 0) {
			errors.add("Missing route id");
			return errors;
		}
		if (routeID.matches(".*[<>\"].*")) {
			errors.add("RouteID may not contain angle brackets or quotes");
		}
		if (!checkInt(routeID, errors)) {
			return errors;
		}

		return errors;
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
