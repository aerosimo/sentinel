/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Forgot.java                                                     *
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

package com.aerosimo.ominet.sentinel.api.web;

import com.aerosimo.ominet.sentinel.dao.impl.ForgotResponseDTO;
import com.aerosimo.ominet.sentinel.mail.ForgotMail;
import com.aerosimo.ominet.sentinel.dao.mapper.AuthDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "forgot",
        description = "A simple servlet to recover password for the application user",
        value = "/forgot")
public class Forgot extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Forgot.class.getName());
    }

    static String email;
    static String result;
    static ForgotResponseDTO response;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        email = req.getParameter("email");
        log.info("Preparing to find user via email address {}", email);
        // Call DAO method
        response = AuthDAO.forgotPassword(email);
        log.info("Logging response of sign in {}", response.getResponse());
        // Check response and redirect
        if ("success".equalsIgnoreCase(response.getResponse())) {
            log.info("Password forgot ran successfully for {}", email);
            log.info("email verification code of password reset is: {}", response.getAuthcode());
            // Send forgot email to the new user
            result = ForgotMail.sendMail(email,response.getUname(),response.getAuthcode());
            log.info("Forgot email response is : {}", result);
            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("uname", response.getUname());
            resp.sendRedirect("reset.jsp");
        } else {
            log.error("Forgot password request failed with the following: {}", response.getResponse());
            resp.sendRedirect("index.jsp");
        }
    }
}