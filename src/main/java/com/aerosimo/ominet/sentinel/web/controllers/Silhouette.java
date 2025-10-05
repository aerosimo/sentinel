/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Silhouette.java                                                 *
 * Created:   26/09/2025, 13:27                                               *
 * Modified:  26/09/2025, 13:27                                               *
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

import com.aerosimo.ominet.sentinel.dao.impl.SilhouetteResponseDTO;
import com.aerosimo.ominet.sentinel.dao.mapper.SilhouetteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "silhouette",
        description = "A simple servlet to populate person, contacts and profile information",
        value = "/silhouette")
public class Silhouette extends HttpServlet {

    private static final Logger log = LogManager.getLogger(Silhouette.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ Get logged-in user's email from session
        String email = (String) req.getSession().getAttribute("email");

        if (email == null) {
            log.warn("User not logged in, redirecting to login.");
            resp.sendRedirect("login.jsp");
            return;
        }
        log.info("Fetching Silhouette for email: {}", email);
        // ✅ Fetch horoscope directly via DAO
        SilhouetteResponseDTO silhouette = SilhouetteDAO.GetSilhouette(email);
        log.info("Fetching Silhouette: {}", silhouette);
        if (silhouette != null) {
            log.info("Loaded silhouette for {}",email);
            req.setAttribute("silhouette", silhouette);
        } else {
            log.warn("No silhouette found for email: {}", email);
            req.setAttribute("silhouette", null);
        }
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}