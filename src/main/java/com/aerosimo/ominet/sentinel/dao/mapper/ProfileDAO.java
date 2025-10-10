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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class ProfileDAO {

    private static final Logger log = LogManager.getLogger(ProfileDAO.class.getName());

    public static String savePerson(String email, String title, String firstName, String middleName, String lastName,
                                    String gender, Date birthday, String modifiedBy) {
        log.info("Preparing to save user personal record");
        String response = "";
        String sql = "{call profile_pkg.SavePerson(?,?,?,?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, title);
            stmt.setString(3, firstName);
            stmt.setString(4, middleName);
            stmt.setString(5, lastName);
            stmt.setString(6, gender);
            stmt.setDate(7, birthday);
            stmt.setString(8, modifiedBy);
            stmt.registerOutParameter(9, java.sql.Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(9);
            log.info("Successfully save person record");
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

    public static String saveAddress(String email, String firstline, String secondline, String thirdline, String city,
                                     String postcode, String country, String modifiedBy) {
        log.info("Preparing to save user address record");
        String response = "";
        String sql = "{call profile_pkg.SaveAddress(?,?,?,?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, firstline);
            stmt.setString(3, secondline);
            stmt.setString(4, thirdline);
            stmt.setString(5, city);
            stmt.setString(6, postcode);
            stmt.setString(7, country);
            stmt.setString(8, modifiedBy);
            stmt.registerOutParameter(9, java.sql.Types.VARCHAR);
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

    public static String saveContact(String email, String channel, String address, String consent, String modifiedBy) {
        log.info("Preparing to save user contact record");
        String response = "";
        String sql = "{call profile_pkg.SaveContact(?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, channel);
            stmt.setString(3, address);
            stmt.setString(4, consent);
            stmt.setString(5, modifiedBy);
            stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
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

    public static String saveProfile(String email, String maritalstatus, String height, String weight, String ethnicity,
                                     String religion, String eyecolour, String phenotype, String genotype, String disability, String modifiedBy) {
        log.info("Preparing to save user profile record");
        String response = "";
        String sql = "{call profile_pkg.SaveProfile(?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, maritalstatus);
            stmt.setString(3, height);
            stmt.setString(4, weight);
            stmt.setString(5, ethnicity);
            stmt.setString(6, religion);
            stmt.setString(7, eyecolour);
            stmt.setString(8, phenotype);
            stmt.setString(9, genotype);
            stmt.setString(10, disability);
            stmt.setString(11, modifiedBy);
            stmt.registerOutParameter(12, java.sql.Types.VARCHAR);
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