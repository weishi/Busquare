/* Wei Shi, weishi, 15437, 2012/1/30 */
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import data.RouteStop;


public class RouteStopDAO extends GenericDAO<RouteStop> {
	public RouteStopDAO(String tableName, ConnectionPool connectionPool)
			throws DAOException {
		super(RouteStop.class, tableName, connectionPool);
	}

	public void create(RouteStop u) throws RollbackException {
		createAutoIncrement(u);
	}

	public RouteStop[] readByBID(int bid) throws RollbackException {
		return match(MatchArg.equals("bid", bid));
	}

}
