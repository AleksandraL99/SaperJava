import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LastWindow extends JPanel  {
    private JFrame window;
    private int window_height;
    private int window_width;

    public LastWindow(JFrame window, Settings settings) {
        this.window = window;
        this.window_height = settings.getHeight();
        this.window_width = settings.getWidth();
    }
}
