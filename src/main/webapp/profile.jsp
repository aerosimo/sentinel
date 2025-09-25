<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance sentinel project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      profile.html                                                   ~
  ~ Created:   24/09/2025, 13:24                                              ~
  ~ Modified:  24/09/2025, 13:24                                              ~
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
<%@ page session="true" %>

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
    <link href="assets/css/server.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap" rel="stylesheet">

    <style>
        .profile-banner {
            position: relative;
            background: url("assets/img/banner/user.jpg") center/cover no-repeat;
            height: 200px;
            border-radius: .5rem;
        }
        .profile-avatar {
            position: absolute;
            bottom: -50px;
            left: 50%;
            transform: translateX(-50%);
            border: 4px solid #fff;
            border-radius: 50%;
            width: 120px;
            height: 120px;
            object-fit: cover;
        }
        .profile-section {
            margin-top: 70px; /* leave space for avatar */
        }
    </style>
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
        <h4 class="text-white">Profile</h4>
        <hr class="bg-light"/>
        <ul class="nav nav-pills flex-column mb-auto">
            <li><a href="index.jsp" class="nav-link"><i class="bi bi-house"></i><span>Home</span></a></li>
            <li><a href="profile.jsp" class="nav-link active"><i class="bi bi-person"></i><span>Profile</span></a></li>
            <li><a href="#" class="nav-link"><i class="bi bi-gear"></i><span>Settings</span></a></li>
            <li>
                <form action="${pageContext.request.contextPath}/logout" method="post" style="display:inline;">
                    <button type="submit" class="nav-link"><i class="bi bi-box-arrow-right"></i><span>Logout</span></button>
                </form>
            </li>
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
                <h5 class="mb-0">Profile</h5>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle d-flex align-items-center" data-bs-toggle="dropdown" href="#">
                            <img src="assets/img/user/user.png" alt="User" class="rounded-circle me-2" width="40" height="40">
                            <span>${uname}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="profile.jsp">Profile</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form action="${pageContext.request.contextPath}/logout" method="post">
                                    <button type="submit" class="dropdown-item">Sign out</button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Banner + Avatar -->
        <div class="profile-banner">
            <img src="assets/img/user/user.png" alt="Avatar" class="profile-avatar">
        </div>

        <!-- Page Content -->
        <main class="container-fluid my-4 profile-section">
            <div class="row g-4">
                <!-- Left column -->
                <div class="col-md-6">
                    <div class="card dashboard-card p-3">
                        <h6 class="mb-3">Personal Info</h6>
                        <p><b>Name:</b> ${person.firstName} ${person.lastName}</p>
                        <p><b>Email:</b> ${person.email}</p>
                        <p><b>Gender:</b> ${person.gender}</p>
                        <p><b>DOB:</b> ${person.birthday} (Age: ${person.age})</p>
                    </div>

                    <div class="card dashboard-card p-3 mt-3">
                        <h6 class="mb-3">Contact Info</h6>
                        <p><b>Phone:</b> ${contact.address}</p>
                        <p><b>City:</b> ${address.city}</p>
                        <p><b>Country:</b> ${address.country}</p>
                    </div>
                </div>

                <!-- Right column -->
                <div class="col-md-6">
                    <div class="card dashboard-card p-3">
                        <h6 class="mb-3">Profile Details</h6>
                        <p><b>Marital Status:</b> ${profile.maritalStatus}</p>
                        <p><b>Ethnicity:</b> ${profile.ethnicity}</p>
                        <p><b>Religion:</b> ${profile.religion}</p>
                        <p><b>Eye Colour:</b> ${profile.eyeColour}</p>
                    </div>

                    <div class="card dashboard-card p-3 mt-3">
                        <h6 class="mb-3">Horoscope</h6>
                        <p><b>${horoscope.zodiac} - ${horoscope.currentDay}</b></p>
                        <p>${horoscope.narrative}</p>
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

<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/main.js"></script>
</body>
</html>