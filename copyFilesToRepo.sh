#!/bin/bash

rm -f /home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/MultiResourceManagerService.java
cp MultiResourceManagerService.java /home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/

rm -f /home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/SystemServer.java
cp SystemServer.java /home/howard/Nexus4/Nexus4Origin/frameworks/base/services/java/com/android/server/

rm -f /home/howard/Nexus4/Nexus4Origin/frameworks/base/core/java/android/os/IMultiResourceManagerService.aidl
cp IMultiResourceManagerService.aidl /home/howard/Nexus4/Nexus4Origin/frameworks/base/core/java/android/os/

rm -f /home/howard/Nexus4/Nexus4Origin/frameworks/base/Android.mk
cp Android.mk /home/howard/Nexus4/Nexus4Origin/frameworks/base/
