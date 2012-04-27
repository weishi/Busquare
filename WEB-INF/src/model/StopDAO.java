/* Wei Shi, weishi, 15437, 2012/1/30 */
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;

import data.Stop;

public class StopDAO extends GenericDAO<Stop> {
	public StopDAO(String tableName, ConnectionPool connectionPool)
			throws DAOException {
		super(Stop.class, tableName, connectionPool);
	}

	public void create(Stop u) throws RollbackException {
		createAutoIncrement(u);
	}
}
