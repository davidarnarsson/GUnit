package Mypackage;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Image;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;




/**
 * Created by Ivar on 25/02/15.
 */
public class LeaderBoard  implements ToolWindowFactory {

    public LeaderBoard(){

    }

    public void createToolWindowContent(final Project project, ToolWindow toolWindow) {
        Component component = toolWindow.getComponent();

        String[] columnNames = {"Rank",
                "Name",
                "Points"};

        Object[][] data = {
                {"1", "David",
                        "150"},
                {"2", "Ivar",
                        "100"},
                {"3", "Eric",
                        "99"},
                {"4", "Roberto",
                        "68"},
                {"5", "VALDI",
                        "0"}
        };


        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        final JTable table = new JTable( model )
        {
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };


        final ImageIcon testRunner = new ImageIcon("/Users/Ivar/IdeaProjects/TestPlugin2/src/resources/testIcon.png");
        final ImageIcon branchMaster = new ImageIcon("/Users/Ivar/IdeaProjects/TestPlugin2/images/gold.png");
        final ImageIcon instructor = new ImageIcon("/Users/Ivar/IdeaProjects/TestPlugin2/src/resources/testIcon.png");

        //CompoundIcon(Axis axis, int gap, Icons... icons)
        //Axis segir til í hvaða átt itemin eru displayuð, gap segir til um bilið á milli
        final CompoundIcon runnerAndBranch = new CompoundIcon(CompoundIcon.Axis.X_AXIS, 5, testRunner, branchMaster);
        final CompoundIcon runnerAndInstructor = new CompoundIcon(CompoundIcon.Axis.X_AXIS, 5, testRunner, instructor);
        final CompoundIcon branchAndInstructor = new CompoundIcon(CompoundIcon.Axis.X_AXIS, 5, branchMaster, instructor);
        final CompoundIcon runnerBranchInstructor = new CompoundIcon(CompoundIcon.Axis.X_AXIS, 5, runnerAndBranch, instructor);



        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int row = table.rowAtPoint(e.getPoint());
                int developer = 1;
                int points = 2;

                String devName = table.getValueAt(row, developer).toString();
                String pointsString = table.getValueAt(row, points).toString();


                JOptionPane.showMessageDialog(null,
                        "Developer: "+devName+"\n"+"Points: "+pointsString+"\n",
                        "Details",
                        JOptionPane.INFORMATION_MESSAGE,
                        runnerBranchInstructor);
                //showStatusBar();
            }
        });


        JScrollPane scrollPane = new JScrollPane(table);
        component.getParent().add(scrollPane);
    }
}
