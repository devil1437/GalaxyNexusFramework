/*
 *   framework/base/services/java/com/android/server/MultiResourceManagerService.java
 */

package com.android.server;

import android.app.Alarm;
import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;
import android.os.IMultiResourceManagerService;

class MultiResourceManagerService extends IMultiResourceManagerService.Stub
{
        final String TAG = "MultiResourceManagerService";

        public MultiResourceManagerService()
        {
                Log.i(TAG,"MultiResourceManagerService is constructed!");
        }

        public long getWakeUpTime(){
        	Log.i(TAG,"getWakeUpTime()");
        	
        	AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        	ArrayList<Alarm> alarms = am.getAlarmList(AlarmManager.RTC_WAKEUP);
        	
        	return 0;
        }
}
