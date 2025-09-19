/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      SpectreServlet.java                                             *
 * Created:   20/09/2025, 00:52                                               *
 * Modified:  20/09/2025, 00:52                                               *
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

import com.aerosimo.ominet.sentinel.models.utils.Spectre;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(name = "spectreErrors", value = "/spectreErrors")
public class SpectreServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        try {
            // Default to 5 records if not provided
            int records = 5;
            String param = req.getParameter("records");
            if (param != null) {
                try {
                    records = Integer.parseInt(param);
                } catch (NumberFormatException ignore) {}
            }

            String soapResponse = Spectre.getTopErrors(records);

            // Parse SOAP XML
            List<Map<String, String>> errors = parseErrors(soapResponse);

            // Convert to JSON manually
            String json = "[" + errors.stream()
                    .map(e -> String.format(
                            "{\"errorRef\":\"%s\",\"message\":\"%s\",\"timestamp\":\"%s\"}",
                            escape(e.get("errorRef")),
                            escape(e.get("errorMessage")),
                            escape(e.get("errorTime"))
                    ))
                    .collect(Collectors.joining(",")) + "]";

            resp.getWriter().write(json);

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("[]"); // fallback
        }
    }

    private static List<Map<String, String>> parseErrors(String xml) throws Exception {
        List<Map<String, String>> list = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

        NodeList nodes = doc.getElementsByTagNameNS("*", "getTopErrors");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element el = (Element) nodes.item(i);
            Map<String, String> map = new HashMap<>();
            map.put("errorID", getTagText(el, "errorID"));
            map.put("errorRef", getTagText(el, "errorRef"));
            map.put("errorTime", getTagText(el, "errorTime"));
            map.put("errorCode", getTagText(el, "errorCode"));
            map.put("errorMessage", getTagText(el, "errorMessage"));
            map.put("errorService", getTagText(el, "errorService"));
            list.add(map);
        }
        return list;
    }

    private static String getTagText(Element parent, String tag) {
        NodeList list = parent.getElementsByTagNameNS("*", tag);
        return list.getLength() > 0 ? list.item(0).getTextContent().trim() : "";
    }

    private static String escape(String text) {
        return text == null ? "" : text.replace("\"", "\\\"");
    }
}