/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      ErrorVaultDAO.java                                                 *
 * Created:   11/09/2025, 00:28                                               *
 * Modified:  11/09/2025, 00:28                                               *
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class ErrorVaultDAO {

    private static final Logger log;

    static {
        log = LogManager.getLogger(ErrorVaultDAO.class.getName());
    }

    static String response;
    static String sql;
    static Connection con;
    static CallableStatement stmt;

    public static String storeError(String faultCode, String faultMessage, String faultService) {
        log.info("Preparing to store new error into error vault");
        try {
            sql = "{call error_vault_pkg.store_error(?,?,?,?)}";
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, faultCode);
            stmt.setString(2, faultMessage);
            stmt.setString(3, faultService);
            stmt.registerOutParameter(4, Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(4);
            log.info("Successfully store new error into error vault");
        } catch (SQLException err) {
            response = "ErrorVaultDAO (storeError) attempt failed";
            log.error("Unknown error occurred in ErrorVaultDAO (storeError) with the following - {}", ErrorVaultDAO.class.getName(), err);
        }
        return response;
    }
}