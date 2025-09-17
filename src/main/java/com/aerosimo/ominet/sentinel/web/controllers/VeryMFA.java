/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      VeryMFA.java                                                    *
 * Created:   15/09/2025, 02:53                                               *
 * Modified:  15/09/2025, 02:53                                               *
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

import com.aerosimo.ominet.sentinel.dao.impl.MFAResponseDTO;
import com.aerosimo.ominet.sentinel.dao.mapper.AuthDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "mfa",
        description = "A simple servlet to capture the information from mfa email form",
        value = "/mfa")
public class VeryMFA extends HttpServlet {

    private static final Logger log;
    static String mfaToken;
    static String modifiedBy;
    static MFAResponseDTO result;

    static {
        log = LogManager.getLogger(VeryMFA.class.getName());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        mfaToken = req.getParameter("mfaToken");
        modifiedBy = "Sentinel";
        log.info("Preparing to confirm login token");
        // Call DAO method
        result = AuthDAO.confirmMFA((String) req.getSession().getAttribute("email"),
                mfaToken, (String) req.getSession().getAttribute("inet"),
                (String) req.getSession().getAttribute("userAgent"),
                modifiedBy);
        log.info("Logging response of login confirmation email {}", result.getResponse());
        // Check response and redirect
        if ("success".equalsIgnoreCase(result.getResponse())) {
            log.info("MFA email confirmed successfully");
            log.info("Session token is: {}", result.getSessionToken());
            req.getSession().setAttribute("SessionToken", result.getSessionToken());
            resp.sendRedirect("index.jsp");
        } else {
            log.error("MFA email confirmation failed with the following: {}", result.getResponse());
            // Stay on signup page, optionally show error message
            req.setAttribute("errorMessage", result.getSessionToken());
            req.getRequestDispatcher("mfa.jsp").forward(req, resp);
        }

    }
}