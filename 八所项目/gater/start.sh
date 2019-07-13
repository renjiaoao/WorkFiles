#!/bin/sh
cd `dirname $0`
ulimit -c unlimited
WORKDIR=$PWD
JAVABIN="/usr/local/jdk1.8.0_11/bin/java"

$WORKDIR/daemon -c $WORKDIR -p $WORKDIR/gater.pid $JAVABIN -Xms1g -Xmx5g -XX:+PrintGCDetails -XX:+UseG1GC -Xloggc:gater.gc.log -XX:+PrintGCTimeStamps -javaagent:classreloader.jar -jar gater.jar config/gater.json config/log4j.properties
echo gater started
