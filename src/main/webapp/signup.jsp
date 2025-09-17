<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance sentinel project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      signup.html                                                    ~
  ~ Created:   13/09/2025, 01:28                                              ~
  ~ Modified:  14/09/2025, 17:59                                              ~
  ~                                                                           ~
  ~ Copyright (c)  2025.  Aerosimo Ltd                                        ~
  ~                                                                           ~
  ~ Permission is hereby granted, free of charge, to any person obtaining a   ~
  ~ copy of this software and associated documentation files (the "Software"),~
  ~ to deal in the Software without restriction, including without limitation ~
  ~ the rights to use, copy, modify, merge, publish, distribute, sublicense,  ~
  ~ and/or sell copies of the Software, and to permit persons to whom the     ~
  ~ Software is furnished to do so, subject to the following conditions:      ~
  ~                                                                           ~
  ~ The above copyright notice and this permission notice shall be included   ~
  ~ in all copies or substantial portions of the Software.                    ~
  ~                                                                           ~
  ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,           ~
  ~ EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES           ~
  ~ OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                  ~
  ~ NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                ~
  ~ HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,              ~
  ~ WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING              ~
  ~ FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                ~
  ~ OR OTHER DEALINGS IN THE SOFTWARE.                                        ~
  ~                                                                           ~
  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"
          name="viewport"/>
    <meta content="Elijah Omisore" name="author">
    <meta content="sentinel 1.0.0" name="generator">
    <meta content="sentinel" name="application-name">
    <meta content="IE=edge" http-equiv="X-UA-Compatible">
    <meta content="A focal point, as of activity" name="description">
    <meta content="sentinel" name="apple-mobile-web-app-title">
    <meta content="Oracle, Java, Tomcat, Maven, Jenkins, Bitbucket, Github, MFT" name="keywords">
    <!-- Title -->
    <title>Sign up | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Bootstrap CSS (local) -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/auth.css" rel="stylesheet">
</head>
<body>

<div class="d-flex align-items-center justify-content-center min-vh-100">
    <div class="row g-0 login-wrap w-100">
        <div class="col-md-6 left-pane d-flex flex-column justify-content-center">
            <div class="form text-center">
                <img src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png" alt="Your Logo" class="mb-3" style="max-width:120px;">
            </div>
            <h1>Signup</h1>
            <p>Please register to make your app management easy and fun!. As Adventure starts here 🚀.</p>
        </div>
            <!-- Display error message if present -->
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null && !errorMessage.isEmpty()) {
            %>
                <div class="alert alert-danger text-center" role="alert">
                    <%= errorMessage %>
                </div>
            <% } %>

        <div class="col-md-6 right-pane">
            <form novalidate action="signup" method="POST">
                <div class="field">
                    <label for="Username">Username</label>
                    <input autofocus
                           class="form-control"
                           id="Username"
                           name="Username"
                           placeholder="Enter your username"
                           pattern="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$"
                           required
                           title="Alternatively referred to as an account name, login ID, nickname, and user ID"
                           type="text">
                </div>

                <div class="field">
                    <label for="email">Email</label>
                    <input autofocus
                           class="form-control"
                           id="email"
                           name="email"
                           placeholder="example@domain.com"
                           pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"
                           required
                           title="Must contain the Local Part (username), the @ symbol and domain name"
                           type="email">
                </div>

                <div class="field">
                    <label for="password">Password</label>
                    <input autocomplete="off"
                           class="form-control"
                           id="password"
                           minlength="8"
                           name="password"
                           placeholder="&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;&#xb7;"
                           required
                           title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
                           type="password">
                </div>

                <div class="form-actions text-center mt-3">
                    <a href="signin.html" class="d-block mb-2">Already have an account? <strong>Sign in</strong></a>
                    <a href="forgot.html" class="d-block">Forgot your password?</a>
                </div>

                <div class="text-center">
                    <button class="btn btn-cta" type="submit">Sign up</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS (local) -->
<script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>