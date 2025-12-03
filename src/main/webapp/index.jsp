<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance sentinel project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      index.jsp                                                      ~
  ~ Created:   03/12/2025, 11:40                                              ~
  ~ Modified:  03/12/2025, 11:50                                              ~
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
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Ominet Sentinel | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Local Bootstrap -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/landing.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>

</head>

<body>
<!-- Parallax Section -->
<div class="parallax">
    <a href="#login" class="login-btn">Login</a>
    <div class="parallax-text">
        <img src="assets/img/logo/logo.png" alt="Aerosimo Logo">
        <h1>OMINET SENTINEL</h1>
    </div>
</div>

<!-- Snapshot Section -->
<div class="container mt-5 mb-5">
    <h2 class="section-title">SYSTEM SNAPSHOT</h2>
    <div class="card-row">
        <!-- Card 1: Security Overview with Star Topology -->
        <div class="card-col">
            <div class="ominet-card">
                <h3 class="card-title-top">Server Rack Overview</h3>
                <p>Tracks realtime server status across ominet estate.</p>
                <div class="topology-wrapper">
                    <div class="star-topology">
                        <!-- Jenkins -->
                        <div class="node jenkins">
                            <img src="assets/img/server/pc.png" alt="Jenkins">
                            <div class="status-light online"></div>
                            <img src="assets/img/server/jenkins.png" alt="Jenkins">
                            <p>Jenkins</p>
                        </div>
                        <!-- Oracle -->
                        <div class="node oracle">
                            <img src="assets/img/server/pc.png" alt="Oracle">
                            <div class="status-light offline"></div>
                            <p><img src="assets/img/server/oracle.png" alt="Oracle">Oracle</p>
                        </div>
                        <!-- TomEE -->
                        <div class="node tomee">
                            <img src="assets/img/server/pc.png" alt="TomEE">
                            <div class="status-light online"></div>
                            <p><img src="assets/img/server/tomee.png" alt="TomEE">TomEE</p>
                        </div>
                        <!-- Fedora -->
                        <div class="node fedora">
                            <img src="assets/img/server/pc.png" alt="Fedora">
                            <div class="status-light warning"></div>
                            <p><img src="assets/img/server/linux.png" alt="Fedora">Fedora Linux</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Card 2: Activity Monitor / Server Overview -->
        <div class="card-col">
            <div class="ominet-card">
                <h3 class="card-title-top">Activity Monitor</h3>
                <p>Tracks realtime server uptime status.</p>
                <br/><br/><br/>
                <div class="activity-monitor">
                    <ul class="overview-list">
                        <li>
                            <span class="metric-icon"><i class="fas fa-server"></i></span>
                            Uptime
                            <span id="uptime" class="badge bg-success">--</span>
                        </li>
                        <li>
                            <span class="metric-icon"><i class="fas fa-tachometer-alt"></i></span>
                            Load Average
                            <span id="load" class="badge bg-info">--</span>
                        </li>
                        <li>
                            <span class="metric-icon"><i class="fas fa-network-wired"></i></span>
                            Active Connections
                            <span id="connections" class="badge bg-primary">--</span>
                        </li>
                        <li>
                            <span class="metric-icon"><i class="fas fa-heartbeat"></i></span>
                            Health Status
                            <span id="status" class="badge bg-success">--</span>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Card 3: Error Intelligence -->
        <div class="card-col">
            <div class="ominet-card">
                <h3 class="card-title-top">Error Intelligence</h3>
                <p>Quick snapshot of top 5 recent error statuses.</p>
                <br/><br/><br/>
                <div class="error-table-wrapper">
                    <div class="table-responsive">
                        <table class="error-table table table-sm table-hover align-middle">
                            <thead>
                            <tr>
                                <th>Reference</th>
                                <th>Status</th>
                                <th>Timestamp</th>
                            </tr>
                            </thead>
                            <tbody>
                            <!-- JS will populate top 5 recent errors -->
                            <!-- Example Row -->
                                <tr class="open">
                                    <td>ERR|B96R39</td>
                                    <td><span class="error-status open">OPEN</span></td>
                                    <td>01-DEC-25 22.02.27.492352000</td>
                                </tr>
                                <tr class="resolved">
                                    <td>ERR|B96R39</td>
                                    <td><span class="error-status resolved">RESOLVED</span></td>
                                    <td>01-DEC-25 22.02.27.492352000</td>
                                </tr>
                                <tr class="pending">
                                    <td>ERR|B96R39</td>
                                    <td><span class="error-status pending">PENDING</span></td>
                                    <td>01-DEC-25 22.02.27.492352000</td>
                                </tr>
                                <tr class="closed">
                                    <td>ERR|B96R39</td>
                                    <td><span class="error-status closed">CLOSED</span></td>
                                    <td>01-DEC-25 22.02.27.492352000</td>
                                </tr>
                                <tr class="open">
                                    <td>ERR|B96R39</td>
                                    <td><span class="error-status open">OPEN</span></td>
                                    <td>01-DEC-25 22.02.27.492352000</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="text-center mt-2">
                        <button class="btn btn-sm btn-outline-primary" onclick="fetchRecentErrors()">Refresh</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Card 4: System Metrics -->
        <div class="card-col">
            <div class="ominet-card">
                <h3>System Metrics</h3>
                <p>Displays current memory, disk, and CPU usage across monitored servers.</p>
                <br/><br/><br/>
                <div class="chart-row">
                    <div class="chart-container">
                        <canvas id="memoryChart" height="150"></canvas>
                        <p class="chart-label">Memory Usage</p>
                    </div>
                    <div class="chart-container">
                        <canvas id="diskChart" height="150"></canvas>
                        <p class="chart-label">Disk Usage</p>
                    </div>
                    <div class="chart-container">
                        <canvas id="cpuChart" height="150"></canvas>
                        <p class="chart-label">CPU Usage</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Card 5: Ominet Logs -->
        <div class="card-col">
            <div class="ominet-card">
                <h3>Ominet Logs</h3>
                <p>Shows curated logs sorted by severity, timestamp, and module source for rapid diagnostics.</p>
                <br/><br/><br/>
                <div class="logs-wrapper">
                    <div class="list-group list-group-flush">
                        <div class="list-group-item log-item warning">
                            ⚠️ High CPU usage detected at 10:20
                        </div>
                        <div class="list-group-item log-item info">
                            ℹ️ User admin logged in at 10:05
                        </div>
                        <div class="list-group-item log-item warning">
                            ⚠️ Disk space warning on /dev/sda1
                        </div>
                        <div class="list-group-item log-item success">
                            ✅ Backup completed successfully
                        </div>
                        <div class="list-group-item log-item warning">
                            ⚠️ Memory threshold exceeded
                        </div>
                    </div>
                    <div class="text-center mt-2">
                        <button class="btn btn-sm btn-outline-primary">Load More</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Card 6: Module Integrations -->
        <div class="card-col">
            <div class="ominet-card">
                <h3>Module Integrations</h3>
                <p>Provides quick access to linked services including Identity Core, Error Harbour, and more.</p>
                <br/><br/><br/>
                <div class="jobs-wrapper">
                    <div class="list-group list-group-flush">
                        <div class="list-group-item job-item success">
                            ✅ Build API Service
                        </div>
                        <div class="list-group-item job-item running">
                            ⏳ Data Sync Job
                        </div>
                        <div class="list-group-item job-item failed">
                            ❌ Frontend Deployment
                        </div>
                        <div class="list-group-item job-item aborted">
                            ⚠️ Nightly Backup
                        </div>
                        <div class="list-group-item job-item success">
                            ✅ Error Handler Job
                        </div>
                    </div>
                    <div class="text-center mt-2">
                        <button class="btn btn-sm btn-outline-primary">Refresh Jobs</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <footer>
            <div class='copy'>&copy; <script>document.write(new Date().getFullYear());</script> Ominet by Aerosimo Ltd. All rights reserved.</div>
        </footer>

    </div>
</div>

<!-- Local Bootstrap JS -->
<script src="assets/js/chart.js"></script>
<script src="assets/js/chartjs-plugin-datalabels.js"></script>
<script src="assets/js/landing.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>

</body>
</html>