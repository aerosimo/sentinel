// landing.js (consolidated)

// ---------------- Charts (single implementation) ----------------
(() => {
  // Register plugin if available
  if (typeof ChartDataLabels !== 'undefined' && typeof Chart !== 'undefined' && Chart.register) {
    Chart.register(ChartDataLabels);
  }

  function usageColor(value) {
    if (value <= 50) return '#00ff44';
    if (value <= 75) return '#ffaa00';
    return '#ff0033';
  }

  function createDoughnutChart(ctx, used, label) {
    if (!ctx || typeof Chart === 'undefined') return null;
    const free = Math.max(0, 100 - used);
    const colorUsed = usageColor(used);

    return new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: [label || 'Used', 'Free'],
        datasets: [{
          data: [used, free],
          backgroundColor: [colorUsed, '#4d3b7a33'],
          borderColor: '#0d0d0f',
          borderWidth: 2
        }]
      },
      options: {
        responsive: true,
        cutout: '50%',
        plugins: {
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: function (context) {
                return context.label + ': ' + Number(context.raw).toFixed(1) + '%';
              }
            }
          },
          datalabels: {
            color: '#ffffff',
            font: { weight: 'bold', size: 14 },
            formatter: function (value, ctx) {
              return ctx.dataIndex === 0 ? Number(value).toFixed(1) + '%' : '';
            }
          }
        }
      }
    });
  }

  // Static demo charts (optional; comment out if using API-driven below)
  document.addEventListener('DOMContentLoaded', () => {
    const memoryCtx = document.getElementById('memoryChart')?.getContext('2d');
    const diskCtx = document.getElementById('diskChart')?.getContext('2d');
    const cpuCtx = document.getElementById('cpuChart')?.getContext('2d');

    if (memoryCtx && diskCtx && cpuCtx) {
      // Example initial values while API loads
      createDoughnutChart(memoryCtx, 65, 'Memory');
      createDoughnutChart(diskCtx, 80, 'Disk');
      createDoughnutChart(cpuCtx, 45, 'CPU');
    }
  });
})();

// ---------------- Server Status (Card 1) ----------------
(() => {
  function refreshServerStatus() {
    fetch('pulseStatus')
      .then(res => res.json())
      .then(data => {
        updateLight('jenkins', data.jenkins);
        updateLight('tomee', data.tomee);
        updateLight('fedora', data.linux);
        updateLight('oracle', data.oracle);
      })
      .catch(err => console.error('Pulse fetch failed:', err));
  }

  function updateLight(server, status) {
    const light = document.querySelector(`.${server} .status-light`);
    if (!light) return;
    light.classList.remove('online', 'offline', 'warning');
    if (status === 'online') light.classList.add('online');
    else if (status === 'offline') light.classList.add('offline');
    else light.classList.add('warning');
  }

  document.addEventListener('DOMContentLoaded', refreshServerStatus);
  setInterval(refreshServerStatus, 10000);
})();

// ---------------- Error Intelligence (Card 3) ----------------
(() => {
  async function fetchRecentErrors() {
    const tableBody = document.getElementById('errorsTableBody') || document.querySelector('.error-table tbody');
    if (!tableBody) return;

    tableBody.innerHTML = '';

    try {
      const response = await fetch('https://ominet.aerosimo.com:9443/spectre/api/errors/retrieve?records=5');
      const data = await response.json();

      data.forEach(err => {
        const ref = err.errorRef;
        const timestamp = err.errorTime;

        let status = 'OPEN';
        if (err.errorCode?.startsWith('TE')) status = 'OPEN';
        else status = 'RESOLVED';

        let statusClass = 'open';
        let statusIcon = '⚠️';
        switch (status) {
          case 'RESOLVED': statusClass = 'resolved'; statusIcon = '✅'; break;
          case 'CLOSED':   statusClass = 'closed';   statusIcon = '❌'; break;
          case 'PENDING':  statusClass = 'pending';  statusIcon = '⏳'; break;
          default:         statusClass = 'open';     statusIcon = '⚠️';
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
  }

  document.addEventListener('DOMContentLoaded', fetchRecentErrors);
})();

// ---------------- System Metrics (Card 4) ----------------
(() => {
  function gbToNumber(gbStr) {
    return parseFloat(String(gbStr).replace('GB', '').trim());
  }

  async function fetchSystemMetrics() {
    try {
      const response = await fetch('https://ominet.aerosimo.com:9443/infraguard/api/guard/metric');
      if (!response.ok) throw new Error('Failed to fetch metrics');
      const data = await response.json();

      const memoryUsed = parseFloat(data.memory.used);
      const memoryMax = parseFloat(data.memory.max);
      const memoryPercent = Math.min(100, Math.max(0, (memoryUsed / memoryMax) * 100));

      const diskTotal = gbToNumber(data.disk.total);
      const diskFree = gbToNumber(data.disk.free);
      const diskPercent = Math.min(100, Math.max(0, ((diskTotal - diskFree) / diskTotal) * 100));

      const cpuTimes = Array.isArray(data.cpu) ? data.cpu.map(t => parseInt(t.cpuTime, 10) || 0) : [];
      const maxCpuTime = cpuTimes.length ? Math.max(...cpuTimes) : 0;
      const cpuPercent = maxCpuTime > 0
        ? Math.min((cpuTimes.reduce((a, b) => a + b, 0) / (maxCpuTime * cpuTimes.length)) * 100, 100)
        : 0;

      const memoryCtx = document.getElementById('memoryChart')?.getContext('2d');
      const diskCtx = document.getElementById('diskChart')?.getContext('2d');
      const cpuCtx = document.getElementById('cpuChart')?.getContext('2d');

      if (memoryCtx) createDoughnutChart(memoryCtx, memoryPercent, 'Memory');
      if (diskCtx) createDoughnutChart(diskCtx, diskPercent, 'Disk');
      if (cpuCtx) createDoughnutChart(cpuCtx, cpuPercent, 'CPU');

    } catch (err) {
      console.error('Error fetching system metrics:', err);
    }
  }

  document.addEventListener('DOMContentLoaded', fetchSystemMetrics);
})();