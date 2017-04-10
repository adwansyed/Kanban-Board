package project;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Author(s): Adwan Syed, Andrew Selvarajah, Ahmed Naeem, Yi Guo
 */
public class IntroSplash extends JWindow {
    private int duration;
    public IntroSplash(int d) {
        duration = d;
    }

    public void showSplash() throws IOException {
        JPanel content = (JPanel)getContentPane();
        Color bg = new Color(236,240,241);
        content.setBackground(bg);
        content.setBorder(BorderFactory.createLineBorder(bg, 15));

        int width = 500;
        int height = 250;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x, y, width, height);

        String filePath = new File("src/main/resources/images/logo.jpg").getAbsolutePath();

        JLabel title = new JLabel("Back To Study", JLabel.CENTER);
        JLabel name = new JLabel("v1.0", JLabel.CENTER);
        System.out.println(new File(".").getCanonicalPath());
        JLabel image = new JLabel(new ImageIcon(filePath));

        name.setFont(new Font("Arial", Font.PLAIN, 12));
        title.setFont(new Font("Arial",Font.BOLD, 30));
        content.add(name, BorderLayout.SOUTH);
        content.add(title, BorderLayout.NORTH);
        content.add(image, BorderLayout.CENTER);

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
