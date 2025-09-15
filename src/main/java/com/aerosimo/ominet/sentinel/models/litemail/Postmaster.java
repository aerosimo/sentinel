/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Postmaster.java                                                 *
 * Created:   11/09/2025, 00:21                                               *
 * Modified:  11/09/2025, 00:21                                               *
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

package com.aerosimo.ominet.sentinel.models.litemail;

import com.aerosimo.ominet.sentinel.dao.mapper.ErrorVaultDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Postmaster {

    private static final Logger log;

    static {
        log = LogManager.getLogger(Postmaster.class.getName());
    }

    private static final String ENDPOINT_URL;

    static {
        ENDPOINT_URL = "http://ominet.aerosimo.com:8081/postalsystem/ws/postmaster";
    }

    static String response;

    public static String sendEmail(String emailAddress, String emailSubject, String emailMessage, String emailFiles) {

        response = "Message sent successfully";
        try {
            // Build SOAP request XML
            String soapRequest =
                    """
                    <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope"
                                   xmlns:pos="https://aerosimo.com/api/ws/postmaster">
                        <soap:Body>
                            <pos:sendEmail>
                                <emailAddress>%s</emailAddress>
                                <emailSubject>%s</emailSubject>
                                <emailMessage>%s</emailMessage>
                                <emailFiles>%s</emailFiles>
                            </pos:sendEmail>
                        </soap:Body>
                    </soap:Envelope>
                    """.formatted(
                            escapeXml(emailAddress),
                            escapeXml(emailSubject),
                            escapeXml(emailMessage),
                            emailFiles != null ? escapeXml(emailFiles) : ""
                    );

            // Open HTTP connection
            URL url = new URL(ENDPOINT_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");

            // Send SOAP request
            try (OutputStream os = conn.getOutputStream()) {
                os.write(soapRequest.getBytes());
            }

            // Parse SOAP response
            Document doc = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(conn.getInputStream());

            // Extract <Status> from response
            if (doc.getElementsByTagName("Status").getLength() > 0) {
                response = doc.getElementsByTagName("Status").item(0).getTextContent();
            } else {
                log.warn("No <Status> element found in SOAP response.");
            }
        } catch (Exception err) {
            response = "Message not successful";
            log.error("Email Notification Service failed in {} with error: ", Postmaster.class.getName(), err);
            ErrorVaultDAO.storeError("EM-20007",err.getMessage(), Postmaster.class.getName());
        }
        return response;
    }
    // Utility to safely escape XML special chars in inputs
    private static String escapeXml(String input) {
        return input == null ? "" : input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}