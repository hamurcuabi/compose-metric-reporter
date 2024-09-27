# Compose Metric Reporter

A plugin to generates HTML report from raw Compose metrics.

## Usage


1. Go to your `toml` file add these with latest version


```
 [versions]
 plugin_compose_metric_reporter = "0.0.1"

 [plugins]
 composeMetricReporter = { id = "com.hamurcu.compose-metric-reporter", version.ref = "plugin_compose_metric_reporter" }
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

composeReportGenerator {
    toolbarTitle = "Compose Toolbar title"
    outputPath = "compose-report"
    excludeSuffix = listOf("Preview")
    hideComposableWithNoParams = true
}

```

3. Run `./gradlew tasks`, You will see


```
Compose Metric Reporter tasks
-----------------------------
presentation_developmentDebugMetricReporter - Generate Compose Compiler Metrics and Report for 'developmentDebug' variant in Android project
presentation_developmentReleaseMetricReporter - Generate Compose Compiler Metrics and Report for 'developmentRelease' variant in Android project
presentation_pilotDebugMetricReporter - Generate Compose Compiler Metrics and Report for 'pilotDebug' variant in Android project
```
Chose one of them and run it.

`./gradlew presentation_developmentDebugMetricReporter`

You can see metrics and reports generated under build folder.



## 💬 Discuss?

Have any questions, doubts or want to present your opinions, views? You're always welcome. You
can [start discussions](https://github.com/hamurcuabi/compose-metric-reporter/discussions).

## License

```
MIT License

Copyright (c) 2024 Emre Hamurcu

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
