// landing.js

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
async function fetchSystemMetrics() {
    try {
        const resp = await fetch("https://ominet.aerosimo.com:9443/infraguard/api/guard/metric");
        const data = await resp.json();

        // Memory
        const usedMem = parseFloat(data.memory.used);
        const maxMem = parseFloat(data.memory.max);
        const memPercent = Math.round((usedMem / maxMem) * 100);
        createDoughnutChart(document.getElementById('memoryChart').getContext('2d'), memPercent);

        // Disk
        const totalDisk = parseFloat(data.disk.total);
        const usedDisk = totalDisk - parseFloat(data.disk.free);
        const diskPercent = Math.round((usedDisk / totalDisk) * 100);
        createDoughnutChart(document.getElementById('diskChart').getContext('2d'), diskPercent);

        // CPU (simplified example: sum of all cpuTimes)
        const cpuTimes = data.cpu.map(c => parseInt(c.cpuTime));
        const totalCpuTime = cpuTimes.reduce((a,b) => a+b, 0);
        const maxCpuTime = Math.max(...cpuTimes);
        const cpuPercent = Math.round((maxCpuTime / totalCpuTime) * 100);
        createDoughnutChart(document.getElementById('cpuChart').getContext('2d'), cpuPercent);

    } catch(err) {
        console.error("Failed to fetch system metrics:", err);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    fetchSystemMetrics();
});