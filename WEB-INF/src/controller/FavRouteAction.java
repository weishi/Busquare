package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FavRouteDAO;
import model.Model;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import data.FavRoute;
import data.User;
import formbean.FavRouteForm;

public class FavRouteAction extends Action {
	private FormBeanFactory<FavRouteForm> formBeanFactory = FormBeanFactory
			.getInstance(FavRouteForm.class);

	private FavRouteDAO favRouteDAO;

	public FavRouteAction(Model model) {
		favRouteDAO = model.getFavRouteDAO();
	}

	public String getName() {
		return "favRoute.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		User curUser = (User) session.getAttribute("user");
		if (curUser == null) {
			return "login.jsp";
		}
		try {
			int routeID;
			FavRouteForm form = formBeanFactory.create(request);

			// validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "error.jsp";
			}
			routeID = Integer.parseInt(form.getRouteID());
			if (form.getAction().equals("remove")) {
				if(favRouteDAO.deleteByPair(curUser.getId(),routeID)==false){
					errors.add("Route not bookmarked.");
					return "error.jsp";
				}
			} else {
				FavRoute fav = new FavRoute();
				fav.setBid(routeID);
				fav.setUid(curUser.getId());
				if (favRouteDAO.hasFav(fav.getUid(), fav.getBid())) {
					errors.add("Route already bookmarked.");
					return "error.jsp";
				}
				favRouteDAO.createAutoIncrement(fav);
			}
			return "home.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (NumberFormatException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
