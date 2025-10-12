/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Signout.java                                                    *
 * Created:   18/09/2025, 23:35                                               *
 * Modified:  10/10/2025, 16:04                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

package com.aerosimo.ominet.sentinel.web;

import com.aerosimo.ominet.sentinel.dao.mapper.AuthDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "logout",
        description = "A simple logout servlet to log a user out of the application",
        value = "/logout")
public class Signout extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Signout.class.getName());
    }

    static String response;
    static String uname;
    static String email;
    static String SessionToken;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        uname = (String) req.getSession().getAttribute("uname");
        email = (String) req.getSession().getAttribute("email");
        SessionToken = (String) req.getSession().getAttribute("SessionToken");
        // Call DAO method
        response = AuthDAO.logout(uname, email, SessionToken);
        if ("success".equalsIgnoreCase(response)) {
            log.info("Sign out successful with the session: {}", email);
            // Remove all stored session attributes
            req.getSession().removeAttribute("inet");
            req.getSession().removeAttribute("host");
            req.getSession().removeAttribute("user");
            req.getSession().removeAttribute("email");
            req.getSession().removeAttribute("device");
            req.getSession().removeAttribute("SessionToken");
            req.getSession().invalidate();
            resp.sendRedirect("signin.jsp");
        } else {
            log.error("Logout request failed with the following: {}", response);
            resp.sendRedirect("index.jsp");
        }
    }
}