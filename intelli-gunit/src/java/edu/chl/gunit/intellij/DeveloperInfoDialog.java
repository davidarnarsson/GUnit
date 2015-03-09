package edu.chl.gunit.intellij;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import edu.chl.gunit.commons.api.ApiUser;
import edu.chl.gunit.commons.api.ApiUserBadge;
import edu.chl.gunit.service.client.Client;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

import java.util.*;
import java.util.List;

/**
 * Created by davida on 5.3.2015.
 */
public class DeveloperInfoDialog extends DialogWrapper {

    private ApiUser user;
    private List<ApiUserBadge> userBadges;
    private final JBPanel contentPanel = new JBPanel();
    public DeveloperInfoDialog(ApiUser user, List<ApiUserBadge> userBadges, Project project) {
        super(project, false);
        super.init();
        initialize(user, userBadges);

    }

    public DeveloperInfoDialog(int userId, Client client, Project project) {
        super(project, false);
        super.init();
        ApiUser user = client.getUser(userId);
        List<ApiUserBadge> badges = client.getBadgesForUser(userId);
        initialize(user, badges);
    }


    public void initialize(ApiUser user, List<ApiUserBadge> userBadges) {
/*
        setTitle("Forritari: " + user.getName());
        this.user = user;
        this.userBadges = userBadges;

        JBLabel userNameLabel = new JBLabel(user.getName());
        userNameLabel.setBounds(10, 10, 30, 100);
        contentPanel.add(userNameLabel);*/
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPanel;
    }
}
