/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Profile.java                                                    *
 * Created:   06/10/2025, 22:34                                               *
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

import com.aerosimo.ominet.sentinel.dao.mapper.ProfileDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "profile",
        description = "A simple servlet to save profile details",
        value = "/profile")
public class Profile extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Profile.class.getName());
    }

    static String email;
    static String maritalStatus;
    static String height;
    static String weight;
    static String ethnicity;
    static String religion;
    static String eyeColour;
    static String phenotype;
    static String genotype;
    static String disability;
    static String uname;
    static String response;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        email = req.getParameter("email");
        maritalStatus = req.getParameter("maritalStatus");
        height = req.getParameter("height");
        weight = req.getParameter("weight");
        ethnicity = req.getParameter("ethnicity");
        religion = req.getParameter("religion");
        eyeColour = req.getParameter("eyeColour");
        phenotype = req.getParameter("phenotype");
        genotype = req.getParameter("genotype");
        disability = req.getParameter("disability");
        uname = (String) req.getSession().getAttribute("uname");;
        response = ProfileDAO.saveProfile(uname, email, maritalStatus, height, weight, ethnicity,
                religion, eyeColour, phenotype, genotype, disability);
        log.info("Logging response of saveProfile {}", response);
        if ("success".equalsIgnoreCase(response)) {
            req.getRequestDispatcher("settings.jsp").forward(req, resp);
        } else {
            // maybe show error back to user
            req.setAttribute("errorMessage", response);
            req.getRequestDispatcher("settings.jsp").forward(req, resp);
        }
    }
}