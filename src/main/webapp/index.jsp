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
</head>
<body>

<%

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
    if (session.getAttribute("email") == null)
        response.sendRedirect("signin.html");
%>

<div class="d-flex">

    <!-- Sidebar -->
    <nav class="sidebar d-flex flex-column p-3" id="sidebar">
        <div class="text-center mb-4">
            <img src="assets/img/favicon/logo-icon.png" alt="Logo" class="sidebar-logo">
        </div>

        <h4 class="text-white">Dashboard</h4>
        <hr class="bg-light"/>
        <ul class="nav nav-pills flex-column mb-auto">
            <li><a class="nav-link active" href="#" title="Home"><i class="bi bi-house"></i><span>Home</span></a></li>
            <li><a class="nav-link" href="#" title="Analytics"><i class="bi bi-graph-up"></i><span>Analytics</span></a>
            </li>
            <li><a class="nav-link" href="#" title="Reports"><i class="bi bi-file-earmark-text"></i><span>Reports</span></a>
            </li>
            <li><a class="nav-link" href="#" title="Users"><i class="bi bi-people"></i><span>Users</span></a></li>
            <li><a class="nav-link" href="#" title="Settings"><i class="bi bi-gear"></i><span>Settings</span></a></li>
        </ul>
    </nav>

    <!-- Overlay -->
    <div class="sidebar-overlay" id="sidebarOverlay"></div>


    <!-- Main Content -->
    <div class="content-wrapper expanded" id="contentWrapper">
        <!-- Header/Navbar -->
        <nav class="navbar navbar-expand navbar-light navbar-custom px-3">
            <div class="container-fluid">
                <!-- Sidebar toggle button -->
                <button class="btn btn-outline-secondary me-2" id="sidebarToggle">
                    <i class="bi bi-list"></i>
                </button>

                <h5 class="mb-0">Homepage</h5>

                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a aria-expanded="false" class="nav-link dropdown-toggle d-flex align-items-center"
                           data-bs-toggle="dropdown"
                           href="#" id="userDropdown" role="button">
                            <img alt="User" class="rounded-circle me-2" height="40" src="assets/img/user/user.png"
                                 width="40"/>
                            <span>User</span>
                        </a>
                        <ul aria-labelledby="userDropdown" class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="#">Settings</a></li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">Sign out</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Page Content -->
        <main class="container-fluid my-4">
            <div class="row g-4">
                <div class="col-md-6 col-lg-4">
                    <div class="card dashboard-card p-3">
                        <h6>Section 1</h6>
                        <p>Some quick content for section one. Could be charts, stats, or tables.</p>
                        <p>Donec eget ex magna. Interdum et malesuada fames ac ante ipsum primis in faucibus.
                            Pellentesque venenatis dolor imperdiet dolor mattis sagittis. Praesent rutrum sem diam,
                            vitae egestas enim auctor sit amet. Pellentesque leo mauris, consectetur id ipsum sit amet,
                            fergiat. Pellentesque in mi eu massa lacinia malesuada et a elit. Donec urna ex, lacinia in
                            purus ac, pretium pulvinar mauris. Curabitur sapien risus, commodo eget turpis at, elementum
                            convallis elit. Pellentesque enim turpis, hendrerit.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis dapibus rutrum facilisis. Class
                            aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam
                            tristique libero eu nibh porttitor fermentum. Nullam venenatis erat id vehicula viverra.
                            Nunc ultrices eros ut ultricies condimentum. Mauris risus lacus, blandit sit amet venenatis
                            non, bibendum vitae dolor. Nunc lorem mauris, fringilla in aliquam at, euismod in lectus.
                            Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis
                            egestas. In non lorem sit amet elit placerat maximus. Pellentesque aliquam maximus risus,
                            vel sed vehicula.</p>
                        <p>Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque venenatis dolor
                            imperdiet dolor mattis sagittis. Praesent rutrum sem diam, vitae egestas enim auctor sit
                            amet. Pellentesque leo mauris, consectetur id ipsum sit amet, fersapien risus, commodo eget
                            turpis at, elementum convallis elit. Pellentesque enim turpis, hendrerit tristique lorem
                            ipsum dolor.</p>
                        <p>Praesent ac adipiscing ullamcorper semper ut amet ac risus. Lorem sapien ut odio odio nunc.
                            Ac adipiscing nibh porttitor erat risus justo adipiscing adipiscing amet placerat accumsan.
                            Vis. Faucibus odio magna tempus adipiscing a non. In mi primis arcu ut non accumsan vivamus
                            ac blandit adipiscing adipiscing arcu metus praesent turpis eu ac lacinia nunc ac commodo
                            gravida adipiscing eget accumsan ac nunc adipiscing adipiscing lorem ipsum dolor sit amet
                            nullam veroeros adipiscing.</p>
                        <p>Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi
                            integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat commodo eu sed ante
                            lacinia. Sapien a lorem in integer ornare praesent commodo adipiscing arcu in massa commodo
                            lorem accumsan at odio massa ac ac. Semper adipiscing varius montes viverra nibh in
                            adipiscing blandit tempus accumsan.</p>
                        <p>In arcu accumsan arcu adipiscing accumsan orci ac. Felis id enim aliquet. Accumsan ac integer
                            lobortis commodo ornare aliquet accumsan erat tempus amet porttitor. Ante commodo blandit
                            adipiscing integer semper orci eget. Faucibus commodo adipiscing mi eu nullam accumsan morbi
                            arcu ornare odio mi adipiscing nascetur lacus ac interdum morbi accumsan vis mi
                            accumsan.</p>

                    </div>
                </div>
                <div class="col-md-6 col-lg-4">
                    <div class="card dashboard-card p-3">
                        <h6>Section 2</h6>
                        <p>Additional content like reports, analytics, or graphs.</p>
                        <p>Donec eget ex magna. Interdum et malesuada fames ac ante ipsum primis in faucibus.
                            Pellentesque venenatis dolor imperdiet dolor mattis sagittis. Praesent rutrum sem diam,
                            vitae egestas enim auctor sit amet. Pellentesque leo mauris, consectetur id ipsum sit amet,
                            fergiat. Pellentesque in mi eu massa lacinia malesuada et a elit. Donec urna ex, lacinia in
                            purus ac, pretium pulvinar mauris. Curabitur sapien risus, commodo eget turpis at, elementum
                            convallis elit. Pellentesque enim turpis, hendrerit.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis dapibus rutrum facilisis. Class
                            aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam
                            tristique libero eu nibh porttitor fermentum. Nullam venenatis erat id vehicula viverra.
                            Nunc ultrices eros ut ultricies condimentum. Mauris risus lacus, blandit sit amet venenatis
                            non, bibendum vitae dolor. Nunc lorem mauris, fringilla in aliquam at, euismod in lectus.
                            Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis
                            egestas. In non lorem sit amet elit placerat maximus. Pellentesque aliquam maximus risus,
                            vel sed vehicula.</p>
                        <p>Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque venenatis dolor
                            imperdiet dolor mattis sagittis. Praesent rutrum sem diam, vitae egestas enim auctor sit
                            amet. Pellentesque leo mauris, consectetur id ipsum sit amet, fersapien risus, commodo eget
                            turpis at, elementum convallis elit. Pellentesque enim turpis, hendrerit tristique lorem
                            ipsum dolor.</p>
                        <p>Praesent ac adipiscing ullamcorper semper ut amet ac risus. Lorem sapien ut odio odio nunc.
                            Ac adipiscing nibh porttitor erat risus justo adipiscing adipiscing amet placerat accumsan.
                            Vis. Faucibus odio magna tempus adipiscing a non. In mi primis arcu ut non accumsan vivamus
                            ac blandit adipiscing adipiscing arcu metus praesent turpis eu ac lacinia nunc ac commodo
                            gravida adipiscing eget accumsan ac nunc adipiscing adipiscing lorem ipsum dolor sit amet
                            nullam veroeros adipiscing.</p>
                        <p>Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi
                            integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat commodo eu sed ante
                            lacinia. Sapien a lorem in integer ornare praesent commodo adipiscing arcu in massa commodo
                            lorem accumsan at odio massa ac ac. Semper adipiscing varius montes viverra nibh in
                            adipiscing blandit tempus accumsan.</p>
                        <p>In arcu accumsan arcu adipiscing accumsan orci ac. Felis id enim aliquet. Accumsan ac integer
                            lobortis commodo ornare aliquet accumsan erat tempus amet porttitor. Ante commodo blandit
                            adipiscing integer semper orci eget. Faucibus commodo adipiscing mi eu nullam accumsan morbi
                            arcu ornare odio mi adipiscing nascetur lacus ac interdum morbi accumsan vis mi
                            accumsan.</p>

                    </div>
                </div>
                <div class="col-md-12 col-lg-4">
                    <div class="card dashboard-card p-3">
                        <h6>Section 3</h6>
                        <p>Notifications, messages, or other widgets.</p>
                        <p>Donec eget ex magna. Interdum et malesuada fames ac ante ipsum primis in faucibus.
                            Pellentesque venenatis dolor imperdiet dolor mattis sagittis. Praesent rutrum sem diam,
                            vitae egestas enim auctor sit amet. Pellentesque leo mauris, consectetur id ipsum sit amet,
                            fergiat. Pellentesque in mi eu massa lacinia malesuada et a elit. Donec urna ex, lacinia in
                            purus ac, pretium pulvinar mauris. Curabitur sapien risus, commodo eget turpis at, elementum
                            convallis elit. Pellentesque enim turpis, hendrerit.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis dapibus rutrum facilisis. Class
                            aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Etiam
                            tristique libero eu nibh porttitor fermentum. Nullam venenatis erat id vehicula viverra.
                            Nunc ultrices eros ut ultricies condimentum. Mauris risus lacus, blandit sit amet venenatis
                            non, bibendum vitae dolor. Nunc lorem mauris, fringilla in aliquam at, euismod in lectus.
                            Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis
                            egestas. In non lorem sit amet elit placerat maximus. Pellentesque aliquam maximus risus,
                            vel sed vehicula.</p>
                        <p>Interdum et malesuada fames ac ante ipsum primis in faucibus. Pellentesque venenatis dolor
                            imperdiet dolor mattis sagittis. Praesent rutrum sem diam, vitae egestas enim auctor sit
                            amet. Pellentesque leo mauris, consectetur id ipsum sit amet, fersapien risus, commodo eget
                            turpis at, elementum convallis elit. Pellentesque enim turpis, hendrerit tristique lorem
                            ipsum dolor.</p>
                        <p>Praesent ac adipiscing ullamcorper semper ut amet ac risus. Lorem sapien ut odio odio nunc.
                            Ac adipiscing nibh porttitor erat risus justo adipiscing adipiscing amet placerat accumsan.
                            Vis. Faucibus odio magna tempus adipiscing a non. In mi primis arcu ut non accumsan vivamus
                            ac blandit adipiscing adipiscing arcu metus praesent turpis eu ac lacinia nunc ac commodo
                            gravida adipiscing eget accumsan ac nunc adipiscing adipiscing lorem ipsum dolor sit amet
                            nullam veroeros adipiscing.</p>
                        <p>Nunc lacinia ante nunc ac lobortis. Interdum adipiscing gravida odio porttitor sem non mi
                            integer non faucibus ornare mi ut ante amet placerat aliquet. Volutpat commodo eu sed ante
                            lacinia. Sapien a lorem in integer ornare praesent commodo adipiscing arcu in massa commodo
                            lorem accumsan at odio massa ac ac. Semper adipiscing varius montes viverra nibh in
                            adipiscing blandit tempus accumsan.</p>
                        <p>In arcu accumsan arcu adipiscing accumsan orci ac. Felis id enim aliquet. Accumsan ac integer
                            lobortis commodo ornare aliquet accumsan erat tempus amet porttitor. Ante commodo blandit
                            adipiscing integer semper orci eget. Faucibus commodo adipiscing mi eu nullam accumsan morbi
                            arcu ornare odio mi adipiscing nascetur lacus ac interdum morbi accumsan vis mi
                            accumsan.</p>

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

<!-- Bootstrap JS (local) -->
<script src="assets/js/bootstrap.bundle.min.js"></script>
<!-- Bootstrap Icons (via CDN for simplicity) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
<script src="assets/js/main.js"></script>

</body>
</html>