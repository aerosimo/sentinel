/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      SilhouetteDAO.java                                              *
 * Created:   26/09/2025, 12:43                                               *
 * Modified:  26/09/2025, 12:43                                               *
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

package com.aerosimo.ominet.sentinel.dao.mapper;

import com.aerosimo.ominet.sentinel.core.config.Connect;
import com.aerosimo.ominet.sentinel.dao.impl.*;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SilhouetteDAO {

    private static final Logger log = LogManager.getLogger(SilhouetteDAO.class.getName());

    public static SilhouetteResponseDTO GetSilhouette(String email) {
        log.info("Fetching silhouette profile for user: {}", email);
        String sql = "{call profile_pkg.GetSilhouette(?,?,?,?,?,?,?)}";

        Connection con = null;
        CallableStatement stmt = null;
        SilhouetteResponseDTO silhouette = new SilhouetteResponseDTO();

        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.registerOutParameter(2, OracleTypes.CURSOR); // person
            stmt.registerOutParameter(3, OracleTypes.CURSOR); // avatar
            stmt.registerOutParameter(4, OracleTypes.CURSOR); // address
            stmt.registerOutParameter(5, OracleTypes.CURSOR); // contact
            stmt.registerOutParameter(6, OracleTypes.CURSOR); // profile
            stmt.registerOutParameter(7, OracleTypes.CURSOR); // horoscope

            stmt.execute();

            // Person
            Object personObj = stmt.getObject(2);
            if (personObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        PersonResponseDTO person = new PersonResponseDTO(
                                rs.getString("email"),
                                rs.getString("title"),
                                rs.getString("FIRSTNAME"),
                                rs.getString("middleName"),
                                rs.getString("lastName"),
                                rs.getString("zodiac"),
                                rs.getString("gender"),
                                rs.getString("birthday"),
                                rs.getString("age"),
                                rs.getString("modifiedBy"),
                                rs.getString("modifiedDate")
                        );
                        silhouette.setPerson(person);
                    }
                }
            }

            // Image
            Object imageObj = stmt.getObject(3);
            if (imageObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        ImageResponseDTO image = new ImageResponseDTO(
                                rs.getString("email"),
                                rs.getString("avatar"),
                                rs.getString("modifiedBy"),
                                rs.getString("modifiedDate")
                        );
                        silhouette.setImage(image);
                    }
                }
            }

            // Address
            Object addressObj = stmt.getObject(4);
            if (addressObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        AddressResponseDTO address = new AddressResponseDTO(
                                rs.getString("email"),
                                rs.getString("firstline"),
                                rs.getString("secondline"),
                                rs.getString("thirdline"),
                                rs.getString("city"),
                                rs.getString("postcode"),
                                rs.getString("country"),
                                rs.getString("modifiedBy"),
                                rs.getString("modifiedDate")
                        );
                        silhouette.setAddress(address);
                    }
                }
            }

            // Contacts
            Object contactObj = stmt.getObject(5);
            List<ContactResponseDTO> contacts = new ArrayList<>();
            if (contactObj instanceof ResultSet rs) {
                try (rs) {
                    while (rs.next()) {
                        ContactResponseDTO contact = new ContactResponseDTO(
                                rs.getString("email"),
                                rs.getString("channel"),
                                rs.getString("address"),
                                rs.getString("consent"),
                                rs.getString("modifiedBy"),
                                rs.getString("modifiedDate")
                        );
                        contacts.add(contact);
                    }
                }
            }
            silhouette.setContacts(contacts);

            // Profile
            Object profileObj = stmt.getObject(6);
            if (profileObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        ProfileResponseDTO profile = new ProfileResponseDTO(
                                rs.getString("email"),
                                rs.getString("maritalStatus"),
                                rs.getString("height"),
                                rs.getString("weight"),
                                rs.getString("ethnicity"),
                                rs.getString("religion"),
                                rs.getString("eyeColour"),
                                rs.getString("phenotype"),
                                rs.getString("genotype"),
                                rs.getString("disability"),
                                rs.getString("modifiedBy"),
                                rs.getString("modifiedDate")
                        );
                        silhouette.setProfile(profile);
                    }
                }
            }

            // Horoscope
            Object horoscopeObj = stmt.getObject(7);
            if (horoscopeObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        HoroscopeResponseDTO horoscope = new HoroscopeResponseDTO(
                                rs.getString("zodiac"),
                                rs.getString("currentDay"),
                                rs.getString("narrative"),
                                rs.getString("modifiedBy"),
                                rs.getString("modifiedDate")
                        );
                        silhouette.setHoroscope(horoscope);
                    }
                }
            }

        } catch (SQLException err) {
            log.error("Error in ProfileDAO.GetSilhouette for {} - {}", email, err.getMessage(), err);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.error("Failed closing resources in GetSilhouette", e);
            }
        }
        return silhouette;
    }
}