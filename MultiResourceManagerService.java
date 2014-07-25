/*
 *   framework/base/services/java/com/android/server/MultiResourceManagerService.java
 */

package com.android.server;

import android.app.MyAlarm;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.os.IMultiResourceManagerService;
import android.os.PowerManager;
import android.os.WorkSource;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import	java.util.List;
import	java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.lang.StringBuffer;

class MultiResourceManagerService extends IMultiResourceManagerService.Stub
{
        final String TAG = "MultiResourceManagerService";
        
        public static final int HARDWARE_DEFAULT = -1;
        public static final int HARDWARE_VIBRATION = 0;
        public static final int HARDWARE_SOUND = 1;
        public static final int HARDWARE_SCREEN = 2;
        public static final int HARDWARE_GPS = 3;
        
        public static int SCREEN_ON_DEFAULT = -1;
    	public static int SCREEN_ON_USER = 0;
    	public static int SCREEN_ON_WAKELOCK = 1;
    	public static int SCREEN_ON_WINDOW_MANAGER = 2;
        
        private static final boolean USE_ORIGINAL_POLICY = true;
        private static final int RECENT_LENGTH = 5;
        private static final int HISTORY_LENGTH = 500;
        private final long mStartTime = 120 * 1000; // ms
        
        private final Context mContext;
        private AlarmManager mAlarmManager;
        private NotificationManager mNotificationManager;
        
        private int mCount = 0;
        private HashMap<String, Long> mLastGrantTime = new HashMap<String, Long>();
        private Object mLock = new Object();
        private Calendar mLastDate;
        
        // For buffered event
        ArrayList<Notification> mBufferedNotification = new ArrayList<Notification>();
        ArrayList<WakeLock> mBufferedWakeLock = new ArrayList<WakeLock>();
        ArrayList<Integer> mBufferedScreen = new ArrayList<Integer>();
        
        // For screen event
        private ScreenEventReceiver mScreenEventReceiver;
        ArrayList<ScreenEvent> mScreenOn = new ArrayList<ScreenEvent>();
        private int mScreenOnReason = SCREEN_ON_DEFAULT;
        private long mScreenOnTime = 0;
        
        // For focus event
        ArrayList<Integer> mFocusUid = new ArrayList<Integer>();
        ArrayList<FocusEvent> mFocusEvent = new ArrayList<FocusEvent>();
        private HashMap<Integer, Long> mLastFocusTime = new HashMap<Integer, Long>();
        
        // For notification event
        int mLastNotificationUid = 0;
        ArrayList<NotificationEvent> mNotificationEvent = new ArrayList<NotificationEvent>();
        
        public MultiResourceManagerService(Context context)
        {
        	super();
        	mContext = context;
        	
        	mAlarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        	mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        	
        	mScreenEventReceiver = new ScreenEventReceiver();
        	mLastDate = Calendar.getInstance();
        	
            Log.i(TAG,"MultiResourceManagerService is constructed!");
        }

        /**
         * Whether grant the specified hardware usage in the duration or not.
         */
        public boolean isGrant(int uid, long startRtc, long stopRtc, int hardware){
        	String id = new String(uid + " " + hardware);
        	long time = lastGrant(uid, hardware);
        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            
            cal.setTimeInMillis(time);
        	
        	Log.i(TAG,"isGrant(). id: " + id + " time: " + dateFormat.format(cal.getTime()));
        	
        	long error = 10*1000;
        	
        	if(time > 0 && (time >= startRtc - error && time <= stopRtc + error)){
        		return true;
        	}
        	
        	return false;
        }
        
        /**
         * Return the last rtc time that grant the hardware to spicified app.
         * If the app didsn't request the hardware, return 0.
         */
        public long lastGrant(int uid, int hardware){
        	String id = new String(uid + " " + hardware);
        	long time = mLastGrantTime.get(id) == null ? 0 : mLastGrantTime.get(id);
        	
        	return time;
        }
        
        public void grant(int uid, int hardware){
        	long nowRtc = System.currentTimeMillis();
        	Log.i(TAG,"grant(). uid: " + uid + " hardware: " + hardware);
        	String id = new String(uid + " " + hardware);
        	
        	mLastGrantTime.put(id, nowRtc);
        	
        	if(hardware == HARDWARE_VIBRATION || hardware == HARDWARE_SOUND){
        		addNotificationEvent(uid, hardware);
        	}
        }
        
        /**
         * Add a notification record to history.
         */
        private void addNotificationEvent(int uid, int hardware){
        	NotificationEvent n = new NotificationEvent(uid, System.currentTimeMillis(), hardware);
        	mNotificationEvent.add(n);
        	
        	while(mNotificationEvent.size() > HISTORY_LENGTH){
        		mNotificationEvent.remove(0);
        	}
        }
        
        /**
         * Check if grant this notification event(sound, vibration).
         */
        public boolean isServeNotification(String pkg, String tag, int id, int callingUid, 
        		int callingPid, int userId, int score, Notification notification){
        	
        	mLastNotificationUid = callingUid;
        	addNotificationEvent(mLastNotificationUid, HARDWARE_DEFAULT);
        	
        	Calendar c = Calendar.getInstance();
        	
        	if( mLastDate.get(Calendar.DATE) != c.get(Calendar.DATE) ){
        		try{
        			File file = new File("/mnt/sdcard/log/" + rtc2Str(mLastDate.getTimeInMillis()) + "_multiResource.log");
        			Writer wt = new FileWriter(file);
            		PrintWriter pw = new PrintWriter(wt); 
            		
            		dump(0, pw, null);
        		} catch (Exception e) {
                    Slog.w(TAG, "Failure writing daily file.", e);
                }
        		
        		mLastDate.setTimeInMillis(c.getTimeInMillis());
        	}
        	
        	if(USE_ORIGINAL_POLICY){
        		return true;
        	}
        	
        	return isServeNotificationInternel(	callingUid, notification);
        }
        
        /**
         * Policy of audio, vibration.
         */
        private boolean isServeNotificationInternel(int callingUid, Notification notification){
        	int size = mBufferedNotification.size();
        	long elapsedTime = android.os.SystemClock.elapsedRealtime();
        	
        	Log.i(TAG,"isServeNotificationInternel(). count = " + size);

        	if(elapsedTime < mStartTime){
        		return true;
        	}
        	
        	if(size % 100 == 0 && size > 0){
        		for(Notification n : mBufferedNotification){
        			Log.i(TAG,"isServeNotification(). notify = " + n.toString());
        			mNotificationManager.notify(0,n);
        			n.isBuffered = false;
        		}
        		mBufferedNotification = new ArrayList<Notification>();
        		
            	return true;
        	}
        	else{
        		Log.i(TAG,"isServeNotification(). Buffered = " + notification.toString());
        		notification.isBuffered = true;
        		notification.bufferedUid = callingUid;
        		mBufferedNotification.add(notification);
        		return false;
        	}
        }
        
        /**
         * Screen acquire wakelock.
         */
        public boolean isServeWakeLock(int flags, String tag, WorkSource ws, int uid, int pid){
        	
        	mScreenOnReason = SCREEN_ON_WAKELOCK;
        	
        	if(USE_ORIGINAL_POLICY){
        		grant(uid, HARDWARE_SCREEN);
        		return true;
        	}
        	
        	WakeLock w = new WakeLock(flags, tag, ws, uid, pid);
        	
        	if(isScreenAcquireLock(w)){
        		return isServeScreenInternal(w);
        	}
        	
        	return true;
        }
        
        /**
         * Screen acquire from window manager.
         * Example: 
         * 	Window window = this.getWindow();
         * 	window.addFlags(LayoutParams.FLAG_DISMISS_KEYGUARD);
         * 	window.addFlags(LayoutParams.FLAG_SHOW_WHEN_LOCKED);
         * 	window.addFlags(LayoutParams.FLAG_TURN_SCREEN_ON);
         */
        public boolean isServeScreen(){
        	
        	mScreenOnReason = SCREEN_ON_WINDOW_MANAGER;
        	
        	if(USE_ORIGINAL_POLICY){
        		grant(mLastNotificationUid, HARDWARE_SCREEN);
        		return true;
        	}
        	
        	return isServeScreenInternal(mLastNotificationUid);
        }
        
        private boolean isServeScreenInternal(final WakeLock w){
        	ArrayList<Integer> source = new ArrayList<Integer>();
        	
        	source.add(w.mOwnerUid);
        	
        	// Has to deal with WorkSource?
        	
        	boolean ret = isServeScreenInternal(source);
        	
        	if(!ret){
        		mBufferedWakeLock.add(w);
        	}
        	
        	return ret;
        }
        
        private boolean isServeScreenInternal(int uid){
        	ArrayList<Integer> source = new ArrayList<Integer>();
        	
        	source.add(uid);
        	
        	boolean ret = isServeScreenInternal(source);
        	
        	if(!ret){
        		mBufferedScreen.add(new Integer(uid));
        	}
        	
        	return ret;
        }
        
        /**
         * Policy of screen.
         */
        private boolean isServeScreenInternal(ArrayList<Integer> source){        	
        	int size = mBufferedWakeLock.size() + mBufferedScreen.size();
        	long elapsedTime = android.os.SystemClock.elapsedRealtime();
        	
        	Log.i(TAG,"isServeScreenInternal(). count = " + size);
        	
        	if(elapsedTime < mStartTime){
        		return true;
        	}
        	
        	if(size % 100 == 0 && size > 0){
        		/*PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	        	PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP| PowerManager.PARTIAL_WAKE_LOCK,
	        	    TAG);
	        	
	        	wakeLock.acquire();
	        	wakeLock.release();
	        	*/
        		StringBuffer sb = new StringBuffer(128);
        		
        		for(int n : mBufferedScreen){
        			sb.append(n);
        			sb.append(" ");
        		}
    			sb.append("\n");
        		
        		for(WakeLock w : mBufferedWakeLock){
        			sb.append(w);
        			sb.append("\n");
        		}
	        	
	        	mBufferedWakeLock = new ArrayList<WakeLock>();
	        	mBufferedScreen = new ArrayList<Integer>();
	        	
	        	Log.i(TAG,"isServeScreenInternal(). Buffered = " + sb.toString());
	        	return true;
        	}
        	else{
        		return false;
        	}
        }
        
        /**
         * Add the focus uid to recent record.
         */
        private void addFocusUid(int uid){        	
        	// Only add app's uid.
        	if(uid < 10000){
        		return ;
        	}
        	
        	// If exists, remove the old one and add to the end of list.
        	if(mFocusUid.contains(uid)){
        		mFocusUid.remove(mFocusUid.indexOf(uid));
        	}
        	mFocusUid.add(0, uid);
        	mLastFocusTime.put(uid, System.currentTimeMillis());
        	
        	if(mFocusUid.size() > RECENT_LENGTH){
        		mFocusUid.remove(mFocusUid.size()-1);
        	}
        }
        
        /**
         * Add the focus app to history record.
         */
        private void addFocusEvent(int uid){      	
        	// Only add app's uid.
        	if(uid < 10000){
        		return ;
        	}
        	
        	long nowRtc = System.currentTimeMillis();
        	FocusEvent fa = new FocusEvent(uid, nowRtc);
        	
        	mFocusEvent.add(fa);
        	
        	if(mFocusEvent.size() > HISTORY_LENGTH){
        		mFocusEvent.remove(0);
        	}
        }
        
        /**
         * The focus window has changed.
         */
        public void focusChanged(int uid){
        	addFocusUid(uid);
        	addFocusEvent(uid);
        	
        	String str = new String();
        	for(int i : mFocusUid){
        		str += i + " ";
        	}
        	
        	Log.i(TAG, "focusChanged(). historyFocus: " + str);
        }
        
        /**
         * Return the last focus time for specified app.
         * If none, return -1.
         */
        public long lastFocus(int uid){
        	return mLastFocusTime.get(uid) == null ? -1 : mLastFocusTime.get(uid);
        }
        
        protected void dump(FileDescriptor fd, PrintWriter pw, String[] args) {      
            final long now = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            pw.print("Multi-resource Manager (now=");pw.print(sdf.format(new Date(now))); pw.println("):");
            
            synchronized (mLock) {
            	pw.println();
            	pw.println("ScreenEvent:");
            	if (mScreenOn.size() > 0) {
                    for(ScreenEvent s : mScreenOn){
                    	pw.println(s.toString());
                    }
                }
            	
            	pw.println();
            	pw.println("FocusEvent:");
            	if (mFocusEvent.size() > 0) {
            		for(FocusEvent fa : mFocusEvent){
                    	pw.println(fa.toString());
                    }
                }
            	
            	pw.println();
            	pw.println("NotificationEvent:");
            	if (mNotificationEvent.size() > 0) {
            		for(NotificationEvent ne : mNotificationEvent){
                    	pw.println(ne.toString());
                    }
                }
            }
        }
        
        private class ScreenEvent{
        	int reason;
        	long startTime;
        	long endTime;
        	
        	public ScreenEvent(){
        		reason = SCREEN_ON_DEFAULT;
        		startTime = -1;
        		endTime = -1;
        	}
        	
        	public ScreenEvent(int r, long s, long e){
        		reason = r;
        		startTime = s;
        		endTime = e;
        	}
        	
        	public String toString(){
        		StringBuffer sb = new StringBuffer(64);
        		
        		sb.append(startTime);
        		sb.append(" ");
        		
        		sb.append(endTime);
        		sb.append(" ");
        		
        		sb.append(reason);
        		sb.append(" ");
        		
        		return sb.toString();
        	}
        }
        
        private class FocusEvent{
        	int uid;
        	long time;
        	
        	public FocusEvent(int u, long t){
        		uid = u;
        		time = t;
        	}
        	
        	public String toString(){
        		StringBuffer sb = new StringBuffer(32);
        		
        		sb.append(time);
        		sb.append(" ");
        		
        		sb.append(uid);
        		sb.append(" ");
        		
        		return sb.toString();
        	}
        }
        
        private class NotificationEvent{
        	int uid;
        	long time;
        	int hardware;
        	
        	public NotificationEvent(int u, long t, int h){
        		uid = u;
        		time = t;
        		hardware = h;
        	}
        	
        	public String toString(){
        		StringBuffer sb = new StringBuffer(32);
        		
        		sb.append(time);
        		sb.append(" ");
        		
        		sb.append(uid);
        		sb.append(" ");
        		
        		sb.append(hardware);
        		sb.append(" ");
        		
        		return sb.toString();
        	}
        }
        
        class ScreenEventReceiver extends BroadcastReceiver {
            public ScreenEventReceiver() {
                IntentFilter filter = new IntentFilter();
                filter.addAction(Intent.ACTION_SCREEN_OFF);
                filter.addAction(Intent.ACTION_SCREEN_ON);
                mContext.registerReceiver(this, filter);
            }
            
            @Override
            public void onReceive(Context context, Intent intent) {
                synchronized (mLock) {
                    String action = intent.getAction();
                    if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    	ScreenEvent s = new ScreenEvent(mScreenOnReason, mScreenOnTime, System.currentTimeMillis());
                    	mScreenOn.add(s);
                    	
                    	while(mScreenOn.size() > HISTORY_LENGTH){
                    		mScreenOn.remove(0);
                    	}
                    	mScreenOnReason = SCREEN_ON_DEFAULT;
                    	
                        return;
                    } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    	mScreenOnTime = System.currentTimeMillis();
                    	return;
                    }
                }
            }
        }
        
        private final class WakeLock {
            public int mFlags;
            public String mTag;
            public WorkSource mWorkSource;
            public int mOwnerUid;
            public int mOwnerPid;
            
            public WakeLock(int flags, String tag, WorkSource workSource,
                    int ownerUid, int ownerPid) {
                mFlags = flags;
                mTag = tag;
                mWorkSource = copyWorkSource(workSource);
                mOwnerUid = ownerUid;
                mOwnerPid = ownerPid;
            }
            
            @Override
            public String toString() {
                return getLockLevelString()
                        + " '" + mTag + "'" + getLockFlagsString()
                        + " (uid=" + mOwnerUid + ", pid=" + mOwnerPid + ", ws=" + mWorkSource + ")";
            }
            
            private String getLockLevelString() {
                switch (mFlags & PowerManager.WAKE_LOCK_LEVEL_MASK) {
                    case PowerManager.FULL_WAKE_LOCK:
                        return "FULL_WAKE_LOCK                ";
                    case PowerManager.SCREEN_BRIGHT_WAKE_LOCK:
                        return "SCREEN_BRIGHT_WAKE_LOCK       ";
                    case PowerManager.SCREEN_DIM_WAKE_LOCK:
                        return "SCREEN_DIM_WAKE_LOCK          ";
                    case PowerManager.PARTIAL_WAKE_LOCK:
                        return "PARTIAL_WAKE_LOCK             ";
                    case PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK:
                        return "PROXIMITY_SCREEN_OFF_WAKE_LOCK";
                    default:
                        return "???                           ";
                }
            }
            
            private String getLockFlagsString() {
                String result = "";
                if ((mFlags & PowerManager.ACQUIRE_CAUSES_WAKEUP) != 0) {
                    result += " ACQUIRE_CAUSES_WAKEUP";
                }
                if ((mFlags & PowerManager.ON_AFTER_RELEASE) != 0) {
                    result += " ON_AFTER_RELEASE";
                }
                return result;
            }
        }
        
        private static WorkSource copyWorkSource(WorkSource workSource) {
            return workSource != null ? new WorkSource(workSource) : null;
        }
        
        private static boolean isScreenLock(final WakeLock wakeLock) {
            switch (wakeLock.mFlags & PowerManager.WAKE_LOCK_LEVEL_MASK) {
                case PowerManager.FULL_WAKE_LOCK:
                case PowerManager.SCREEN_BRIGHT_WAKE_LOCK:
                case PowerManager.SCREEN_DIM_WAKE_LOCK:
                    return true;
            }
            return false;
        }

        private boolean isScreenAcquireLock(WakeLock wakeLock) {
            if ((wakeLock.mFlags & PowerManager.ACQUIRE_CAUSES_WAKEUP) != 0 &&
                    isScreenLock(wakeLock)) {
                return true;
            }
            return false;
        }
}
