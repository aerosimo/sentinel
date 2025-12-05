<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  ~ This piece of work is to enhance sentinel project functionality.          ~
  ~                                                                           ~
  ~ Author:    eomisore                                                       ~
  ~ File:      register.html                                                  ~
  ~ Created:   05/12/2025, 21:01                                              ~
  ~ Modified:  05/12/2025, 21:01                                              ~
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
    <!-- Title -->
    <title>Register | Aerosimo Ltd</title>
    <!-- Favicon -->
    <link href="assets/img/favicon/favicon.ico" rel="shortcut icon"/>
    <link href="assets/img/favicon/favicon.ico" rel="icon" type="image/x-icon">
    <link href="assets/img/favicon/favicon-32x32.png" rel="icon" sizes="32x32" type="image/png">
    <link href="assets/img/favicon/favicon-16x16.png" rel="icon" sizes="16x16" type="image/png">
    <link href="assets/img/favicon/apple-touch-icon.png" rel="apple-touch-icon" sizes="180x180">
    <link href="assets/img/favicon/android-chrome-192x192.png" rel="android-chrome" sizes="192x192">
    <!-- Local Bootstrap -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
    <link rel="stylesheet" href="assets/css/auth.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"/>
</head>
<body>

<div class="d-flex align-items-center justify-content-center min-vh-100">
    <div class="row g-0 login-wrap w-100">
        <div class="col-md-6 left-pane d-flex flex-column justify-content-center text-center">
            <img src="https://thumbs4.imagebam.com/3e/10/82/MED2HDH_t.png" alt="Logo" style="max-width:120px;" class="mb-3">
            <h1>Register</h1>
            <p>Create your account and start managing your app. 🚀</p>
        </div>
        <div class="col-md-6 right-pane">
            <form id="registerForm" novalidate>
                <div class="field">
                    <label for="regUsername">Username</label>
                    <span class="input-icon">👤</span>
                    <input type="text" id="regUsername" name="username" class="form-control"
                           placeholder="Username" required pattern="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$">
                    <div class="error-msg">4-20 chars, letters, numbers, . _ - allowed</div>
                </div>

                <div class="field">
                    <label for="regEmail">Email</label>
                    <span class="input-icon">📧</span>
                    <input type="email" id="regEmail" name="email" class="form-control"
                           placeholder="Email" required>
                    <div class="error-msg">Enter a valid email</div>
                </div>

                <div class="field">
                    <label for="regPassword">Password</label>
                    <span class="input-icon">🔒</span>
                    <input type="password" id="regPassword" name="password" class="form-control"
                           placeholder="●●●●●●●●" required
                           pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$">
                    <div class="error-msg">Must have 1 uppercase, 1 lowercase, 1 number, 1 symbol</div>
                </div>

                <div id="regMessage" class="text-center mt-3"></div>

                <div class="text-center">
                    <button type="submit" class="btn btn-cta">Register</button>
                </div>
                <div class="form-actions text-center mt-3">
                    <a href="login.jsp">Already have an account? Sign in</a>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- Bootstrap JS (local) -->
<script>
document.getElementById('registerForm').addEventListener('submit', async function(e) {
    e.preventDefault();
    const uname = document.getElementById('regUsername').value.trim();
    const email = document.getElementById('regEmail').value.trim();
    const password = document.getElementById('regPassword').value.trim();
    const msgDiv = document.getElementById('regMessage');
    msgDiv.textContent = '';

    try {
        const res = await fetch('https://ominet.aerosimo.com:9443/authcore/api/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username: uname, email: email, password: password })
        });
        const data = await res.json();
        if(data.status === 'success') {
            msgDiv.style.color = '#00ff44';
            msgDiv.textContent = "Registered successfully! Check your email for the verification code.";

            // Removed auto redirect; user will manually go to verify.jsp
        } else {
            msgDiv.style.color = '#ff0033';
            msgDiv.textContent = data.message || 'Registration failed';
        }
    } catch(err) {
        msgDiv.style.color = '#ff0033';
        msgDiv.textContent = 'Unable to reach server';
        console.error(err);
    }
});
</script>
<script src="assets/js/auth.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>