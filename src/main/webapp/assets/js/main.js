/* main.js - handles dashboard interactivity */

// Wait for DOM
document.addEventListener('DOMContentLoaded', () => {

  // ---------------- Sidebar Toggle ----------------
  const sidebar = document.querySelector('.sidebar');
  const toggleBtn = document.getElementById('sidebarToggle');
  if (toggleBtn && sidebar) {
    toggleBtn.addEventListener('click', () => {
      sidebar.classList.toggle('collapsed');
    });
  }

  // ---------------- Active Link Highlight ----------------
  const currentPage = window.location.pathname.split('/').pop();
  const navLinks = document.querySelectorAll('.nav-links a');
  navLinks.forEach(link => {
    const hrefPage = link.getAttribute('href');
    if (hrefPage === currentPage) {
      link.classList.add('active');
    }
  });

  // ---------------- User Dropdown ----------------
  const userAvatar = document.querySelector('.topbar .user .avatar');
  const userDropdown = document.querySelector('.topbar .user .dropdown');
  if (userAvatar && userDropdown) {
    userAvatar.addEventListener('click', () => {
      userDropdown.style.display = userDropdown.style.display === 'flex' ? 'none' : 'flex';
    });
    document.addEventListener('click', (e) => {
      if (!userAvatar.contains(e.target) && !userDropdown.contains(e.target)) {
        userDropdown.style.display = 'none';
      }
    });
  }

  // ---------------- Inject Username / Avatar ----------------
  const username = sessionStorage.getItem('username') || 'Guest';
  const authToken = sessionStorage.getItem('authToken') || null;
  const topUsernameEl = document.querySelector('.topbar .user .name');
  const avatarEl = document.querySelector('.topbar .user .avatar');

  if (topUsernameEl) topUsernameEl.textContent = username;
  if (avatarEl) {
    // Placeholder image by default
    avatarEl.src = 'assets/img/user/user.webp';
    // Optionally, inject live avatar if available via session or API
  }

  // ---------------- Logout ----------------
  const logoutBtn = document.getElementById('logoutBtn');
  if (logoutBtn) {
    logoutBtn.addEventListener('click', () => {
      sessionStorage.removeItem('username');
      sessionStorage.removeItem('authToken');
      window.location.href = 'login.jsp';
    });
  }

});