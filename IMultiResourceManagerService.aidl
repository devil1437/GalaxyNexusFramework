package android.os;

import android.app.MyAlarm;
import android.app.Notification;

interface IMultiResourceManagerService
{
	// long getWakeUpTime(in List<Alarm> rtcWakeUp, in List<Alarm> rtc, in List<Alarm> elapsedWakeUp, in List<Alarm> elapsed);
	long getWakeUpTime();
	boolean isServe(in MyAlarm alarm, long now);
	boolean isServe(in String pkg, in String tag, int id, int callingUid, int callingPid, int userId, int score, in Notification notification);
}
