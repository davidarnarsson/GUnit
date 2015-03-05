package edu.chl.gunit.intellij;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import edu.chl.gunit.commons.api.ApiUser;
import edu.chl.gunit.intellij.pollers.LeaderboardMessagePoller;
import edu.chl.gunit.intellij.recipients.MessageRecipient;
import edu.chl.gunit.service.client.Client;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by Ivar on 25/02/15.
 */
public class LeaderBoard  implements ToolWindowFactory {

    private JTable table;

    final MessageRecipient leaderBoardRecipient = new MessageRecipient<List<LinkedHashMap<String,String>>>() {
        @Override
        public void receive(final List<LinkedHashMap<String,String>> msg) {
            ApplicationManager.getApplication().invokeLater(new Runnable() {
                @Override
                public void run() {
                    updateTable(msg);
                }
            });
        }

        @Override
        public String forMessageName() {
            return Messages.LEADERBOARD;
        }

        @Override
        public boolean oneTime() {
            return false;
        }
    };

    public LeaderBoard(ServiceListener listener) {
        listener.addRecipient(leaderBoardRecipient);
        listener.addPoller(new LeaderboardMessagePoller());
    }

    private TableModel createTableModel(List<LinkedHashMap<String,String>> users) {

        final String[] columnNames = {"Rank",
                "Name",
                "Points"};

        List<Object[]> transformed = new ArrayList<Object[]>();
        for (LinkedHashMap<String, String> u: users) {
            transformed.add(new Object[]{u.get("id"), u.get("name"),u.get("points")});
        }

        DefaultTableModel model = new DefaultTableModel(transformed.toArray(new Object[transformed.size()][]),columnNames);

        return model;
    }

    private void updateTable(List<LinkedHashMap<String,String>> msg) {
        if (table != null && msg != null) {
            table.setModel(createTableModel(msg));
        }
    }

    private URL getUrl(String p) {
        try {
            return new URL(p);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void createToolWindowContent(final Project project, ToolWindow toolWindow) {
        Component component = toolWindow.getComponent();


        final JTable table = new JTable(createTableModel(new ArrayList<LinkedHashMap<String, String>>()))
        {
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };

        this.table = table;


        final ImageIcon testRunner = new ImageIcon(getUrl("http://moodlebadges.com/wp-content/uploads/dlm_uploads/2013/05/moodle-180x180.png"));
        final ImageIcon branchMaster = new ImageIcon(getUrl("https://pbs.twimg.com/media/B3HjDuWCIAAXoD5.png:large"));

        //CompoundIcon(Axis axis, int gap, Icons... icons)
        //Axis segir til í hvaða átt itemin eru displayuð, gap segir til um bilið á milli
        final CompoundIcon runnerAndBranch = new CompoundIcon(CompoundIcon.Axis.X_AXIS, 5, testRunner, branchMaster);
        final CompoundIcon runnerAndInstructor = new CompoundIcon(CompoundIcon.Axis.X_AXIS, 5, testRunner, testRunner);
        final CompoundIcon branchAndInstructor = new CompoundIcon(CompoundIcon.Axis.X_AXIS, 5, branchMaster, branchMaster);
        final CompoundIcon runnerBranchInstructor = new CompoundIcon(CompoundIcon.Axis.X_AXIS, 5, runnerAndBranch, testRunner);



        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int row = table.rowAtPoint(e.getPoint());
                int developer = 1;
                int points = 2;
                int id = Integer.getInteger(table.getValueAt(row, 0).toString());
                String devName = table.getValueAt(row, developer).toString();
                String pointsString = table.getValueAt(row, points).toString();

                DeveloperInfoDialog dialog = new DeveloperInfoDialog(id, new Client("http://gunit.axlabond.in/api"), project);
                dialog.show();
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        component.getParent().add(scrollPane);
    }
}
