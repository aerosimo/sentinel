/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AvatarREST.java                                                 *
 * Created:   18/10/2025, 00:46                                               *
 * Modified:  18/10/2025, 00:46                                               *
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

package com.aerosimo.ominet.sentinel.api.rest;

import com.aerosimo.ominet.sentinel.dao.impl.ImageResponseDTO;
import com.aerosimo.ominet.sentinel.dao.mapper.ProfileDAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;

@Path("/avatar")
public class AvatarREST {

    private static final Logger log = LogManager.getLogger(AvatarREST.class.getName());

    @GET
    @Path("/{email}")
    @Produces({"image/jpeg", "image/png"})
    public Response getAvatar(@PathParam("email") String email) {
        log.info("Received avatar fetch request for {}", email);

        try {
            // username not strictly needed, can be same as email
            ImageResponseDTO imageDTO = ProfileDAO.getImage(email, email);

            if (imageDTO == null || imageDTO.getAvatar() == null) {
                log.warn("No avatar found for {}", email);
                return Response.status(Response.Status.NOT_FOUND).build();
            }

            // Convert Base64 string (data:image/png;base64,...) back to bytes
            String base64Data = imageDTO.getAvatar();
            if (base64Data.startsWith("data:image")) {
                base64Data = base64Data.substring(base64Data.indexOf(",") + 1);
            }

            byte[] imageBytes = java.util.Base64.getDecoder().decode(base64Data);

            // Detect MIME type based on content (or fallback)
            String mimeType = "image/png";
            if (imageDTO.getAvatar().startsWith("data:image/jpeg")) {
                mimeType = "image/jpeg";
            }

            return Response.ok(new ByteArrayInputStream(imageBytes), mimeType)
                    .header("Cache-Control", "max-age=86400") // cache 1 day
                    .build();

        } catch (Exception e) {
            log.error("Error retrieving avatar for {}: {}", email, e.getMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving avatar for " + email)
                    .build();
        }
    }
}