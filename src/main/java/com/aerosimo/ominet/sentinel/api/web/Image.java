/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Image.java                                                      *
 * Created:   11/10/2025, 14:11                                               *
 * Modified:  11/10/2025, 14:11                                               *
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

package com.aerosimo.ominet.sentinel.api.web;

import com.aerosimo.ominet.sentinel.dao.mapper.ProfileDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/image")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class Image extends HttpServlet {

    private static final Logger log = LogManager.getLogger(Image.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = (String) req.getSession().getAttribute("email");
        String uname = (String) req.getSession().getAttribute("uname");

        if (email == null || uname == null) {
            log.error("Session expired or invalid; email or uname is null");
            resp.sendRedirect("login.jsp?error=session_expired");
            return;
        }

        Part filePart = null;
        try {
            filePart = req.getPart("avatar"); // must match JSP input name
        } catch (IllegalStateException | ServletException err) {
            log.error("Error accessing multipart data: ", err);
            resp.sendRedirect("settings.jsp?error=upload_error");
            return;
        }

        if (filePart == null) {
            log.warn("No file part found for 'avatar'. Check form name and enctype.");
            resp.sendRedirect("settings.jsp?error=no_file");
            return;
        }

        if (filePart.getSize() == 0) {
            log.warn("Empty or same image re-upload detected.");
            resp.sendRedirect("settings.jsp?error=empty_file");
            return;
        }

        try (InputStream avatarStream = filePart.getInputStream()) {
            String dbResponse = ProfileDAO.saveImage(uname, email, avatarStream);
            log.info("ProfileDAO.saveImage returned: {}", dbResponse);
            resp.sendRedirect("settings.jsp?msg=" + dbResponse);
        } catch (Exception err) {
            log.error("Error saving avatar for {}: {}", email, err.getMessage(), err);
            resp.sendRedirect("settings.jsp?error=upload_failed");
        }
    }
}