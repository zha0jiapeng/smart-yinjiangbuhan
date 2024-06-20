#!/usr/bin/env bash
ps -ef | grep "com.rubin.rpan.launch.RPanLaunch" | grep -v grep | awk -F " " '{print $2}' | xargs -r kill
