package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FavRouteDAO;
import model.Model;
import model.ReportDAO;
import model.RouteDAO;
import model.UserDAO;

import org.genericdao.RollbackException;

import data.FavRoute;
import data.FeedEntry;
import data.Report;
import data.Route;
import data.User;

public class HomeAction extends Action {
	private FavRouteDAO favRouteDAO;
	private RouteDAO routeDAO;
	private ReportDAO reportDAO;
	private UserDAO userDAO;

	public HomeAction(Model model) {
		userDAO = model.getUserDAO();
		favRouteDAO = model.getFavRouteDAO();
		routeDAO = model.getRouteDAO();
		reportDAO = model.getReportDAO();

	}

	public String getName() {
		return "home.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		User curUser = (User) session.getAttribute("user");
		if (curUser == null) {
			return "welcome.jsp";
		}
		try {
			ArrayList<FeedEntry> feed = new ArrayList<FeedEntry>();
			ArrayList<FeedEntry> mine = new ArrayList<FeedEntry>();
			System.out.println("in home");

			/* FavRoute list */
			FavRoute[] fr = favRouteDAO.readByUID(curUser.getId());
			ArrayList<Route> froute = new ArrayList<Route>();
			for (FavRoute r : fr) {
				froute.add(routeDAO.read(r.getBid()));
			}
			request.setAttribute("favRoutes", froute);
			System.out.println("done fav");
			/* My recent reports */
			Report[] myrep = reportDAO.readByUID(curUser.getId());
			for (Report rep : myrep) {
				mine.add(new FeedEntry(userDAO.read(curUser.getId()), rep,
						routeDAO.read(rep.getBid())));
			}
			// Sort by time
			Collections.sort(mine, new Comparator<FeedEntry>() {
				public int compare(FeedEntry item1, FeedEntry item2) {
					return item2.report.getTimestamp().compareTo(
							item1.report.getTimestamp());
				}
			});
			request.setAttribute("mine", mine);
			System.out.println("done my post");
			/* Main Feed */
			if (fr == null || fr.length == 0) {
				/* No FavRoutes. Show most recent 50 posts */
				Report[] rs = reportDAO.getReports();
				for (int i = 0; i < rs.length && i < 50; i++) {
					feed.add(new FeedEntry(userDAO.read(rs[i].getUid()),
							rs[i], routeDAO.read(rs[i].getBid())));
				}

			} else {
				/* Has FavRoutes. Show all fav posts */
				for (Route r : froute) {
					Report[] routeReport = reportDAO.readByBID(r.getId());
					for (Report rep : routeReport) {
						feed.add(new FeedEntry(userDAO.read(rep.getUid()), rep,
								routeDAO.read(rep.getBid())));
					}
				}
			}
			// Sort by time
			Collections.sort(feed, new Comparator<FeedEntry>() {
				public int compare(FeedEntry item1, FeedEntry item2) {
					return item2.report.getTimestamp().compareTo(
							item1.report.getTimestamp());
				}
			});
			request.setAttribute("feed", feed);
			System.out.println("all done");
			return ("home.jsp");
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
