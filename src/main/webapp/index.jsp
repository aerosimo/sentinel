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
    <title>Sentinel Dashboard | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Bootstrap CSS -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/main.css" rel="stylesheet">
    <link href="assets/css/weather.css" rel="stylesheet">
    <link href="assets/css/server.css" rel="stylesheet">

    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <!-- Chart.js -->
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

<div class="d-flex">
    <!-- Sidebar -->
    <nav class="sidebar d-flex flex-column p-3" id="sidebar">
        <div class="text-center mb-4">
            <img alt="Logo" class="sidebar-logo" src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png">
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
                            <img alt="User" class="rounded-circle me-2" height="40" src="assets/img/user/user.png" width="40"/>
                            <span>${uname}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="#">Settings</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <!-- <li><a class="dropdown-item" href="logout">Sign out</a></li> -->
                            <li>
                                <form action="${pageContext.request.contextPath}/logout" method="post" style="display: inline;">
                                    <button type="submit" class="dropdown-item">Sign out</button>
                                </form>
                            </li>
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
                                Uptime <span id="uptime" class="badge bg-success">--</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Load Average <span id="load" class="badge bg-info">--</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Active Connections <span id="connections" class="badge bg-primary">--</span>
                            </li>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                Health Status <span id="status" class="badge bg-success">--</span>
                            </li>
                        </ul>
                    </div>
                </div>

                <!-- Section 2: Server Rack -->
                <div class="col-md-6">
                    <div class="card dashboard-card server-widget p-3 h-100">
                        <h6 class="mb-3">Server Rack</h6>

                        <section class="server-rack">
                            <!-- TomEE -->
                            <article id="server-tomee">
                                <div class="led"></div>
                                <ul>
                                    <li></li><li></li><li></li><li></li>
                                    <li></li><li></li><li></li><li></li>
                                    <li class="server-logo"><img src="assets/img/logo/tomee.png" alt="TomEE"></li>
                                </ul>
                            </article>

                            <!-- Jenkins -->
                            <article id="server-jenkins">
                                <div class="led"></div>
                                <ul>
                                    <li></li><li></li><li></li><li></li>
                                    <li></li><li></li><li></li><li></li>
                                    <li class="server-logo"><img src="assets/img/logo/jenkins.png" alt="Jenkins"></li>
                                </ul>
                            </article>

                            <!-- Oracle -->
                            <article id="server-oracle">
                                <div class="led"></div>
                                <ul>
                                    <li></li><li></li><li></li><li></li>
                                    <li></li><li></li><li></li><li></li>
                                    <li class="server-logo"><img src="assets/img/logo/oracle.png" alt="Oracle"></li>
                                </ul>
                            </article>

                            <!-- Linux -->
                            <article id="server-linux">
                                <div class="led"></div>
                                <ul>
                                    <li></li><li></li><li></li><li></li>
                                    <li></li><li></li><li></li><li></li>
                                    <li class="server-logo"><img src="assets/img/logo/linux.png" alt="Linux"></li>
                                </ul>
                            </article>
                        </section>
                    </div>
                </div>

                <!-- Row 2: Section 3 (Charts) -->
                <div class="col-12">
                    <div class="card dashboard-card p-3">
                        <div class="row">
                            <div class="col-md-4 text-center"><canvas height="200" id="memoryChart"></canvas></div>
                            <div class="col-md-4 text-center"><canvas height="200" id="diskChart"></canvas></div>
                            <div class="col-md-4 text-center"><canvas height="200" id="cpuChart"></canvas></div>
                        </div>
                    </div>
                </div>

                <!-- Row 3: Section 4 + Section 5 -->
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
                        <div class="text-center mt-2"><button class="btn btn-sm btn-outline-primary">Load More</button></div>
                    </div>
                </div>

                <!-- Section 5: Recent Errors -->
                <div class="col-md-6">
                    <div class="card dashboard-card p-3 h-100">
                        <h6 class="mb-3">Recent Errors</h6>
                        <div class="table-responsive" style="max-height: 200px; overflow-y: auto;">
                            <table id="recentErrorsTable" class="table table-sm table-hover align-middle">
                                <thead class="table-light">
                                <tr>
                                    <th>Error Ref</th>
                                    <th>Message</th>
                                    <th>Timestamp</th>
                                </tr>
                                </thead>
                                <tbody id="errorsTableBody">
                                    <!-- Auto-populated from spectreErrors servlet -->
                                </tbody>
                            </table>
                        </div>
                        <div class="text-center mt-2">
                            <button class="btn btn-sm btn-outline-primary" onclick="fetchRecentErrors()">Load More</button>
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
<script src="assets/js/main.js"></script>

<!-- Server Overview Poller -->
<script>
async function refreshOverview() {
    try {
        const res = await fetch("overview");
        const data = await res.json();

        document.getElementById("uptime").textContent = data.uptime;
        document.getElementById("load").textContent = data.load.toFixed(2);
        document.getElementById("connections").textContent = data.connections;
        document.getElementById("status").textContent = data.status;

        // Change badge colour based on status
        const statusBadge = document.getElementById("status");
        statusBadge.className = "badge " + (data.status === "Running" ? "bg-success" : "bg-danger");

    } catch (err) {
        console.error("Overview fetch error:", err);
    }
}

setInterval(refreshOverview, 10000); // every 10s
refreshOverview(); // run once
</script>

<!-- Server Rack Poller -->
<script>
    async function fetchStatus() {
        try {
            const res = await fetch("serverStatus");
            const data = await res.json();

            Object.entries(data).forEach(([name, status]) => {
                const row = document.getElementById("server-" + name);
                const logo = row.querySelector(".server-logo");

                if (status === "online") {
                    row.classList.remove("server-down");
                    logo.classList.remove("offline");
                } else {
                    row.classList.add("server-down");
                    logo.classList.add("offline");
                }
            });
        } catch (e) {
            console.error("Error fetching server status:", e);
        }
    }
    setInterval(fetchStatus, 5000);
    fetchStatus();
</script>

<!-- System Metrics Poller -->
<script>
    document.addEventListener("DOMContentLoaded", function () {
        let memoryChart, diskChart, cpuChart;

        async function fetchMetrics() {
            try {
                const res = await fetch("metrics");
                const data = await res.json();

                const memoryData = data.memory.map(v => parseFloat(v));
                const diskData = data.disk.map(v => parseFloat(v));
                const cpuStates = {};
                for (let i = 0; i < data.cpu.length; i += 3) {
                    const state = data.cpu[i + 1];
                    cpuStates[state] = (cpuStates[state] || 0) + 1;
                }

                if (!memoryChart) {
                    memoryChart = new Chart(document.getElementById("memoryChart"), {
                        type: "pie",
                        data: { labels: ["Init", "Used", "Max", "Committed"], datasets: [{ data: memoryData, backgroundColor: ["#4d3b7a", "#64b5f6", "#81c784", "#ffb74d"] }] },
                        options: { responsive: true, plugins: { legend: { position: "bottom" }, title: { display: true, text: "Memory Usage" } } }
                    });
                } else {
                    memoryChart.data.datasets[0].data = memoryData;
                    memoryChart.update();
                }

                if (!diskChart) {
                    diskChart = new Chart(document.getElementById("diskChart"), {
                        type: "doughnut",
                        data: { labels: ["Total", "Free", "Usable"], datasets: [{ data: diskData, backgroundColor: ["#9575cd", "#ffb74d", "#4db6ac"] }] },
                        options: { responsive: true, cutout: "70%", plugins: { legend: { position: "bottom" }, title: { display: true, text: "Disk Usage" } } }
                    });
                } else {
                    diskChart.data.datasets[0].data = diskData;
                    diskChart.update();
                }

                if (!cpuChart) {
                    cpuChart = new Chart(document.getElementById("cpuChart"), {
                        type: "bar",
                        data: { labels: Object.keys(cpuStates), datasets: [{ label: "Threads", data: Object.values(cpuStates), backgroundColor: ["#4d3b7a", "#64b5f6", "#ff7043", "#81c784", "#ba68c8"] }] },
                        options: { responsive: true, plugins: { legend: { display: false }, title: { display: true, text: "CPU Threads" } }, scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } } }
                    });
                } else {
                    cpuChart.data.labels = Object.keys(cpuStates);
                    cpuChart.data.datasets[0].data = Object.values(cpuStates);
                    cpuChart.update();
                }
            } catch (err) {
                console.error("Metrics fetch error:", err);
            }
        }

        fetchMetrics();
        setInterval(fetchMetrics, 10000);
    });
</script>

<!-- Recent Errors Poller -->
<script>
async function fetchRecentErrors() {
    try {
        const res = await fetch("spectreErrors?records=6");
        const data = await res.json();

        const tbody = document.getElementById("errorsTableBody");
        tbody.innerHTML = ""; // clear old rows

        data.forEach(err => {
            const tr = document.createElement("tr");

            // Escape text (to avoid broken HTML)
            const safeMessage = err.message.replace(/\n/g, "<br/>");

            tr.innerHTML = `
                <td>${err.errorRef}</td>
                <td>${safeMessage}</td>
                <td>${err.timestamp}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) {
        console.error("Error fetching recent errors:", e);
    }
}

// Run once on page load
fetchRecentErrors();

// Auto-refresh every 15 seconds
setInterval(fetchRecentErrors, 15000);
</script>

</body>
</html>