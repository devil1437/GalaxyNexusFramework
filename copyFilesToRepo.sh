#!/bin/bash

AOSPPath="/home/howard/Nexus4/Nexus4Origin/"

MultiResourceManagerService=$AOSPPath"frameworks/base/services/java/com/android/server/MultiResourceManagerService.java"
MultiResourceManagerServiceFolder=$AOSPPath"frameworks/base/services/java/com/android/server/"

IMultiResourceManagerService=$AOSPPath"frameworks/base/core/java/android/os/IMultiResourceManagerService.aidl"
IMultiResourceManagerServiceFolder=$AOSPPath"frameworks/base/core/java/android/os/"

MultiResourceManager=$AOSPPath"frameworks/base/core/java/android/os/MultiResourceManager.java"
MultiResourceManagerFolder=$AOSPPath"frameworks/base/core/java/android/os/"

SystemServer=$AOSPPath"frameworks/base/services/java/com/android/server/SystemServer.java"
SystemServerFolder=$AOSPPath"frameworks/base/services/java/com/android/server/"

AndroidMk=$AOSPPath"frameworks/base/Android.mk"
AndroidMkFolder=$AOSPPath"frameworks/base/"

Context=$AOSPPath"frameworks/base/core/java/android/content/Context.java"
ContextFolder=$AOSPPath"frameworks/base/core/java/android/content/"

ContextImpl=$AOSPPath"frameworks/base/core/java/android/app/ContextImpl.java"
ContextImplFolder=$AOSPPath"frameworks/base/core/java/android/app/"

PowerManager=$AOSPPath"frameworks/base/core/java/android/os/PowerManager.java"
PowerManagerFolder=$AOSPPath"frameworks/base/core/java/android/os/"

PowerManagerService=$AOSPPath"frameworks/base/services/java/com/android/server/power/PowerManagerService.java"
PowerManagerServiceFolder=$AOSPPath"frameworks/base/services/java/com/android/server/power/"

DisplayPowerController=$AOSPPath"frameworks/base/services/java/com/android/server/power/DisplayPowerController.java"
DisplayPowerControllerFolder=$AOSPPath"frameworks/base/services/java/com/android/server/power/"

NotificationManagerService=$AOSPPath"frameworks/base/services/java/com/android/server/NotificationManagerService.java"
NotificationManagerServiceFolder=$AOSPPath"frameworks/base/services/java/com/android/server/"

Notification=$AOSPPath"frameworks/base/core/java/android/app/Notification.java"
NotificationFolder=$AOSPPath"frameworks/base/core/java/android/app/"

AlarmManagerService=$AOSPPath"frameworks/base/services/java/com/android/server/AlarmManagerService.java"
AlarmManagerServiceFolder=$AOSPPath"frameworks/base/services/java/com/android/server/"

AlarmManager=$AOSPPath"frameworks/base/core/java/android/app/AlarmManager.java"
AlarmManagerFolder=$AOSPPath"frameworks/base/core/java/android/app/"

IAlarmManager=$AOSPPath"frameworks/base/core/java/android/app/IAlarmManager.aidl"
IAlarmManagerFolder=$AOSPPath"frameworks/base/core/java/android/app/"

MyAlarm=$AOSPPath"frameworks/base/core/java/android/app/MyAlarm.java"
MyAlarmFolder=$AOSPPath"frameworks/base/core/java/android/app/"

IMyAlarm=$AOSPPath"frameworks/base/core/java/android/app/MyAlarm.aidl"
IMyAlarmFolder=$AOSPPath"frameworks/base/core/java/android/app/"

WindowManagerService=$AOSPPath"frameworks/base/services/java/com/android/server/wm/WindowManagerService.java"
WindowManagerServiceFolder=$AOSPPath"frameworks/base/services/java/com/android/server/wm/"

WindowAnimator=$AOSPPath"frameworks/base/services/java/com/android/server/wm/WindowAnimator.java"
WindowAnimatorFolder=$AOSPPath"frameworks/base/services/java/com/android/server/wm/"

InputMonitor=$AOSPPath"frameworks/base/services/java/com/android/server/wm/InputMonitor.java"
InputMonitorFolder=$AOSPPath"frameworks/base/services/java/com/android/server/wm/"

if diff $MultiResourceManagerService MultiResourceManagerService.java >/dev/null ; then
	echo "MultiResourceManagerService.java is same"
else
	echo "Copy MultiResourceManagerService.java..."
	rm -f $MultiResourceManagerService
	cp MultiResourceManagerService.java $MultiResourceManagerServiceFolder
fi

if diff $IMultiResourceManagerService IMultiResourceManagerService.aidl >/dev/null ; then
	echo "IMultiResourceManagerService.aidl is same"
else
	echo "Copy IMultiResourceManagerService.aidl..."
	rm -f $IMultiResourceManagerService
	cp IMultiResourceManagerService.aidl $IMultiResourceManagerServiceFolder
fi

if diff $MultiResourceManager MultiResourceManager.java >/dev/null ; then
	echo "MultiResourceManager.java is same"
else
	echo "Copy MultiResourceManager.java..."
	rm -f $MultiResourceManager
	cp MultiResourceManager.java $MultiResourceManagerFolder
fi

if diff $SystemServer SystemServer.java >/dev/null ; then
	echo "SystemServer.java is same"
else
	echo "Copy SystemServer.java..."
	rm -f $SystemServer
	cp SystemServer.java $SystemServerFolder
fi

if diff $AndroidMk Android.mk >/dev/null ; then
	echo "Android.mk is same"
else
	echo "Copy Android.mk..."
	rm -f $AndroidMk
	cp Android.mk $AndroidMkFolder
fi

if diff $Context Context.java >/dev/null ; then
	echo "Context.java is same"
else
	echo "Copy Context.java..."
	rm -f $Context
	cp Context.java $ContextFolder
fi

if diff $ContextImpl ContextImpl.java >/dev/null ; then
	echo "ContextImpl.java is same"
else
	echo "Copy ContextImpl.java..."
	rm -f $ContextImpl
	cp ContextImpl.java $ContextImplFolder
fi

if diff $PowerManager PowerManager.java >/dev/null ; then
	echo "PowerManager.java is same"
else
	echo "Copy PowerManager.java..."
	rm -f $PowerManager
	cp PowerManager.java $PowerManagerFolder
fi

if diff $PowerManagerService PowerManagerService.java >/dev/null ; then
	echo "PowerManagerService.java is same"
else
	echo "Copy PowerManagerService.java..."
	rm -f $PowerManagerService
	cp PowerManagerService.java $PowerManagerServiceFolder
fi

if diff $DisplayPowerController DisplayPowerController.java >/dev/null ; then
	echo "DisplayPowerController.java is same"
else
	echo "Copy DisplayPowerController.java..."
	rm -f $DisplayPowerController
	cp DisplayPowerController.java $DisplayPowerControllerFolder
fi

if diff $NotificationManagerService NotificationManagerService.java >/dev/null ; then
	echo "NotificationManagerService.java is same"
else
	echo "Copy NotificationManagerService.java..."
	rm -f $NotificationManagerService
	cp NotificationManagerService.java $NotificationManagerServiceFolder
fi

if diff $Notification Notification.java >/dev/null ; then
	echo "Notification.java is same"
else
	echo "Copy Notification.java..."
	rm -f $Notification
	cp Notification.java $NotificationFolder
fi

if diff $AlarmManagerService AlarmManagerService.java >/dev/null ; then
	echo "AlarmManagerService.java is same"
else
	echo "Copy AlarmManagerService.java..."
	rm -f $AlarmManagerService
	cp AlarmManagerService.java $AlarmManagerServiceFolder
fi

if diff $AlarmManager AlarmManager.java >/dev/null ; then
	echo "AlarmManager.java is same"
else
	echo "Copy AlarmManager.java..."
	rm -f $AlarmManager
	cp AlarmManager.java $AlarmManagerFolder
fi

if diff $IAlarmManager IAlarmManager.aidl >/dev/null ; then
	echo "IAlarmManager.aidl is same"
else
	echo "Copy IAlarmManager.aidl..."
	rm -f $IAlarmManager
	cp IAlarmManager.aidl $IAlarmManagerFolder
fi

if diff $MyAlarm MyAlarm.java >/dev/null ; then
	echo "MyAlarm.java is same"
else
	echo "Copy MyAlarm.java..."
	rm -f $MyAlarm
	cp MyAlarm.java $MyAlarmFolder
fi

if diff $IMyAlarm MyAlarm.aidl >/dev/null ; then
	echo "MyAlarm.aidl is same"
else
	echo "Copy MyAlarm.aidl..."
	rm -f $IMyAlarm
	cp MyAlarm.aidl $IMyAlarmFolder
fi

if diff $WindowManagerService WindowManagerService.java >/dev/null ; then
	echo "WindowManagerService.java is same"
else
	echo "Copy WindowManagerService.java..."
	rm -f $WindowManagerService
	cp WindowManagerService.java $WindowManagerServiceFolder
fi

if diff $WindowAnimator WindowAnimator.java >/dev/null ; then
	echo "WindowAnimator.java is same"
else
	echo "Copy WindowAnimator.java..."
	rm -f $WindowAnimator
	cp WindowAnimator.java $WindowAnimatorFolder
fi

if diff $InputMonitor InputMonitor.java >/dev/null ; then
	echo "InputMonitor.java is same"
else
	echo "Copy InputMonitor.java..."
	rm -f $InputMonitor
	cp InputMonitor.java $InputMonitorFolder
fi
