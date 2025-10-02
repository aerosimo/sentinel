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
import com.aerosimo.ominet.sentinel.models.utils.Spectre;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
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
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getString(10),
                                rs.getString(11)
                        );
                        silhouette.setPerson(person);
                        log.info("DAO call person = '{}'", person.toString());
                    }
                }
            }

            // Image
            Object imageObj = stmt.getObject(3);
            if (imageObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        ImageResponseDTO image = new ImageResponseDTO(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4)
                        );
                        silhouette.setImage(image);
                        log.info("DAO call image = '{}'", image.toString());
                    }
                }
            }

            // Address
            Object addressObj = stmt.getObject(4);
            if (addressObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        AddressResponseDTO address = new AddressResponseDTO(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9)
                        );
                        silhouette.setAddress(address);
                        log.info("DAO call address = '{}'", address.toString());
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
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6)
                        );
                        contacts.add(contact);
                    }
                }
            }
            silhouette.setContacts(contacts);
            log.info("DAO call contacts = '{}'", contacts.toString());

            // Profile
            Object profileObj = stmt.getObject(6);
            if (profileObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        ProfileResponseDTO profile = new ProfileResponseDTO(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getString(10),
                                rs.getString(11),
                                rs.getString(12)
                        );
                        silhouette.setProfile(profile);
                        log.info("DAO call profile = '{}'", profile.toString());
                    }
                }
            }

            // Horoscope
            Object horoscopeObj = stmt.getObject(7);
            if (horoscopeObj instanceof ResultSet rs) {
                try (rs) {
                    if (rs.next()) {
                        HoroscopeResponseDTO horoscope = new HoroscopeResponseDTO(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5)
                        );
                        silhouette.setHoroscope(horoscope);
                        log.info("DAO call horoscope = '{}'", horoscope.toString());
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

    public static String saveImage(String email, String avatar, String modifiedBy) {
        log.info("Preparing to save user avatar");
        String response = "";
        String sql = "{call profile_pkg.SaveImage(?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, avatar);
            stmt.setString(3, modifiedBy);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(4);
            log.info("Successfully save image");
        } catch (SQLException err) {
            log.error("Error in saving image", err);
            try {
                Spectre.recordError("DB-20008", err.getMessage(), SilhouetteDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // Close the statement and connection
                try {
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    log.error("Failed closing resources in SaveImage", e);
                }
                log.info("DB Connection for (SaveImage) Closed....");
            }
        }
        return response;
    }

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
                Spectre.recordError("DB-20009", err.getMessage(), SilhouetteDAO.class.getName());
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
                Spectre.recordError("DB-20010", err.getMessage(), SilhouetteDAO.class.getName());
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
                Spectre.recordError("DB-20011", err.getMessage(), SilhouetteDAO.class.getName());
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
                Spectre.recordError("DB-20012", err.getMessage(), SilhouetteDAO.class.getName());
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