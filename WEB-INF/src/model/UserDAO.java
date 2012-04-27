/* Wei Shi, weishi, 15437, 2012/1/30 */
package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import data.User;

public class UserDAO extends GenericDAO<User> {
	public UserDAO(String tableName, ConnectionPool connectionPool)
			throws DAOException {
		super(User.class, tableName, connectionPool);
	}

	public void create(User u) throws RollbackException {
		createAutoIncrement(u);
	}

	public User readByUserName(String username) throws RollbackException {

		User[] users = match(MatchArg.equals("username", username));
		if (users == null || users.length == 0) {
			return null;
		}
		for (User u : users) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		return null;
	}

	public User[] getUsers() throws RollbackException {
		return match();
	}

}
