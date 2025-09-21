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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Spectre client utility that calls Spectre REST service instead of SOAP.
 * Supports recordError and getTopErrors operations.
 */
public class Spectre {

    private static final String BASE_URL = "http://ominet.aerosimo.com:8081/spectre/api";
    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Stores a new error in Spectre.
     * @param faultCode error code
     * @param faultMessage error message
     * @param faultService originating service
     * @return server response (e.g. generated error reference)
     * @throws Exception if HTTP request fails
     */
    public static String recordError(String faultCode, String faultMessage, String faultService) throws Exception {
        String endpoint = BASE_URL + "/errors";

        // Construct JSON payload
        String payload = mapper.writeValueAsString(Map.of(
                "faultCode", faultCode,
                "faultMessage", faultMessage,
                "faultService", faultService
        ));

        String json = post(endpoint, payload);
        // Expecting {"recordError":"ERR|XYZ123"} style response
        Map<String, Object> response = mapper.readValue(json, new TypeReference<>() {});
        return response.getOrDefault("recordError", "UNKNOWN").toString();
    }

    /**
     * Fetches top errors from Spectre REST API.
     * @param count number of records to fetch
     * @return list of errors as List<Map<String,Object>>
     * @throws Exception if HTTP request fails
     */
    public static List<Map<String, Object>> getTopErrors(int count) throws Exception {
        String endpoint = BASE_URL + "/errors?records=" + count;
        String json = get(endpoint);
        return mapper.readValue(json, new TypeReference<>() {});
    }

    /**
     * Executes a GET request and returns response as String.
     */
    private static String get(String endpoint) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("GET failed : HTTP error code : " + conn.getResponseCode());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            conn.disconnect();
        }
    }

    /**
     * Executes a POST request with JSON payload.
     */
    private static String post(String endpoint, String payload) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(endpoint).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(3000);
        conn.setReadTimeout(3000);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK &&
                conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new RuntimeException("POST failed : HTTP error code : " + conn.getResponseCode());
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } finally {
            conn.disconnect();
        }
    }
}