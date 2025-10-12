/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Contact.java                                                    *
 * Created:   06/10/2025, 22:36                                               *
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

@WebServlet(name = "contact",
        description = "Servlet to save user contact details",
        value = "/contact")
public class Contact extends HttpServlet {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Contact.class.getName());
    }

    static String email;
    static String[] channels;
    static String[] addresses;
    static String[] consents;
    static String uname;
    static String channel;
    static String address;
    static String consent;
    static String response;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");

        email = req.getParameter("email");
        uname = (String) req.getSession().getAttribute("uname");;
        // Multiple contacts - expects arrays from the form
        channels = req.getParameterValues("channel");
        addresses = req.getParameterValues("address");
        consents = req.getParameterValues("consent");

        if (channels != null && addresses != null) {
            int total = channels.length;
            log.info("Saving {} contact(s) for user {}", total, email);
            for (int i = 0; i < channels.length; i++) {
                channel = channels[i];
                address = (i < addresses.length) ? addresses[i] : "";
                consent = (consents != null && i < consents.length) ? consents[i] : "NO";
                log.info("Saving contact: email={}, channel={}, address={}, consent={}",
                        email, channel, address, consent);
                response = ProfileDAO.saveContact(uname, email, channel, address, consent);
                log.info("SaveContact response: {}", response);
                if (!"success".equalsIgnoreCase(response)) {
                    log.warn("Contact save failed for {} on channel {}: {}", email, channel, response);
                }
            }
        } else {
            log.warn("No contact details provided for {}", email);
        }
        // Redirect or forward back to settings.jsp
        req.getRequestDispatcher("settings.jsp").forward(req, resp);
    }
}