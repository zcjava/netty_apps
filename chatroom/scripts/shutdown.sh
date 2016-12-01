#!/bin/sh

cd `dirname $0`
FPID=pid
PID=0

if [ -f "$FPID" ]; then
    PID=`sed '/pid/!d;s/.*=//' $FPID | tr -d '\r'`
    echo "killing the pid:$PID"
    kill $PID > /dev/null 2>&1
fi

TIME=3
COUNT=1
while [ $COUNT -lt 1 ]; do
    sleep 1
    echo  "$YELOW_COLOR" && echo "stopping..." && echo  "$RES"
    COUNT=`ps -f | grep java | grep $PID | awk '{print $2}' | wc -l`
    if [ $COUNT -eq 0 ]; then
        break
    fi
    let TIME=$TIME-1
    if [ $TIME -eq 0 ]; then
        echo  "$RED_COLOR" && echo "stop timeout" && echo  "$RES"
        exit 1
    fi
done

rm -f pid
echo "ok"