/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Delete.java                                                     *
 * Created:   06/10/2025, 22:33                                               *
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

import com.aerosimo.ominet.sentinel.dao.mapper.AccountDAO;
import com.aerosimo.ominet.sentinel.mail.FarewellMail;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "delete",
        description = "A simple servlet to delete user",
        value = "/delete")
public class Delete extends HttpServlet {
    private static final Logger log;

    static {
        log = LogManager.getLogger(Account.class.getName());
    }

    static String email;
    static String uname;
    static String response;
    static String result;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        email = (String) req.getSession().getAttribute("email");
        uname = (String) req.getSession().getAttribute("uname");
        log.info("Preparing to delete user account for {}", email);
        response = AccountDAO.deleteAccount(email);
        if ("success".equalsIgnoreCase(response)) {
            result = FarewellMail.sendMail(uname,email);
            log.info("Account Deletion Confirmation Email response is : {}", result);
            req.getSession().removeAttribute("inet");
            req.getSession().removeAttribute("host");
            req.getSession().removeAttribute("user");
            req.getSession().removeAttribute("email");
            req.getSession().removeAttribute("userAgent");
            req.getSession().removeAttribute("SessionToken");
            req.getSession().removeAttribute("countryList");
            req.getSession().invalidate();
            req.getRequestDispatcher("logout").forward(req, resp);
        } else {
            log.error("Account delete request failed with the following: {}", response);
            req.setAttribute("errorMessage", response);
            req.getRequestDispatcher("settings.jsp").forward(req, resp);
        }
    }
}