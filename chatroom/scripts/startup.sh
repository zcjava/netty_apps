#!/bin/bash

## $1 是端口号 $2 是应用名
SERVER_PORT=9000
PROJECT=chatroom

cd `dirname $0`
FPID=pid
PID=0

if [ -f "$FPID" ];
then
    PID=`sed '/pid/!d;s/.*=//' $FPID | tr -d '\r'`
    COUNT=0
    COUNT=`ps -f | grep java | grep $PID | awk '{print $2}' | wc -l`
    if [ $COUNT -gt 0 ]; then
        echo "the server is already started at pid:" $PID
        exit 1
    else
        echo "the pid is expired, remove pid and start server"
        rm -f pid
    fi
fi

if [ ! -z $1 ]; then ls
    SERVER_PORT=$1
fi

echo server port=${SERVER_PORT}

export jconsoleArgs="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=2990 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"

nohup java $jconsoleArgs -jar -Dserver.port=${SERVER_PORT}  ../${PROJECT}.jar  & PID=$!

TIME=10
COUNT=0
while [ $COUNT -lt 1 ]; do
    sleep 1
    echo "starting..."
    COUNT=`ps -f | grep java | grep $PID | awk '{print $2}' | wc -l`
    if [ $COUNT -gt 0 ]; then
        break
    fi
    let TIME=$TIME-1
    if [ $TIME -eq 0 ]; then
        echo  "$RED_COLOR" && echo "start timeout" && echo  "$RES"
        exit 1
    fi
done
echo "PORT: ${SERVER_PORT}"
echo "pid=$PID" > pid

echo "PID: $PID"
echo "ok"




