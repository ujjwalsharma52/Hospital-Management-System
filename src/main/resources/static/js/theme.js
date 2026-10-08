/**
 * MedCare HMS - Theme Engine & UI Helper
 * Light/Dark theme persistence, mobile sidebar, and dynamic active navigation
 */

(function() {
    'use strict';

    function initTheme() {
        const savedTheme = localStorage.getItem('hms-theme') || 'light';
        document.documentElement.setAttribute('data-theme', savedTheme);
        updateToggleButtons(savedTheme);
    }

    function updateToggleButtons(theme) {
        const buttons = document.querySelectorAll('.theme-toggle-btn');
        buttons.forEach(function(btn) {
            if (theme === 'dark') {
                btn.classList.add('is-dark');
                btn.setAttribute('aria-label', 'Switch to light theme');
                btn.setAttribute('title', 'Switch to light theme');
            } else {
                btn.classList.remove('is-dark');
                btn.setAttribute('aria-label', 'Switch to dark theme');
                btn.setAttribute('title', 'Switch to dark theme');
            }
        });
    }

    window.toggleTheme = function() {
        const currentTheme = document.documentElement.getAttribute('data-theme') || 'light';
        const newTheme = currentTheme === 'light' ? 'dark' : 'light';
        document.documentElement.setAttribute('data-theme', newTheme);
        localStorage.setItem('hms-theme', newTheme);
        updateToggleButtons(newTheme);
    };

    window.toggleSidebar = function() {
        const sidebar = document.getElementById('sidebar');
        if (sidebar) {
            sidebar.classList.toggle('open');
        }
    };

    // Auto-highlight active navigation link
    function highlightActiveNavLink() {
        const path = window.location.pathname;
        const navLinks = document.querySelectorAll('.sidebar-nav .nav-link');
        let matched = false;

        // Try exact match first
        navLinks.forEach(function(link) {
            const href = link.getAttribute('href');
            if (href && (href === path || href === path + '/')) {
                link.classList.add('active');
                matched = true;
            }
        });

        // If not matched and not on dashboard root, match prefix
        if (!matched && path !== '/' && path !== '/dashboard') {
            navLinks.forEach(function(link) {
                const href = link.getAttribute('href');
                if (href && href !== '/dashboard' && href !== '/' && path.startsWith(href)) {
                    link.classList.add('active');
                }
            });
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', function() {
            initTheme();
            highlightActiveNavLink();
        });
    } else {
        initTheme();
        highlightActiveNavLink();
    }
})();
