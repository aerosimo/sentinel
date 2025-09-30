<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance sentinel project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      404.html                                                       ~
  ~ Created:   13/09/2025, 01:39                                              ~
  ~ Modified:  13/09/2025, 01:39                                              ~
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

<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" language="java" %>
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
    <title>404 NOT FOUND | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Bootstrap CSS (local) -->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/fault.css" rel="stylesheet">
</head>

<body>
<h1>404</h1>
<div><p>> <span>ERROR CODE</span>: "<i>HTTP 404 Not Found</i>"</p>
    <p>> <span>ERROR DESCRIPTION</span>: "<i>The server cannot find the requested resource</i>"</p>
    <p>> <span>ERROR POSSIBLY CAUSED BY</span>: [<b>This can also mean that the endpoint is valid but the resource
        itself does not exist or possibly the server may also send this response instead of 403 to hide the existence of
        a resource from an unauthorized client</b>...]</p>
    <p>> <span>SOME PAGES ON THIS SERVER THAT YOU DO HAVE PERMISSION TO ACCESS</span>: [<a href="index.jsp">Welcome Page</a>, <a
            href="signin.jsp">Login</a>, <a href="signup.jsp">Sign Up</a>...]</p>
    <p>> <span>HAVE A NICE DAY :-)</span></p>
    <br>
    <br>
    <p>> <span><a href="javascript:history.back()">Go Back</a></span></p>

    <%-- 🔹 Show actual server error if available --%>
    <%
    if (exception != null) {
    %>
    <hr>
    <h3>Debug Information (Server Error)</h3>
    <p><b>Message:</b> <%= exception.getMessage() %></p>
    <details>
        <summary>Stack Trace</summary>
        <pre>
                <%
                    exception.printStackTrace(new java.io.PrintWriter(out));
                %>
            </pre>
    </details>
    <%
    }
    %>

</div>

<!--   Java Script   -->
<script src="assets/js/fault.js"></script>
</body>
</html>