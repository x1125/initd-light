package x1125io.initdlight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText appText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.appText = (EditText)findViewById(R.id.appText);
        final Button testButton = (Button)findViewById(R.id.testButton);

        addAppText("Target directory for scripts:");
        addAppText(this.getFilesDir().toString());

        addAppText("\nCurrent scripts:");

        for (String file: this.getFilesDir().list()) {
            addAppText(file);
        }

        addAppText("\nRoot access check:");
        ProcessRunner pr = new ProcessRunner();
        int exitCode = pr.Run(new String[] { "su", "-c", "whoami"});

        if (exitCode == 0 && pr.getStdout().equals("root\n")) {
            addAppText("OK");
            testButton.setEnabled(true);
        } else {
            addAppText("Error: " + pr.getStderr());
        }

        testButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addAppText("\nRunning Tests:");
                for (String file: v.getContext().getFilesDir().list()) {
                    ProcessRunner pr = new ProcessRunner();
                    String fullFilePath = v.getContext().getFilesDir().toString() + "/" + file;
                    int exitCode = pr.Run(new String[] { "su", "-c", fullFilePath });
                    addAppText(String.format(
                            "%s, exit: %d, stdout: %s, stderr: %s",
                            fullFilePath,
                            exitCode,
                            pr.getStdout(),
                            pr.getStderr()
                    ));
                }
            }
        });

    }

    void addAppText(String str) {
        appText.setText(appText.getText() + str + "\n");
    }
}
