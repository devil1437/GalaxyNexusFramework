#!/bin/bash

MultiResourceManagerService=/home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/MultiResourceManagerService.java
MultiResourceManagerServiceFolder=/home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/

SystemServer=/home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/SystemServer.java
SystemServerFolder=/home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/

IMultiResourceManagerService=/home/howard/Nexus4/Nexus4Origin/frameworks/base/core/java/android/os/IMultiResourceManagerService.aidl
IMultiResourceManagerServiceFolder=/home/howard/Nexus4/Nexus4Origin/frameworks/base/core/java/android/os/

AndroidMk=/home/howard/Nexus4/Nexus4Origin/frameworks/base/Android.mk
AndroidMkFolder=/home/howard/Nexus4/Nexus4Origin/frameworks/base/

PowerManager=/home/howard/Nexus4/Nexus4Origin/frameworks/base/core/java/android/os/PowerManager.java
PowerManagerFolder=/home/howard/Nexus4/Nexus4Origin/frameworks/base/core/java/android/os/

PowerManagerService=/home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/power/PowerManagerService.java
PowerManagerServiceFolder=/home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/power/

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
