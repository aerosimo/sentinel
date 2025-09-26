/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      ProfileDAO.java                                                 *
 * Created:   25/09/2025, 17:11                                               *
 * Modified:  25/09/2025, 17:11                                               *
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
import com.aerosimo.ominet.sentinel.dao.impl.HoroscopeResponseDTO;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class ProfileDAO {

    private static final Logger log = LogManager.getLogger(ProfileDAO.class.getName());

    public static HoroscopeResponseDTO GetHoroscope(String email) {
        log.info("Preparing to fetch daily horoscope for this email : {}", email);
        String sql = "{call profile_pkg.GetHoroscope(?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        HoroscopeResponseDTO horoscopeList = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet rs = (ResultSet) stmt.getObject(2);
            if (rs.next()) {
                horoscopeList = new HoroscopeResponseDTO(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                );
            }
        } catch (SQLException err) {
            log.error("Database adaptor error (GetHoroscope) occurred with the following - {}", ProfileDAO.class.getName(), err);
        } finally {
            // Close the statement and connection
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            log.info("DB Connection for (getTopErrors) Closed....");
        }
        log.info("Horoscope fetch completed for email: {}", email);
        return horoscopeList;
    }

}