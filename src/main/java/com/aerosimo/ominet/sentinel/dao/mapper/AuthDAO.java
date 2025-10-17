/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      AuthDAO.java                                                    *
 * Created:   11/09/2025, 00:59                                               *
 * Modified:  11/09/2025, 00:59                                               *
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
import com.aerosimo.ominet.sentinel.dao.impl.ForgotResponseDTO;
import com.aerosimo.ominet.sentinel.dao.impl.LoginResponseDTO;
import com.aerosimo.ominet.sentinel.dao.impl.MFAResponseDTO;
import com.aerosimo.ominet.sentinel.dao.impl.SignupResponseDTO;
import com.aerosimo.ominet.sentinel.core.model.Spectre;
import oracle.jdbc.OracleTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.plaf.basic.BasicPasswordFieldUI;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthDAO {

    private static final Logger log = LogManager.getLogger(AuthDAO.class.getName());

    public static SignupResponseDTO signup(String uname, String email, String pword) {
        log.info("Preparing to register new user");
        String token = null;
        String response = "Signup error";
        Connection con = null;
        CallableStatement stmt = null;
        String sql = "{call auth_pkg.signup(?,?,?,?,?)}";
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, uname);
            stmt.setString(2, email);
            stmt.setString(3, pword);
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.registerOutParameter(5, OracleTypes.VARCHAR);
            stmt.execute();
            token = stmt.getString(4);
            response = stmt.getString(5);
            log.info("Successfully complete registering new user steps with the following: {}",response);
        } catch (SQLException err) {
            log.error("Error in auth_pkg (SIGNUP)", err);
            try {
                Spectre.recordError("DB-20001", err.getMessage(), AuthDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.error("Failed closing resources in signup", e);
            }
            log.info("DB Connection for (signup) Closed....");
        }
        return new SignupResponseDTO(token, response);
    }

    public static String verifyEmail(String uname, String email, String verificationToken) {
        log.info("Preparing to verify new user email");
        String response = "Email verification error";
        String sql = "{call auth_pkg.confirmEmail(?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, uname);
            stmt.setString(2, email);
            stmt.setString(3, verificationToken);
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(4);
            log.info("Successfully verified email");
        } catch (SQLException err) {
            log.error("Error in auth_pkg (CONFIRM EMAIL)", err);
            try {
                Spectre.recordError("DB-20002", err.getMessage(), AuthDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.error("Failed closing resources in verifyEmail", e);
            }
            log.info("DB Connection for (verifyEmail) Closed....");
        }
        return response;
    }

    public static LoginResponseDTO login(String email, String pword, String inet, String device) {
        log.info("Preparing to authenticate user");
        String token = null;
        String uname = null;
        String response = "Login error";
        Connection con = null;
        CallableStatement stmt = null;
        String sql = "{call auth_pkg.signin(?,?,?,?,?,?,?)}";
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, pword);
            stmt.setString(3, inet);
            stmt.setString(4, device);
            stmt.registerOutParameter(5, OracleTypes.VARCHAR);
            stmt.registerOutParameter(6, OracleTypes.VARCHAR);
            stmt.registerOutParameter(7, OracleTypes.VARCHAR);
            stmt.execute();
            token = stmt.getString(5);
            uname = stmt.getString(6);
            response = stmt.getString(7);
            log.info("Successfully logged in");
        } catch (SQLException err) {
            log.error("Error in auth_pkg (LOGIN)", err);
            try {
                Spectre.recordError("DB-20003", err.getMessage(), AuthDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.error("Failed closing resources in login", e);
            }
            log.info("DB Connection for (login) Closed....");
        }
        return new LoginResponseDTO(uname, token, response);
    }

    public static MFAResponseDTO confirmMFA(String uname, String email, String mfaCode, String inet, String device) {
        log.info("Preparing to confirm mfa code");
        String token = null;
        String response = "Confirm MFA error";
        Connection con = null;
        CallableStatement stmt = null;
        String sql = "{call auth_pkg.confirmMfa(?,?,?,?,?,?,?)}";
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, uname);
            stmt.setString(2, email);
            stmt.setString(3, mfaCode);
            stmt.setString(4, inet);
            stmt.setString(5, device);
            stmt.registerOutParameter(6, OracleTypes.VARCHAR);
            stmt.registerOutParameter(7, OracleTypes.VARCHAR);
            stmt.execute();
            token = stmt.getString(6);
            response = stmt.getString(7);
            log.info("Successfully verified MFA token with response: {}", response);
        } catch (SQLException err) {
            log.error("Error in auth_pkg (CONFIRM MFA)", err);
            try {
                Spectre.recordError("DB-20004", err.getMessage(), AuthDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.error("Failed closing resources in confirmMFA", e);
            }
            log.info("DB Connection for (confirmMFA) Closed....");
        }
        return new MFAResponseDTO(token, response);
    }

    public static String logout(String uname, String email, String sessionToken) {
        log.info("Preparing to logout user");
        String response = "Logout error";
        String sql = "{call auth_pkg.signout(?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, uname);
            stmt.setString(2, email);
            stmt.setString(3, sessionToken);
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(4);
            log.info("Successfully logged out");
        } catch (SQLException err) {
            log.error("Error in auth_pkg (LOGOUT)", err);
            try {
                Spectre.recordError("DB-20005", err.getMessage(), AuthDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.error("Failed closing resources in logout", e);
            }
            log.info("DB Connection for (logout) Closed....");
        }
        return response;
    }

    public static ForgotResponseDTO forgotPassword(String email) {
        log.info("Preparing to start forgot Password");
        String token = null;
        String uname = null;
        String response = "Forgot password error";
        String sql = "{call auth_pkg.forgotPassword(?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.registerOutParameter(2, OracleTypes.VARCHAR);
            stmt.registerOutParameter(3, OracleTypes.VARCHAR);
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.execute();
            token = stmt.getString(2);
            uname = stmt.getString(3);
            response = stmt.getString(4);
            log.info("Successfully generated new verification code");
        } catch (SQLException err) {
            log.error("Error in auth_pkg (FORGOT PASSWORD)", err);
            try {
                Spectre.recordError("DB-20006", err.getMessage(), AuthDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.error("Failed closing resources in forgotPassword", e);
            }
            log.info("DB Connection for (forgotPassword) Closed....");
        }
        return new ForgotResponseDTO(token, uname, response);
    }

    public static String resetPassword(String email, String verificationToken, String pword) {
        log.info("Preparing to start reset Password");
        String response = "Reset password error";
        String sql = "{call auth_pkg.resetPassword(?,?,?,?)}";
        Connection con = null;
        CallableStatement stmt = null;
        try {
            con = Connect.dbase();
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, verificationToken);
            stmt.setString(3, pword);
            stmt.registerOutParameter(4, OracleTypes.VARCHAR);
            stmt.execute();
            response = stmt.getString(4);
            log.info("Successfully reset password");
            log.info("Reset Password response is : {}", response);
        } catch (SQLException err) {
            log.error("Error in auth_pkg (RESET PASSWORD)", err);
            try {
                Spectre.recordError("DB-20007", err.getMessage(), AuthDAO.class.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                log.error("Failed closing resources in resetPassword", e);
            }
            log.info("DB Connection for (resetPassword) Closed....");
        }
        return response;
    }


}