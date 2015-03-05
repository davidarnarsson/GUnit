package edu.chl.gunit.intellij;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.*;
import com.intellij.ui.awt.RelativePoint;
import edu.chl.gunit.service.client.Client;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Ivar on 04/03/15.
 */
public class IntelliGUnitComponent implements ProjectComponent {
    private final Project project;
    private final Client client;
    private StatusBar bar;
    final ServiceListener listener;
    Thread listenThread;

    public IntelliGUnitComponent(Project project) {
        this.project = project;

        this.client = new Client("http://gunit.axlabond.in/api");

        final Project p = project;

        listener = new ServiceListener(this.client);

    }


    public void drawMessage(final String message) {
        final StatusBar bar = WindowManager.getInstance().getStatusBar(this.project);
        if (bar == null) return;

        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                JBPopupFactory.getInstance()
                        .createHtmlTextBalloonBuilder(message, MessageType.INFO, null)
                        .setFadeoutTime(7500)
                        .createBalloon()
                        .show(RelativePoint.getCenterOf(bar.getComponent()),
                                Balloon.Position.atRight);
            }
        });

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
        ToolWindow w = ToolWindowManager.getInstance(this.project).registerToolWindow("Leaderboard", true,ToolWindowAnchor.RIGHT);
        LeaderBoard l = new LeaderBoard(this.listener);

        l.createToolWindowContent(this.project, w);

        ApplicationManager.getApplication().executeOnPooledThread(this.listener);
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
