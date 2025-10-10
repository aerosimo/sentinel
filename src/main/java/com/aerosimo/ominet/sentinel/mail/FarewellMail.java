/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      FarewellMail.java                                               *
 * Created:   10/10/2025, 23:14                                               *
 * Modified:  10/10/2025, 23:14                                               *
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

package com.aerosimo.ominet.sentinel.mail;

import com.aerosimo.ominet.sentinel.core.model.Postmaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FarewellMail {

    private static final Logger log;

    static {
        log = LogManager.getLogger(FarewellMail.class.getName());
    }

    static String response;

    public static String sendMail(String uname, String email) {
        log.info("Preparing email body content to the following email address: {}", email);
        StringBuilder memo;
        try {
            memo = new StringBuilder("<!DOCTYPE html>\n" +
                    "<html lang=\"en\" style=\"font-family: 'Outfit', Arial, sans-serif; background-color: #f8f9fa; margin: 0; padding: 0;\">\n" +
                    "\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Sentinel Account Deletion</title>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body style=\"margin:0; padding:0;\">\n" +
                    "    <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#f8f9fa; padding:40px 0;\">\n" +
                    "        <tr>\n" +
                    "            <td align=\"center\">\n" +
                    "                <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:#ffffff; border-radius:8px; overflow:hidden; box-shadow:0 2px 8px rgba(0,0,0,0.05);\">\n" +
                    "                    <tr>\n" +
                    "                        <td align=\"center\" style=\"background-color:#4d3b7a; padding:20px;\">\n" +
                    "                            <div class=\"header\">\n" +
                    "                                <img src=\"https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png\" alt=\"Sentinel Logo\">\n" +
                    "                            </div>\n" +
                    "                            <h2 style=\"color:#ffffff; margin:0; font-weight:600;\">Sentinel</h2>\n" +
                    "                            <p style=\"color:#d6c9ff; margin:0; font-size:14px;\">Diligent and watchful guardian</p>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                    <tr>\n" +
                    "                        <td style=\"padding:30px;\">\n" +
                    "                            <h3 style=\"color:#4d3b7a; font-weight:600; margin-top:0;\">Sorry to See You Go</h3>\n" +
                    "                            <p style=\"color:#444; line-height:1.6; font-size:15px;\">\n" +
                    "                                Hi <b>");
            memo.append(uname);
            memo.append("</b>,\n" +
                    "                            </p>\n" +
                    "                            <p style=\"color:#444; line-height:1.6; font-size:15px;\">\n" +
                    "                                We’ve received your request to delete your Sentinel account associated with\n" +
                    "                                <b>");
            memo.append(email);
            memo.append("</b>. Your account and all associated data have now been permanently removed from our systems.\n" +
                    "                            </p>\n" +
                    "                            <p style=\"color:#444; line-height:1.6; font-size:15px;\">\n" +
                    "                                We’re truly sorry to see you go. If this was a mistake or if you change your mind, you’re always welcome to return — creating a new Sentinel account takes only a few moments.\n" +
                    "                            </p>\n" +
                    "                            <p style=\"color:#444; line-height:1.6; font-size:15px;\">\n" +
                    "                                Should you have any questions or need assistance, please contact our support team at\n" +
                    "                                <a href=\"mailto:support@aerosimo.com\" style=\"color:#4d3b7a; text-decoration:none;\">support@aerosimo.com</a>.\n" +
                    "                            </p>\n" +
                    "\n" +
                    "                            <div style=\"margin-top:30px; text-align:center;\">\n" +
                    "                                <a href=\"https://ominet.aerosimo.com/sentinel\" style=\"display:inline-block; background-color:#4d3b7a; color:#ffffff; text-decoration:none; padding:10px 20px; border-radius:4px; font-weight:500;\">\n" +
                    "                                   Visit Sentinel\n" +
                    "                                </a>\n" +
                    "                            </div>\n" +
                    "\n" +
                    "                            <hr style=\"border:none; border-top:1px solid #eee; margin:30px 0;\">\n" +
                    "\n" +
                    "                            <p style=\"color:#888; font-size:13px; line-height:1.5; text-align:center;\">\n" +
                    "                                This message was sent by <b>Sentinel</b> — an Aerosimo Ltd service.\n" +
                    "                                <br> &copy;\n" +
                    "                                <script>\n" +
                    "                                    document.write(new Date().getFullYear());\n" +
                    "                                </script> Aerosimo Ltd. All rights reserved.\n" +
                    "                            </p>\n" +
                    "                        </td>\n" +
                    "                    </tr>\n" +
                    "                </table>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>");
            response = Postmaster.sendEmail(email," \uD83D\uDC8C Sentinel Account Deletion Confirmation — Sorry to See You Go",memo.toString(),"");
            log.info("Sent Account Deletion Confirmation email from Sentinel to {}", email);
        } catch (Exception err) {
            response = "Account Deletion Confirmation Email failed";
            log.error("{} failed with email error - {}", FarewellMail.class.getName(), err);
        }
        return response;
    }
}