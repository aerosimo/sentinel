/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      ServerStatus.java                                               *
 * Created:   10/10/2025, 16:03                                               *
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

package com.aerosimo.ominet.sentinel.api.web;

import com.aerosimo.ominet.sentinel.core.model.PingServer;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "serverStatus",
        description = "Returns current server availability as JSON",
        value = "/serverStatus")
public class ServerStatus extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        // Ping application servers (HTTP endpoints)
        boolean jenkins = PingServer.isAlive("ominet.aerosimo.com:8080");
        boolean tomee   = PingServer.isAlive("http://ominet.aerosimo.com:8081");
        boolean linux   = PingServer.isAlive("http://ominet.aerosimo.com:9090");

        // ⚠️ Oracle DB cannot be pinged with HttpURLConnection (port 1521 is not HTTP).
        // TODO: Replace with a JDBC-based check (e.g. try a lightweight SELECT 1).
        boolean oracle = true;

        // Return JSON response
        String json = String.format(
                "{ \"jenkins\": \"%s\", \"tomee\": \"%s\", \"linux\": \"%s\", \"oracle\": \"%s\" }",
                jenkins ? "online" : "offline",
                tomee   ? "online" : "offline",
                linux   ? "online" : "offline",
                oracle  ? "online" : "offline"
        );
        resp.getWriter().write(json);
    }
}