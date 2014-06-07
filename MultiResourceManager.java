package android.os;

import android.app.MyAlarm;
import android.util.Log;
import android.os.IMultiResourceManagerService;
import	java.util.List;

public class MultiResourceManager {
	final String TAG = "MultiResourceManager";
	private IMultiResourceManagerService mService;
	
	public MultiResourceManager(IMultiResourceManagerService service){
		mService = service;
	}
	
	public long getWakeUpTime(){
		try{
			if(mService != null){
				Log.i(TAG, "Try to call the getWakeUpTime()");
				return mService.getWakeUpTime();
			}
			else{
				Log.i(TAG, "Service is null..");
				return -1;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
			return 0;
		}
	}
}