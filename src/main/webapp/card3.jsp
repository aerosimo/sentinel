<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    request.setAttribute("page", "card3");
    request.setAttribute("contentPage", "card3-content.jspf");
%>

<jsp:include page="dashboard.jspf" />


<div class="page-header">
    <h1>CPU Usage</h1>
    <p>Detailed view of CPU utilization across all cores.</p>
</div>

<div class="metrics-container">
    <!-- CPU usage chart -->
    <canvas id="cpuChartDetail" width="400" height="200"></canvas>

    <!-- CPU details -->
    <div class="cpu-details">
        <div class="detail-item">
            <span class="label">Number of Cores:</span>
            <span class="value" id="cpuCores">Loading...</span>
        </div>
        <div class="detail-item">
            <span class="label">Max CPU Time:</span>
            <span class="value" id="cpuMaxTime">Loading...</span>
        </div>
        <div class="detail-item">
            <span class="label">Average CPU Usage:</span>
            <span class="value" id="cpuPercent">Loading...</span>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', async () => {
    try {
        const res = await fetch('https://ominet.aerosimo.com:9443/infraguard/api/guard/metric');
        const data = await res.json();

        const cpuTimes = Array.isArray(data.cpu) ? data.cpu.map(t => parseInt(t.cpuTime, 10) || 0) : [];
        const cores = cpuTimes.length;
        const maxCpuTime = cores ? Math.max(...cpuTimes) : 0;
        const totalTime = cores ? cpuTimes.reduce((a,b)=>a+b,0) : 0;
        const cpuPercent = maxCpuTime > 0 ? Math.min((totalTime / (maxCpuTime * cores)) * 100, 100) : 0;

        document.getElementById('cpuCores').textContent = cores;
        document.getElementById('cpuMaxTime').textContent = maxCpuTime + ' ms';
        document.getElementById('cpuPercent').textContent = cpuPercent.toFixed(1) + ' %';

        // Render chart
        const ctx = document.getElementById('cpuChartDetail').getContext('2d');
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Used', 'Free'],
                datasets: [{
                    data: [cpuPercent, 100 - cpuPercent],
                    backgroundColor: ['#00ff44', '#4d3b7a33'],
                    borderColor: '#0d0d0f',
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                cutout: '60%',
                plugins: {
                    legend: { display: true, position: 'bottom' },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return context.label + ': ' + Number(context.raw).toFixed(1) + '%';
                            }
                        }
                    }
                }
            }
        });

    } catch (err) {
        console.error('Error fetching CPU metrics:', err);
    }
});
</script>