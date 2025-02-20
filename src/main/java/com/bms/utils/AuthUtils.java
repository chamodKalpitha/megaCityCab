package com.bms.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bms.enums.AccountType;

public class AuthUtils {
	 public static boolean isAuthenticated(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
	        HttpSession session = request.getSession(false);

	        if (session == null || session.getAttribute("accountType") == null) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return false;
	        }
	        return true;
	    }
	 
	 
	 public static boolean isAuthorized(HttpServletRequest request, HttpServletResponse response, AccountType requiredRole) throws IOException {
	        HttpSession session = request.getSession(false);

	        if (session == null || session.getAttribute("accountType") == null) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return false;
	        }

	        AccountType userRole;
	        try {
	            userRole = AccountType.valueOf(session.getAttribute("accountType").toString());
	        } catch (IllegalArgumentException | NullPointerException e) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return false;
	        }

	        if (userRole != requiredRole) {
	            response.sendRedirect(request.getContextPath() + "/login.jsp");
	            return false;
	        }

	        return true;
	    }
}
