package project;

import java.awt.*;
import javax.swing.*;

/**
 * Created by 100518792 on 4/2/2017.
 */
public class IntroSplash extends JWindow {
    private int duration;
    public IntroSplash(int d) {
        duration = d;
    }

    public void showSplash() {
        JPanel content = (JPanel)getContentPane();
        content.setBackground(Color.white);

        int width = 1000;
        int height = 500;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x, y, width, height);

        JLabel label = new JLabel(new ImageIcon("./"));
        JLabel name = new JLabel("Neo BlackBoard ver1.0", JLabel.CENTER);
        name.setFont(new Font("UOIT", Font.BOLD, 12));
        content.add(label, BorderLayout.CENTER);
        content.add(name, BorderLayout.SOUTH);
        Color dodgerBlue = new Color(0, 0,255);
        content.setBorder(BorderFactory.createLineBorder(dodgerBlue, 15));

        //display the splash
        setVisible(true);

        //make it sleep after a period of time
        try {
            Thread.sleep(duration);
        } catch (Exception e) {

        }

        setVisible(false);
    }

}
