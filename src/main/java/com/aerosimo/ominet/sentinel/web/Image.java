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

package com.aerosimo.ominet.sentinel.web;

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
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // 5 MB
public class Image extends HttpServlet {

    private static final Logger log = LogManager.getLogger(Image.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = (String) req.getSession().getAttribute("email");
        String uname = (String) req.getSession().getAttribute("uname");
        Part filePart = req.getPart("avatar");

        if (filePart == null || filePart.getSize() == 0) {
            log.warn("No file uploaded in avatar field.");
            resp.sendRedirect("settings.jsp?error=no_file");
            return;
        }

        if (email == null || uname == null) {
            log.error("Session attributes missing (email/uname). Cannot save image.");
            resp.sendRedirect("signin.jsp");
            return;
        }

        try (InputStream avatarStream = filePart.getInputStream()) {
            String dbResponse = ProfileDAO.saveImage(uname, email, avatarStream);
            log.info("ProfileDAO.saveImage -> {}", dbResponse);
            if ("success".equalsIgnoreCase(dbResponse)) {
                resp.sendRedirect("settings.jsp?msg=avatar_updated");
            } else {
                resp.sendRedirect("settings.jsp?error=upload_failed");
            }

        } catch (Exception err) {
            log.error("Exception while saving avatar for user -> {}", email, err);
            resp.sendRedirect("settings.jsp?error=exception");
        }
    }
}