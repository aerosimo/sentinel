/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Forgot.java                                                     *
 * Created:   15/09/2025, 15:16                                               *
 * Modified:  15/09/2025, 15:16                                               *
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

package com.aerosimo.ominet.sentinel.web.controllers;

import com.aerosimo.ominet.sentinel.com.mail.ForgotMail;
import com.aerosimo.ominet.sentinel.dao.impl.SignupResponseDTO;
import com.aerosimo.ominet.sentinel.dao.mapper.AuthDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "forgot",
        description = "A simple servlet to recover password for the application user",
        value = "/forgot")
public class Forgot {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Forgot.class.getName());
    }

    static String email;
    static String modifiedBy;
    static String result;
    static SignupResponseDTO response;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        email = req.getParameter("email");
        modifiedBy = "Sentinel";
        log.info("Preparing to find user via email address {}", email);
        // Call DAO method
        response = AuthDAO.forgotPassword(email, modifiedBy);
        log.info("Logging response of sign in {}", response.getResponse());
        // Remove all stored session attributes
        req.getSession().removeAttribute("inet");
        req.getSession().removeAttribute("host");
        req.getSession().removeAttribute("user");
        req.getSession().removeAttribute("email");
        req.getSession().removeAttribute("userAgent");
        req.getSession().removeAttribute("SessionToken");
        req.getSession().invalidate();
        // Check response and redirect
        if ("success".equalsIgnoreCase(response.getResponse())) {
            log.info("Password forgot ran successfully for {}", email);
            log.info("email verification code of password reset is: {}", response.getVerificationCode());
            // Send forgot email to the new user
            result = ForgotMail.sendMail(email,response.getVerificationCode());
            log.info("Forgot email response is : {}", result);
            resp.sendRedirect("reset.jsp");
        } else {
            log.error("Forgot password request failed with the following: {}", response.getResponse());
            resp.sendRedirect("index.jsp");
        }
    }
}