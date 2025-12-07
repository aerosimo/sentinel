<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    request.setAttribute("page", "card2");
    request.setAttribute("contentPage", "card2-content.jspf");
%>

<jsp:include page="dashboard.jspf" />


<div class="page-header">
    <h1>Disk Usage</h1>
    <p>Detailed view of disk storage across the system.</p>
</div>

<div class="metrics-container">
    <!-- Disk usage chart -->
    <canvas id="diskChartDetail" width="400" height="200"></canvas>

    <!-- Additional disk details -->
    <div class="disk-details">
        <div class="detail-item">
            <span class="label">Total Disk:</span>
            <span class="value" id="diskTotal">Loading...</span>
        </div>
        <div class="detail-item">
            <span class="label">Used Disk:</span>
            <span class="value" id="diskUsed">Loading...</span>
        </div>
        <div class="detail-item">
            <span class="label">Free Disk:</span>
            <span class="value" id="diskFree">Loading...</span>
        </div>
        <div class="detail-item">
            <span class="label">Disk Utilization:</span>
            <span class="value" id="diskPercent">Loading...</span>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', async () => {
    try {
        const res = await fetch('https://ominet.aerosimo.com:9443/infraguard/api/guard/metric');
        const data = await res.json();

        function gbToNumber(str) {
            return parseFloat(String(str).replace('GB','').trim());
        }

        const diskTotal = gbToNumber(data.disk.total);
        const diskFree = gbToNumber(data.disk.free);
        const diskUsed = diskTotal - diskFree;
        const diskPercent = Math.min(100, Math.max(0, (diskUsed / diskTotal) * 100));

        document.getElementById('diskTotal').textContent = diskTotal + ' GB';
        document.getElementById('diskUsed').textContent = diskUsed + ' GB';
        document.getElementById('diskFree').textContent = diskFree + ' GB';
        document.getElementById('diskPercent').textContent = diskPercent.toFixed(1) + ' %';

        // Render chart
        const ctx = document.getElementById('diskChartDetail').getContext('2d');
        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['Used', 'Free'],
                datasets: [{
                    data: [diskUsed, diskFree],
                    backgroundColor: ['#ffaa00', '#4d3b7a33'],
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
        console.error('Error fetching disk metrics:', err);
    }
});
</script>