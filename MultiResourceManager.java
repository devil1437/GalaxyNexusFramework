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
		return 0;
	}
}