#!/bin/bash
for DEVICE in $(adb devices|grep device|grep -v List|cut  -f1)
do
    adb -s $DEVICE uninstall com.aspenshore.timekeeper
    adb -s $DEVICE uninstall com.aspenshore.timekeeper.test
done
