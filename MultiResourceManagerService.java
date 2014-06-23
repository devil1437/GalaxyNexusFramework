/*
 *   framework/base/services/java/com/android/server/MultiResourceManagerService.java
 */

package com.android.server;

import android.app.MyAlarm;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
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
        private NotificationManager mNotificationManager;
        private ArrayList<Notification> mBufferedNotification = new ArrayList<Notification>();
        private int mCount = 0;
        
        public MultiResourceManagerService(Context context)
        {
        	super();
        	mContext = context;
        	
        	mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        	mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Log.i(TAG,"MultiResourceManagerService is constructed!");
        }

        /**
         * For Alarm Manager. Return the next wake up time to alarm manager. 
         */
        public long getWakeUpTime(){
        	Log.i(TAG,"getWakeUpTime()");

        	List<MyAlarm> elapsedWakeUp = mAlarmManager.getAlarmList(AlarmManager.ELAPSED_REALTIME_WAKEUP);
        	
        	/*if(elapsedWakeUp != null){
	        	for(MyAlarm a : elapsedWakeUp){
	        		Log.i(TAG, a.toString());
	        	}
        	}*/
        	
        	return 0;
        }
        
        /**
         * Check if grant this alarm event.
         */
        public boolean isServeAlarm(MyAlarm alarm, long now){
        	Log.i(TAG,"isServeAlarm()");
        	if (alarm.when > now) {
                return false;
            }
        	else{
        		return true;
        	}
        }
        
        /**
         * Check if grant this notification event(sound, vibration).
         */
        public boolean isServeNotification(String pkg, String tag, int id, int callingUid, 
        		int callingPid, int userId, int score, Notification notification){
        	Log.i(TAG,"isServeNotification(). count = " + mCount++);
        	if(mCount % 5 == 0){
        		if(mBufferedNotification.size() > 0){
        			for(Notification n : mBufferedNotification){
        				Log.i(TAG,"isServeNotification(). notify = " + n.toString());
        				mNotificationManager.notify(0,n);
        				n.isBuffered = false;
        			}
        			mBufferedNotification = new ArrayList<Notification>();
        			Log.i(TAG,"isServeNotification(). Buffered = " + notification.toString());
            		notification.isBuffered = true;
            		mBufferedNotification.add(notification);
        		}
        		return false;
        	}
        	else{
        		Log.i(TAG,"isServeNotification(). Buffered = " + notification.toString());
        		notification.isBuffered = true;
        		mBufferedNotification.add(notification);
        		return false;
        	}
        }
}
