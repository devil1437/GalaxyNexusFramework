package android.os;

import android.util.Log;

public class MultiResourceManager {
	final String TAG = "MultiResourceManager";
	private IMultiResourceManager mService;
	
	public MultiResourceManager(IMultiResourceManager service){
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