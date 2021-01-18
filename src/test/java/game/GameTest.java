package game;

import external_elements.CONSTANS;
import external_elements.Field;
import external_elements.Settings;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class GameTest {

    @Test
    public void unveilingTest() {
            Settings settings = new Settings();
            Field[][] fields = new Field[3][3];
            settings.setHeight(3);
            settings.setWidth(3);
            settings.setBombs(3);
            int counter = 0;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    fields[i][j] = new Field();
                    fields[i][j].setState(CONSTANS.ACTIVE);
                    fields[i][j].setButton(new JButton());
                }
            }

            fields[0][0].setValue(9);
            fields[0][1].setValue(2);
            fields[0][2].setValue(0);
            fields[1][0].setValue(9);
            fields[1][1].setValue(2);
            fields[1][2].setValue(0);
            fields[2][0].setValue(1);
            fields[2][1].setValue(1);
            fields[2][2].setValue(0);

            JFrame window = new JFrame();
            Game game = new Game(window, settings);
            game.unveiling(fields, 1, 2);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.println(fields[i][j].getState());
                    if(fields[i][j].getState()==CONSTANS.INACTIVE)
                        counter++;
                }
            }
        Assert.assertEquals(6, counter);
    }

}