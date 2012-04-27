package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.ReportDAO;
import model.RouteDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import data.Report;
import data.User;
import formbean.LocationForm;

public class ReportAction extends Action {
	private FormBeanFactory<LocationForm> formBeanFactory = FormBeanFactory
			.getInstance(LocationForm.class);

	private ReportDAO reportDAO;
	private RouteDAO routeDAO;
	private static String errPage = "report.jsp";

	public ReportAction(Model model) {
		reportDAO = model.getReportDAO();
		routeDAO = model.getRouteDAO();
	}

	public String getName() {
		return "report.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User curUser = (User) session.getAttribute("user");
		if (curUser == null) {
			return "login.jsp";
		}

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			/* Prepare route list */
			request.setAttribute("routeList", routeDAO.getRoutes());

			LocationForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return "report.jsp";
			}
			System.out.println("here");
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return errPage;
			}
			System.out.println("there");
			if (form.getAction().equals("report")) {
				/* Must report on an existing route */
				if (null != routeDAO.read(Integer.parseInt(form.getRouteID()))) {
					Report r = new Report();
					r.setTimestamp(new Date());
					r.setUid(curUser.getId());
					r.setFlagUser(-1);
					r.setComment(form.getComment());
					r.setLatitude(Float.parseFloat(form.getGeolat()));
					r.setLongitude(Float.parseFloat(form.getGeolong()));
					r.setBid(Integer.parseInt(form.getRouteID()));
					reportDAO.create(r);
				} else {
					errors.add("Must report on an existing route.");
					return errPage;
				}
			} else if (form.getAction().equals("remove")) {
				Report target = reportDAO.read(Integer.parseInt(form
						.getReportID()));
				if (target != null) {
					if (target.getUid() == curUser.getId()) {
						reportDAO.delete(target.getId());
					} else {
						errors.add("Cannot remove a report that does not belong to you. Try Flag:-)");
						return "error.jsp";
					}
				} else {
					errors.add("Report does not exist");
					return "error.jsp";
				}
				return "home.do";
			} else if (form.getAction().equals("flag")) {
				Report target = reportDAO.read(Integer.parseInt(form
						.getReportID()));
				if (target != null) {
					if (target.getUid() == curUser.getId()) {
						errors.add("Cannot flag your own post");
						return "error.jsp";
					}
					if (target.getFlagUser() == -1) {
						target.setFlagUser(curUser.getId());
						reportDAO.update(target);
					} else {
						if (target.getFlagUser() == curUser.getId()) {
							errors.add("Already flagged this post.");
							return "error.jsp";
						} else {
							reportDAO.delete(target.getId());
						}
					}
				} else {
					errors.add("Report does not exist");
					return "error.jsp";
				}
				return "home.do";
			} else {
				// Should not reach here
				return "error.jsp";
			}
			return ("home.do");
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
