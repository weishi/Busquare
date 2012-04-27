/* Wei Shi, weishi, 15437, 2012/1/30 */
package model;

import java.util.ArrayList;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import data.Report;

public class ReportDAO extends GenericDAO<Report> {
	public ReportDAO(String tableName, ConnectionPool connectionPool)
			throws DAOException {
		super(Report.class, tableName, connectionPool);
	}

	public void create(Report u) throws RollbackException {
		createAutoIncrement(u);
	}

	public Report[] getReports() throws RollbackException {
		return match();
	}

	public Report[] readByUID(int uid) throws RollbackException {
		return match(MatchArg.equals("uid", uid));
	}

	public Report[] readByBID(int bid) throws RollbackException {
		return match(MatchArg.equals("bid", bid));
	}

	public Report[] readByDistance(int bid, float lat, float lng, float dist)
			throws RollbackException {
		Report[] rep = readByBID(bid);
		ArrayList<Report> near = new ArrayList<Report>();
		for (Report r : rep) {
			if (distFrom(r.getLatitude(), r.getLongitude(), lat, lng) <= dist) {
				near.add(r);
			}
		}
		if (near.size() == 0) {
			return new Report[0];
		}
		Report[] ret = new Report[near.size()];
		near.toArray(ret);
		return ret;
	}

	/*
	 * Credit:
	 * http://stackoverflow.com/questions/120283/working-with-latitude-longitude
	 * -values-in-java
	 */
	public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double sindLat = Math.sin(dLat / 2);
		double sindLon = Math.sin(dLng / 2);
		double a = Math.pow(sindLat, 2) + Math.pow(sindLon, 2) * Math.cos(lat1)
				* Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;
		System.out.println(dist+"1:"+lat1+","+lng1+" 2:"+lat2+","+lng2);
		return (float) dist;
	}
}
