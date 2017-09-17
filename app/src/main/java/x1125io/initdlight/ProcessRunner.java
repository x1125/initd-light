package x1125io.initdlight;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class ProcessRunner {

    private String stdout;
    private String stderr;

    int Run(String[] cmdarray) {

        try {

            Process process = Runtime.getRuntime().exec(cmdarray);

            this.stdout = getProcessStreamAsString(process.getInputStream());
            this.stderr = getProcessStreamAsString(process.getErrorStream());

            return process.waitFor();

        } catch(IOException ex) {

            this.stderr = ex.getMessage();
            return 1;

        } catch(InterruptedException ex) {

            this.stderr = ex.getMessage();
            return 1;

        }
    }

    String getStdout() {
        return stdout;
    }

    String getStderr() {
        return this.stderr;
    }

    private String getProcessStreamAsString(InputStream is) {

        final InputStreamReader isr = new InputStreamReader(is);
        final BufferedReader ibr = new BufferedReader(isr);
        final StringBuilder isb = new StringBuilder();

        String line = null;
        try {
            while ((line = ibr.readLine()) != null)
            {
                isb.append(line).append("\n");
            }
        } catch(IOException ex) {
            return ex.getMessage();
        }

        return isb.toString();
    }
}