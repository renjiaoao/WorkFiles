@echo off
set WORKDIR=%cd%
echo %WORKDIR%
set JAVABIN=%JAVA_HOME%\bin\java.exe

%JAVABIN% -Xms1g -Xmx5g -XX:+PrintGCDetails -XX:+UseG1GC -Xloggc:gater.gc.log -XX:+PrintGCTimeStamps -javaagent:classreloader.jar -jar gater.jar config/gater.json config/log4j.properties
echo gater started


