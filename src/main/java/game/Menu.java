package game;

import external_elements.CONSTANS;
import external_elements.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Menu extends JPanel implements ActionListener {

    private JFrame window;
    private JPanel chosen_field;
    private JPanel lower;

    private JButton next;

    private JComboBox field_height;
    private JComboBox field_width;
    private JComboBox field_bombs;

    public Menu(JFrame window) {
        this.window = window;

        JLabel label1;
        JLabel label2;
        JLabel label3;

        JPanel free_field;
        window.setSize(CONSTANS.MENU_WIDTH,CONSTANS.MENU_HEIGHT);

        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        free_field = new JPanel();
        free_field.setPreferredSize((new Dimension(CONSTANS.MENU_WIDTH,30)));
        free_field.setMaximumSize(new Dimension(CONSTANS.MENU_WIDTH,30));
        free_field.setBackground(new Color(192, 192, 192));
        add(free_field);

        chosen_field = new JPanel();
        chosen_field.setPreferredSize((new Dimension(CONSTANS.MENU_WIDTH,270)));
        chosen_field.setMaximumSize(new Dimension(CONSTANS.MENU_WIDTH,270));
        chosen_field.setBackground(new Color(192, 192, 192));
        add(chosen_field);

        label1 = new JLabel("Width", JLabel.CENTER);
        label1.setFont(CONSTANS.CALIBRI20);

        label2 = new JLabel("Height", JLabel.CENTER);
        label2.setFont(CONSTANS.CALIBRI20);

        label3 = new JLabel("Number of bombs", JLabel.CENTER);
        label3.setFont(CONSTANS.CALIBRI20);

        field_height = new JComboBox();
        field_width = new JComboBox();
        field_bombs = new JComboBox();

        for (int i = 10; i < 21; i++) {
            field_height.addItem(i);
            field_width.addItem(i);
        }
        field_height.addActionListener(this);
        field_width.addActionListener(this);
        for (int i = 30; i < 71; i++) {
            field_bombs.addItem(i);
        }

        field_height.setPreferredSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        field_height.setMaximumSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        field_height.setFont(CONSTANS.CALIBRI20);

        field_width.setPreferredSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        field_width.setMaximumSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        field_width.setFont(CONSTANS.CALIBRI20);

        field_bombs.setPreferredSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        field_bombs.setMaximumSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        field_bombs.setFont(CONSTANS.CALIBRI20);

        chosen_field.add(label1);
        chosen_field.add(field_height);
        chosen_field.add(label2);
        chosen_field.add(field_width);
        chosen_field.add(label3);
        chosen_field.add(field_bombs);



        lower = new JPanel();
        lower.setPreferredSize((new Dimension(CONSTANS.MENU_WIDTH,100)));
        lower.setMaximumSize(new Dimension(CONSTANS.MENU_WIDTH,100));
        lower.setBackground(CONSTANS.GREY);
        lower.setLayout(new BoxLayout(lower,BoxLayout.PAGE_AXIS));
        add(lower);

        JPanel lowerColumn = new JPanel();
        lowerColumn.setPreferredSize((new Dimension(150,100)));
        lowerColumn.setMaximumSize(new Dimension(150,100));
        lowerColumn.setLayout(new BoxLayout(lowerColumn,BoxLayout.LINE_AXIS));
        lowerColumn.setBackground(CONSTANS.GREY);
        lower.add(lowerColumn);

        JPanel lowerRow = new JPanel();
        lowerRow.setPreferredSize((new Dimension(150,100)));
        lowerRow.setMaximumSize(new Dimension(150,100));
        lowerRow.setBackground(CONSTANS.GREY);
        lowerColumn.add(lowerRow);


        next = new JButton("Okey");
        next.setPreferredSize((new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT)));
        next.setMaximumSize(new Dimension(CONSTANS.COMBOBOX_WIDTH,CONSTANS.COMBOBOX_HEIGHT));
        next.setBackground(CONSTANS.DARK_GREY);
        next.setFont(CONSTANS.CALIBRI20);
        next.setFocusable(false);
        lowerRow.add(next);

        next.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if((Object) e.getSource() == next)
        {
            Settings settings;
            settings = new Settings();
            settings.setHeight((Integer) field_height.getSelectedItem());
            settings.setWidth((Integer) field_width.getSelectedItem());
            settings.setBombs((Integer) field_bombs.getSelectedItem());
            setVisible(false);
            window.add(new Game(window, settings));
        }else {
            int bombs_sum, bombs_min, bombs_max;
            bombs_sum = (Integer) field_height.getSelectedItem() * (Integer) field_width.getSelectedItem();
            bombs_max = (Integer) 7 * bombs_sum / 10;
            bombs_min = (Integer) 3 * bombs_sum / 10;
            field_bombs.removeAllItems();
            for (int i = bombs_min; i < bombs_max+1; i++) {
                field_bombs.addItem(i);
            }
        }
    }
}
