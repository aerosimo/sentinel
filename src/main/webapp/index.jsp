<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance sentinel project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      index.jsp                                                      ~
  ~ Created:   04/09/2025, 20:59                                              ~
  ~ Modified:  04/09/2025, 20:59                                              ~
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

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true"%>

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
    <title>Admin Dashboard | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Bootstrap CSS (local) -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/main.css" rel="stylesheet">
    <link href="assets/css/server.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

<%

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
    if (session.getAttribute("email") == null)
        response.sendRedirect("signin.jsp");
%>

<div class="d-flex">

    <!-- Sidebar -->
    <nav class="sidebar d-flex flex-column p-3" id="sidebar">
        <div class="text-center mb-4">
            <img src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png" alt="Logo" class="sidebar-logo">
        </div>
        <h4 class="text-white">Dashboard</h4>
        <hr class="bg-light"/>
        <ul class="nav nav-pills flex-column mb-auto">
            <li><a class="nav-link active" href="#"><i class="bi bi-house"></i><span>Home</span></a></li>
            <li><a class="nav-link" href="#"><i class="bi bi-graph-up"></i><span>Analytics</span></a></li>
            <li><a class="nav-link" href="#"><i class="bi bi-file-earmark-text"></i><span>Reports</span></a></li>
            <li><a class="nav-link" href="#"><i class="bi bi-people"></i><span>Users</span></a></li>
            <li><a class="nav-link" href="#"><i class="bi bi-gear"></i><span>Settings</span></a></li>
        </ul>
    </nav>

    <!-- Overlay -->
    <div class="sidebar-overlay" id="sidebarOverlay"></div>

    <!-- Main Content -->
    <div class="content-wrapper expanded" id="contentWrapper">
        <!-- Header/Navbar -->
        <nav class="navbar navbar-expand navbar-light navbar-custom px-3">
            <div class="container-fluid">
                <button class="btn btn-outline-secondary me-2" id="sidebarToggle"><i class="bi bi-list"></i></button>
                <h5 class="mb-0">Homepage</h5>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle d-flex align-items-center" data-bs-toggle="dropdown" href="#">
                            <img src="assets/img/user/user.png" alt="User" class="rounded-circle me-2" width="40" height="40"/>
                            <span>${uname}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="#">Settings</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="#">Sign out</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Page Content -->
        <main class="container-fluid my-4">
            <div class="row g-4">

                <!-- Row 1: Section 1 + Section 2 -->
                <div class="col-md-6">
                    <div class="card dashboard-card p-3 h-100">
                        <h6>Server Overview</h6>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Uptime <span class="badge bg-success">12 days</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Load Average <span class="badge bg-info">1.24</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Active Connections <span class="badge bg-primary">152</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Health Status <span class="badge bg-success">Running</span>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="card dashboard-card p-3 h-100">
                        <h6 class="mb-3">Server Rack</h6>
                        <section class="server-rack">
                            <article>
                                <span></span><span></span><span></span>
                                <ul>
                                    <li></li><li></li><li></li><li></li>
                                    <li></li><li></li><li></li><li></li>
                                    <li class="server-name">Jenkins <span class="badge bg-success">🟢</span></li>
                                </ul>
                            </article>
                            <article>
                                <span></span><span></span><span></span>
                                <ul>
                                    <li></li><li></li><li></li><li></li>
                                    <li></li><li></li><li></li><li></li>
                                    <li class="server-name">Oracle <span class="badge bg-danger">🔴</span></li>
                                </ul>
                            </article>
                            <article>
                                <span></span><span></span><span></span>
                                <ul>
                                    <li></li><li></li><li></li><li></li>
                                    <li></li><li></li><li></li><li></li>
                                    <li class="server-name">TomEE <span class="badge bg-success">🟢</span></li>
                                </ul>
                            </article>
                        </section>
                    </div>
                </div>

                <!-- Row 2: Section 3 (full-width) -->
                <div class="col-12">
                    <div class="card dashboard-card p-3">
                        <h6 class="mb-3">System Metrics</h6>
                        <div class="row">
                            <div class="col-md-4"><canvas id="memoryChart" height="180"></canvas></div>
                            <div class="col-md-4"><canvas id="diskChart" height="180"></canvas></div>
                            <div class="col-md-4"><canvas id="cpuChart" height="180"></canvas></div>
                        </div>
                    </div>
                </div>

                <!-- Row 3: Section 4 + Section 5 -->
                <!-- Section 4: Alerts & Logs -->
                <div class="col-md-6">
                    <div class="card dashboard-card p-3 h-100">
                        <h6 class="mb-3">Alerts & Logs</h6>
                        <div class="list-group list-group-flush" style="max-height: 200px; overflow-y: auto;">
                            <div class="list-group-item">⚠️ High CPU usage detected at 10:20</div>
                            <div class="list-group-item">ℹ️ User admin logged in at 10:05</div>
                            <div class="list-group-item">⚠️ Disk space warning on /dev/sda1</div>
                            <div class="list-group-item">✅ Backup completed successfully</div>
                            <div class="list-group-item">⚠️ Memory threshold exceeded</div>
                            <div class="list-group-item">ℹ️ Scheduled job completed</div>
                            <div class="list-group-item">⚠️ Network latency spike detected</div>
                        </div>
                        <div class="text-center mt-2">
                            <button class="btn btn-sm btn-outline-primary">Load More</button>
                        </div>
                    </div>
                </div>

                <!-- Section 5: Recent Errors -->
                <div class="col-md-6">
                    <div class="card dashboard-card p-3 h-100">
                        <h6 class="mb-3">Recent Errors</h6>
                        <div class="table-responsive" style="max-height: 200px; overflow-y: auto;">
                            <table class="table table-sm table-hover align-middle">
                                <thead class="table-light">
                                <tr>
                                    <th>Error Ref</th>
                                    <th>Message</th>
                                    <th>Timestamp</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr><td>#E1023</td><td>Database connection timeout</td><td>2025-09-15 10:22</td></tr>
                                <tr><td>#E1022</td><td>Null pointer in Auth module</td><td>2025-09-15 10:05</td></tr>
                                <tr><td>#E1021</td><td>Disk write failure</td><td>2025-09-15 09:55</td></tr>
                                <tr><td>#E1020</td><td>Service unavailable</td><td>2025-09-15 09:40</td></tr>
                                <tr><td>#E1019</td><td>Invalid user token</td><td>2025-09-15 09:25</td></tr>
                                <tr><td>#E1018</td><td>API request timeout</td><td>2025-09-15 09:15</td></tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="text-center mt-2">
                            <button class="btn btn-sm btn-outline-primary">Load More</button>
                        </div>
                    </div>
                </div>

            </div>
        </main>

        <!-- Footer -->
        <footer>
            <div class='copy'>&copy; <script>document.write(new Date().getFullYear());</script> Sentinel by Aerosimo Ltd. All rights reserved.</div>
        </footer>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="assets/js/bootstrap.bundle.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
<script src="assets/js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Memory Pie
        new Chart(document.getElementById("memoryChart"), {
            type: "pie",
            data: { labels: ["Used", "Free", "Committed"],
                datasets: [{ data: ${memoryusage}, backgroundColor: ["#4d3b7a", "#64b5f6", "#81c784"] }]},
            options: { plugins: { legend: { position: "bottom" } } }
        });

        // Disk Doughnut
        new Chart(document.getElementById("diskChart"), {
            type: "doughnut",
            data: { labels: ["Used", "Free", "Usable"],
                datasets: [{ data: ${diskusage}, backgroundColor: ["#9575cd", "#ffb74d", "#4db6ac"] }]},
            options: { cutout: "70%", plugins: { legend: { position: "bottom" } } }
        });

        // CPU Bar
        new Chart(document.getElementById("cpuChart"), {
            type: "bar",
            data: { labels: ["Running", "Waiting", "Blocked", "Terminated"],
                datasets: [{ label: "Threads", data: [12, 5, 2, 1],
                    backgroundColor: ["#4d3b7a", "#64b5f6", "#ff7043", "#81c784"] }]},
            options: { plugins: { legend: { display: false } }, scales: { y: { beginAtZero: true } } }
        });
    });
</script>
</body>
</html>