/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      ResetMail.java                                                  *
 * Created:   15/09/2025, 18:33                                               *
 * Modified:  15/09/2025, 18:33                                               *
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

public class ResetMail {

    private static final Logger log;

    static {
        log = LogManager.getLogger(ResetMail.class.getName());
    }

    static String response;

    public static String sendMail(String email) {
        log.info("Preparing email body content to the following email address: {}", email);
        StringBuilder memo;
        try {
            memo = new StringBuilder("<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <meta charset=\"UTF-8\">\n" +
                    "  <title>Sentinel - Password Reset Successful</title>\n" +
                    "  <style>\n" +
                    "    body {\n" +
                    "      font-family: Arial, sans-serif;\n" +
                    "      background-color: #f4f6f9;\n" +
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
                    "      background: #4d3b7a;\n" +
                    "      color: #ffffff;\n" +
                    "      padding: 20px;\n" +
                    "      text-align: center;\n" +
                    "    }\n" +
                    "    .header h1 {\n" +
                    "      margin: 0;\n" +
                    "      font-size: 20px;\n" +
                    "    }\n" +
                    "    .content {\n" +
                    "      padding: 20px;\n" +
                    "      line-height: 1.6;\n" +
                    "    }\n" +
                    "    .footer {\n" +
                    "      background: #f4f6f9;\n" +
                    "      text-align: center;\n" +
                    "      padding: 15px;\n" +
                    "      font-size: 12px;\n" +
                    "      color: #666;\n" +
                    "    }\n" +
                    "  </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "  <div class=\"container\">\n" +
                    "    <div class=\"header\">\n" +
                    "    <img src=\"https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png\" alt=\"Sentinel Logo\">\n" +
                    "      <h1>Password Reset Successful</h1>\n" +
                    "    </div>\n" +
                    "    <div class=\"content\">\n" +
                    "      <p>Hi <strong>");
            memo.append(email);
            memo.append("</strong>,</p>\n" +
                    "      <p>This is to notify you that your Sentinel Account password has been changed successfully.</p>\n" +
                    "      <p>If you did not initiate this change, please contact our support team immediately to secure your Account.</p>\n" +
                    "      <p>Stay safe, and thank you for trusting <strong>Sentinel</strong> – your diligent and watchful server guardian.</p>\n" +
                    "    </div>\n" +
                    "    <div class=\"footer\">\n" +
                    "      &copy; <script>document.write(new Date().getFullYear());</script> Sentinel by Aerosimo Ltd. All rights reserved.\n" +
                    "    </div>\n" +
                    "  </div>\n" +
                    "</body>\n" +
                    "</html>");
            response = Postmaster.sendEmail(email, "Sentinel Password Reset Notification", memo.toString(), "");
            log.info("Sent password reset email from Sentinel to {}", email);
        } catch (Exception err) {
            response = "Reset email failed";
            log.error("{} failed with email error - {}", ResetMail.class.getName(), err);
        }
        return response;
    }
}