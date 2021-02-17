package controllers;

import business.User;
import data.UserDB;
import java.io.IOException;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dev
 */
public class UserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String url = "";

        if (requestURI.endsWith("/newAccount")) {
            url = add(request, url);
        } else if (requestURI.endsWith("/login")) {
            url = login(request, url);
        } else if (requestURI.endsWith("/logout")) {
            url = logout(request, url);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles request to make a new account.
     *
     * @param request servlet request to add attributes to
     * @param url current URL
     * @return updated URL to redirect user
     */
    protected String add(HttpServletRequest request, String url) {
        url = "/new-account.jsp";
        ServletContext sc = getServletContext();

        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        if (action.equals("join")) {
            url = "/new-account.jsp";
        } else if (action.equals("add")) {
            String firstName = request.getParameter("firstName");
            String lastName  = request.getParameter("lastName");
            String email     = request.getParameter("email");
            String username  = request.getParameter("username");
            String password  = request.getParameter("password");

            String filepath = sc.getRealPath("/WEB-INF/users.txt");
            User user = new User(firstName, lastName, email, username, password);

            // input validation
            StringJoiner message = new StringJoiner("<br>\n*", "\n*", "<br>\n");
            boolean isValid = true;

            if (email == null   || username == null   || password == null ||
                email.isEmpty() || username.isEmpty() || password.isEmpty())
            {
                message.add("Please fill out all required text boxes.");
                isValid = false;
            } else {
                if (firstName.length() > 64) {
                    message.add("First name must not exceed 64 characters.");
                    isValid = false;
                }

                if (lastName.length() > 64) {
                    message.add("Last name must not exceed 64 characters.");
                    isValid = false;
                }

                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    message.add("Email is not correctly formatted.");
                    isValid = false;
                }

                if (username.length() < 6) {
                    message.add("Username must not be less than 6 characters long.");
                    isValid = false;
                } else if (username.length() > 24) {
                    message.add("Username must not exceed 24 characters.");
                    isValid = false;
                }

                if (password.length() < 8) {
                    message.add("Password must not be less than 8 characters long.");
                    isValid = false;
                }else if (password.length() > 16) {
                    message.add("Password must not exceed 16 characters.");
                    isValid = false;
                }
            }

            if (isValid) {
                try {
                    message = new StringJoiner("", "", "");

                    if (UserDB.hasUser(username, filepath)) {
                        message.add("*An account with the username <i>" + username + "</i> already exists.");
                    } else {
                        url = "/new-account-summary.jsp";
                        UserDB.insert(user, filepath);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                url = "/new-account.jsp";
            }

            request.setAttribute("user", user);
            request.setAttribute("message", message.toString());
        }

        return url;
    }

    /**
     * Handles request to log in user; adds username and user first name
     * attributes to current session.
     *
     * @param request servlet request to add attributes to
     * @param url current URL
     * @return updated URL to redirect user
     */
    protected String login(HttpServletRequest request, String url) {
        url = "/login.jsp";
        ServletContext sc = getServletContext();

        String action = request.getParameter("action");
        if (action == null) {
            action = "login";
        }

        if (action.equals("login")) {
            url = "/login.jsp";
        } else if (action.equals("auth")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            String message = ""; //validation message
            if (username == null   || password == null ||
                username.isEmpty() || password.isEmpty())
            {
                message = "*Please fill out all required text boxes.";
            } else {
                try {
                    String filepath = sc.getRealPath("/WEB-INF/users.txt");
                    User user = UserDB.getUser(username, password, filepath);

                    if (user == null) {
                        message = "*Username or password is incorrect.";
                    } else {
                        HttpSession session = request.getSession();
                        session.setAttribute("username", user.getUsername());
                        session.setAttribute("firstName", user.getFirstName());
                        url = "/index.jsp";
                    }
                } catch (IOException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            request.setAttribute("message", message);
        }

        return url;
    }

    /**
     * Handles request to log out user; removes username and user first name
     * attributes from the current session.
     *
     * @param request servlet request to remove attributes from
     * @param url current URL
     * @return updated URL to redirect user
     */
    protected String logout(HttpServletRequest request, String url) {

        String action = request.getParameter("action");
        if (action == null) {
            action = "logout";
        }

        switch (action) {
            case "logout":
                url = "/logout.jsp";
                break;
            case "leave":
                HttpSession session = request.getSession();
                session.removeAttribute("username");
                session.removeAttribute("firstName");
            case "stay":
                url = "/index.jsp";
                break;
            default:
                url = "/logout.jsp";
                break;
        }

        return url;
    }

}
