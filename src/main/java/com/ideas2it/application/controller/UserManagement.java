package com.ideas2it.application.controller;

import java.io.IOException;

import com.ideas2it.application.common.Constants;
import com.ideas2it.application.exception.ApplicationException;
import com.ideas2it.application.logger.ApplicationLogger;
import com.ideas2it.application.model.User;
import com.ideas2it.application.service.UserService;
import com.ideas2it.application.service.impl.UserServiceImpl;
import com.ideas2it.application.util.EncryptionUtil;

import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException; 

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * <p>
 * UserManagement class acts as a user interface which interacts with
 * the User JSP and is used to create and authenticate login information
 * </p>
 *
 * @author Latheeshwar Raj
 */
public class UserManagement extends HttpServlet {
    private static UserService userService;

    public  void setUserService(UserService userService) {
        this.userService = userService;
    }
    public UserService getUserService() {
        return this.userService;
    }

    /**
     * <p>
     * doPost method recieves actions from user and does operations accordingly
     * </p>
     *
     * @param request                 request from the user interface 
     * @param response                response to the user interface 
     */  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
                throws ServletException, IOException{
        String operation = request.getParameter(Constants.OPERATION);
        if(operation.equals(Constants.LOGIN)) {
            authenticateUser(request, response);
        } else if(operation.equals(Constants.SIGNUP) ||
                                    operation.equals(Constants.UPDATE))  {
            createUser(request, response);
        } else if(operation.equals(Constants.LOGOUT)) {
            logoutUser(request, response);
        } else {
            request.getRequestDispatcher(Constants.ERROR_JSP_URL).
                                                    forward(request,response);
        }   
    }

    /**
     * <p>
     * authenticateUser is used to authenticate the user for the
     * given credentials
     * </p>
     *
     * @param request                 request from the user interface 
     * @param response                response to the user interface 
     */  
    public void authenticateUser(HttpServletRequest request,
           HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(Constants.EMAIL);
        String password = request.getParameter(Constants.PASS);
        try {
            User user = userService.getUserByEmail(email);
            if(null == user) {
                request.setAttribute(Constants.FAIL_MESSAGE, 
                                         Constants.WRONG_LOGIN_CREDENTIALS);
                request.getRequestDispatcher(Constants.INDEX_JSP).forward(request,
                                                                     response);
            }
            String decryptedPassword = EncryptionUtil.decrypt(user.getPassword());
            if(email.equals(user.getEmail()) && password.equals(decryptedPassword)){
                HttpSession oldSession = request.getSession(Boolean.FALSE);
                if (oldSession != null) {
                    oldSession.invalidate();
                }
                HttpSession session = request.getSession(Boolean.TRUE);
                session.setAttribute(Constants.STATUS, Constants.ACTIVE);
                request.getRequestDispatcher(Constants.MENU_JSP).
                                                    forward(request, response);
            } else  {
                HttpSession oldSession = request.getSession(Boolean.FALSE);
                if (oldSession != null) {
                    oldSession.invalidate();
                }
                HttpSession session = request.getSession(Boolean.TRUE);
                session.setAttribute(Constants.STATUS, Constants.ACTIVE);
            }
        } catch(ApplicationException e){
            request.setAttribute(Constants.FAIL_MESSAGE, e);
            request.getRequestDispatcher(Constants.INDEX_JSP).
                                                forward(request, response);
        }
    }

    /**
     * <p>
     * createUser is used to create the user and save it in database.
     * </p>
     *
     * @param request                 request from the user interface 
     * @param response                response to the user interface 
     */  
    public void createUser(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        String email = request.getParameter(Constants.EMAIL);
        String password = request.getParameter(Constants.PASS);
        String confirmPassword = request.getParameter(Constants.CONFIRM_PASS);
        try {
            if(confirmPassword.equals(password)) {
                if(userService.createUser(new User(email, password))){
                    request.setAttribute(Constants.MESSAGE,
                                                        Constants.USER_CREATED);
                } else {
                    request.setAttribute(Constants.FAIL_MESSAGE,
                                                Constants.CREATION_FAILED);
                }
                    request.getRequestDispatcher(Constants.INDEX_JSP).
                                                    forward(request, response);   
            } else {
                request.setAttribute(Constants.FAIL_MESSAGE,
                                                     Constants.PASS_MISMATCH);
            }
        request.getRequestDispatcher(Constants.INDEX_JSP).
                                                forward(request, response);
        } catch (ApplicationException e) {
            request.setAttribute(Constants.FAIL_MESSAGE, e);
            request.getRequestDispatcher(Constants.INDEX_JSP).
                                                forward(request, response);
        }
    }

    /**
     * <p>
     * logoutUser is used to clear the session and the log the user out.
     * </p>
     *
     * @param request                 request from the user interface 
     * @param response                response to the user interface 
     */  
    public void logoutUser(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(Boolean.FALSE);
        if(null != session) {
            session.setAttribute(Constants.STATUS, null);
        }
        response.sendRedirect(Constants.INDEX_JSP);
    }

    public void doGet(HttpServletRequest request ,HttpServletResponse response)  
                    throws ServletException,IOException{ 
        HttpSession session = request.getSession(Boolean.FALSE);
        if(null != session.getAttribute("status")) {
            request.getRequestDispatcher("menu.jsp").
                                                forward(request, response);
        } else {
            request.getRequestDispatcher(Constants.INDEX_JSP).
                                                forward(request,response);
        }
    }
}
