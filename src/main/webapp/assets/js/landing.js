```css
body {
  margin: 0;
  padding: 0;
  font-family: "Segoe UI", sans-serif;
  background-color: #0d0d0f;
  color: #f0f0f0;
  overflow-x: hidden;
}

/* Parallax Background */
.parallax {
  background-image: url("../img/banner/parallax.jpg");
  height: 80vh;
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  filter: brightness(60%);
  position: relative;
}

.parallax-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.parallax-text img {
  width: 150px;
  height: auto;
  margin-bottom: 20px;
}

.parallax-text h1 {
    font-size: 3rem;
    font-weight: 700;
    letter-spacing: 3px;
    color: #ffffff; /* White text */
    text-shadow: 2px 2px 6px rgba(0, 0, 0, 0.6); /* subtle shadow for contrast */
}

/* CTA Button */
.cta-login-btn {
    background: linear-gradient(145deg, #4d3b7a, #6a55b0);
    color: #fff;
    border: none;
    padding: 12px 28px;
    font-size: 16px;
    font-weight: 500;
    border-radius: 12px;
    cursor: pointer;
    box-shadow: 0 5px 15px rgba(0,0,0,0.25);
    transition: all 0.3s ease;
    display: inline-flex;
    align-items: center;
    gap: 8px;
}

.cta-login-btn:hover {
    background: linear-gradient(145deg, #3b2e61, #5a3f99);
    transform: translateY(-2px);
    box-shadow: 0 8px 20px rgba(0,0,0,0.35);
}

.cta-login-btn:active {
    transform: translateY(1px);
    box-shadow: 0 4px 10px rgba(0,0,0,0.25);
}

.btn-icon {
    font-size: 18px;
}

/* ============== New Styling =================== */
.cta-btn {
    padding: 10px 22px;
    background: #4d3b7a;
    color: #fff;
    border: none;
    border-radius: 25px;
    cursor: pointer;
    font-size: 14px;
    transition: 0.3s ease;
}
.cta-btn:hover {
    background: #6a54a3;
}
.cta-btn.register {
    background: transparent;
    border: 2px solid #4d3b7a;
    color: #4d3b7a;
}
.cta-btn.register:hover {
    background: #4d3b7a;
    color: #fff;
}

/* ============================================== */

/* ============= NEW MODAL STYLING ================ */
<style>
.auth-modal {
    display: none;
    position: fixed;
    z-index: 2000;
    left: 0; top: 0;
    width: 100%; height: 100%;
    background: rgba(0,0,0,0.65);
}

.auth-content {
    background: #1b1b1f;
    width: 350px;
    margin: 8% auto;
    padding: 25px;
    border-radius: 15px;
    color: white;
    box-shadow: 0 0 20px rgba(0,0,0,0.4);
}

.close-btn {
    float: right;
    cursor: pointer;
    font-size: 22px;
    color: #aaa;
}

.auth-panel h2 {
    margin-bottom: 20px;
    color: #c6b7ff;
}

.input-group {
    background: #2a2a31;
    border-radius: 10px;
    display: flex;
    align-items: center;
    margin-bottom: 12px;
    padding: 8px;
}

.input-group .icon {
    margin-right: 8px;
    font-size: 18px;
}

.input-group input {
    width: 100%;
    background: transparent;
    border: none;
    outline: none;
    color: #fff;
}

.submit-btn {
    width: 100%;
    padding: 10px;
    background: #4d3b7a;
    border: none;
    border-radius: 10px;
    color: white;
    cursor: pointer;
    transition: 0.3s;
}

.submit-btn:hover {
    background: #6a54a3;
}

.auth-message {
    margin-top: 15px;
    text-align: center;
    font-size: 14px;
    color: #c8ffc8;
}
</style>
/* ================================================== */

/* === Modal Login + Signup + Token ==== */
/* MODAL BACKDROP */
.ominet-modal {
    position: fixed;
    inset: 0;
    background: rgba(0,0,0,0.75);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 999;
}

.hidden { display: none !important; }

/* CONTENT */
.ominet-modal-content.modern-dark {
    width: 420px;
    padding: 30px;
    border-radius: 18px;

    background: rgba(20, 20, 30, 0.85);
    backdrop-filter: blur(18px);
    border: 1px solid rgba(255,255,255,0.08);

    box-shadow: 0 8px 25px rgba(0,0,0,0.35);
    animation: fadeIn 0.35s ease;
    position: relative;
}

@keyframes fadeIn {
    from { transform: scale(0.95); opacity: 0; }
    to   { transform: scale(1); opacity: 1; }
}

.modal-close {
    position: absolute;
    right: 20px;
    top: 12px;
    font-size: 28px;
    color: #bbb;
    cursor: pointer;
    transition: 0.2s;
}

.modal-close:hover {
    color: white;
}

/* SWITCH BUTTONS */
.auth-switch {
    display: flex;
    gap: 10px;
    margin-bottom: 25px;
}

.switch-btn {
    flex: 1;
    padding: 10px;
    border-radius: 12px;
    border: 1px solid rgba(255,255,255,0.08);
    background: rgba(255,255,255,0.06);
    color: #aaa;
    font-weight: 600;
    cursor: pointer;
    transition: 0.25s;
}

.switch-btn.active {
    background: #4d3b7a;
    color: #fff;
    border: none;
}

/* TITLES */
.modal-title {
    color: white;
    margin-bottom: 20px;
    font-size: 22px;
}

/* INPUT GROUP WITH ICON */
.input-group {
    display: flex;
    align-items: center;
    background: rgba(255,255,255,0.08);
    border: 1px solid rgba(255,255,255,0.1);
    border-radius: 10px;
    padding: 10px;
    margin-top: 15px;
}

.input-group input {
    background: transparent;
    border: none;
    outline: none;
    color: white;
    width: 100%;
    padding-left: 8px;
}

.input-icon {
    font-size: 18px;
    color: #bbb;
}

/* PRIMARY BUTTON */
.btn-primary-dark {
    width: 100%;
    background: linear-gradient(135deg, #4d3b7a, #6a56b0);
    color: white;
    padding: 14px;
    border-radius: 12px;
    border: none;
    margin-top: 25px;
    font-weight: 600;
    cursor: pointer;
    transition: 0.25s;
}

.btn-primary-dark:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0,0,0,0.4);
}

/* === Ominet Cards === */
.ominet-card {
  background: linear-gradient(145deg, #121214, #0a0a0c);
  border: 1px solid #1c1c1f;
  box-shadow: 0 0 25px rgba(77, 59, 122, 0.15);
  border-radius: 12px;
  width: 100%;
  aspect-ratio: 1 / 1;
  transition: 0.35s ease-in-out;
  backdrop-filter: blur(5px);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 20px;
  text-align: center;
}

.ominet-card:hover {
  transform: scale(1.03);
  box-shadow: 0 0 35px rgba(77, 59, 122, 0.45);
}

.ominet-card h3 {
  color: #4d3b7a;
  margin-bottom: 10px;
  font-weight: 600;
}

/* === Card 2: Activity Monitor / Server Overview === */
.activity-monitor {
  display: flex;
  flex-direction: column;
  gap: 10px;
  text-align: left;
}

.activity-monitor h6 {
  color: #4d3b7a;
  font-weight: 600;
  margin-bottom: 10px;
}

.activity-monitor ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.activity-monitor li {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: #1a1a1c;
  padding: 8px 12px;
  border-radius: 6px;
  margin-bottom: 6px;
  font-size: 0.9rem;
}

.activity-monitor li span {
  padding: 2px 6px;
  border-radius: 6px;
  font-weight: 600;
  color: #fff;
}

.activity-monitor .bg-success { background-color: #00ff44; }
.activity-monitor .bg-info    { background-color: #00bfff; }
.activity-monitor .bg-primary { background-color: #4d3b7a; }
.activity-monitor .bg-warning { background-color: #ffaa00; }
.activity-monitor .bg-danger  { background-color: #ff0033; }

/* === Section & Layout === */
.section-title {
  font-size: 2rem;
  color: #4d3b7a;
  margin-top: 60px;
  text-align: center;
  margin-bottom: 40px;
  letter-spacing: 1px;
}

.card-row {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.card-col {
  flex: 0 0 48%; /* Two cards per row */
}

@media (max-width: 768px) {
  .card-col {
    flex: 0 0 100%; /* Single column on smaller screens */
  }
}

/* === Star Topology Card 1 === */
.star-topology {
  position: relative;
  width: 100%;
  height: 230px;
  margin-top: 10px;
}

/* Hub */
.hub-center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
}

.hub-core {
  width: 45px;
  height: 45px;
  background-color: #4d3b7a;
  border-radius: 50%;
  box-shadow: 0 0 12px #4d3b7a;
}

.hub-center p {
  margin-top: 5px;
  font-size: 12px;
}

/* Generic server node */
.node {
  position: absolute;
  text-align: center;
  width: 70px;
}

.node img {
  width: 55px;
  height: auto;
  filter: drop-shadow(0 0 5px rgba(255,255,255,0.1));
}

.node p {
  font-size: 12px;
  margin-top: 4px;
}

.status-light {
  position: absolute;
  top: -5px;
  right: 8px;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  box-shadow: 0 0 6px currentColor;
}

.online  { background: #00ff44; animation: blink 1.5s infinite; }
.offline { background: #ff0033; animation: blink 1.2s infinite; }
.warning { background: #ffaa00; animation: blink 1.4s infinite; }

@keyframes blink {
  0%, 100% { opacity: 1; }
  50% { opacity: .3; }
}

/* Star layout positions */
.jenkins { top: 0%; left: 50%; transform: translate(-50%, 0); }
.oracle  { top: 50%; left: 100%; transform: translate(-100%, -50%); }
.tomee   { top: 130%; left: 50%; transform: translate(-50%, -100%); }
.fedora  { top: 50%; left: 0%; transform: translate(0, -50%); }

.node::before {
  content: "";
  position: absolute;
  width: 2px;
  height: 55px;
  top: 28px;
  left: 50%;
  background: #4d3b7a60;
  transform-origin: top;
}

/* Optional: Topology wrapper inside card for alignment */
.topology-wrapper {
  width: 100%;
  height: 75%;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Activity Monitor / Server Overview  Card 2*/
.activity-monitor h4 {
    color: #4d3b7a;
    margin-bottom: 10px;
    font-weight: 600;
}

.overview-list {
    list-style: none;
    padding: 0;
    margin: 0;
}

.overview-list li {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 8px 0;
    border-bottom: 1px solid #1c1c1f;
    font-size: 0.95rem;
}

.overview-list li:last-child {
    border-bottom: none;
}

/* Metric Icons */
.metric-icon {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 18px;
    margin-right: 8px;
    color: #4d3b7a;
    font-size: 0.9rem;
}

/* Adjust badge spacing */
.overview-list span.badge {
    min-width: 40px;
    font-size: 0.85rem;
}

/* Error Intelligence Table Card 3*/
/* Error Table Wrapper */
.error-table-wrapper {
    /* No max-height or scroll, natural height for top 5 rows */
    overflow: visible;
}

/* Table Styles */
.error-table {
    width: 100%;
    border-collapse: collapse;
}

/* Table Header */
.error-table thead th {
    background-color: #4d3b7a; /* purple header */
    color: #fff;
    font-weight: 600;
    text-align: center; /* center header text */
    padding: 6px;
}

/* Table Body Cells */
.error-table tbody td {
    background-color: #121214; /* dark background */
    color: #f0f0f0;
    text-align: center; /* center cell content */
    vertical-align: middle;
    position: relative;
    padding: 6px 10px;
}

/* Row Left Border Based on Status */
.error-table tbody tr.open td:first-child::before {
    background-color: #ff0033; /* Red for OPEN */
}
.error-table tbody tr.resolved td:first-child::before {
    background-color: #00ff44; /* Green for RESOLVED */
}
.error-table tbody tr.closed td:first-child::before {
    background-color: #4d3b7a; /* Purple for CLOSED */
}
.error-table tbody tr.pending td:first-child::before {
    background-color: #ffaa00; /* Orange for PENDING */
}

/* Status Badges */
.error-status {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    gap: 4px;
    padding: 2px 8px;
    border-radius: 12px;
    font-size: 0.75rem;
    font-weight: 600;
    color: #fff;
    min-width: 70px;
    text-align: center;
}

/* Status Colors + Icons */
.error-status.open      { background-color: #ff0033; content: "⚠️"; }
.error-status.resolved  { background-color: #00ff44; content: "✅"; }
.error-status.closed    { background-color: #4d3b7a; content: "🗂️"; }
.error-status.pending   { background-color: #ffaa00; content: "⏳"; }

/* Hover effect for rows */
.error-table tbody tr:hover td {
    background-color: #1c1c1f;
}

/* System Metrics Card 4*/
.chart-row {
    display: flex;
    justify-content: space-around;
    align-items: center;
    margin-top: 15px;
    flex-wrap: wrap;
}

.chart-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 30%;
    margin-bottom: 10px;
}

.chart-label {
    margin-top: 8px;
    font-size: 0.9rem;
    color: #f0f0f0;
}
```

```js
// landing.js
/* ===============NEW MODAL ============== */
function openLoginModal() {
    document.getElementById("authModal").style.display = "block";
    document.getElementById("loginPanel").style.display = "block";
    document.getElementById("registerPanel").style.display = "none";
    document.getElementById("verifyPanel").style.display = "none";
}

function openRegisterModal() {
    document.getElementById("authModal").style.display = "block";
    document.getElementById("loginPanel").style.display = "none";
    document.getElementById("registerPanel").style.display = "block";
    document.getElementById("verifyPanel").style.display = "none";
}

function closeAuthModal() {
    document.getElementById("authModal").style.display = "none";
}

function submitRegister() {
    let data = {
        username: document.getElementById("regUsername").value,
        email: document.getElementById("regEmail").value,
        password: document.getElementById("regPassword").value
    };

    fetch("https://ominet.aerosimo.com:9443/authcore/api/auth/register", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(r => {
        document.getElementById("authMessage").innerText =
            "Account created! Check email for verification code.";

        // Switch to Verify Panel
        document.getElementById("registerPanel").style.display = "none";
        document.getElementById("verifyPanel").style.display = "block";
    });
}

function submitVerify() {
    let data = { token: document.getElementById("verifyToken").value };

    fetch("https://ominet.aerosimo.com:9443/authcore/api/auth/verify", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(r => {
        document.getElementById("authMessage").innerText =
            "Email verified! You can now log in.";

        // Switch to login panel
        document.getElementById("verifyPanel").style.display = "none";
        document.getElementById("loginPanel").style.display = "block";
    });
}

function submitLogin() {
    let data = {
        username: document.getElementById("loginUsername").value,
        password: document.getElementById("loginPassword").value
    };

    fetch("https://ominet.aerosimo.com:9443/authcore/api/auth/login", {
        method: "POST",
        headers: {"Content-Type":"application/json"},
        body: JSON.stringify(data)
    })
    .then(res => res.json())
    .then(r => {
        // Store session variables
        fetch("session.jsp?username=" + data.username + "&token=" + r.message);

        window.location.href = "dashboard.jsp";
    });
}

/* ======================================= */
/** ===============================
    MODAL CONTROL
================================**/
function openAuthModal() {
    document.getElementById("authModal").classList.remove("hidden");
}

function closeAuthModal() {
    document.getElementById("authModal").classList.add("hidden");
}

function openTokenModal() {
    document.getElementById("tokenModal").classList.remove("hidden");
}

function closeTokenModal() {
    document.getElementById("tokenModal").classList.add("hidden");
}

function showSignup() {
    document.getElementById("signupForm").classList.remove("hidden");
    document.getElementById("loginForm").classList.add("hidden");
    document.getElementById("btnSignupSwitch").classList.add("active");
    document.getElementById("btnLoginSwitch").classList.remove("active");
}

function showLogin() {
    document.getElementById("loginForm").classList.remove("hidden");
    document.getElementById("signupForm").classList.add("hidden");
    document.getElementById("btnLoginSwitch").classList.add("active");
    document.getElementById("btnSignupSwitch").classList.remove("active");
}

/** ===============================
    SIGNUP + TOKEN FLOW
================================**/
function signupUser() {
    const user = {
        username: document.getElementById("signupUsername").value,
        password: document.getElementById("signupPassword").value,
        email: document.getElementById("signupEmail").value
    };

    // Call your signup API
    fetch("/auth/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user)
    })
    .then(res => res.json())
    .then(data => {
        if (data.status === "success") {
            closeAuthModal();
            openTokenModal();
        } else {
            alert(data.message || "Signup failed");
        }
    });
}

function openTokenModal() {
    document.getElementById("tokenModal").classList.remove("hidden");
}

function closeTokenModal() {
    document.getElementById("tokenModal").classList.add("hidden");
}

function verifyToken() {
    const token = document.getElementById("verifyToken").value;

    fetch("/auth/verify-token", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ token: token })
    })
    .then(res => res.json())
    .then(data => {
        if (data.status === "verified") {
            alert("Account verified successfully!");
            closeTokenModal();
        } else {
            alert("Invalid token");
        }
    });
}

/** ===============================
    LOGIN
================================**/
function loginUser() {
    const creds = {
        username: document.getElementById("loginUsername").value,
        password: document.getElementById("loginPassword").value
    };

    fetch("/auth/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(creds)
    })
    .then(res => res.json())
    .then(data => {
        if (data.status === "authorized") {
            window.location.href = "dashboard.jsp";
        } else {
            alert("Invalid credentials");
        }
    });
}

// Ensure plugin is available before registering
if (typeof ChartDataLabels !== "undefined") {
    Chart.register(ChartDataLabels);
}

// Helper: choose color based on percentage
function usageColor(value) {
    if (value <= 50) return '#00ff44';
    if (value <= 75) return '#ffaa00';
    return '#ff0033';
}

// Build doughnut chart
function createDoughnutChart(ctx, used) {
    const free = 100 - used;
    const colorUsed = usageColor(used);

    return new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Used', 'Free'],
            datasets: [{
                data: [used, free],
                backgroundColor: [colorUsed, '#4d3b7a'],
                borderColor: '#0d0d0f',
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            cutout: '50%',   // ⬅️ THICKER DOUGHNUT RING
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.label + ': ' + context.raw + '%';
                        }
                    }
                },
                datalabels: {
                    color: '#ffffff',
                    font: {
                        weight: 'bold',
                        size: 14
                    },
                    formatter: function(value, ctx) {
                        if (ctx.dataIndex === 0) {
                            return value + '%';  // Show only "Used"
                        }
                        return '';
                    }
                }
            }
        }
    });
}

// Init
document.addEventListener('DOMContentLoaded', () => {
    const memoryCtx = document.getElementById('memoryChart')?.getContext('2d');
    const diskCtx = document.getElementById('diskChart')?.getContext('2d');
    const cpuCtx = document.getElementById('cpuChart')?.getContext('2d');

    if (!memoryCtx || !diskCtx || !cpuCtx) {
        console.warn('One or more chart canvases not found.');
        return;
    }

    createDoughnutChart(memoryCtx, 65);
    createDoughnutChart(diskCtx, 80);
    createDoughnutChart(cpuCtx, 45);
});

// CARD 1
function refreshServerStatus() {
    fetch("pulseStatus")
        .then(res => res.json())
        .then(data => {
            updateLight("jenkins", data.jenkins);
            updateLight("tomee", data.tomee);
            updateLight("fedora", data.linux);
            updateLight("oracle", data.oracle);
        })
        .catch(err => console.error("Pulse fetch failed:", err));
}

function updateLight(server, status) {
    const light = document.querySelector(`.${server} .status-light`);

    light.classList.remove("online", "offline", "warning");

    if (status === "online") light.classList.add("online");
    else if (status === "offline") light.classList.add("offline");
    else light.classList.add("warning");
}

// Auto-refresh every 10 seconds
setInterval(refreshServerStatus, 10000);

// Run immediately on load
document.addEventListener("DOMContentLoaded", refreshServerStatus);

// CARD 3
// Function to fetch recent errors for Card 3
async function fetchRecentErrors() {
    const tableBody = document.getElementById('errorsTableBody') || document.querySelector('.error-table tbody');
    tableBody.innerHTML = ''; // Clear existing rows

    try {
        const response = await fetch('https://ominet.aerosimo.com:9443/spectre/api/errors/retrieve?records=5');
        const data = await response.json();

        data.forEach(err => {
            const ref = err.errorRef;
            const timestamp = err.errorTime;

            // Derive status (example logic)
            let status = 'OPEN';
            if (err.errorCode.startsWith('TE')) status = 'OPEN';
            else status = 'RESOLVED';

            let statusClass = 'open';
            let statusIcon = '⚠️';
            switch(status) {
                case 'OPEN': statusClass = 'open'; statusIcon='⚠️'; break;
                case 'RESOLVED': statusClass = 'resolved'; statusIcon='✅'; break;
                case 'CLOSED': statusClass = 'closed'; statusIcon='❌'; break;
                case 'PENDING': statusClass = 'pending'; statusIcon='⏳'; break;
            }

            const tr = document.createElement('tr');
            tr.className = statusClass;

            tr.innerHTML = `
                <td>${ref}</td>
                <td><span class="error-status ${statusClass}">${statusIcon} ${status}</span></td>
                <td>${timestamp}</td>
            `;
            tableBody.appendChild(tr);
        });

    } catch(err) {
        console.error('Error fetching recent errors:', err);
        tableBody.innerHTML = `<tr><td colspan="3">Unable to load errors</td></tr>`;
    }
}

// Auto-load on page load
document.addEventListener('DOMContentLoaded', () => {
    fetchRecentErrors();
});

// CARD 4

// landing.js

// Helper: Convert GB string to number
function gbToNumber(gbStr) {
    return parseFloat(gbStr.replace('GB', '').trim());
}

// Helper: Determine color based on usage %
function usageColor(value) {
    if (value <= 50) return '#00ff44'; // Green
    if (value <= 75) return '#ffaa00'; // Orange
    return '#ff0033';                 // Red
}

// Create doughnut chart with internal labels
function createDoughnutChart(ctx, used, label) {
    const free = 100 - used;
    const colorUsed = usageColor(used);

    return new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: [label, 'Free'],
            datasets: [{
                data: [used, free],
                backgroundColor: [colorUsed, '#4d3b7a33'],
                borderColor: '#0d0d0f',
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            cutout: '40%',
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return context.label + ': ' + context.raw.toFixed(1) + '%';
                        }
                    }
                },
                datalabels: {
                    color: '#fff',
                    font: { weight: 'bold', size: 14 },
                    formatter: (value, ctx) => {
                        return ctx.chart.data.labels[ctx.dataIndex] === label ? value.toFixed(1) + '%' : '';
                    }
                }
            }
        },
        plugins: [ChartDataLabels]
    });
}

// Fetch metrics from API and update charts
async function fetchSystemMetrics() {
    try {
        const response = await fetch('https://ominet.aerosimo.com:9443/infraguard/api/guard/metric');
        if (!response.ok) throw new Error('Failed to fetch metrics');
        const data = await response.json();

        // Memory usage % = (used / max) * 100
        const memoryUsed = parseFloat(data.memory.used);
        const memoryMax = parseFloat(data.memory.max);
        const memoryPercent = (memoryUsed / memoryMax) * 100;

        // Disk usage % = ((total - free) / total) * 100
        const diskTotal = gbToNumber(data.disk.total);
        const diskFree = gbToNumber(data.disk.free);
        const diskPercent = ((diskTotal - diskFree) / diskTotal) * 100;

        // CPU usage % approximation based on cumulative cpuTime per thread
        let cpuTimes = data.cpu.map(t => parseInt(t.cpuTime));
        const maxCpuTime = Math.max(...cpuTimes); // approximate scaling
        const cpuPercent = Math.min((cpuTimes.reduce((a,b) => a+b,0) / (maxCpuTime*cpuTimes.length))*100, 100);

        // Draw charts
        const memoryCtx = document.getElementById('memoryChart').getContext('2d');
        const diskCtx = document.getElementById('diskChart').getContext('2d');
        const cpuCtx = document.getElementById('cpuChart').getContext('2d');

        createDoughnutChart(memoryCtx, memoryPercent, 'Memory');
        createDoughnutChart(diskCtx, diskPercent, 'Disk');
        createDoughnutChart(cpuCtx, cpuPercent, 'CPU');

    } catch (err) {
        console.error('Error fetching system metrics:', err);
    }
}

// Initialize on DOM ready
document.addEventListener('DOMContentLoaded', () => {
    fetchSystemMetrics();
});
```