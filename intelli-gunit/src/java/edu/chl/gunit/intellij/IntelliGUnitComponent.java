package edu.chl.gunit.intellij;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.*;
import com.intellij.ui.awt.RelativePoint;
import edu.chl.gunit.commons.api.ApiSession;
import edu.chl.gunit.intellij.pollers.NewSessionPoller;
import edu.chl.gunit.intellij.recipients.MessageRecipient;
import edu.chl.gunit.service.client.Client;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

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

        this.client = new Client("http://localhost:8080/api");

        final Project p = project;

        listener = new ServiceListener(this.client);


        listener.addRecipient(new MessageRecipient<ApiSession>() {
            @Override
            public void receive(ApiSession msg) {
                String base = "Ný prófunargögn hafa verið greind á þjóninum okkar! %s";
                if (msg.getPointsCollected() > 0) {
                    base = String.format(base, "Vel gert, þú fékkst %d stig fyrir þessi prófunargögn!");
                } else if (msg.getPointsCollected() < 0) {
                    base = String.format(base, "Ansans, þú fékkst %d stig fyrir þessi prófunargögn!");
                }

                String path = "<br/><a href=\"http://gunit.axlabond.in/site/#/session/%d\">Sjá gögn</a>";
                String message = String.format(base, msg.getPointsCollected());
                drawMessage(message + path);
            }

            @Override
            public String forMessageName() {
                return Messages.NEWSESSION;
            }

            @Override
            public boolean oneTime() {
                return false;
            }
        });
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
        String userName = JOptionPane.showInputDialog("Hvað er notandanafnið þitt?");
        l.createToolWindowContent(this.project, w);
        listener.addPoller(new NewSessionPoller(userName));
        ApplicationManager.getApplication().executeOnPooledThread(this.listener);
    }

    public void projectClosed() {
        // called when project is being closed
    }
}
