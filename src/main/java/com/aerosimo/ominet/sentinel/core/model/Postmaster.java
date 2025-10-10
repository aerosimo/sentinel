/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Postmaster.java                                                 *
 * Created:   23/09/2025, 02:08                                               *
 * Modified:  10/10/2025, 15:59                                               *
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

package com.aerosimo.ominet.sentinel.core.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Postmaster {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Postmaster.class.getName());
    }

    // REST endpoint instead of SOAP
    private static final String ENDPOINT_URL = "http://ominet.aerosimo.com:8081/postmaster/api/postmaster";

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Send an email via Postmaster REST service
     */
    public static String sendEmail(String emailAddress, String emailSubject, String emailMessage, String emailFiles) {
        String response = "Message sent successfully";

        try {
            // Build JSON request body
            Map<String, Object> payload = new HashMap<>();
            payload.put("emailAddress", emailAddress);
            payload.put("emailSubject", emailSubject);
            payload.put("emailMessage", emailMessage);
            payload.put("emailFiles", emailFiles);

            String jsonRequest = mapper.writeValueAsString(payload);

            // Open HTTP connection
            URL url = new URL(ENDPOINT_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Send JSON request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonRequest.getBytes(StandardCharsets.UTF_8));
            }

            // Read JSON response
            if (conn.getResponseCode() == 200) {
                Map<String, String> result = mapper.readValue(conn.getInputStream(), Map.class);
                response = result.getOrDefault("Status", "Message sent successfully");
            } else {
                response = "Message not successful: HTTP " + conn.getResponseCode();
                log.error("Postmaster REST failed with HTTP code {}", conn.getResponseCode());
            }

        } catch (Exception err) {
            response = "Message not successful";
            log.error("Email Notification Service failed in {} with error: ", Postmaster.class.getName(), err);
            try {
                Spectre.recordError("EM-20007", err.getMessage(), Postmaster.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return response;
    }
}