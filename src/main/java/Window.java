import javax.swing.*;

public class Window extends JFrame {

    public Window(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Saper");

        add(new Menu(this));
        setSize(CONSTANS.MENU_WIDTH,CONSTANS.MENU_HEIGHT);
    }

}
