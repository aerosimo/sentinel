/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Account.java                                                    *
 * Created:   05/10/2025, 17:29                                               *
 * Modified:  05/10/2025, 17:29                                               *
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

import com.aerosimo.ominet.sentinel.dao.mapper.AuthDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "account",
        description = "A simple servlet to update user account security details",
        value = "/account")
public class Account extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Account.class.getName());
    }

    static String password;
    static String email;
    static String uname;
    static String modifiedBy;
    static String response;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        uname = req.getParameter("Username");
        email = (String) req.getSession().getAttribute("email");
        password = req.getParameter("password");
        modifiedBy = "Sentinel";
        log.info("Preparing to update user account for {}", email);
        response = AuthDAO.updateAccount(uname, email, password, modifiedBy);
        if ("success".equalsIgnoreCase(response)) {
            req.getRequestDispatcher("settings.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Failed to save address details");
            req.getRequestDispatcher("settings.jsp").forward(req, resp);
        }
    }
}