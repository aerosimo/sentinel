// main.js — Dashboard interactions
(function(){
const sidebar = document.querySelector('.sidebar');
const toggleBtn = document.getElementById('sidebarToggle');
const body = document.body;


function setCollapsed(v){
if(v){ sidebar.classList.add('collapsed'); localStorage.setItem('sidCollapsed','1'); }
else { sidebar.classList.remove('collapsed'); localStorage.removeItem('sidCollapsed'); }
}


// initialize
document.addEventListener('DOMContentLoaded', ()=>{
const collapsed = localStorage.getItem('sidCollapsed') === '1';
setCollapsed(collapsed);


if(toggleBtn) toggleBtn.addEventListener('click', ()=> setCollapsed(!sidebar.classList.contains('collapsed')));


// user dropdown
document.querySelectorAll('.user-toggle').forEach(btn=>{
btn.addEventListener('click', (ev)=>{
const user = btn.closest('.user'); user.classList.toggle('open');
});
});


// load username from sessionStorage
const uname = sessionStorage.getItem('username') || 'Guest';
const nameEl = document.getElementById('topUsername');
if(nameEl) nameEl.textContent = uname;


// logout
const logout = document.getElementById('logoutBtn');
if(logout) logout.addEventListener('click', ()=>{
sessionStorage.removeItem('username'); sessionStorage.removeItem('authToken');
window.location.href = 'index.jsp';
});


// card click navigation
document.querySelectorAll('[data-card-link]').forEach(el=>{
el.style.cursor = 'pointer';
el.addEventListener('click', ()=> {
const target = el.getAttribute('data-card-link');
if(target) window.location.href = target;
});
});
});
})();