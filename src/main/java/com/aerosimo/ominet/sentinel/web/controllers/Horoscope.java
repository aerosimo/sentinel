/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Horoscope.java                                                  *
 * Created:   26/09/2025, 00:27                                               *
 * Modified:  26/09/2025, 00:27                                               *
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

import com.aerosimo.ominet.sentinel.dao.impl.HoroscopeResponseDTO;
import com.aerosimo.ominet.sentinel.dao.mapper.ProfileDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/horoscope")
public class Horoscope extends HttpServlet {

    private static final Logger log = LogManager.getLogger(Horoscope.class.getName());

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
        log.info("Fetching horoscope for email: {}", email);
        // ✅ Fetch horoscope directly via DAO
        HoroscopeResponseDTO horoscope = ProfileDAO.GetHoroscope(email);
        if (horoscope != null) {
            log.info("Loaded horoscope for {} [{} - {}]",
                    email, horoscope.getzodiacSign(), horoscope.getCurrentDay());
            req.setAttribute("horoscope", horoscope);
        } else {
            log.warn("No horoscope found for email: {}", email);
            req.setAttribute("horoscope", null);
        }
        req.getRequestDispatcher("/profile.jsp").forward(req, resp);
    }
}