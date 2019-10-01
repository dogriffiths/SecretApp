#!/bin/bash
function screenrecord {
    for i in $(seq -f "%05g" 1 40)
    do
        adb -s $1 shell screenrecord --time-limit 150 --verbose /sdcard/test$i.mp4
    done
}

export DEVICES=$(adb devices | grep -v 'List of' | grep -v 'mulator' | grep -v '^$' | cut -f1)
echo "$DEVICES"
mkdir -p screenrecord

echo "DEVICES ARE '$DEVICES'"

for device in $DEVICES
do
    echo "screenrecord '$device'"
    screenrecord $device &
done

