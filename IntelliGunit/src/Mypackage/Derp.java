package Mypackage;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.*;
import com.intellij.ui.awt.RelativePoint;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Ivar on 04/03/15.
 */
public class Derp implements ProjectComponent {
    private final Project project;
    private StatusBar bar;
    final MockServiceListener listener;
    Thread listenThread;

    public Derp(Project project) {
        this.project = project;

        final Project p = project;

        listener = new MockServiceListener(new MockServiceListener.OnMessageReceived() {
            @Override
            public void receive(String msg) {
                drawMessage(msg);
            }
        },project);
    }


    public void drawMessage(String message) {
        final StatusBar bar = WindowManager.getInstance().getStatusBar(this.project);
        if (bar == null) return;

        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(message, MessageType.INFO, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(RelativePoint.getCenterOf(bar.getComponent()),
                        Balloon.Position.atRight);
    }

    public StatusBar getStatusBar(){
        return this.bar;
    }

    public void initComponent() {

    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "Derp";
    }

    public void projectOpened() {
        ProgressManager.getInstance().run(listener);
        String[] ids = ToolWindowManager.getInstance(this.project).getToolWindowIds();
        ToolWindow w = ToolWindowManager.getInstance(this.project).registerToolWindow("rightwindow", true,ToolWindowAnchor.RIGHT);
        LeaderBoard l = new LeaderBoard();
        l.createToolWindowContent(this.project,w);
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
