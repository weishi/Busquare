/* Wei Shi, weishi, 15437, 2012/1/30 */
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;

import data.Route;

public class RouteDAO extends GenericDAO<Route> {
	public RouteDAO(String tableName, ConnectionPool connectionPool)
			throws DAOException {
		super(Route.class, tableName, connectionPool);
	}

	public void create(Route u) throws RollbackException {
		createAutoIncrement(u);
	}

	public Route[] getRoutes() throws RollbackException {
		return match();
	}

}
