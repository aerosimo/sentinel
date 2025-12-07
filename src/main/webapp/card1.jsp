<%@ page session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    request.setAttribute("page", "card1");
    request.setAttribute("contentPage", "card1-content.jspf");
%>

<jsp:include page="dashboard.jspf" />



<div class="page-header">
    <h1>Memory Usage</h1>
    <p>Detailed view of memory consumption across the system.</p>
</div>

<div class="metrics-container">
    <!-- Memory usage chart -->
    <canvas id="memoryChartDetail" width="400" height="200"></canvas>

    <!-- Additional memory details -->
    <div class="memory-details">
        <div class="detail-item">
            <span class="label">Total Memory:</span>
            <span class="value" id="memoryTotal">Loading...</span>
        </div>
        <div class="detail-item">
            <span class="label">Used Memory:</span>
            <span class="value" id="memoryUsed">Loading...</span>
        </div>
        <div class="detail-item">
            <span class="label">Free Memory:</span>
            <span class="value" id="memoryFree">Loading...</span>
        </div>
        <div class="detail-item">
            <span class="label">Memory Utilization:</span>
            <span class="value" id="memoryPercent">Loading...</span>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', async () => {
    try {
        const res = await fetch('https://ominet.aerosimo.com:9443/infraguard/api/guard/metric');
        const data = await res.json();

        const memoryUsed = parseFloat(data.memory.used);
        const memoryMax = parseFloat(data.memory.max);
        const memoryPercent = Math.min(100, Math.max(0, (memoryUsed / memoryMax) * 100));
        const memoryFree = memoryMax - memoryUsed;

        document.getElementById('memoryTotal').textContent = memoryMax + ' GB';
        document.getElementById('memoryUsed').textContent = memoryUsed + ' GB';
        document.getElementById('memoryFree').textContent = memoryFree + ' GB';
        document.getElementById('memoryPercent').textContent = memoryPercent.toFixed(1) + ' %';

        // Render chart
        const ctx = document.getElementById('memoryChartDetail').getContext('2d');
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Used', 'Free'],
                datasets: [{
                    data: [memoryUsed, memoryFree],
                    backgroundColor: ['#ff0033', '#4d3b7a33'],
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
                                return context.label + ': ' + Number(context.raw).toFixed(1) + ' GB';
                            }
                        }
                    }
                }
            }
        });

    } catch (err) {
        console.error('Error fetching memory metrics:', err);
    }
});
</script>