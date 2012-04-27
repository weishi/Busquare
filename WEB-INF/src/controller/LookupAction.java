package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import model.Model;
import model.ReportDAO;
import model.RouteDAO;
import model.RouteStopDAO;
import model.StopDAO;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import data.FeedEntry;
import data.Report;
import data.Route;
import data.RouteStop;
import data.Stop;
import formbean.LookupForm;

public class LookupAction extends Action {

	private FormBeanFactory<LookupForm> formBeanFactory = FormBeanFactory
			.getInstance(LookupForm.class);

	private ReportDAO reportDAO;
	private RouteDAO routeDAO;
	private RouteStopDAO routeStopDAO;
	private StopDAO stopDAO;
	private UserDAO userDAO;
	private static float MAX_DIST = 50000;

	public LookupAction(Model model) {
		reportDAO = model.getReportDAO();
		routeDAO = model.getRouteDAO();
		routeStopDAO = model.getRouteStopDAO();
		stopDAO = model.getStopDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "lookup.do";
	}

	public String perform(HttpServletRequest request) {
		int curStop=0;
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		List<FeedEntry> nearReports = new ArrayList<FeedEntry>();
		request.setAttribute("nearReports", nearReports);
		request.setAttribute("curStop", curStop);
		try {
			/* Prepare route list */
			String routeIDStr = request.getParameter("routeID");
			int routeID = 1;
			if (routeIDStr != null) {
				routeID = Integer.parseInt(routeIDStr);
			}
			request.setAttribute("routeID", routeID);
			List<Stop> stopList = new ArrayList<Stop>();
			request.setAttribute("stopList", stopList);
			Route[] routeList = routeDAO.getRoutes();
			request.setAttribute("routeList", routeList);
			System.out.println(routeList.length);
			if (routeList != null && routeList.length > 0) {
				RouteStop[] rsList = routeStopDAO.readByBID(routeID);
				for (RouteStop rs : rsList) {
					stopList.add(stopDAO.read(rs.getSid()));
				}
			}
			/* Generate Report List */
			// Case 1: from bus stop link
			if (request.getParameterMap().containsKey("routeID")
					&& request.getParameterMap().containsKey("stopID")) {
				int stopID = Integer.parseInt(request.getParameter("stopID"));
				Stop s = stopDAO.read(stopID);
				Report[] near = reportDAO.readByDistance(routeID,
						s.getLatitude(), s.getLongitude(), MAX_DIST);
				for (Report r : near) {
					nearReports.add(new FeedEntry(userDAO.read(r.getUid()), r,
							routeDAO.read(r.getBid())));
				}
				curStop=stopID;
			} else if (request.getParameterMap().containsKey("routeID")
					&& request.getParameterMap().size() == 1) {

			} else {
				// Case 2: locate nearest stop
				LookupForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				if (!form.isPresent()) {
					return "error.jsp";
				}
				RouteStop[] rsList = routeStopDAO.readByBID(routeID);
				float minDist = Float.MAX_VALUE;
				int minStopID = 1;
				for (RouteStop rs : rsList) {
					Stop s = stopDAO.read(rs.getSid());
					float dist = ReportDAO.distFrom(s.getLatitude(),
							s.getLongitude(),
							Float.parseFloat(form.getGeolat()),
							Float.parseFloat(form.getGeolong()));
					if (minDist > dist) {
						minDist = dist;
						minStopID = s.getId();
					}
				}
				Stop s = stopDAO.read(minStopID);
				curStop=s.getId();
				Report[] near = reportDAO.readByDistance(routeID,
						s.getLatitude(), s.getLongitude(), MAX_DIST);
				for (Report r : near) {
					nearReports.add(new FeedEntry(userDAO.read(r.getUid()), r,
							routeDAO.read(r.getBid())));
				}
			}
			request.setAttribute("curStop", curStop);
			// Sort by time
			Collections.sort(nearReports, new Comparator<FeedEntry>() {
				public int compare(FeedEntry item1, FeedEntry item2) {
					return item2.report.getTimestamp().compareTo(
							item1.report.getTimestamp());
				}
			});
			return ("lookup.jsp");
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
