package com.hamurcuabi.compose.metric.reporter.util

const val SCRIPTS_FILE_NAME = "scripts.js"

val SCRIPTS = """
function toggleSection(id) {
    var content = document.getElementById(id);
    var icon = document.getElementById(id + '-icon');
   
    if (content.style.maxHeight) {
        content.style.maxHeight = null;
        icon.classList.remove('fa-chevron-up');
        icon.classList.add('fa-chevron-down');
    } else {
        content.style.maxHeight = content.scrollHeight + 'px';
        icon.classList.remove('fa-chevron-down');
        icon.classList.add('fa-chevron-up');
    }
}

function toggleDarkMode() {
    const body = document.body;
    body.classList.toggle('dark-mode');

    const icon = document.querySelector('.toolbar .toolbar-buttons button i');
    if (body.classList.contains('dark-mode')) {
        icon.classList.remove('fa-moon');
        icon.classList.add('fa-sun');
    } else {
        icon.classList.remove('fa-sun');
        icon.classList.add('fa-moon');
    }
}

""".trimIndent()
