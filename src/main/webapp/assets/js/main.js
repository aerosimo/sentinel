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