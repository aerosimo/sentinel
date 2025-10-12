/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Signup.java                                                     *
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

import com.aerosimo.ominet.sentinel.mail.WelcomeMail;
import com.aerosimo.ominet.sentinel.dao.impl.SignupResponseDTO;
import com.aerosimo.ominet.sentinel.dao.mapper.AuthDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "signup",
        description = "A simple registration servlet to capture the information from registration form",
        value = "/signup")
public class Signup extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Signup.class.getName());
    }

    static String password;
    static String email;
    static String uname;
    static String result;
    static SignupResponseDTO response;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        uname = req.getParameter("Username");
        email = req.getParameter("email");
        password = req.getParameter("password");
        log.info("Preparing to register new user Account for {}", email);
        // Call DAO method
        response = AuthDAO.signup(uname, email, password);
        log.info("Logging response of Account registration {}", response.getResponse());
        // Check response and redirect
        if ("success".equalsIgnoreCase(response.getResponse())) {
            log.info("New user is now successfully created and now sending welcome email");
            log.info("verification code is: {}", response.getVerificationCode());
            // Send welcome email to the new user
            result = WelcomeMail.sendMail(uname,email,response.getVerificationCode());
            log.info("Welcome email response is : {}", result);
            // Store data in session
            req.getSession().setAttribute("email", email);
            resp.sendRedirect("verify.jsp");
        } else {
            log.error("New user registration failed with the following: {}", response.getResponse());
            // Stay on signup page, optionally show error message
            req.setAttribute("errorMessage", response.getResponse());
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        }
    }
}