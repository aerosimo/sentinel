/* auth.js - handles register / verify / login flows
   Expects pages to include form elements with ids used below.
*/

const AUTH_API_BASE = "https://ominet.aerosimo.com:9443/authcore/api/auth";

// Simple toast (replaceable)
function toast(msg, type="info") {
  // very small non-intrusive toast - create ephemeral top-right bubble
  const el = document.createElement('div');
  el.className = 'toast';
  el.style.position = 'fixed';
  el.style.top = '20px';
  el.style.right = '20px';
  el.style.zIndex = 9999;
  el.textContent = msg;
  if (type === 'error') el.style.background = 'rgba(255,30,30,0.06)';
  if (type === 'success') el.style.background = 'rgba(0,200,120,0.07)';
  document.body.appendChild(el);
  setTimeout(()=> el.style.opacity = '0', 2800);
  setTimeout(()=> el.remove(), 3200);
}

/* ---------- Utilities ---------- */

function showFieldError(inputEl, message) {
  const parent = inputEl.closest('.field');
  if (!parent) return;
  const err = parent.querySelector('.error-msg');
  if (err) {
    err.textContent = message;
    err.classList.add('error-visible');
    inputEl.classList.add('input-error');
    inputEl.style.borderColor = 'rgba(255,0,60,0.7)';
  }
}

function clearFieldError(inputEl) {
  const parent = inputEl.closest('.field');
  if (!parent) return;
  const err = parent.querySelector('.error-msg');
  if (err) {
    err.textContent = '';
    err.classList.remove('error-visible');
    inputEl.classList.remove('input-error');
    inputEl.style.borderColor = '';
  }
}

function validatePattern(inputEl) {
  const pattern = inputEl.getAttribute('pattern');
  const val = inputEl.value.trim();
  if (inputEl.required && !val) {
    showFieldError(inputEl, 'This field is required');
    return false;
  }
  if (pattern) {
    try {
      const re = new RegExp(pattern);
      if (!re.test(val)) {
        showFieldError(inputEl, inputEl.dataset.err || 'Invalid value');
        return false;
      }
    } catch (e) {
      // malformed pattern - ignore
    }
  }
  if (inputEl.minLength) {
    if (val.length < parseInt(inputEl.minLength,10)) {
      showFieldError(inputEl, `Minimum length ${inputEl.minLength}`);
      return false;
    }
  }
  clearFieldError(inputEl);
  return true;
}

/* ---------- Register ---------- */

async function submitRegisterForm(evt) {
  if (evt) evt.preventDefault();
  const form = document.getElementById('registerForm');
  if (!form) return;

  const username = document.getElementById('regUsername');
  const email = document.getElementById('regEmail');
  const password = document.getElementById('regPassword');

  // validate
  let ok = true;
  [username, email, password].forEach(i => { if (!validatePattern(i)) ok = false; });

  if (!ok) { toast('Please fix the highlighted fields', 'error'); return; }

  const payload = {
    username: username.value.trim(),
    email: email.value.trim(),
    password: password.value
  };

  try {
    const res = await fetch(`${AUTH_API_BASE}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    const data = await res.json();

    if (data.status === 'success') {
      // store token temporarily (API may also email it)
      sessionStorage.setItem('pendingVerificationToken', data.message || '');
      toast('Account created. Verify your email code next.', 'success');
      // redirect to verify page
      window.location.href = 'verify.jsp';
    } else {
      toast(data.message || 'Registration failed', 'error');
    }
  } catch (e) {
    console.error('Register call failed', e);
    toast('Network error during registration', 'error');
  }
}

/* ---------- Verify ---------- */

async function submitVerifyForm(evt) {
  if (evt) evt.preventDefault();
  const tokenInput = document.getElementById('verifyToken');
  if (!tokenInput) return;
  if (!validatePattern(tokenInput)) { toast('Please provide a valid verification code', 'error'); return; }

  const payload = { token: tokenInput.value.trim() };

  try {
    const res = await fetch(`${AUTH_API_BASE}/verify`, {
      method: 'POST',
      headers: { 'Content-Type':'application/json' },
      body: JSON.stringify(payload)
    });
    const data = await res.json();
    if (data.status === 'success') {
      toast('Email verified successfully — please sign in', 'success');
      // clear pending token
      sessionStorage.removeItem('pendingVerificationToken');
      setTimeout(()=> window.location.href = 'login.jsp', 800);
    } else {
      toast(data.message || 'Verification failed', 'error');
    }
  } catch (e) {
    console.error('Verify call failed', e);
    toast('Network error during verification', 'error');
  }
}

/* ---------- Login ---------- */

async function submitLoginForm(evt) {
  if (evt) evt.preventDefault();
  const username = document.getElementById('loginUsername');
  const password = document.getElementById('loginPassword');
  let ok = true;
  [username, password].forEach(i => { if (!validatePattern(i)) ok = false; });
  if (!ok) { toast('Please fix the highlighted fields', 'error'); return; }

  const payload = { username: username.value.trim(), password: password.value };

  try {
    const res = await fetch(`${AUTH_API_BASE}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    const data = await res.json();
    if (data.status === 'success') {
      // store username + token for session usage
      sessionStorage.setItem('username', payload.username);
      sessionStorage.setItem('authToken', data.message || '');
      toast('Login successful — redirecting...', 'success');
      setTimeout(()=> window.location.href = 'dashboard.jsp', 700);
    } else {
      toast(data.message || 'Login failed', 'error');
    }
  } catch (e) {
    console.error('Login call failed', e);
    toast('Network error during login', 'error');
  }
}

/* ---------- Hook up event listeners if forms are present ---------- */

document.addEventListener('DOMContentLoaded', () => {
  const regForm = document.getElementById('registerForm');
  if (regForm) regForm.addEventListener('submit', submitRegisterForm);

  const verifyForm = document.getElementById('verifyForm');
  if (verifyForm) {
    // prefill token from sessionStorage (useful while testing)
    const pending = sessionStorage.getItem('pendingVerificationToken');
    const tinput = document.getElementById('verifyToken');
    if (pending && tinput && !tinput.value) tinput.value = pending;
    verifyForm.addEventListener('submit', submitVerifyForm);
  }

  const loginForm = document.getElementById('loginForm');
  if (loginForm) loginForm.addEventListener('submit', submitLoginForm);

  // Attach input-level validation helpers
  document.querySelectorAll('.input-control').forEach(inp => {
    inp.addEventListener('input', (e) => validatePattern(e.target));
    inp.addEventListener('blur', (e) => validatePattern(e.target));
  });
});