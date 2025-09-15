/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      LoginMail.java                                                  *
 * Created:   15/09/2025, 02:26                                               *
 * Modified:  15/09/2025, 02:26                                               *
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

package com.aerosimo.ominet.sentinel.com.mail;

import com.aerosimo.ominet.sentinel.models.litemail.Postmaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginMail {

    private static final Logger log;

    static {
        log = LogManager.getLogger(LoginMail.class.getName());
    }

    static String response;

    public static String sendMail(String email, String token, String inet, String user, String device) {

        log.info("Preparing email body content to the following email address: {}", email);
        StringBuilder memo;
        try {
            memo = new StringBuilder("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <title>Sentinel MFA Login</title>\n" +
                    "  <style>\n" +
                    "    body {\n" +
                    "      font-family: system-ui, -apple-system, Segoe UI, Roboto, \"Helvetica Neue\", Arial, sans-serif;\n" +
                    "      background-color: #f9f9f9;\n" +
                    "      margin: 0;\n" +
                    "      padding: 0;\n" +
                    "      color: #333;\n" +
                    "    }\n" +
                    "    .container {\n" +
                    "      max-width: 600px;\n" +
                    "      margin: 20px auto;\n" +
                    "      background: #ffffff;\n" +
                    "      border-radius: 8px;\n" +
                    "      overflow: hidden;\n" +
                    "      box-shadow: 0 2px 8px rgba(0,0,0,0.1);\n" +
                    "    }\n" +
                    "    .header {\n" +
                    "      background-color: #4d3b7a;\n" +
                    "      color: #fff;\n" +
                    "      padding: 20px;\n" +
                    "      text-align: center;\n" +
                    "    }\n" +
                    "    .header img {\n" +
                    "      max-width: 60px;\n" +
                    "      margin-bottom: 10px;\n" +
                    "    }\n" +
                    "    .content {\n" +
                    "      padding: 20px;\n" +
                    "    }\n" +
                    "    .content h1 {\n" +
                    "      font-size: 20px;\n" +
                    "      margin-bottom: 10px;\n" +
                    "      color: #4d3b7a;\n" +
                    "    }\n" +
                    "    .content p {\n" +
                    "      font-size: 14px;\n" +
                    "      margin: 5px 0;\n" +
                    "    }\n" +
                    "    .token-box {\n" +
                    "      text-align: center;\n" +
                    "      margin: 20px 0;\n" +
                    "    }\n" +
                    "    .token {\n" +
                    "      display: inline-block;\n" +
                    "      font-size: 24px;\n" +
                    "      font-weight: bold;\n" +
                    "      letter-spacing: 4px;\n" +
                    "      padding: 12px 20px;\n" +
                    "      background: #f3f3f3;\n" +
                    "      border: 2px dashed #4d3b7a;\n" +
                    "      border-radius: 6px;\n" +
                    "      color: #4d3b7a;\n" +
                    "    }\n" +
                    "    .footer {\n" +
                    "      font-size: 12px;\n" +
                    "      color: #777;\n" +
                    "      text-align: center;\n" +
                    "      padding: 15px;\n" +
                    "      background: #f1f1f1;\n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div class=\"container\">\n" +
                    "    <!-- Header -->\n" +
                    "    <div class=\"header\">\n" +
                    "      <img src=\"https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png\" alt=\"Sentinel Logo\">\n" +
                    "      <h2>Sentinel Login Verification</h2>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <!-- Content -->\n" +
                    "    <div class=\"content\">\n" +
                    "      <h1>Hi, <strong>");
            memo.append(email);
            memo.append("</strong></h1>\n" +
                    "      <p>We detected a login attempt on your account. Please confirm it's you by entering the code below.</p>\n" +
                    "\n" +
                    "      <div class=\"token-box\">\n" +
                    "        <div class=\"token\">");
            memo.append(token);
            memo.append("</div>\n" +
                    "      </div>\n" +
                    "\n" +
                    "      <p><strong>Login details:</strong></p>\n" +
                    "      <p>\uD83D\uDCCC <strong>IP Address: </strong>");
            memo.append(inet);
            memo.append("</p>\n" +
                    "      <p>\uD83D\uDC64 <strong>User: </strong>");
            memo.append(user);
            memo.append("</p>\n" +
                    "      <p>\uD83D\uDCBB <strong>Device: </strong>");
            memo.append(device);
            memo.append("</p>\n" +
                    "\n" +
                    "      <p>If this was you, enter the code to continue. If not, please secure your account immediately.</p>\n" +
                    "    </div>\n" +
                    "\n" +
                    "    <!-- Footer -->\n" +
                    "    <div class=\"footer\">\n" +
                    "      &copy; <script>document.write(new Date().getFullYear());</script> Sentinel by Aerosimo Ltd. All rights reserved.<br>\n" +
                    "      This is an automated message, please do not reply.\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>");
            response = Postmaster.sendEmail(email,"Sentinel login notification",memo.toString(),"");
            log.info("Sent login notification email from Sentinel to {}", email);
        } catch (Exception err) {
            response = "Login Email failed";
            log.error("{} failed with email error - {}", LoginMail.class.getName(), err);
        }
        return response;
    }
}