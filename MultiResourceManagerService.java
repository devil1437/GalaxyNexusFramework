/*
 *   framework/base/services/java/com/android/server/MultiResourceManagerService.java
 */

package com.android.server;

import android.app.MyAlarm;
import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;
import android.os.IMultiResourceManagerService;
import	java.util.List;
import	java.util.ArrayList;

class MultiResourceManagerService extends IMultiResourceManagerService.Stub
{
        final String TAG = "MultiResourceManagerService";
        private Context mContext;
        private AlarmManager mAlarmManager;
        
        public MultiResourceManagerService(Context context)
        {
        	super();
        	mContext = context;
        	
        	mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        	
            Log.i(TAG,"MultiResourceManagerService is constructed!");
        }

        public long getWakeUpTime(){
        	Log.i(TAG,"getWakeUpTime()");

        	List<MyAlarm> elapsedWakeUp = mAlarmManager.getAlarmList(AlarmManager.ELAPSED_REALTIME_WAKEUP);
        	
        	if(elapsedWakeUp != null){
	        	for(MyAlarm a : elapsedWakeUp){
	        		Log.i(TAG, a.toString());
	        	}
        	}
        	
        	return 0;
        }
}
