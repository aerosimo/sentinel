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