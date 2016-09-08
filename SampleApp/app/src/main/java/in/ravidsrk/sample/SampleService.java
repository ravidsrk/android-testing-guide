package in.ravidsrk.sample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SampleService extends Service {
    public SampleService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("SampleService", "Binding SampleService");
        return new LocalBinder();
    }

    @Override
    public void onCreate() {
        Log.e("SampleService", "Creating SampleService");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.e("SampleService", "Destroying SampleService");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("SampleService", "Executing some service work");
        return super.onStartCommand(intent, flags, startId);
    }

    public class LocalBinder extends Binder {
        SampleService getService() {
            return SampleService.this;
        }
    }

}
