package android.os;

import android.app.MyAlarm;
import android.app.Notification;

interface IMultiResourceManagerService
{
	long getWakeUpTime();
	boolean isServeAlarm(in MyAlarm alarm, long now);
	boolean isServeNotification(in String pkg, in String tag, int id, int callingUid, int callingPid, int userId, int score, inout Notification notification);
}
