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
import com.aerosimo.ominet.sentinel.dao.impl.LoginResponseDTO;
import com.aerosimo.ominet.sentinel.dao.impl.MFAResponseDTO;
import com.aerosimo.ominet.sentinel.dao.impl.SignupResponseDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthDAO {

    private static final Logger log = LogManager.getLogger(AuthDAO.class.getName());

    static String response;
    static String token;
    static String sql;
    static Connection con;
    static {
        con = Connect.dbase();
    }
    static CallableStatement stmt;

    public static SignupResponseDTO signup(String email, String pword, String modifiedBy) {
        log.info("Preparing to register new user");
        try {
            sql = "{call auth_pkg.signup(?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, pword);
            stmt.setString(3, modifiedBy);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(5, java.sql.Types.VARCHAR);
            stmt.execute();
            token = stmt.getString(4);
            response = stmt.getString(5);
            log.info("Successfully register new user");
        } catch (SQLException err) {
            token =  null;
            response = "Signup error";
            log.error("Unknown error occurred in auth_pkg (SIGNUP) with the following - {}", AuthDAO.class.getName(), err);
            ErrorVaultDAO.storeError("DB-20001",err.getMessage(),AuthDAO.class.getName());
        }
        return new SignupResponseDTO(token, response);
    }

    public static String verifyemail(String email, String VerificationToken, String modifiedBy) {
        log.info("Preparing to verify email");
        try {
            sql = "{call auth_pkg.confirm_email(?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, VerificationToken);
            stmt.setString(3, modifiedBy);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(4);
            log.info("Successfully verify email");
        } catch (SQLException err) {
            response = "Email verification error";
            log.error("Unknown error occurred in auth_pkg (CONFIRM EMAIL) with the following - {}", AuthDAO.class.getName(), err);
            ErrorVaultDAO.storeError("DB-20002",err.getMessage(),AuthDAO.class.getName());
        }
        return response;
    }

    public static LoginResponseDTO login(String email, String pword, String inet, String device, String modifiedBy) {
        log.info("Preparing to login");
        try{
            sql = "{call auth_pkg.login(?,?,?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, pword);
            stmt.setString(3, inet);
            stmt.setString(4, device);
            stmt.setString(5, modifiedBy);
            stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(7, java.sql.Types.VARCHAR);
            stmt.execute();
            token = stmt.getString(6);
            response = stmt.getString(7);
            log.info("Successfully login");
        } catch (SQLException err) {
            token = null;
            response = "Login error";
            log.error("Unknown error occurred in auth_pkg (LOGIN) with the following - {}", AuthDAO.class.getName(), err);
            ErrorVaultDAO.storeError("DB-20003",err.getMessage(),AuthDAO.class.getName());
        }

        return  new LoginResponseDTO(token,response);
    }

    public static MFAResponseDTO confirmMFA(String email, String MFACode, String inet, String device, String modifiedBy) {
        log.info("Preparing to verify MFA token");
        try{
            sql = "{call auth_pkg.confirm_mfa(?,?,?,?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, MFACode);
            stmt.setString(3, inet);
            stmt.setString(4, device);
            stmt.setString(5, modifiedBy);
            stmt.registerOutParameter(6, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(7, java.sql.Types.VARCHAR);
            stmt.execute();
            token = stmt.getString(6);
            response = stmt.getString(7);
            log.info("Successfully verify mfa token");
        } catch (SQLException err) {
            token = null;
            response = "confirm mfa error";
            log.error("Unknown error occurred in auth_pkg (CONFIRM MFA) with the following - {}", AuthDAO.class.getName(), err);
            ErrorVaultDAO.storeError("DB-20004",err.getMessage(),AuthDAO.class.getName());
        }
        return new MFAResponseDTO(token,response);
    }

    public static String logout(String email, String SessionToken, String modifiedBy) {
        log.info("Preparing to logout");
        try{
            sql = "{call auth_pkg.logout(?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, SessionToken);
            stmt.setString(3, modifiedBy);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(4);
            log.info("Successfully logout");
        } catch (SQLException err){
            response = "Logout error";
            log.error("Unknown error occurred in auth_pkg (LOGOUT) with the following - {}", AuthDAO.class.getName(), err);
            ErrorVaultDAO.storeError("DB-20005",err.getMessage(),AuthDAO.class.getName());
        }
        return response;
    }

    public static SignupResponseDTO forgotPassword(String email, String modifiedBy) {
        log.info("Preparing to process forgot password");
        try{
            sql = "{call auth_pkg.forgot_password(?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, modifiedBy);
            stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            stmt.registerOutParameter(4, java.sql.Types.VARCHAR);
            stmt.execute();
            token = stmt.getString(3);
            response = stmt.getString(4);
            log.info("Successfully generate new verification code");
        } catch (SQLException err){
            token = null;
            response = "forgot password error";
            log.error("Unknown error occurred in auth_pkg (FORGOT PASSWORD) with the following - {}", AuthDAO.class.getName(), err);
            ErrorVaultDAO.storeError("DB-20005",err.getMessage(),AuthDAO.class.getName());
        }
        return new SignupResponseDTO(token,response);
    }

    public static String resetPassword(String email, String VerificationToken, String pword, String modifiedBy) {
        log.info("Preparing to reset password");
        try {
            sql = "{call auth_pkg.reset_password(?,?,?,?)}";
            stmt = con.prepareCall(sql);
            stmt.setString(1, email);
            stmt.setString(2, VerificationToken);
            stmt.setString(3, pword);
            stmt.setString(4, modifiedBy);
            stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
            stmt.execute();
            response = stmt.getString(4);
            log.info("Successfully reset password");
        } catch (SQLException err){
            response = "Reset password error";
            log.error("Unknown error occurred in auth_pkg (RESET PASSWORD) with the following - {}", AuthDAO.class.getName(), err);
            ErrorVaultDAO.storeError("DB-20006",err.getMessage(),AuthDAO.class.getName());
        }
        return response;
    }
}