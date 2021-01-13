import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


public class Game extends JPanel implements MouseListener {

    private JFrame window;
    private int window_height;
    private int window_width;
    private int bombs;
    private int to_mark;

    private JPanel upper;
    private JPanel lower;
    private JPanel upper_left;
    private JPanel upper_center;
    private JPanel upper_right;
    private JPanel right_upper;
    private JPanel right_lower;

   // private JButton[][] buttons;
    private JButton retry_button;
    private Field[][] fields;
    private Settings settings;
    private JLabel to_be_marked;
    private Prepare after_first_shoot;
    private Boolean first_shoot ;

    private ImageIcon flag;
    private ImageIcon question_mark;
    private ImageIcon bomb;
    private ImageIcon retry;

    public Game(JFrame window, Settings settings) {
        this.window = window;
        this.window_height = settings.getHeight();
        this.window_width = settings.getWidth();
        this.bombs = settings.getBombs();
        this.settings = settings;
        to_mark = bombs;
        first_shoot = true;

        try {
            flag = new ImageIcon( ImageIO.read(getClass().getResource("/flag.jpg")));
            question_mark = new ImageIcon( ImageIO.read(getClass().getResource("/question_mark.jpg")));
            bomb = new ImageIcon( ImageIO.read(getClass().getResource("/bomb.jpg")));
            retry = new ImageIcon( ImageIO.read(getClass().getResource("/retry.jpg")));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

      //  this.buttons = new JButton[window_width][window_height];
        this.fields = new Field[window_width][window_height];
//TODO zmienić szerokość żeby nie wcinało
        int game_window_width = window_height*CONSTANS.FIELD_SIZE+20;
        int game_window_height = window_width*CONSTANS.FIELD_SIZE+110;

        window.setSize(game_window_width+8, game_window_height+40);

        setBackground(CONSTANS.GREY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        upper = new JPanel();
        upper.setPreferredSize((new Dimension(game_window_width,80)));
        upper.setMaximumSize(new Dimension(game_window_width,80));
        upper.setBackground(CONSTANS.DARK_GREY);
        upper.setLayout(new GridLayout(1,3));
        add(upper);

        upper_left = new JPanel();
        upper_left.setLayout(new FlowLayout(FlowLayout.CENTER));
        upper_left.setAlignmentY(CENTER_ALIGNMENT);
        upper.add(upper_left);

        upper_center = new JPanel();
        upper.add(upper_center);

        upper_right = new JPanel();
        upper_right.setLayout(new FlowLayout(FlowLayout.CENTER));
        upper.add(upper_right);

        right_upper = new JPanel();
        right_upper.setPreferredSize((new Dimension(game_window_width/3,20)));
        right_upper.setMaximumSize(new Dimension(game_window_width/3,20));
        upper_right.add(right_upper);

        right_lower = new JPanel();
        upper_right.add(right_lower);


        lower = new JPanel();

        add(lower);

        JPanel test = new JPanel();
        test.setPreferredSize((new Dimension(game_window_width,game_window_height-80)));
        test.setMaximumSize(new Dimension(game_window_width,game_window_height-80));
        test.setBackground(CONSTANS.GREY);
        test.setLayout(new GridLayout(window_width, window_height));
        lower.add(test);

        String number_of_bombs = Integer.toString(bombs);
        to_be_marked = new JLabel(number_of_bombs);
        to_be_marked.setForeground(Color.RED);
        to_be_marked.setFont(CONSTANS.CALIBRI20);
        right_lower.add(to_be_marked);

        retry_button = new JButton();
        retry_button.setIcon(retry);
        retry_button.setBorder(BorderFactory.createEtchedBorder(0));
        retry_button.addMouseListener(this);
        upper_left.add(retry_button);


        JButton bt;
        Field field;
        for (int i = 0; i < window_width; i++) {
            for (int j = 0; j < window_height; j++) {
                bt = new JButton("");
                field = new Field();
                bt.setFont(CONSTANS.CALIBRI15); //nazwa, typ, rozmiar czcionki
                bt.setPreferredSize(new Dimension(CONSTANS.FIELD_SIZE, CONSTANS.FIELD_SIZE));
                bt.setMaximumSize(new Dimension(CONSTANS.FIELD_SIZE, CONSTANS.FIELD_SIZE));
                bt.setBackground(CONSTANS.GREY);
                bt.setBorder(BorderFactory.createEtchedBorder(1));
                test.add(bt);

                field.setButton(bt);
                field.setState(0);
                fields[i][j] = field;
                bt.addMouseListener(this);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {


        JButton activeButton = (JButton) e.getSource();
        int j = activeButton.getX() / CONSTANS.FIELD_SIZE;
        int i = activeButton.getY() / CONSTANS.FIELD_SIZE;

        //lewe kliknięcie
        if(e.getButton() == MouseEvent.BUTTON1) {
            if((Object) e.getSource() == retry_button)
            {
                setVisible(false);
                window.add(new Menu(window));
            }
            else if(fields[i][j].getState() == CONSTANS.ACTIVE) {
                if(first_shoot) {
                    Prepare after_first_shoot = new Prepare();
                    fields = after_first_shoot.getRandomBombs(i, j, settings, fields);
                    for(int q=0; q<window_width; q++) {
                        for (int t = 0; t < window_height; t++) {
                            System.out.print(fields[q][t].getValue()+" ");
                        }
                        System.out.println(" ");
                    }
                    System.out.println(" ");
                    fields = after_first_shoot.countFields(settings, fields);
                    for(int q=0; q<window_width; q++) {
                        for (int t = 0; t < window_height; t++) {
                            System.out.print(fields[q][t].getValue()+" ");
                        }
                        System.out.println(" ");
                    }
                    first_shoot = false;
                }
                if(fields[i][j].getValue() == 9) {
                    setVisible(false);
                    window.add(new LastWindow(window, settings));
                }
                unveiling(fields, i, j);
            }
        }
        //środkowe kliknięcie
        if(e.getButton() == MouseEvent.BUTTON2) {
            setVisible(false);
            window.add(new LastWindow(window, settings));
        }
        //prawe kliknięcie
        if(e.getButton() == MouseEvent.BUTTON3) {
            if(fields[i][j].getState() == CONSTANS.FLAG) {
                fields[i][j].setState(CONSTANS.QUESTION_MARK);
                activeButton.setIcon(question_mark);
                activeButton.setHorizontalTextPosition(JButton.CENTER);
                activeButton.setVerticalTextPosition(JButton.CENTER);
                to_mark++;
            }
            else if(fields[i][j].getState() == CONSTANS.ACTIVE) {
                fields[i][j].setState(CONSTANS.FLAG);
                activeButton.setIcon(flag);
                activeButton.setHorizontalTextPosition(JButton.CENTER);
                activeButton.setVerticalTextPosition(JButton.CENTER);
                to_mark--;

            }
            else if(fields[i][j].getState() == CONSTANS.QUESTION_MARK) {
                fields[i][j].setState(CONSTANS.ACTIVE);
                activeButton.setIcon(null);
                activeButton.setHorizontalTextPosition(JButton.CENTER);
                activeButton.setVerticalTextPosition(JButton.CENTER);
            }
            String number_of_bombs = Integer.toString(to_mark);
            to_be_marked.setText(number_of_bombs);
        }
    }

    public void unveiling(Field[][] fields, int cor_1, int cor_2) {
        JButton bt = new JButton();
        bt= fields[cor_1][cor_2].getButton();
        fields[cor_1][cor_2].setState(CONSTANS.INACTIVE);
        changeButton(bt);
        color(fields, cor_1, cor_2, bt);
        if(fields[cor_1][cor_2].getValue() == CONSTANS.EMPTY) {
            System.out.println(cor_1+" "+cor_2);
            if(cor_1 != 0 && fields[cor_1-1][cor_2].getState() == CONSTANS.ACTIVE)
                unveiling(fields, cor_1-1, cor_2);
            if(cor_2 != 0 && fields[cor_1][cor_2-1].getState() == CONSTANS.ACTIVE)
                unveiling(fields, cor_1, cor_2-1);
            if(cor_1 != window_width-1 && fields[cor_1+1][cor_2].getState() == CONSTANS.ACTIVE)
                unveiling(fields, cor_1+1, cor_2);
            if(cor_2 != window_height-1 && fields[cor_1][cor_2+1].getState() == CONSTANS.ACTIVE)
                unveiling(fields, cor_1, cor_2+1);
            if(cor_1 != window_width-1 && cor_2 != window_height-1 && fields[cor_1+1][cor_2+1].getState() == CONSTANS.ACTIVE)
                unveiling(fields, cor_1+1, cor_2+1);
            if(cor_1 != 0 && cor_2 != 0 && fields[cor_1-1][cor_2-1].getState() == CONSTANS.ACTIVE)
                unveiling(fields, cor_1-1, cor_2-1);
            if(cor_1 != 0 && cor_2 != window_height-1 && fields[cor_1-1][cor_2+1].getState() == CONSTANS.ACTIVE)
                unveiling(fields, cor_1-1, cor_2+1);
            if(cor_1 != window_width-1 && cor_2 != 0 && fields[cor_1+1][cor_2-1].getState() == CONSTANS.ACTIVE)
                unveiling(fields, cor_1+1, cor_2-1);
        }
    }

    private void changeButton(JButton bt) {
        bt.setIcon(null);
        bt.setBackground(CONSTANS.DARK_GREY);
        bt.setBorder(null);
        bt.removeMouseListener(this);
    }

    private void color(Field[][] fields, int i, int j, JButton bt){
        int value = fields[i][j].getValue();
        String string_value = Integer.toString(value);
        bt.setText(string_value);
        if(value == 0)
            bt.setText("");
        else if(value == 1)
            bt.setForeground(new Color(0,0,204));
        else if(value == 2)
            bt.setForeground(new Color(0,153,0));
        else if(value == 3)
            bt.setForeground(new Color(255,0,0));
        else if(value == 4)
            bt.setForeground(new Color(0,51,102));
        else if(value == 5)
            bt.setForeground(new Color(102,0,0));
        else if(value == 6)
            bt.setForeground(new Color(245,0,87));
        else if(value == 7)
            bt.setForeground(new Color(170,0,255));
        else if(value == 8)
            bt.setForeground(new Color(118,255,3));
    }

    private void endConditions(Field[][] fields){

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
