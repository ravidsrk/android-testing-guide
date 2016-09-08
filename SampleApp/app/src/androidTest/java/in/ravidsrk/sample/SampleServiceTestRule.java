package in.ravidsrk.sample;

import android.content.Intent;
import android.os.IBinder;
import android.support.test.rule.ServiceTestRule;
import android.util.Log;

import java.util.concurrent.TimeoutException;

public class SampleServiceTestRule extends ServiceTestRule {

    @Override
    public void startService(Intent intent) throws TimeoutException {
        Log.e("SampleServiceTestRule", "start the service");
        super.startService(intent);
    }

    @Override
    public IBinder bindService(Intent intent) throws TimeoutException {
        Log.e("SampleServiceTestRule", "binding the service");
        return super.bindService(intent);
    }

    @Override
    protected void beforeService() {
        Log.e("SampleServiceTestRule", "work before the service starts");
        super.beforeService();
    }

    @Override
    protected void afterService() {
        Log.e("SampleServiceTestRule", "work after the service has started");
        super.afterService();
    }
}
