/******************************************************************************
 * This piece of work is to enhance sentinel project functionality.           *
 *                                                                            *
 * Author:    eomisore                                                        *
 * File:      main.js                                                         *
 * Created:   13/09/2025, 01:11                                               *
 * Modified:  13/09/2025, 01:11                                               *
 *                                                                            *
 * Copyright (c)  2025.  Aerosimo Ltd                                         *
 *                                                                            *
 * Permission is hereby granted, free of charge, to any person obtaining a    *
 * copy of this software and associated documentation files (the "Software"), *
 * to deal in the Software without restriction, including without limitation  *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,   *
 * and/or sell copies of the Software, and to permit persons to whom the      *
 * Software is furnished to do so, subject to the following conditions:       *
 *                                                                            *
 * The above copyright notice and this permission notice shall be included    *
 * in all copies or substantial portions of the Software.                     *
 *                                                                            *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,            *
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES            *
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND                   *
 * NONINFINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT                 *
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,               *
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING               *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE                 *
 * OR OTHER DEALINGS IN THE SOFTWARE.                                         *
 *                                                                            *
 ******************************************************************************/

(function () {
    const MOBILE_BREAKPOINT = 768; // px

    const sidebar = document.getElementById('sidebar');
    const sidebarToggle = document.getElementById('sidebarToggle');
    const sidebarOverlay = document.getElementById('sidebarOverlay');
    const contentWrapper = document.getElementById('contentWrapper');
    const toggleIcon = sidebarToggle.querySelector('i'); // the <i> inside button

    if (!sidebar || !sidebarToggle || !contentWrapper || !sidebarOverlay) {
        console.warn('Sidebar script: missing DOM elements (sidebar/sidebarToggle/contentWrapper/sidebarOverlay).');
        return;
    }

    function isMobile() {
        return window.innerWidth < MOBILE_BREAKPOINT;
    }

    function setToggleIcon(opened) {
        if (!toggleIcon) return;
        if (opened) {
            toggleIcon.classList.remove('bi-list'); // Bootstrap "hamburger"
            toggleIcon.classList.add('bi-x-lg');    // Bootstrap "close"
        } else {
            toggleIcon.classList.remove('bi-x-lg');
            toggleIcon.classList.add('bi-list');
        }
    }

    function setInitialState() {
        if (isMobile()) {
            sidebar.classList.add('sidebar-collapsed');
            sidebar.classList.remove('shrink');
            sidebarOverlay.classList.remove('active');
            contentWrapper.classList.remove('expanded', 'collapsed');
            setToggleIcon(false);
        } else {
            sidebar.classList.remove('sidebar-collapsed');
            sidebarOverlay.classList.remove('active');

            if (sidebar.classList.contains('shrink')) {
                contentWrapper.classList.add('collapsed');
                contentWrapper.classList.remove('expanded');
            } else {
                contentWrapper.classList.add('expanded');
                contentWrapper.classList.remove('collapsed');
            }
            setToggleIcon(false);
        }
    }

    function openMobileSidebar() {
        sidebar.classList.remove('sidebar-collapsed');
        sidebarOverlay.classList.add('active');
        setToggleIcon(true);
    }

    function closeMobileSidebar() {
        sidebar.classList.add('sidebar-collapsed');
        sidebarOverlay.classList.remove('active');
        setToggleIcon(false);
    }

    sidebarToggle.addEventListener('click', function () {
        if (isMobile()) {
            if (sidebar.classList.contains('sidebar-collapsed')) {
                openMobileSidebar();
            } else {
                closeMobileSidebar();
            }
        } else {
            sidebar.classList.toggle('shrink');
            if (sidebar.classList.contains('shrink')) {
                contentWrapper.classList.add('collapsed');
                contentWrapper.classList.remove('expanded');
            } else {
                contentWrapper.classList.add('expanded');
                contentWrapper.classList.remove('collapsed');
            }
            // On desktop, we keep hamburger icon (no "X")
            setToggleIcon(false);
        }
    });

    sidebarOverlay.addEventListener('click', function () {
        closeMobileSidebar();
    });

    let lastWasMobile = isMobile();
    window.addEventListener('resize', function () {
        const nowMobile = isMobile();
        if (nowMobile !== lastWasMobile) {
            setInitialState();
            lastWasMobile = nowMobile;
        }
    });

    document.addEventListener('DOMContentLoaded', setInitialState);
})();

// assets/js/main.js

// 1. Server Overview Poller
async function refreshOverview() {
    try {
        const res = await fetch("overview");
        const data = await res.json();

        document.getElementById("uptime").textContent = data.uptime;
        document.getElementById("load").textContent = data.load.toFixed(2);
        document.getElementById("connections").textContent = data.connections;
        const statusBadge = document.getElementById("status");
        statusBadge.textContent = data.status;
        statusBadge.className = "badge " + (data.status === "Running" ? "bg-success" : "bg-danger");
    } catch (err) {
        console.error("Overview fetch error:", err);
    }
}
setInterval(refreshOverview, 10000);
refreshOverview();

// 2. Server Rack Poller
async function fetchStatus() {
    try {
        const res = await fetch("serverStatus");
        const data = await res.json();

        Object.entries(data).forEach(([name, status]) => {
            const row = document.getElementById("server-" + name);
            const logo = row.querySelector(".server-logo");

            if (status === "online") {
                row.classList.remove("server-down");
                logo.classList.remove("offline");
            } else {
                row.classList.add("server-down");
                logo.classList.add("offline");
            }
        });
    } catch (e) {
        console.error("Error fetching server status:", e);
    }
}
setInterval(fetchStatus, 5000);
fetchStatus();

// 3. System Metrics Poller
let memoryChart, diskChart, cpuChart;
async function fetchMetrics() {
    try {
        const res = await fetch("metrics");
        const data = await res.json();

        const memoryData = data.memory.map(v => parseFloat(v));
        const diskData = data.disk.map(v => parseFloat(v));
        const cpuStates = {};
        for (let i = 0; i < data.cpu.length; i += 3) {
            const state = data.cpu[i + 1];
            cpuStates[state] = (cpuStates[state] || 0) + 1;
        }

        if (!memoryChart) {
            memoryChart = new Chart(document.getElementById("memoryChart"), {
                type: "pie",
                data: { labels: ["Init", "Used", "Max", "Committed"], datasets: [{ data: memoryData, backgroundColor: ["#4d3b7a", "#64b5f6", "#81c784", "#ffb74d"] }] },
                options: { responsive: true, plugins: { legend: { position: "bottom" }, title: { display: true, text: "Memory Usage" } } }
            });
        } else {
            memoryChart.data.datasets[0].data = memoryData;
            memoryChart.update();
        }

        if (!diskChart) {
            diskChart = new Chart(document.getElementById("diskChart"), {
                type: "doughnut",
                data: { labels: ["Total", "Free", "Usable"], datasets: [{ data: diskData, backgroundColor: ["#9575cd", "#ffb74d", "#4db6ac"] }] },
                options: { responsive: true, cutout: "70%", plugins: { legend: { position: "bottom" }, title: { display: true, text: "Disk Usage" } } }
            });
        } else {
            diskChart.data.datasets[0].data = diskData;
            diskChart.update();
        }

        if (!cpuChart) {
            cpuChart = new Chart(document.getElementById("cpuChart"), {
                type: "bar",
                data: { labels: Object.keys(cpuStates), datasets: [{ label: "Threads", data: Object.values(cpuStates), backgroundColor: ["#4d3b7a", "#64b5f6", "#ff7043", "#81c784", "#ba68c8"] }] },
                options: { responsive: true, plugins: { legend: { display: false }, title: { display: true, text: "CPU Threads" } }, scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } } }
            });
        } else {
            cpuChart.data.labels = Object.keys(cpuStates);
            cpuChart.data.datasets[0].data = Object.values(cpuStates);
            cpuChart.update();
        }
    } catch (err) {
        console.error("Metrics fetch error:", err);
    }
}
setInterval(fetchMetrics, 10000);
fetchMetrics();

// 4. Recent Errors Poller
async function fetchRecentErrors() {
    try {
        const res = await fetch("spectreErrors?records=6");
        const errors = await res.json();

        const tbody = document.getElementById("errorsTableBody");
        tbody.innerHTML = "";
        if (!Array.isArray(errors)) return;

        errors.forEach(err => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${err.errorRef ?? ""}</td>
                <td>${(err.errorMessage ?? "").replace(/\n/g, "<br/>")}</td>
                <td>${err.errorTime ?? ""}</td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) {
        console.error("Error fetching recent errors:", e);
    }
}
setInterval(fetchRecentErrors, 15000);
fetchRecentErrors();

// Adding multiple contacts
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