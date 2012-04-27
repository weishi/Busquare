/* Wei Shi, weishi, 15437, 2012/1/30 */
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import data.FavRoute;

public class FavRouteDAO extends GenericDAO<FavRoute> {
	public FavRouteDAO(String tableName, ConnectionPool connectionPool)
			throws DAOException {
		super(FavRoute.class, tableName, connectionPool);
	}

	public void create(FavRoute u) throws RollbackException {
		createAutoIncrement(u);
	}

	public FavRoute[] readByUID(int uid) throws RollbackException {

		FavRoute[] fr = match(MatchArg.equals("uid", uid));
		return fr;
	}

	public boolean hasFav(int uid, int bid) throws RollbackException {
		FavRoute[] fr = readByUID(uid);
		if (fr == null) {
			return false;
		}
		for (FavRoute f : fr) {
			if (f.getBid() == bid) {
				return true;
			}
		}
		return false;
	}
}
