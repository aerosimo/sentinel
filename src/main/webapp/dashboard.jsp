<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance sentinel project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      dashboard.html                                                 ~
  ~ Created:   06/12/2025, 00:11                                              ~
  ~ Modified:  06/12/2025, 00:11                                              ~
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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Dashboard | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Bootstrap + Custom CSS -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/auth.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
    <style>
        .dashboard-header { display:flex; justify-content:space-between; align-items:center; padding:20px; background: var(--panel-bg); color: var(--right-fg); }
        .dashboard-content { padding: 30px; }
    </style>
</head>
<body>

<!-- Header -->
<div class="dashboard-header">
    <h2>Welcome, <span id="usernameDisplay">User</span></h2>
    <button class="btn btn-cta" onclick="logout()">Logout</button>
</div>

<!-- Main content -->
<div class="dashboard-content">
    <h3>Quick Actions</h3>
    <div style="display:flex; gap:20px; flex-wrap:wrap;">
        <button class="btn btn-cta">Manage Account</button>
        <button class="btn btn-cta">View Reports</button>
        <button class="btn btn-cta">Settings</button>
    </div>

    <hr style="margin:30px 0; border-color:rgba(255,255,255,0.2);">

    <h3>Notifications</h3>
    <div id="notifications" style="padding:15px; background:rgba(255,255,255,0.05); border-radius:10px; min-height:80px;">
        No new notifications
    </div>
</div>

<!-- Scripts -->
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script>
    // Display logged-in username from sessionStorage
    const uname = sessionStorage.getItem('username');
    if (uname) document.getElementById('usernameDisplay').textContent = uname;

    function logout() {
        sessionStorage.removeItem('username');
        sessionStorage.removeItem('authToken');
        window.location.href = 'login.jsp';
    }
</script>
</body>
</html>