package game;

import external_elements.CONSTANS;
import external_elements.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LastWindow extends JPanel implements ActionListener  {
    private JFrame window;
    private int window_height;
    private int window_width;

    private JPanel labelPanel;
    private JPanel buttonPanel;
    private JPanel freePanel;
    private JPanel panel;

    private JButton retry;
    private JButton close;
    private JLabel label;

    public LastWindow(JFrame window, Settings settings, int status) {
        this.window = window;
        this.window_height = settings.getHeight();
        this.window_width = settings.getWidth();

        int game_window_width = window_height* CONSTANS.FIELD_SIZE+20;
        int game_window_height = window_width*CONSTANS.FIELD_SIZE+110;

        window.setSize(game_window_width+8, game_window_height+40);

        JPanel panel = new JPanel();
        panel.setPreferredSize((new Dimension(game_window_width,game_window_height)));
        panel.setMaximumSize(new Dimension(game_window_width,game_window_height));
        panel.setBackground(CONSTANS.GREY);
        add(panel);

        JPanel freePanel = new JPanel();
        freePanel.setPreferredSize((new Dimension(game_window_width,game_window_height/4)));
        freePanel.setMaximumSize(new Dimension(game_window_width,game_window_height/4));
        freePanel.setBackground(CONSTANS.GREY);
        panel.add(freePanel);

        JPanel labelPanel = new JPanel();
        labelPanel.setPreferredSize((new Dimension(game_window_width,game_window_height/4)));
        labelPanel.setMaximumSize(new Dimension(game_window_width,game_window_height/4));
        labelPanel.setBackground(CONSTANS.GREY);
        panel.add(labelPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize((new Dimension(game_window_width,game_window_height/2)));
        buttonPanel.setMaximumSize(new Dimension(game_window_width,game_window_height/2));
        buttonPanel.setBackground(CONSTANS.GREY);
        panel.add(buttonPanel);

        JLabel label = new JLabel();
        if(status == CONSTANS.LOSS) {
            label.setText("You lost");
        } else if(status == CONSTANS.WIN) {
            label.setText("You won");
        }
        label.setFont(CONSTANS.CALIBRI40);
        labelPanel.add(label);

        retry = new JButton("Try again");
        retry.setPreferredSize((new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT)));
        retry.setMaximumSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        retry.setBackground(CONSTANS.DARK_GREY);
        retry.setFont(CONSTANS.CALIBRI20);
        retry.setFocusable(false);//fokus na blok - jeśli wcisniesz enter to uruchomi się akcja powiązana z tym blokiem, ta funckja wyłacza fokus
        buttonPanel.add(retry);
        retry.addActionListener(this);

        close = new JButton("Close");
        close.setPreferredSize((new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT)));
        close.setMaximumSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        close.setBackground(CONSTANS.DARK_GREY);
        close.setFont(CONSTANS.CALIBRI20);
        close.setFocusable(false);
        buttonPanel.add(close);
        close.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if((Object) e.getSource() == retry)
        {
            setVisible(false);
            window.add(new Menu(window));
        } else if((Object) e.getSource() == close)
        {
            window.dispose();
        }
    }
}