#!/bin/bash

AOSPPath="/home/howard/Nexus4/Nexus4Origin/"

MultiResourceManagerService=$AOSPPath"frameworks/base/services/java/com/android/server/MultiResourceManagerService.java"
MultiResourceManagerServiceFolder=$AOSPPath"frameworks/base/services/java/com/android/server/"

SystemServer=$AOSPPath"frameworks/base/services/java/com/android/server/SystemServer.java"
SystemServerFolder=$AOSPPath"frameworks/base/services/java/com/android/server/"

IMultiResourceManagerService=$AOSPPath"frameworks/base/core/java/android/os/IMultiResourceManagerService.aidl"
IMultiResourceManagerServiceFolder=$AOSPPath"frameworks/base/core/java/android/os/"

AndroidMk=$AOSPPath"frameworks/base/Android.mk"
AndroidMkFolder=$AOSPPath"frameworks/base/"

PowerManager=$AOSPPath"frameworks/base/core/java/android/os/PowerManager.java"
PowerManagerFolder=$AOSPPath"frameworks/base/core/java/android/os/"

PowerManagerService=$AOSPPath"frameworks/base/services/java/com/android/server/power/PowerManagerService.java"
PowerManagerServiceFolder=$AOSPPath"frameworks/base/services/java/com/android/server/power/"

DisplayPowerController=$AOSPPath"frameworks/base/services/java/com/android/server/power/DisplayPowerController.java"
DisplayPowerControllerFolder=$AOSPPath"frameworks/base/services/java/com/android/server/power/"

if diff $MultiResourceManagerService MultiResourceManagerService.java >/dev/null ; then
	echo "MultiResourceManagerService.java is same"
else
	rm -f $MultiResourceManagerService
	cp MultiResourceManagerService.java $MultiResourceManagerServiceFolder
fi

if diff $SystemServer SystemServer.java >/dev/null ; then
	echo "SystemServer.java is same"
else
	rm -f $SystemServer
	cp SystemServer.java $SystemServerFolder
fi

if diff $IMultiResourceManagerService IMultiResourceManagerService.aidl >/dev/null ; then
	echo "IMultiResourceManagerService.aidl is same"
else
	rm -f $IMultiResourceManagerService
	cp IMultiResourceManagerService.aidl $IMultiResourceManagerServiceFolder
fi

if diff $AndroidMk Android.mk >/dev/null ; then
	echo "Android.mk is same"
else
	rm -f $AndroidMk
	cp Android.mk $AndroidMkFolder
fi

if diff $PowerManager PowerManager.java >/dev/null ; then
	echo "PowerManager.java is same"
else
	rm -f $PowerManager
	cp PowerManager.java $PowerManagerFolder
fi

if diff $PowerManagerService PowerManagerService.java >/dev/null ; then
	echo "PowerManagerService.java is same"
else
	rm -f $PowerManagerService
	cp PowerManagerService.java $PowerManagerServiceFolder
fi

if diff $DisplayPowerController DisplayPowerController.java >/dev/null ; then
	echo "DisplayPowerController.java is same"
else
	rm -f $DisplayPowerController
	cp DisplayPowerController.java $DisplayPowerControllerFolder
fi
