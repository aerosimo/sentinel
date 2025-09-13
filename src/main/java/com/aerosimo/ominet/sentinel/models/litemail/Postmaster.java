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

import com.aerosimo.ominet.sentinel.core.config.Connect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.IOException;
import java.util.Date;

public class Postmaster {

    private static final Logger log = LogManager.getLogger(Postmaster.class.getName());
    static String response;
    static MimeBodyPart messageBodyPart;
    static Multipart multipart;
    static Message msg;

    public static String sendEmail(String emailAddress, String emailSubject, String emailMessage, String emailFiles,
                                   String emailFrom, String emailContentType) {
        response = "Message sent successfully";
        try {
            msg = new MimeMessage(Connect.email());
            msg.setFrom(new InternetAddress(emailFrom));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
            msg.setSubject(emailSubject);
            msg.setSentDate(new Date());
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(emailMessage, emailContentType + "; charset=UTF-8");
            multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            if (!emailFiles.isEmpty()) {
                String[] attachFiles = emailFiles.split(",");
                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    attachPart.attachFile(filePath);
                    multipart.addBodyPart(attachPart);
                }
            }
            msg.setContent(multipart);
            Transport.send(msg);
            log.info("Message sent successfully");
        } catch (MessagingException | IOException err) {
            response = "Message not successful";
            log.error("Email Notification Service failed with the following - {}", Postmaster.class.getName(), err);
        }
        return response;
    }
}