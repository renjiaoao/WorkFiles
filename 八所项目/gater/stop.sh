#!/bin/sh
cd `dirname $0`
kill `cat gater.pid`
sleep 10
rm -f gater.pid

echo gater stopped
