/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      Spectre.java                                                    *
 * Created:   20/09/2025, 00:40                                               *
 * Modified:  20/09/2025, 00:40                                               *
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

package com.aerosimo.ominet.sentinel.models.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Spectre {

    private static final String ENDPOINT =
            "http://ominet.aerosimo.com:8081/spectre/ws/spectre";

    private static String callService(String soapAction, String payload) throws IOException {
        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        conn.setRequestProperty("SOAPAction", soapAction);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }

        int code = conn.getResponseCode();
        InputStream is = (code >= 200 && code < 300)
                ? conn.getInputStream()
                : conn.getErrorStream();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder resp = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                resp.append(line).append("\n");
            }
            return resp.toString();
        }
    }

    /** Call recordError operation */
    public static String recordError(String faultCode, String faultMessage, String serviceName) throws IOException {
        String body =
                "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" " +
                        "               xmlns:err=\"https://aerosimo.com/api/ws/spectre\">" +
                        "   <soap:Header/>" +
                        "   <soap:Body>" +
                        "       <err:recordError>" +
                        "           <faultcode>" + faultCode + "</faultcode>" +
                        "           <faultmessage>" + faultMessage + "</faultmessage>" +
                        "           <faultservicename>" + serviceName + "</faultservicename>" +
                        "       </err:recordError>" +
                        "   </soap:Body>" +
                        "</soap:Envelope>";

        return callService("recordError", body);
    }

    /** Call getTopErrors operation */
    public static String getTopErrors(int records) throws IOException {
        String body =
                "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" " +
                        "               xmlns:err=\"https://aerosimo.com/api/ws/spectre\">" +
                        "   <soap:Header/>" +
                        "   <soap:Body>" +
                        "       <err:getTopErrors>" +
                        "           <records>" + records + "</records>" +
                        "       </err:getTopErrors>" +
                        "   </soap:Body>" +
                        "</soap:Envelope>";

        return callService("getTopErrors", body);
    }
}