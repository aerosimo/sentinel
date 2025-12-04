// landing.js

/** ===============================
    MODAL CONTROL
================================**/
function openAuthModal() {
    document.getElementById("authModal").classList.remove("hidden");
}

function closeAuthModal() {
    document.getElementById("authModal").classList.add("hidden");
}

function showSignup() {
    document.getElementById("loginForm").classList.add("hidden");
    document.getElementById("signupForm").classList.remove("hidden");
}

function showLogin() {
    document.getElementById("signupForm").classList.add("hidden");
    document.getElementById("loginForm").classList.remove("hidden");
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