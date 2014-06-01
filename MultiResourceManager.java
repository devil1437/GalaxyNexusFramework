package android.os;

import android.util.Log;
import android.os.IMultiResourceManagerService;

public class MultiResourceManager {
	final String TAG = "MultiResourceManager";
	private IMultiResourceManagerService mService;
	
	public MultiResourceManager(IMultiResourceManagerService service){
		mService = service;
	}
	
	public long getWakeUpTime(){
		try{
			return mService.getWakeUpTime();
		} catch (RemoteException e) {
			e.printStackTrace();
			return 0;
		}
	}
}