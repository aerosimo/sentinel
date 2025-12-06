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
    <link rel="stylesheet" href="assets/css/main.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
</head>
<body>
<div class="app">
    <aside class="sidebar" id="sidebar">
        <div class="brand">
            <img src="assets/img/logo/logo.png" alt="logo">
            <h3>OMINET</h3>
        </div>
        <div style="padding:0 16px">
            <button id="sidebarToggle" class="toggle-btn">Toggle</button>
        </div>
        <nav class="nav">
            <a href="dashboard.jsp"><span class="icon"><i class="fas fa-home"></i></span><span class="label">Home</span></a>
            <a href="card1.jsp"><span class="icon"><i class="fas fa-server"></i></span><span class="label">Server Rack</span></a>
            <a href="card2.jsp"><span class="icon"><i class="fas fa-heartbeat"></i></span><span class="label">Server Health</span></a>
            <a href="card3.jsp"><span class="icon"><i class="fas fa-bug"></i></span><span class="label">Errors</span></a>
            <a href="card4.jsp"><span class="icon"><i class="fas fa-chart-pie"></i></span><span class="label">Metrics</span></a>
            <hr style="border:none;height:1px;background:rgba(255,255,255,0.03);margin:12px 0">
            <a href="#"><span class="icon"><i class="fas fa-cog"></i></span><span class="label">Settings</span></a>
            <a href="#"><span class="icon"><i class="fas fa-users"></i></span><span class="label">Teams</span></a>
        </nav>
    </aside>
    <div class="main">
        <header class="topbar">
            <div class="left">
                <div class="search">Search (TODO)</div>
            </div>
            <div class="user">
                <div class="avatar user-toggle"> <span id="topUsername">G</span></div>
                <div style="text-align:right;margin-left:8px;">
                    <div id="topUsername" class="name">Guest</div>
                </div>
                <div class="dropdown">
                    <a href="#">Profile</a>
                    <a href="#">Settings</a>
                    <a id="logoutBtn">Logout</a>
                </div>
            </div>
        </header>


        <main class="content">
            <div class="grid">
                <div class="card" data-card-link="card1.jsp">
                    <h4>Server Rack Overview</h4>
                    <div class="small">Quick snapshot of rack topology and node statuses</div>
                </div>
                <div class="card" data-card-link="card2.jsp">
                    <h4>Server Health Monitor</h4>
                    <div class="small">Uptime, load, active connections and health</div>
                </div>
                <div class="card" data-card-link="card3.jsp">
                    <h4>Error Intelligence</h4>
                    <div class="small">Recent errors and aggregated insight</div>
                </div>
                <div class="card" data-card-link="card4.jsp">
                    <h4>System Metrics</h4>
                    <div class="small">Memory / Disk / CPU usage</div>
                </div>
            </div>


            <section style="margin-top:18px">
                <div class="card">
                    <h4>Overview</h4>
                    <p class="small">This dashboard shell is wired to use your existing landing APIs for data. Click a card to view a detailed page.</p>
                </div>
            </section>
        </main>
    </div>
</div>
<script src="assets/js/main.js"></script>
<script src="assets/js/chart.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/js/all.min.js"></script>
</body>
</html>