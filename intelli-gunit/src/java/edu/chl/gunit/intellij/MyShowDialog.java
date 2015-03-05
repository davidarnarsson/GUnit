package edu.chl.gunit.intellij;

import com.intellij.execution.process.ProcessHandler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.xml.actions.xmlbeans.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Timer;
import java.util.TimerTask;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;



import javax.net.ssl.HttpsURLConnection;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;


/**
 * Created by Ivar on 06/02/15.
 */
public class MyShowDialog extends AnAction {

    // If you register the action from Java code, this constructor is used to set the menu item name
    // (optionally, you can specify the menu description and an icon to display next to the menu item).
    // You can omit this constructor when registering the action in the plugin.xml file.

    Project project;

    public MyShowDialog() {
        // Set the menu item name.
        super("Text _Boxes");

        System.out.println("Dialog Klasi smíðaður");
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);

        displayFeedback();

    }

    public void displayFeedback(){

        String branchCoverage = "Branch Coverage: 56%";
        String instructionCoverage = "Instruction Coverage: 32.5%";

        String branchPoints = "BranchCoverage Points: +11 out of 15";
        String instructionPoints = "Instruction Coverage Points: +4 out of 5";
        String totalTestPoints = "15 out of 20";

        String dailyQuest = "Daily Quest bonus: +20";

        String overAllPoints = "Over All Points: +35";

        String feedbackString = branchCoverage+"<br>"+branchPoints+"<br>"+instructionCoverage+"<br>"+instructionPoints+"<br>"+
                totalTestPoints+"<br>"+dailyQuest+"<br>"+overAllPoints+"<br>";

        // for copying style
        JLabel label = new JLabel();
        Font font = label.getFont();

        // create some css from the label's font
        StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
        style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
        style.append("font-size:" + font.getSize() + "pt;");

        // html content
        JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">" //
                + feedbackString+"View <a href=\"http://google.com/\">Leaderboard</a>" //
                + "</body></html>");

        // handle link events
        ep.addHyperlinkListener(new HyperlinkListener()
        {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
                //ProcessHandler.launchUrl(e.getURL().toString()); // roll your own link launcher or use Desktop if J6+
                {
                    try {
                        // Create Desktop object
                        Desktop d = Desktop.getDesktop();

                        // Browse a URL, for example www.facebook.com
                        d.browse(new URI("http://www.reddit.com/r/GlobalOffensive"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });
        ep.setEditable(false);
        ep.setBackground(label.getBackground());


        JOptionPane.showMessageDialog(null, ep);
    }

    public void startBackgroundTask(){

        System.out.println("Before Request");

        System.out.println("Testing 1 - Send Http GET request");

        ProgressManager.getInstance().run(new Task.Backgroundable(project, "GET REQUEST BACKGROUND"){
            public void run(@NotNull ProgressIndicator progressIndicator) {

                try {
                    sendGet();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Set the progress bar percentage and text
                progressIndicator.setFraction(0.10);
                progressIndicator.setText("90% to finish");


                // 50% done
                progressIndicator.setFraction(0.50);
                progressIndicator.setText("50% to finish");


                // Finished
                progressIndicator.setFraction(1.0);
                progressIndicator.setText("finished");

            }});
        System.out.println("GET finished");
    }

    public void sendGet() throws IOException, InterruptedException {

        int x = 0;

        while(x < 10) {

            System.out.println("inside loop, before get and x++");
            String url = "http://www.google.com/search?q=mkyong";
            String USER_AGENT = "Mozilla/5.0";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());
            Thread.sleep(1000);
            x++;
            System.out.println("After x++");
        }
    }


}
