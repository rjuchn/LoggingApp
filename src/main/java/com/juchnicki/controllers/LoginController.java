package com.juchnicki.controllers;

import com.juchnicki.helpers.UserHelper;
import com.juchnicki.pojos.User;
import com.juchnicki.utils.PasswordService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Rafal on 2016-11-13.
 */
public class LoginController extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private HttpSession session;
    private String url;
    private int loginAttempts;

    public LoginController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        session = request.getSession();

        if(request.getParameter("logout") != null){
            logout();
            url = "index.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        session = request.getSession();

        if(session.getAttribute("loginAttempts") == null){
            loginAttempts = 0;
        }

        if(loginAttempts > 2){
            String errorMessage = "Error: Number of login attempts exceeded";
            request.setAttribute("errorMessage", errorMessage);
            url = "index.jsp";
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            PasswordService pws = new PasswordService();
            String encryptedPass = pws.encrypt(password);

            UserHelper uh = new UserHelper();
            User user = uh.authenticateUser(username, encryptedPass);

            if(user != null){
                session.invalidate();
                session= request.getSession(true);
                session.setAttribute("user", user);
                url="UserAccount.jsp";
            } else {
                String errorMessage = "Error: Username or password not found.";
                request.setAttribute("errorMessage", errorMessage);
                url="index.jsp";
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    public void logout() {
        session.invalidate();
    }
}
