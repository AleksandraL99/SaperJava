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
    private Settings settings_copy;
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
        this.settings_copy = settings;
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

        window.setSize(game_window_width, game_window_height);

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

               // buttons[i][j] = bt;
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
            if(fields[i][j].getState() == 0) {
                fields[i][j].setState(CONSTANS.INACTIVE);
                activeButton.setIcon(null);
                activeButton.setBackground(CONSTANS.DARK_GREY);
                activeButton.setBorder(null);
                activeButton.removeMouseListener(this);
            }
            if(first_shoot) {
                Prepare after_first_shoot = new Prepare();
                fields = after_first_shoot.getRandomBombs(i, j, settings_copy, fields);
                for(int q=0; q<window_width; q++) {
                    for (int t = 0; t < window_height; t++) {
                        System.out.print(fields[q][t].getValue()+" ");
                    }
                    System.out.println(" ");
                }
                System.out.println(" ");
                fields = after_first_shoot.countFields(settings_copy, fields);
                for(int q=0; q<window_width; q++) {
                    for (int t = 0; t < window_height; t++) {
                        System.out.print(fields[q][t].getValue()+" ");
                    }
                    System.out.println(" ");
                }
                first_shoot = false;
            }
            int value = fields[i][j].getValue();
            //System.out.println(activeButton.hashCode()+ " ");
            String string_value = Integer.toString(value);
            activeButton.setText(string_value);

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

    public Field[][] unveiling(Field[][] fields, int cor_1, int cor_2) {
        /* if(fields[cor_1][cor_2].getValue() == CONSTANS.EMPTY && fields[cor_1][cor_2].getState() == CONSTANS.ACTIVE
        {
            fields[cor_1][cor_2].setState(CONSTANS.INACTIVE);
            odslon.append((wsp_1, wsp_2))  #odsłoń
            if wsp_1 != 0:  #jeśli to nie jest element w zerowym wierszu
            #to idź rekurencyjnie do góry
            odsloniecia(plansza, szerokosc, wysokosc, wsp_1 - 1, wsp_2, odslon)
            if wsp_1 != wysokosc - 1:  #jeśli to nie ostatni wiersz
            #to idź do dołu
            odsloniecia(plansza, szerokosc, wysokosc, wsp_1 + 1, wsp_2, odslon)
            if wsp_2 != 0:  #jeśli to nie zerowa kolumna
            #to idź w lewo
            odsloniecia(plansza, szerokosc, wysokosc, wsp_1, wsp_2 - 1, odslon)
            if wsp_2 != szerokosc - 1:  #jeśli to nie ostatnia kolumna
            #to idź w prawo
            odsloniecia(plansza, szerokosc, wysokosc, wsp_1, wsp_2 + 1, odslon)
    else:  #jeśli trafimy na komórkę z wartością lub już odsłoniętą
            plansza[wsp_1][wsp_2].set_stan(ODSLONIETY)  #zmień stan na odsłonięty
            odslon.append((wsp_1, wsp_2))  #odsłoń ją
        #lewy górny róg
            if (wsp_1 != 0 and wsp_2 !=szerokosc - 1 and
            plansza[wsp_1][wsp_2 + 1].wartosc == PUSTY and
            plansza[wsp_1 - 1][wsp_2].wartosc != PUSTY and
            plansza[wsp_1][wsp_2 + 1].stan == ODSLONIETY):
            plansza[wsp_1 - 1][wsp_2].set_stan(1)  #ustaw stan 1
            odslon.append((wsp_1 - 1, wsp_2))
        #prawy dolny róg
            if (wsp_1 != wysokosc - 1 and wsp_2 !=0 and
            plansza[wsp_1][wsp_2 - 1].wartosc == PUSTY and
            plansza[wsp_1 + 1][wsp_2].wartosc != PUSTY and
            plansza[wsp_1][wsp_2 - 1].stan == ODSLONIETY):
            plansza[wsp_1 + 1][wsp_2].set_stan(ODSLONIETY)
            odslon.append((wsp_1 + 1, wsp_2))
        #lewy dolny róg
            if (wsp_1 != wysokosc - 1 and wsp_2 !=szerokosc - 1 and
            plansza[wsp_1][wsp_2 + 1].wartosc == PUSTY and
            plansza[wsp_1 + 1][wsp_2].wartosc != PUSTY and
            plansza[wsp_1][wsp_2 + 1].stan == ODSLONIETY):
            plansza[wsp_1 + 1][wsp_2].set_stan(ODSLONIETY)
            odslon.append((wsp_1 + 1, wsp_2))
        #prawy górny róg
            if (wsp_1 != 0 and wsp_2 !=0 and plansza[ wsp_1][wsp_2 - 1].wartosc == PUSTY and
            plansza[wsp_1 - 1][wsp_2].wartosc != PUSTY and
            plansza[wsp_1][wsp_2 - 1].stan == ODSLONIETY):
            plansza[wsp_1 - 1][wsp_2].set_stan(ODSLONIETY)
            odslon.append((wsp_1 - 1, wsp_2))
        }*/
        return fields;
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
