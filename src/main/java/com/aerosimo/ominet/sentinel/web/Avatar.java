/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Avatar.java                                                     *
 * Created:   13/10/2025, 03:42                                               *
 * Modified:  13/10/2025, 03:42                                               *
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

import com.aerosimo.ominet.sentinel.dao.impl.ImageResponseDTO;
import com.aerosimo.ominet.sentinel.dao.mapper.ProfileDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/avatar")
public class Avatar extends HttpServlet {

    private static final Logger log = LogManager.getLogger(Avatar.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json; charset=UTF-8");
        String email = (String) req.getSession().getAttribute("email");
        String uname = (String) req.getSession().getAttribute("uname");

        if (email == null || email.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Missing email parameter\"}");
            return;
        }

        try {
            log.info("Retrieving avatar for user: {}", email);
            ImageResponseDTO imageResponse = ProfileDAO.getImage(uname != null ? uname : "system", email);

            if (imageResponse == null || imageResponse.getAvatar() == null) {
                log.warn("No avatar found for {}", email);
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"No avatar found for this user\"}");
                return;
            }

            // ✅ Option 1: Return JSON with Base64 inline image
            String json = String.format(
                    "{\"username\": \"%s\", \"email\": \"%s\", \"avatar\": \"%s\", \"modifiedBy\": \"%s\", \"modifiedDate\": \"%s\"}",
                    safeJson(imageResponse.getUsername()),
                    safeJson(imageResponse.getEmail()),
                    imageResponse.getAvatar(),  // already has "data:image/png;base64," prefix
                    safeJson(imageResponse.getModifiedBy()),
                    safeJson(imageResponse.getModifiedDate())
            );

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(json);

        } catch (Exception e) {
            log.error("Error retrieving avatar for {}", email, e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Internal server error occurred\"}");
        }
    }

    /**
     * Utility method for safe JSON escaping.
     */
    private String safeJson(String input) {
        return input == null ? "" : input.replace("\"", "\\\"");
    }
}