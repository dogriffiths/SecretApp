GRADLE = ./gradlew --no-daemon

# Ignore these
.PHONY: clean build test build-release test uninstall

reboot_devices:
	for device in $$(adb devices|grep -v List|grep device|cut -d$$'\t' -f1); do adb -s "$$device" reboot; done

clean: reboot_devices
	rm -rf .gradle
	$(GRADLE) clean	

build:
	$(GRADLE) clean check

start-record:
	(bin/screenrecord.sh || echo 'Failed to start recording') 2>&1

uninstall:
	bin/uninstall.sh

test: assembleAndroidTest assembleDebug
	java -jar spoon-runner-1.7.1-jar-with-dependencies.jar \
		--fail-on-failure --output app/build/spoon/debug\
		--apk app/build/outputs/apk/debug/app-debug.apk \
		--test-apk app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk

stop-record:
	(bin/getrecord.sh || echo "Unable to get recording") 2>&1

build-app-release:
	$(GRADLE) increaseVersion assembleRelease bundleRelease

features:
	mkdir -p features

gather-features: features
	cp -f ./app/src/androidTest/assets/features/* features

sonar:
	~/tools/sonar-scanner-3.0.3.778-macosx/bin/sonar-scanner

publish-tests:
	cp -rf app/build/reports /Volumes/media/secretapp-webtest/app-reports||echo 'Cannot find it'
	cp -rf app/build/spoon/debug /Volumes/media/secretapp-webtest/app-spoon||echo 'Cannot find it'
	cp -f app/build/outputs/apk/release/app-release.apk /Volumes/media/secretapp-webtest/||echo 'Cannot find it'

%:
	$(GRADLE) $@
