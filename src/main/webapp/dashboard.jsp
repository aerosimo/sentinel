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
<%@ include file="dashboard.jspf" %>
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
    <link rel="stylesheet" href="assets/css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
</head>
<body>
<div class="dashboard-cards">
    <div class="card" onclick="location.href='card1.jsp'">
        <div class="card-icon"><i class="bi bi-memory"></i></div>
        <div class="card-title">Memory Usage</div>
        <div class="card-desc">View detailed memory statistics</div>
    </div>
    <div class="card" onclick="location.href='card2.jsp'">
        <div class="card-icon"><i class="bi bi-hdd"></i></div>
        <div class="card-title">Disk Usage</div>
        <div class="card-desc">Check disk space utilization</div>
    </div>
    <div class="card" onclick="location.href='card3.jsp'">
        <div class="card-icon"><i class="bi bi-cpu"></i></div>
        <div class="card-title">CPU Usage</div>
        <div class="card-desc">Monitor CPU load and performance</div>
    </div>
    <div class="card" onclick="location.href='card4.jsp'">
        <div class="card-icon"><i class="bi bi-exclamation-triangle"></i></div>
        <div class="card-title">Recent Errors</div>
        <div class="card-desc">Analyze recent system errors</div>
    </div>
</div>

<script>
    // Highlight active sidebar link
    document.addEventListener('DOMContentLoaded', () => {
        const sidebarLinks = document.querySelectorAll('.sidebar .nav-links a');
        sidebarLinks.forEach(link => {
            if(link.href === window.location.href) {
                link.classList.add('active');
            }
        });
    });
</script>
<script src="assets/js/main.js"></script>
<script src="assets/js/chart.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/js/all.min.js"></script>
</body>
</html>