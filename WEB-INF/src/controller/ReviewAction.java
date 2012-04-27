package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FavRouteDAO;
import model.Model;
import model.ReportDAO;
import model.RouteDAO;
import model.RouteStopDAO;
import model.StopDAO;
import model.UserDAO;

import org.genericdao.RollbackException;

import data.FeedEntry;
import data.Report;
import data.RouteStop;
import data.Stop;
import data.User;

public class ReviewAction extends Action {

	private ReportDAO reportDAO;
	private RouteDAO routeDAO;
	private RouteStopDAO routeStopDAO;
	private StopDAO stopDAO;
	private UserDAO userDAO;
	private FavRouteDAO favRouteDAO;

	public ReviewAction(Model model) {
		reportDAO = model.getReportDAO();
		routeDAO = model.getRouteDAO();
		routeStopDAO = model.getRouteStopDAO();
		stopDAO = model.getStopDAO();
		userDAO = model.getUserDAO();
		favRouteDAO = model.getFavRouteDAO();
	}

	public String getName() {
		return "review.do";
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
			/* Prepare cur report */
			if (!request.getParameterMap().containsKey("reportID")) {
				errors.add("Invalid parameters.");
				return "error.jsp";
			}
			String reportIDStr = request.getParameter("reportID");
			int reportID = Integer.parseInt(reportIDStr);
			Report curRep = reportDAO.read(reportID);
			if (curRep == null) {
				errors.add("Report does not exist");
				return "error.jsp";
			}
			request.setAttribute("curRep",
					new FeedEntry(userDAO.read(curRep.getUid()), curRep,
							routeDAO.read(curRep.getBid())));
			request.setAttribute("hasFlagged",
					curRep.getFlagUser() == curUser.getId() ? 1 : 0);
			request.setAttribute("hasBookmarked", favRouteDAO.hasFav(
					curUser.getId(), curRep.getBid()) ? 1 : 0);
			/* Prepare all bus stop for cur report route */
			List<Stop> stopList = new ArrayList<Stop>();
			request.setAttribute("stopList", stopList);
			RouteStop[] rsList = routeStopDAO.readByBID(curRep.getBid());
			for (RouteStop rs : rsList) {
				stopList.add(stopDAO.read(rs.getSid()));
			}
			return ("review.jsp");
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (NumberFormatException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
