/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      ProfileDAO.java                                                 *
 * Created:   10/10/2025, 19:48                                               *
 * Modified:  10/10/2025, 19:48                                               *
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
import com.aerosimo.ominet.sentinel.core.model.Spectre;
import com.aerosimo.ominet.sentinel.dao.impl.ImageResponseDTO;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.Base64;

public class ProfileDAO {

    private static final Logger log = LogManager.getLogger(ProfileDAO.class.getName());

    public static String saveImage(String uname, String email, InputStream avatarStream) {
        log.info("Preparing to save user Avatar");
        String response = "";
        String sql = "{call profile_pkg.saveImage(?,?,?,?)}";
        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {

                stmt.setString(1, uname);
                stmt.setString(2, email);
                stmt.setBlob(3, avatarStream);
                stmt.registerOutParameter(4, OracleTypes.VARCHAR);
                stmt.execute();
                response = stmt.getString(4);
            log.info("Image saved for user {} -> {}", email, response);
            } catch (SQLException err) {
                log.error("Error saving image for {}", email,err);
                try {
                    Spectre.recordError("DB-20008", err.getMessage(), SilhouetteDAO.class.getName());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } finally {
            log.info("DB Connection for (saveImage) Closed....");
        }
        return response;
    }

    public static ImageResponseDTO getImage(String uname, String email) {
        log.info("Preparing to retrieve user Avatar");
        ImageResponseDTO response = null;
        String sql = "{call profile_pkg.getImage(?,?,?)}";

        try (Connection con = Connect.dbase();
             CallableStatement stmt = con.prepareCall(sql)) {
                stmt.setString(1, uname);
                stmt.setString(2, email);
                stmt.registerOutParameter(3, OracleTypes.CURSOR);
                stmt.execute();
                try (ResultSet rs = (ResultSet) stmt.getObject(3)) {
                    if (rs != null && rs.next()) {
                        response = new ImageResponseDTO();
                        response.setUsername(rs.getString("username"));
                        response.setEmail(rs.getString("email"));
                        response.setModifiedBy(rs.getString("modifiedBy"));
                        response.setModifiedDate(rs.getString("modifiedDate"));
                        Blob blob = rs.getBlob("avatar");
                        if (blob != null) {
                            byte[] bytes = blob.getBytes(1, (int) blob.length());
                            blob.free();
                            String base64 = Base64.getEncoder().encodeToString(bytes);
                            response.setAvatar("data:image/png;base64," + base64);
                        } else {
                            response.setAvatar(null);
                        }
                    }
                }
            } catch (SQLException err) {
                log.error("Error retrieving avatar for {}", email, err);
                try {
                    Spectre.recordError("DB-20008", err.getMessage(), SilhouetteDAO.class.getName());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } finally {
            log.info("DB Connection for (getImage) Closed....");
        }
        return response;
    }


    public static String savePerson(String uname, String email, String title, String firstName, String middleName, String lastName,
                                    String gender, Date birthday) {
        log.info("Preparing to save user personal record");
        String response = "";
        String sql = "{call profile_pkg.savePerson(?,?,?,?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, uname);
            stmt.setString(2, email);
            stmt.setString(3, title);
            stmt.setString(4, firstName);
            stmt.setString(5, middleName);
            stmt.setString(6, lastName);
            stmt.setString(7, gender);
            stmt.setDate(8, birthday);
            stmt.registerOutParameter(9, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(9);
            log.info("Successfully save person record with response: {}", response);
        } catch (SQLException err) {
            log.error("Error in saving person record", err);
            try {
                Spectre.recordError("DB-20009", err.getMessage(), ProfileDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // Close the statement and connection
                try {
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    log.error("Failed closing resources in SavePerson", e);
                }
                log.info("DB Connection for (SavePerson) Closed....");
            }
        }
        return response;
    }

    public static String saveAddress(String uname, String email, String firstline, String secondline, String thirdline, String city, String postcode, String country) {
        log.info("Preparing to save user address record");
        String response = "";
        String sql = "{call profile_pkg.saveAddress(?,?,?,?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, uname);
            stmt.setString(2, email);
            stmt.setString(3, firstline);
            stmt.setString(4, secondline);
            stmt.setString(5, thirdline);
            stmt.setString(6, city);
            stmt.setString(7, postcode);
            stmt.setString(8, country);
            stmt.registerOutParameter(9, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(9);
            log.info("Successfully save address record");
        } catch (SQLException err) {
            log.error("Error in saving address record", err);
            try {
                Spectre.recordError("DB-20010", err.getMessage(), ProfileDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // Close the statement and connection
                try {
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    log.error("Failed closing resources in SaveAddress", e);
                }
                log.info("DB Connection for (SaveAddress) Closed....");
            }
        }
        return response;
    }

    public static String saveContact(String uname, String email, String channel, String address, String consent) {
        log.info("Preparing to save user contact record");
        String response = "";
        String sql = "{call profile_pkg.saveContact(?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, uname);
            stmt.setString(2, email);
            stmt.setString(3, channel);
            stmt.setString(4, address);
            stmt.setString(5, consent);
            stmt.registerOutParameter(6, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(6);
            log.info("Successfully save contacts record");
        } catch (SQLException err) {
            log.error("Error in saving contacts record", err);
            try {
                Spectre.recordError("DB-20011", err.getMessage(), ProfileDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // Close the statement and connection
                try {
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    log.error("Failed closing resources in SaveContact", e);
                }
                log.info("DB Connection for (SaveContact) Closed....");
            }
        }
        return response;
    }

    public static String saveProfile(String uname, String email, String maritalstatus, String height, String weight, String ethnicity,
                                     String religion, String eyecolour, String phenotype, String genotype, String disability) {
        log.info("Preparing to save user profile record");
        String response = "";
        String sql = "{call profile_pkg.saveProfile(?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, uname);
            stmt.setString(2, email);
            stmt.setString(3, maritalstatus);
            stmt.setString(4, height);
            stmt.setString(5, weight);
            stmt.setString(6, ethnicity);
            stmt.setString(7, religion);
            stmt.setString(8, eyecolour);
            stmt.setString(9, phenotype);
            stmt.setString(10, genotype);
            stmt.setString(11, disability);
            stmt.registerOutParameter(12, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(12);
            log.info("Successfully save profile record");
        } catch (SQLException err) {
            log.error("Error in saving profile record", err);
            try {
                Spectre.recordError("DB-20012", err.getMessage(), ProfileDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // Close the statement and connection
                try {
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    log.error("Failed closing resources in SaveProfile", e);
                }
                log.info("DB Connection for (SaveProfile) Closed....");
            }
        }
        return response;
    }
}