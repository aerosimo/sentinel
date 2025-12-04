// landing.js

/** ===============================
    MODAL CONTROL
================================**/
function openAuthModal() {
    document.getElementById("authModal").classList.remove("hidden");
    showLogin();
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
    })
    .catch(err => console.error("Signup error:", err));
}

function verifyToken() {
    const token = document.getElementById("verifyToken").value;

    fetch("/auth/verify-token", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ token })
    })
    .then(res => res.json())
    .then(data => {
        if (data.status === "verified") {
            alert("Account verified successfully!");
            closeTokenModal();
        } else {
            alert("Invalid token");
        }
    })
    .catch(err => console.error("Token verification error:", err));
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
    })
    .catch(err => console.error("Login error:", err));
}

/** ===============================
    SERVER STATUS (CARD 1)
================================**/
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
    if (!light) return;

    light.classList.remove("online", "offline", "warning");
    if (status === "online") light.classList.add("online");
    else if (status === "offline") light.classList.add("offline");
    else light.classList.add("warning");
}

/** ===============================
    ERROR INTELLIGENCE (CARD 3)
================================**/
async function fetchRecentErrors() {
    const tableBody = document.querySelector('.error-table tbody');
    if (!tableBody) return;

    tableBody.innerHTML = ''; // Clear existing rows

    try {
        const res = await fetch('https://ominet.aerosimo.com:9443/spectre/api/errors/retrieve?records=5');
        const data = await res.json();

        data.forEach(err => {
            const ref = err.errorRef;
            const timestamp = err.errorTime;

            let status = err.errorCode.startsWith('TE') ? 'OPEN' : 'RESOLVED';
            let statusClass = 'open';
            let statusIcon = '⚠️';
            switch(status) {
                case 'OPEN': statusClass='open'; statusIcon='⚠️'; break;
                case 'RESOLVED': statusClass='resolved'; statusIcon='✅'; break;
                case 'CLOSED': statusClass='closed'; statusIcon='❌'; break;
                case 'PENDING': statusClass='pending'; statusIcon='⏳'; break;
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

/** ===============================
    SYSTEM METRICS (CARD 4)
================================**/
if (typeof ChartDataLabels !== "undefined") Chart.register(ChartDataLabels);

function usageColor(value) {
    if (value <= 50) return '#00ff44';
    if (value <= 75) return '#ffaa00';
    return '#ff0033';
}

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
                    callbacks: { label: ctx => ctx.label + ': ' + ctx.raw.toFixed(1) + '%' }
                },
                datalabels: {
                    color: '#fff',
                    font: { weight: 'bold', size: 14 },
                    formatter: (value, ctx) => ctx.chart.data.labels[ctx.dataIndex] === label ? value.toFixed(1)+'%' : ''
                }
            }
        },
        plugins: [ChartDataLabels]
    });
}

function gbToNumber(gbStr) {
    return parseFloat(gbStr.replace('GB','').trim());
}

async function fetchSystemMetrics() {
    try {
        const res = await fetch('https://ominet.aerosimo.com:9443/infraguard/api/guard/metric');
        const data = await res.json();

        const memoryPercent = (parseFloat(data.memory.used)/parseFloat(data.memory.max))*100;
        const diskPercent = ((gbToNumber(data.disk.total)-gbToNumber(data.disk.free))/gbToNumber(data.disk.total))*100;
        const cpuTimes = data.cpu.map(t => parseInt(t.cpuTime));
        const cpuPercent = Math.min((cpuTimes.reduce((a,b)=>a+b,0) / (Math.max(...cpuTimes)*cpuTimes.length))*100, 100);

        const memoryCtx = document.getElementById('memoryChart').getContext('2d');
        const diskCtx = document.getElementById('diskChart').getContext('2d');
        const cpuCtx = document.getElementById('cpuChart').getContext('2d');

        // Clear previous charts if exist
        if (window.memoryChart) window.memoryChart.destroy();
        if (window.diskChart) window.diskChart.destroy();
        if (window.cpuChart) window.cpuChart.destroy();

        window.memoryChart = createDoughnutChart(memoryCtx, memoryPercent, 'Memory');
        window.diskChart = createDoughnutChart(diskCtx, diskPercent, 'Disk');
        window.cpuChart = createDoughnutChart(cpuCtx, cpuPercent, 'CPU');

    } catch(err) {
        console.error('Error fetching system metrics:', err);
    }
}

/** ===============================
    AUTO REFRESH ALL
================================**/
document.addEventListener('DOMContentLoaded', () => {
    refreshServerStatus();
    fetchRecentErrors();
    fetchSystemMetrics();

    setInterval(refreshServerStatus, 10000);
    setInterval(fetchRecentErrors, 10000);
    setInterval(fetchSystemMetrics, 10000);
});