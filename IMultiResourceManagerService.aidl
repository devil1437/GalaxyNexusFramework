package android.os;

import android.app.MyAlarm;
import android.app.Notification;
import android.os.WorkSource;

interface IMultiResourceManagerService
{
	boolean isGrant(int uid, long startRtc, long stopRtc, int hardware);
	long lastGrant(int uid, int hardware);
	void grant(int uid, int hardware);
	boolean isServeNotification(in String pkg, in String tag, int id, int callingUid, int callingPid, int userId, int score, inout Notification notification);
	boolean isServeWakeLock(int flags, in String tag, in WorkSource ws, int uid, int pid);
	boolean isServeScreen();
	
	void focusChanged(int uid);
	long lastFocus(int uid);
}
