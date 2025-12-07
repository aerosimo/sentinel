<%@ page session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    request.setAttribute("page", "card4");
    request.setAttribute("contentPage", "card4-content.jspf");
%>

<jsp:include page="dashboard.jspf" />


<div class="page-header">
    <h1>Recent Errors</h1>
    <p>Track recent system errors and their status for monitoring and debugging.</p>
</div>

<div class="metrics-container">
    <!-- Errors Table -->
    <div class="table-responsive error-table-container">
        <table class="table table-dark table-striped table-hover">
            <thead>
                <tr>
                    <th>Error Ref</th>
                    <th>Status</th>
                    <th>Timestamp</th>
                </tr>
            </thead>
            <tbody id="errorsTableBody">
                <tr><td colspan="3">Loading errors...</td></tr>
            </tbody>
        </table>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', async () => {
    const tableBody = document.getElementById('errorsTableBody');
    try {
        const res = await fetch('https://ominet.aerosimo.com:9443/spectre/api/errors/retrieve?records=10');
        const data = await res.json();

        tableBody.innerHTML = ''; // clear loading row

        data.forEach(err => {
            const ref = err.errorRef || 'N/A';
            const timestamp = err.errorTime || 'N/A';

            let status = 'OPEN';
            if (err.errorCode?.startsWith('TE')) status = 'OPEN';
            else status = 'RESOLVED';

            let statusClass = 'open';
            let statusIcon = '⚠️';
            switch(status) {
                case 'RESOLVED': statusClass='resolved'; statusIcon='✅'; break;
                case 'CLOSED':   statusClass='closed';   statusIcon='❌'; break;
                case 'PENDING':  statusClass='pending';  statusIcon='⏳'; break;
                default:         statusClass='open';     statusIcon='⚠️';
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

    } catch (err) {
        console.error('Error fetching recent errors:', err);
        tableBody.innerHTML = `<tr><td colspan="3">Unable to load errors</td></tr>`;
    }
});
</script>