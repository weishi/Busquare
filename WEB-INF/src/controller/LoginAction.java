/* Wei Shi, weishi, 15437, 2012/3/18 */
package controller;

import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import data.User;
import formbean.LoginForm;

public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory
			.getInstance(LoginForm.class);

	private UserDAO userDAO;

	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "login.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = null;
		;
		// If user is already logged in, redirect to home.do
		if (session.getAttribute("user") != null) {
			return "home.do";
		}

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			LoginForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (!form.isPresent()) {
				return "login.jsp";
			}

			// validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "login.jsp";
			}
			if (form.getAction().equals("Sign in")) {
				user = userDAO.readByUserName(form.getUsername());
				if (user == null) {
					errors.add("User not found");
					return "login.jsp";
				}

				if (!user.samePassword(form.getPassword())) {
					errors.add("Incorrect password");
					return "login.jsp";
				}
			} else if (form.getAction().equals("Sign up")) {
				user = userDAO.readByUserName(form.getUsername());
				if (user != null) {
					errors.add("A user with this name is already registered");
					return "login.jsp";
				}
				user = new User();
				user.setUsername(form.getUsername());
				user.setPassword(form.getPassword());
				userDAO.create(user);

			} else {
				return "login.jsp";
			}
			/* Good to go */
			session.setAttribute("user", user);
			return "home.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
