package com.hamurcuabi.compose.metric.reporter.util

const val CSS_FILE_NAME = "styles.css"

val CSS = """
    /* General Styles */
    body {
        font-family: Arial, sans-serif;
        transition: background-color 0.3s ease, color 0.3s ease;
        margin: 0;
        padding: 0;
    }

    .dark-mode {
        background-color: #121212;
        color: #000;
    }

    .content {
        padding: 20px;
    }

    /* Region: Toolbar Styles */
    .toolbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding-left: 20px;
        padding-right: 20px;
        background-color: #333;
        color: white;
        position: fixed;
        width: 100%;
        top: 0;
        left: 0;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        z-index: 1000;
    }

    /* Ensure toolbar does not overlap content */
    body {
        padding-top: 60px; /* Ensure content starts below toolbar height */
    }

    .toolbar .title {
        font-size: 20px;
        font-weight: bold;
    }

    .toolbar .toolbar-buttons {
        display: flex;
        padding-right: 24px;
        gap: 15px;
    }

    .toolbar .toolbar-buttons button {
        background: none;
        border: none;
        color: white;
        font-size: 24px;
        cursor: pointer;
        transition: background 0.3s ease;
        padding: 10px;
    }

    .toolbar .toolbar-buttons button:hover {
        background-color: #444;
        border-radius: 5px;
    }

    .toolbar .toolbar-buttons i {
        font-size: 24px;
        padding: 10px;
    }

    /* Region: Card Styles */
    .cardRed {
        background-color: #ffecec;
        border-left: 4px solid #d9534f;
        padding: 20px;
        margin-bottom: 10px;
        margin-left: 10px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        position: relative;
    }

    .cardComposable {
        background-color: #e2e2e2;
        border-left: 4px solid #FF9900;
        padding: 20px;
        margin-bottom: 10px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        position: relative;
    }

    .cardClass {
        background-color: #e2e2e2;
        border-left: 4px solid #336699;
        padding: 20px;
        margin-bottom: 10px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        position: relative;
    }

    .cardGreen {
        background-color: #ecffee;
        border-left: 4px solid #5cb85c;
        padding: 20px;
        margin-left: 10px;
        margin-bottom: 10px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        position: relative;
    }

    .cardHeader {
        background-color: #CFD8DC;
        border-left: 4px solid #0277BD;
        padding: 20px;
        margin-bottom: 10px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        position: relative;
        font-size: larger;
        font-weight: bold;
    }

    /* Region: Collapsible Styles */
    .card-header {
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 10px;
        cursor: pointer;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .collapse-icon {
        font-size: 18px;
        transition: transform 0.3s ease;
    }

    .collapsed .collapse-icon {
        transform: rotate(-180deg);
    }

    .collapsed-content {
        max-height: 0;
        overflow: hidden;
        transition: max-height 0.3s ease;
    }

    .expanded-content {
        transition: max-height 0.3s ease;
        margin: 0;
        padding: 0;
    }

    /* Region: Status Styles */
    .toolbar-status-container {
        display: flex;
        align-items: center;
        margin: 10px;
        overflow-x: auto;
    }
    .status-container {
        display: flex;
        align-items: center;
        margin-bottom: 10px;
    }

    .status {
        margin-right: 10px;
        font-weight: bold;
        font-size: 14px;
    }

    .non-restartable-skippable {
       background-color: #ecffee;
       color: #d9534f;
       border: 2px solid #d9534f;
       border-radius: 8px;
       padding: 5px 10px;
       margin-right: 10px;
    }

    .restartable-skippable {
       background-color: #ecffee;
       color: #5cb85c;
       border: 2px solid #5cb85c;
       border-radius: 8px;
       padding: 5px 10px;
       margin-right: 10px;
    }
    
    .toolbar-status {
       background-color: #ecffee;
       color: #0277BD;
       border: 2px solid #4FC3F7;
       border-radius: 8px;
       padding: 5px 10px;
       margin: 10px;
    }
    
    .toolbar-status-project {
       background-color: #ecffee;
       color: #0277BD;
       border: 2px solid #4FC3F7;
       border-radius: 8px;
       padding: 5px 10px;
       margin: 10px;
    }
    
    .toolbar-status-variant {
       background-color: #ecffee;
       color: #0277BD;
       border: 2px solid #4FC3F7;
       border-radius: 8px;
       padding: 5px 10px;
       margin: 10px;
    }

    /* Region: Table Styles */
    .table-container {
        margin-top: 10px;
        overflow-x: auto;
    }
    
     .table-statistic-container {
        margin-top: 10px;
        margin-left: 10px;
        overflow-x: auto;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 10px 0;
        font-size: 18px;
        font-family: Arial, sans-serif;
        text-align: left;
        background-color: #f9f9f9;
      }
      th, td {
        padding: 12px 15px;
        border: 1px solid #ddd;
      }
      th {
        background-color: #455A64;
        color: white;
      }
      tr:nth-child(even) {
        background-color: #f2f2f2;
      }
     tr:hover {
         background-color: #FFCDD2;
      }
      

    /* Region: Condition Styles */
    .condition-stable {
       background-color: #d4edda;
       color: #155724;
    }

    .condition-unstable {
        background-color: #f8d7da;
        color: #721c24;
    }

    .condition-missing {
        background-color: #fff3cd;
        color: #856404;
    }

    /* Region: Parameter Styles */
    .parameter-row {
        display: flex;
        align-items: center;
        padding: 5px 0;
    }

    .parameter-container {
        flex: 1;
        padding: 6px;
    }
    
    
     @media (max-width: 768px) {
        th, td {
            white-space: nowrap;
        }
    }

""".trimIndent()
