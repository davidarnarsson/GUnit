package Mypackage;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

/**
 * Created by Ivar on 04/03/15.
 */
public class MockServiceListener extends Task.Backgroundable {


    static interface OnMessageReceived {
        void receive(String msg);
    }

    private OnMessageReceived recipient;

    public MockServiceListener(OnMessageReceived recipient, Project p) {
        super(p, "GUnit Listener");
        this.recipient = recipient;
    }

    @Override
    public void run(ProgressIndicator indicator) {

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            recipient.receive("This is a message!");
        }
    }
}
