/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Reset.java                                                      *
 * Created:   10/10/2025, 16:03                                               *
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

import com.aerosimo.ominet.sentinel.mail.ResetMail;
import com.aerosimo.ominet.sentinel.dao.mapper.AuthDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "reset",
        description = "A simple servlet to reset password for the application user",
        value = "/reset")
public class Reset extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Reset.class.getName());
    }

    static String password;
    static String email;
    static String uname;
    static String result;
    static String token;
    static String response;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        email = (String) req.getSession().getAttribute("email");
        uname = (String) req.getSession().getAttribute("uname");
        password = req.getParameter("password");
        token = req.getParameter("token");
        log.info("Preparing to reset user password for {}", email);
        // Call DAO method
        result = AuthDAO.resetPassword(email, token.toUpperCase(Locale.ROOT), password);
        // Check response and redirect
        if ("success".equalsIgnoreCase(result)) {
            log.info("Password reset ran successfully for {}", email);
            // Send reset email to the new user
            response = ResetMail.sendMail(email,uname);
            log.info("Password reset email response is : {}", response);
            resp.sendRedirect("signin.jsp");
        } else {
            resp.sendRedirect("reset.jsp");
        }
    }
}