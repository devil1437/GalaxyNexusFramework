/*
 *   framework/base/services/java/com/android/server/MultiResourceManagerService.java
 */

package com.android.server;

import android.util.Log;
import android.os.IMultiResourceManagerService;

class MultiResourceManagerService extends IMultiResourceManagerService.Stub
{
        final String TAG = "MultiResourceManagerService";

        public MultiResourceManagerService()
        {
                Log.i(TAG,"MultiResourceManagerService is constructed!");
        }

}
