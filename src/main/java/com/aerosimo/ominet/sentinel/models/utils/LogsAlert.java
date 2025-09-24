/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      LogsAlert.java                                                  *
 * Created:   24/09/2025, 00:38                                               *
 * Modified:  24/09/2025, 00:38                                               *
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

package com.aerosimo.ominet.sentinel.models.utils;

import com.aerosimo.ominet.sentinel.core.config.Connect;
import com.aerosimo.ominet.sentinel.dao.impl.AlertResponseDTO;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogsAlert {

    private static final Logger log = LogManager.getLogger(LogsAlert.class.getName());

    public static List<AlertResponseDTO> getTopAlerts(int records){
        log.info("Preparing to fetch new top logs and alerts");
        List<AlertResponseDTO> alertList = new ArrayList<>();
        String sql = "{call alert_log_pkg.get_alerts(?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try{
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setInt(1, records);
            stmt.registerOutParameter(2, OracleTypes.CURSOR);
            stmt.execute();
            ResultSet rs = (ResultSet) stmt.getObject(2);
            while (rs.next()) alertList.add(new AlertResponseDTO(rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)));
        } catch (SQLException err) {
            log.error("Database adaptor error (getTopAlerts) occurred with the following - {}", LogsAlert.class.getName(), err);
        } finally {
            // Close the statement and connection
            try {
                stmt.close();
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            log.info("DB Connection for (getTopAlerts) Closed....");
        }
        return alertList;
    }

}