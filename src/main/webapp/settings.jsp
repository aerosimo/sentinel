<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance sentinel project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      settings.jsp                                                   ~
  ~ Created:   01/10/2025, 14:21                                              ~
  ~ Modified:  01/10/2025, 14:21                                              ~
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

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
    <title>Sentinel Settings | Aerosimo Ltd</title>
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
        <h4 class="text-white">Settings</h4>
        <hr class="bg-light"/>
        <ul class="nav nav-pills flex-column mb-auto">
            <li><a href="index.jsp" class="nav-link"><i class="bi bi-house"></i><span>Home</span></a></li>
            <li><a href="silhouette" class="nav-link"><i class="bi bi-person"></i><span>Profile</span></a></li>
            <li><a href="#" class="nav-link active"><i class="bi bi-gear"></i><span>Settings</span></a></li>
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
                <h5 class="mb-0">Settings</h5>
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle d-flex align-items-center" data-bs-toggle="dropdown" href="#">
                            <img src="assets/img/user/user.png" alt="User" class="rounded-circle me-2" width="40" height="40">
                            <span>${uname}</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="silhouette">Profile</a></li>
                            <li><a class="dropdown-item" href="#">Settings</a></li>
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
        <div class="profile-banner" style="background: url('assets/img/banner/setting.jpg') center/cover no-repeat; height:200px;">
            <form action="saveImage" method="post" enctype="multipart/form-data">
                <input type="hidden" name="email" value="${sessionScope.email}">
                <label for="avatarUpload">
                    <img src="${silhouette.image.avatar != null ? silhouette.image.avatar : 'assets/img/user/user.png'}"
                         alt="Avatar"
                         class="profile-avatar">
                </label>
                <input id="avatarUpload" type="file" name="avatar" accept="image/*" hidden onchange="this.form.submit()">
            </form>
        </div>

        <!-- Page Content -->
        <main class="container-fluid my-4 profile-section">
            <div class="row g-4">
                <!-- Left Column -->
                <div class="col-md-6 d-flex flex-column gap-3">
                    <!-- Personal Info -->
                    <form action="person" method="post" class="card dashboard-card p-3">
                        <h6 class="mb-3">Personal Info</h6>
                        <input type="hidden" name="email" value="${sessionScope.email}">
                        <div class="mb-2"><label class="form-label">Title</label>
                            <select class="form-select" name="title">
                                <option ${silhouette.person.title=='Mr' ? 'selected':''}>Mr</option>
                                <option ${silhouette.person.title=='Mrs' ? 'selected':''}>Mrs</option>
                                <option ${silhouette.person.title=='Miss' ? 'selected':''}>Miss</option>
                                <option ${silhouette.person.title=='Ms' ? 'selected':''}>Ms</option>
                                <option ${silhouette.person.title=='Dr' ? 'selected':''}>Dr</option>
                                <option ${silhouette.person.title=='Professor' ? 'selected':''}>Professor</option>
                                <option ${silhouette.person.title=='Sir' ? 'selected':''}>Sir</option>
                                <option ${silhouette.person.title=='Dame' ? 'selected':''}>Dame</option>
                                <option ${silhouette.person.title=='Reverend' ? 'selected':''}>Reverend</option>
                                <option ${silhouette.person.title=='Pastor' ? 'selected':''}>Pastor</option>
                            </select>
                        </div>
                        <div class="mb-2"><label class="form-label">First Name</label>
                            <input type="text" class="form-control" name="firstName" value="${silhouette.person.firstName}"></div>
                        <div class="mb-2"><label class="form-label">Middle Name</label>
                            <input type="text" class="form-control" name="middleName" value="${silhouette.person.middleName}"></div>
                        <div class="mb-2"><label class="form-label">Last Name</label>
                            <input type="text" class="form-control" name="lastName" value="${silhouette.person.lastName}"></div>
                        <div class="mb-2"><label class="form-label">Gender</label>
                            <select class="form-select" name="gender">
                                <option ${silhouette.person.gender=='Male' ? 'selected':''}>Male</option>
                                <option ${silhouette.person.gender=='Female' ? 'selected':''}>Female</option>
                            </select></div>
                        <div class="mb-2"><label class="form-label">Birthday</label>
                            <input type="date" class="form-control" name="birthday" value="${silhouette.person.birthday}"></div>
                        <button class="btn btn-primary mt-2" type="submit">Save Personal Info</button>
                    </form>

                    <!-- Address -->
                    <form action="address" method="post" class="card dashboard-card p-3">
                        <h6 class="mb-3">Address</h6>
                        <input type="hidden" name="email" value="${sessionScope.email}">
                        <input class="form-control mb-2" type="text" name="firstline" value="${silhouette.address.firstline}" placeholder="First Line">
                        <input class="form-control mb-2" type="text" name="secondline" value="${silhouette.address.secondline}" placeholder="Second Line">
                        <input class="form-control mb-2" type="text" name="thirdline" value="${silhouette.address.thirdline}" placeholder="Third Line">
                        <input class="form-control mb-2" type="text" name="city" value="${silhouette.address.city}" placeholder="City">
                        <input class="form-control mb-2" type="text" name="postcode" value="${silhouette.address.postcode}" placeholder="Postcode">
                        <div class="mb-2">
                            <label class="form-label">Country</label>
                            <select class="form-select" name="country">
                                <c:forEach var="c" items="${countries}">
                                    <option value="${c.code}" ${silhouette.address.country == c.code ? 'selected' : ''}>
                                        ${c.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <button class="btn btn-primary" type="submit">Save Address</button>
                    </form>
                </div>

                <!-- Right Column -->
                <div class="col-md-6 d-flex flex-column gap-3">
                <!-- Contacts -->
                    <form action="saveContact" method="post" class="card dashboard-card p-3">
                        <h6 class="mb-3">Contact Info</h6>
                        <input type="hidden" name="email" value="${sessionScope.email}">

                        <!-- Contact rows wrapper -->
                        <div id="contactsContainer">
                            <c:forEach var="c" items="${silhouette.contacts}">
                                <div class="contact-row mb-3 border p-2 rounded">
                                    <div class="mb-2">
                                        <label class="form-label">Channel</label>
                                        <select class="form-select" name="channel">
                                            <option value="Phone"    ${c.channel=='Phone' ? 'selected' : ''}>Phone</option>
                                            <option value="Email"    ${c.channel=='Email' ? 'selected' : ''}>Email</option>
                                            <option value="Fax"      ${c.channel=='Fax' ? 'selected' : ''}>Fax</option>
                                            <option value="Twitter"  ${c.channel=='Twitter' ? 'selected' : ''}>Twitter</option>
                                            <option value="Facebook" ${c.channel=='Facebook' ? 'selected' : ''}>Facebook</option>
                                            <option value="LinkedIn" ${c.channel=='LinkedIn' ? 'selected' : ''}>LinkedIn</option>
                                            <option value="Snapchat" ${c.channel=='Snapchat' ? 'selected' : ''}>Snapchat</option>
                                            <option value="Website"  ${c.channel=='Website' ? 'selected' : ''}>Website</option>
                                        </select>
                                    </div>

                                    <div class="mb-2">
                                        <label class="form-label">Value</label>
                                        <input class="form-control" type="text" name="address" value="${c.address}">
                                    </div>

                                    <div class="mb-2">
                                        <label class="form-label">Consent</label>
                                        <select class="form-select" name="consent">
                                            <option value="YES" ${c.consent=='YES' ? 'selected' : ''}>YES</option>
                                            <option value="NO"  ${c.consent=='NO'  ? 'selected' : ''}>NO</option>
                                        </select>
                                    </div>

                                    <button type="button" class="btn btn-sm btn-outline-danger removeContact">Remove</button>
                                </div>
                            </c:forEach>
                        </div>

                        <!-- Add Contact Button -->
                        <button type="button" class="btn btn-sm btn-outline-success mb-3" id="addContactBtn">+ Add Another Contact</button>

                        <button class="btn btn-primary" type="submit">Save Contacts</button>
                    </form>

                    <!-- Template for new contacts (hidden) -->
                    <template id="contactTemplate">
                        <div class="contact-row mb-3 border p-2 rounded">
                            <div class="mb-2">
                                <label class="form-label">Channel</label>
                                <select class="form-select" name="channel">
                                    <option value="Phone">Phone</option>
                                    <option value="Email">Email</option>
                                    <option value="Fax">Fax</option>
                                    <option value="Twitter">Twitter</option>
                                    <option value="Facebook">Facebook</option>
                                    <option value="LinkedIn">LinkedIn</option>
                                    <option value="Snapchat">Snapchat</option>
                                    <option value="Website">Website</option>
                                </select>
                            </div>

                            <div class="mb-2">
                                <label class="form-label">Value</label>
                                <input class="form-control" type="text" name="address" placeholder="Enter contact value">
                            </div>

                            <div class="mb-2">
                                <label class="form-label">Consent</label>
                                <select class="form-select" name="consent">
                                    <option value="YES">YES</option>
                                    <option value="NO">NO</option>
                                </select>
                            </div>

                            <button type="button" class="btn btn-sm btn-outline-danger removeContact">Remove</button>
                        </div>
                    </template>



                    <!-- Profile -->
                    <form action="profile" method="post" class="card dashboard-card p-3">
                        <h6 class="mb-3">Profile Details</h6>
                        <input type="hidden" name="email" value="${sessionScope.email}">
                        <div class="mb-2"><label class="form-label">Marital Status</label>
                            <select class="form-select" name="maritalStatus">
                                <option ${silhouette.profile.maritalStatus=='Separated' ? 'selected':''}>Separated</option>
                                <option ${silhouette.profile.maritalStatus=='Widowed' ? 'selected':''}>Widowed</option>
                                <option ${silhouette.profile.maritalStatus=='Single' ? 'selected':''}>Single</option>
                                <option ${silhouette.profile.maritalStatus=='Married' ? 'selected':''}>Married</option>
                                <option ${silhouette.profile.maritalStatus=='Lone' ? 'selected':''}>Lone</option>
                                <option ${silhouette.profile.maritalStatus=='Live-in' ? 'selected':''}>Live-in</option>
                                <option ${silhouette.profile.maritalStatus=='Estranged' ? 'selected':''}>Estranged</option>
                                <option ${silhouette.profile.maritalStatus=='Engaged' ? 'selected':''}>Engaged</option>
                                <option ${silhouette.profile.maritalStatus=='Divorced' ? 'selected':''}>Divorced</option>
                            </select>
                        </div>
                        <input class="form-control mb-2" type="text" name="height" value="${silhouette.profile.height}" placeholder="Height">
                        <input class="form-control mb-2" type="text" name="weight" value="${silhouette.profile.weight}" placeholder="Weight">
                        <div class="mb-2"><label class="form-label">Ethnicity</label>
                            <select class="form-select" name="ethnicity">
                                <option ${silhouette.profile.ethnicity=='African' ? 'selected':''}>African</option>
                                <option ${silhouette.profile.ethnicity=='Bangladeshi' ? 'selected':''}>Bangladeshi</option>
                                <option ${silhouette.profile.ethnicity=='Caribbean' ? 'selected':''}>Caribbean</option>
                                <option ${silhouette.profile.ethnicity=='Chinese' ? 'selected':''}>Chinese</option>
                                <option ${silhouette.profile.ethnicity=='Arab' ? 'selected':''}>Arab</option>
                                <option ${silhouette.profile.ethnicity=='Irish' ? 'selected':''}>Irish</option>
                                <option ${silhouette.profile.ethnicity=='Indian' ? 'selected':''}>Indian</option>
                                <option ${silhouette.profile.ethnicity=='Pakistani' ? 'selected':''}>Pakistani</option>
                            </select>
                        </div>
                        <div class="mb-2"><label class="form-label">Religion</label>
                            <select class="form-select" name="religion">
                                <option ${silhouette.profile.religion=='Christianity' ? 'selected':''}>Christianity</option>
                                <option ${silhouette.profile.religion=='Islam' ? 'selected':''}>Islam</option>
                                <option ${silhouette.profile.religion=='Atheist' ? 'selected':''}>Atheist</option>
                                <option ${silhouette.profile.religion=='Hinduism' ? 'selected':''}>Hinduism</option>
                                <option ${silhouette.profile.religion=='Buddhism' ? 'selected':''}>Buddhism</option>
                                <option ${silhouette.profile.religion=='Sikhism' ? 'selected':''}>Sikhism</option>
                                <option ${silhouette.profile.religion=='Judaism' ? 'selected':''}>Judaism</option>
                            </select>
                        </div>
                        <div class="mb-2"><label class="form-label">Eye Colour</label>
                            <select class="form-select" name="eyeColour">
                                <option ${silhouette.profile.eyeColour=='Amber' ? 'selected':''}>Amber</option>
                                <option ${silhouette.profile.eyeColour=='Blue' ? 'selected':''}>Blue</option>
                                <option ${silhouette.profile.eyeColour=='Brown' ? 'selected':''}>Brown</option>
                                <option ${silhouette.profile.eyeColour=='Grey' ? 'selected':''}>Grey</option>
                                <option ${silhouette.profile.eyeColour=='Green' ? 'selected':''}>Green</option>
                                <option ${silhouette.profile.eyeColour=='Hazel' ? 'selected':''}>Hazel</option>
                                <option ${silhouette.profile.eyeColour=='Red' ? 'selected':''}>Red</option>
                                <option ${silhouette.profile.eyeColour=='Violet' ? 'selected':''}>Violet</option>
                            </select>
                        </div>
                        <div class="mb-2"><label class="form-label">Phenotype</label>
                            <select class="form-select" name="phenotype">
                                <option ${silhouette.profile.phenotype=='A+' ? 'selected':''}>A+</option>
                                <option ${silhouette.profile.phenotype=='A-' ? 'selected':''}>A-</option>
                                <option ${silhouette.profile.phenotype=='B+' ? 'selected':''}>B+</option>
                                <option ${silhouette.profile.phenotype=='B-' ? 'selected':''}>B-</option>
                                <option ${silhouette.profile.phenotype=='O+' ? 'selected':''}>O+</option>
                                <option ${silhouette.profile.phenotype=='O-' ? 'selected':''}>O-</option>
                                <option ${silhouette.profile.phenotype=='AB+' ? 'selected':''}>AB+</option>
                                <option ${silhouette.profile.phenotype=='AB-' ? 'selected':''}>AB-</option>
                            </select>
                        </div>
                        <div class="mb-2"><label class="form-label">Genotype</label>
                            <select class="form-select" name="genotype">
                                <option ${silhouette.profile.genotype=='AA' ? 'selected':''}>AA</option>
                                <option ${silhouette.profile.genotype=='AS' ? 'selected':''}>AS</option>
                                <option ${silhouette.profile.genotype=='SS' ? 'selected':''}>SS</option>
                                <option ${silhouette.profile.genotype=='AC' ? 'selected':''}>AC</option>
                            </select>
                        </div>
                        <div class="mb-2"><label class="form-label">Disability</label>
                            <select class="form-select" name="disability">
                                <option ${silhouette.profile.disability=='None' ? 'selected':''}>None</option>
                                <option ${silhouette.profile.disability=='Deafness' ? 'selected':''}>Deafness</option>
                                <option ${silhouette.profile.disability=='Anxiety' ? 'selected':''}>Anxiety</option>
                                <option ${silhouette.profile.disability=='Schizophrenia' ? 'selected':''}>Schizophrenia</option>
                                <option ${silhouette.profile.disability=='Depression' ? 'selected':''}>Depression</option>
                                <option ${silhouette.profile.disability=='Arthritis' ? 'selected':''}>Arthritis</option>
                                <option ${silhouette.profile.disability=='Diabetes' ? 'selected':''}>Diabetes</option>
                                <option ${silhouette.profile.disability=='Amputation' ? 'selected':''}>Amputation</option>
                            </select>
                        </div>
                        <button class="btn btn-primary" type="submit">Save Profile</button>
                    </form>
                </div>
            </div>
        </main>

        <!-- Footer -->
        <footer>
            <div class="copy">&copy; <script>document.write(new Date().getFullYear());</script> Sentinel by Aerosimo Ltd. All rights reserved.</div>
        </footer>
    </div>
</div>

<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/main.js"></script>

<script>
document.addEventListener("DOMContentLoaded", () => {
    const addBtn = document.getElementById("addContactBtn");
    const container = document.getElementById("contactsContainer");
    const template = document.getElementById("contactTemplate");

    // Add new contact row
    addBtn.addEventListener("click", () => {
        const clone = template.content.cloneNode(true);
        container.appendChild(clone);
    });

    // Delegate remove button
    container.addEventListener("click", (e) => {
        if (e.target.classList.contains("removeContact")) {
            e.target.closest(".contact-row").remove();
        }
    });
});
</script>

</body>
</html>