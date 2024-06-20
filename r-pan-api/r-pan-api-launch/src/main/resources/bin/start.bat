@echo off
start javaw -Xms1024m -Xmx1024m -classpath "..\lib\*;." com.rubin.rpan.launch.RPanLaunch --spring.config.location=..\conf\application.properties
exit


