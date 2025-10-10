/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      VerifyEmail.java                                                *
 * Created:   24/09/2025, 01:13                                               *
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
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "verify",
        description = "A simple verification servlet to capture the information from email verification form",
        value = "/verify")
public class VerifyEmail extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(VerifyEmail.class.getName());
    }

    static String verifyToken;
    static String modifiedBy;
    static String result;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        verifyToken = req.getParameter("verifyToken");
        modifiedBy = "Sentinel";
        log.info("Preparing to verify new user email address");
        // Call DAO method
        result = AuthDAO.verifyEmail((String) req.getSession().getAttribute("email"), verifyToken.toUpperCase(Locale.ROOT), modifiedBy);
        log.info("Logging response of verification email {}", result);
        // Check response and redirect
        if ("success".equalsIgnoreCase(result)) {
            log.info("Email verified successfully");
            // Remove stored data in session
            req.getSession().removeAttribute("email");
            resp.sendRedirect("signin.jsp");
        } else {
            log.error("email verification failed with the following: {}", result);
            // Stay on signup page, optionally show error message
            req.setAttribute("errorMessage", result);
            req.getRequestDispatcher("verify.jsp").forward(req, resp);
        }

    }
}