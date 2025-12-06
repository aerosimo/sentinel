<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Server Health</title>
<link rel="stylesheet" href="assets/css/main.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div style="padding:20px;max-width:1100px;margin:0 auto">
<a href="dashboard.jsp">← Back</a>
<h2>Server Metrics</h2>
<div class="grid" style="margin-top:12px">
<div class="card"><canvas id="memChart"></canvas><p class="small">Memory</p></div>
<div class="card"><canvas id="diskChart"></canvas><p class="small">Disk</p></div>
</div>
<script>
// small demo
const ctx = document.getElementById('memChart'); if(ctx) new Chart(ctx,{type:'doughnut',data:{labels:['Used','Free'],datasets:[{data:[60,40],backgroundColor:['#4d3b7a','#1f1f24']}]}});
const dctx = document.getElementById('diskChart'); if(dctx) new Chart(dctx,{type:'doughnut',data:{labels:['Used','Free'],datasets:[{data:[32,68],backgroundColor:['#ffaa00','#1f1f24']}]}});
</script>
</div>
</body>
</html>