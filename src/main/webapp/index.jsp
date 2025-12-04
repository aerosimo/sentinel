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

                    <%
                        com.aerosimo.ominet.core.model.HealthService healthService = new com.aerosimo.ominet.core.model.HealthService();
                        org.json.JSONObject health = healthService.fetchHealth();
                        String uptime = health.optString("uptime", "--");
                        String load = health.optString("load", "--");
                        String connections = health.optString("connections", "--");
                        String status = health.optString("status", "--");
                        String badgeStatusClass = status.equalsIgnoreCase("Running") ? "bg-success" : "bg-danger";
                    %>

                    <!-- Card 2: Server Health Monitor -->
                    <div class="card-col">
                        <div class="ominet-card">
                            <h3 class="card-title-top">Server Health Monitor</h3>
                            <p>Tracks realtime server uptime status.</p>
                            <br/><br/><br/>
                            <div class="activity-monitor">
                                <ul class="overview-list">
                                    <li>
                                        <span class="metric-icon"><i class="fas fa-server"></i></span>
                                        Uptime
                                        <span id="uptime" class="badge bg-success"><%= uptime %></span>
                                    </li>
                                    <li>
                                        <span class="metric-icon"><i class="fas fa-tachometer-alt"></i></span>
                                        Load Average
                                        <span id="load" class="badge bg-info"><%= load %></span>
                                    </li>
                                    <li>
                                        <span class="metric-icon"><i class="fas fa-network-wired"></i></span>
                                        Active Connections
                                        <span id="connections" class="badge bg-primary"><%= connections %></span>
                                    </li>
                                    <li>
                                        <span class="metric-icon"><i class="fas fa-heartbeat"></i></span>
                                        Health Status
                                        <span id="status" class="badge <%= badgeStatusClass %>"><%= status %></span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>


                    <%@ page import="com.aerosimo.ominet.core.model.ErrorRetriever" %>
                    <%@ page import="org.json.*" %>
                    <%
                        String jsonErrors = "[]";
                        try {
                            jsonErrors = ErrorRetriever.getRecentErrors();
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                        JSONArray errors = new JSONArray(jsonErrors);
                    %>

                    <!-- Card 3: Error Intelligence -->
                    <div class="card-col">
                        <div class="ominet-card">
                            <h3 class="card-title-top">Error Intelligence</h3>
                            <p>Integrates with Error Harbour to provide insights into anomalies and recoverable fault patterns.</p>
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
                                        <%
                                            // --- Fetch JSON from API ---
                                            java.net.URL url = new java.net.URL("https://ominet.aerosimo.com:9443/spectre/api/errors/retrieve?records=5");
                                            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                                            conn.setRequestMethod("GET");
                                            conn.setRequestProperty("Accept", "application/json");
                                            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
                                            StringBuilder sb = new StringBuilder();
                                            String line;
                                            while ((line = br.readLine()) != null) {
                                                sb.append(line);
                                            }
                                            br.close();
                                            conn.disconnect();
                                            org.json.JSONArray errorsArray = new org.json.JSONArray(sb.toString());
                                            // --- Loop through top 5 errors ---
                                            for (int i = 0; i < errorsArray.length() && i < 5; i++) {
                                                org.json.JSONObject err = errorsArray.getJSONObject(i);
                                                String ref = err.getString("errorRef");
                                                String time = err.getString("errorTime");
                                                // Derive status from errorCode (example logic)
                                                String errorStatus = err.getString("errorCode").startsWith("TE") ? "OPEN" : "RESOLVED";
                                                String statusClass = "badge bg-primary";
                                                String statusIcon = "ℹ️";
                                                switch(errorStatus) {
                                                    case "OPEN":
                                                        statusClass = "badge bg-danger";
                                                        statusIcon = "⚠️";
                                                        break;
                                                    case "RESOLVED":
                                                        statusClass = "badge bg-success";
                                                        statusIcon = "✅";
                                                        break;
                                                    case "CLOSED":
                                                        statusClass = "badge bg-secondary";
                                                        statusIcon = "✖️";
                                                        break;
                                                    case "PENDING":
                                                        statusClass = "badge bg-warning";
                                                        statusIcon = "⏳";
                                                        break;
                                                }
                                        %>
                                        <tr class="<%=errorStatus.toLowerCase()%>">
                                            <td><%= ref %></td>
                                            <td><span class="error-status <%=errorStatus.toLowerCase()%>"><%= statusIcon %> <%= errorStatus %></span></td>
                                            <td><%= time %></td>
                                        </tr>
                                        <%
                                            }
                                        %>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="text-center mt-2">
                                    <button class="btn btn-sm btn-outline-primary" onclick="location.reload()">Refresh</button>
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