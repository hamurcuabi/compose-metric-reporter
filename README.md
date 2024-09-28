# Compose Metric Reporter

A plugin to generates HTML report from raw Compose metrics.

## Usage


1. Go to your `toml` file add below

![Install](https://img.shields.io/badge/Maven-0.0.3-blue.svg)
```
 [versions]
 plugin_compose_metric_reporter = "0.0.3"

 [plugins]
 composeMetricReporter = { id = "io.gitlab.hamurcuabi.compose.metric.reporter.plugin", version.ref = "plugin_compose_metric_reporter" }
```

2. Go to module that you want to create compose report


```
plugins {
    alias(libs.plugins.composeMetricReporter)
}

 android {
 
  composeCompiler {
        reportsDestination = project.layout.buildDirectory.dir(COMPOSE_METRICS_PATH)
        metricsDestination = project.layout.buildDirectory.dir(COMPOSE_METRICS_PATH)
    }
    
}

composeReporter {
    toolbarTitle = "Compose Toolbar title"
    outputPath = "compose-report"
    excludeSuffixForFunctions = listOf("Preview")
    excludeSuffixForClasses = listOf("Activity")
    hideComposableWithNoParams = true
}

```

3. Run `./gradlew tasks`, You will see some tasks with each variant like below


```
Compose Metric Reporter tasks
-----------------------------
presentation_developmentDebugMetricReporter - Generate Compose Compiler Metrics and Report for 'developmentDebug' variant in Android project
presentation_developmentReleaseMetricReporter - Generate Compose Compiler Metrics and Report for 'developmentRelease' variant in Android project
presentation_pilotDebugMetricReporter - Generate Compose Compiler Metrics and Report for 'pilotDebug' variant in Android project
....
```
Chose one of them and run it.

`./gradlew presentation_developmentDebugMetricReporter`

You can see metrics and reports generated under build folder ends with `<project-name>-compose-report`.
Open `index.html` file with your browser

https://github.com/user-attachments/assets/77d54886-461b-4725-b572-77509ac59cd9



