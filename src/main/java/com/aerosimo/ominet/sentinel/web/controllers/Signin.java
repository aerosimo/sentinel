/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Signin.java                                                     *
 * Created:   14/09/2025, 19:42                                               *
 * Modified:  14/09/2025, 19:42                                               *
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

import static com.aerosimo.ominet.sentinel.models.metrics.SystemMetrics.*;
import com.aerosimo.ominet.sentinel.com.mail.LoginMail;
import com.aerosimo.ominet.sentinel.dao.impl.LoginResponseDTO;
import com.aerosimo.ominet.sentinel.dao.mapper.AuthDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


@WebServlet(name = "signin",
        description = "A simple login servlet to validate user credentials",
        value = "/signin")
public class Signin extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Signin.class.getName());
    }

    static String password;
    static String email;
    static String modifiedBy;
    static String result;
    static LoginResponseDTO response;
    static String[] diskusage;
    static String[] memoryusage;
    static ArrayList<String> cpuusage;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        email = req.getParameter("email");
        password = req.getParameter("password");
        modifiedBy = "Sentinel";
        log.info("Preparing to sign in user with email {}", email);
        // Call DAO method
        response = AuthDAO.login(email, password,req.getRemoteAddr(),req.getHeader("user-agent"), modifiedBy);
        log.info("Logging response of sign in {}", response.getResponse());
        // Check response and redirect
        if ("success".equalsIgnoreCase(response.getResponse())) {
            log.info("Sign in successful");
            log.info("Multi-factor token is: {}", response.getMfaToken());
            // Send login email to the new user
            result = LoginMail.sendMail(response.getUsername(), email,response.getMfaToken(),req.getRemoteAddr(),req.getHeader("user-agent"));
            log.info("Login email response is : {}", result);
            diskusage = getDisk();
            memoryusage = getMemory();
            cpuusage = getCpu();
            // Store data in session
            req.getSession().setAttribute("diskusage", Arrays.toString(diskusage));
            req.getSession().setAttribute("memoryusage", Arrays.toString(memoryusage));
            req.getSession().setAttribute("cpuusage", cpuusage);
            req.getSession().setAttribute("email", email);
            req.getSession().setAttribute("inet", req.getRemoteAddr());
            req.getSession().setAttribute("host", req.getRemoteHost());
            req.getSession().setAttribute("uname", response.getUsername());
            req.getSession().setAttribute("userAgent", req.getHeader("user-agent"));
            resp.sendRedirect("mfa.jsp");
        } else {
            log.error("Login request failed with the following: {}", response.getResponse());
            // Stay on login page, optionally show error message
            req.setAttribute("errorMessage", response.getResponse());
            req.getRequestDispatcher("signin.jsp").forward(req, resp);
        }
    }
}