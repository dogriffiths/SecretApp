#!/bin/bash
export DEVICES=$(adb devices | grep -v 'List of' | grep -v 'mulator' | grep -v '^$' | cut -f1)
echo "$DEVICES"
mkdir -p screenrecord
for device in $DEVICES
do
    export PID_REC=$(ps -ef | grep screenrecord | grep $device | sed 's/  / /g' | cut -d' ' -f3 | xargs)
    echo "Stopping $PID_REC"
    kill -9 $PID_REC
    sleep 1
    rm -f screenrecord/test*.mp4
    for i in $(seq -f "%05g" 1 40)
    do
        adb -s $device pull /sdcard/test$i.mp4 screenrecord/test$i.mp4 2>&1 > /dev/null || rm screenrecord/test$i.mp4
        adb -s $device shell rm -f /sdcard/test$i.mp4 2>&1 > /dev/null
    done
    : > mylist.txt
    for f in screenrecord/test*.mp4; do echo "file '$f'" >> mylist.txt; done
    ffmpeg -y -f concat -safe 0 -i mylist.txt -c copy screenrecord/$device.mp4 2>&1 > /dev/null || echo "Cannot ffmpeg"
done
export PID_REC=$(ps -ef | grep screenrecord | grep -v grep | sed 's/  / /g' | cut -d' ' -f3 | xargs)
kill -9 $PID_REC || echo "Nothing to stop"

rm -rf screenrecord/test*.mp4
mkdir -p /Volumes/media/secretapp-webtest/android-screenrecord || echo "Directory already exists"
cp screenrecord/*.mp4 /Volumes/media/secretapp-webtest/android-screenrecord/
