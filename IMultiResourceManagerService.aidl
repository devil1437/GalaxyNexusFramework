package android.os;

import android.app.MyAlarm;

interface IMultiResourceManagerService
{
	// long getWakeUpTime(in List<Alarm> rtcWakeUp, in List<Alarm> rtc, in List<Alarm> elapsedWakeUp, in List<Alarm> elapsed);
	long getWakeUpTime();
}
