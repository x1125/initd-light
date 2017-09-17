package x1125io.initdlight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    final String TAG = "initdlight";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

            for (String file: context.getFilesDir().list()) {

                ProcessRunner pr = new ProcessRunner();
                String fullFilePath = context.getFilesDir().toString() + "/" + file;
                int exitCode = pr.Run(new String[] { "su", "-c", "sh", fullFilePath });

                if (exitCode == 0) {
                    Log.d(TAG, "started " + file);
                } else {
                    Log.d(TAG, String.format(
                            "error starting %s; exit code: %d; stdout: %s; stderr: %s",
                            file,
                            exitCode,
                            pr.getStdout(),
                            pr.getStderr()
                    ));
                }
            }

        }
    }
}