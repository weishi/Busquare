/* Wei Shi, weishi, 15437, 2012/3/18 */
package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private UserDAO userDAO;
	private FavRouteDAO favRouteDAO;
	private RouteDAO routeDAO;
	private RouteStopDAO routeStopDAO;
	private ReportDAO reportDAO;
	private StopDAO stopDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public FavRouteDAO getFavRouteDAO() {
		return favRouteDAO;
	}

	public RouteDAO getRouteDAO() {
		return routeDAO;
	}

	public ReportDAO getReportDAO() {
		return reportDAO;
	}

	public RouteStopDAO getRouteStopDAO() {
		return routeStopDAO;
	}

	public StopDAO getStopDAO() {
		return stopDAO;
	}

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL = config.getInitParameter("jdbcURL");
			String tablePrefix = config.getInitParameter("tablePrefix");

			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);

			userDAO = new UserDAO(tablePrefix + "user", pool);
			favRouteDAO = new FavRouteDAO(tablePrefix + "favRoute", pool);
			routeDAO = new RouteDAO(tablePrefix + "route", pool);
			reportDAO = new ReportDAO(tablePrefix + "report", pool);
			routeStopDAO = new RouteStopDAO(tablePrefix + "routeStop", pool);
			stopDAO = new StopDAO(tablePrefix + "stop", pool);

		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

}
