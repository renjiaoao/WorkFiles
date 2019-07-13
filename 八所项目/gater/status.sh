#!/bin/sh
cd `dirname $0`

if [ -f gater.pid  ]; then
	PID=`cat gater.pid`
	ps -o pid,%cpu,rss,args --no-heading --pid $PID
    netstat -natp | grep $PID
else
    echo "gater not running"
fi	
exit 0
