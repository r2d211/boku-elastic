Task is to simulate different traffic patterns with log generation and build pipeline in elastic to accept those logs:
* Modify source code to have different traffic patterns, daily and/or weekly cycle.
* Build pipeline for log index rotation and retention in elastic, configuration is up to you.
* Create a solution to store compacted information quantity of logs preserving status and qualifier per 1 day, use any of the means elastic provides: transforms, rollup jobs, downsampling.
* Provide the modified application source code.
* For all created resources in elastic provide REST requests or their configuration JSON. We should be able to easily replicate your setup using them.

This repository contains:
* Basic code to send synthetic traffic to elastic
* Elastic & kibana docker configuration with instructions

Traffic graphs examples:

![Daily traffic pattern](images/daily_pattern.png)
![Weekly traffic pattern](images/weekly_pattern.png)
