package game;

import external_elements.CONSTANS;
import external_elements.Field;
import external_elements.Settings;
import org.junit.Assert;
import org.junit.Test;

public class PrepareTest  {

    @Test
    public void countFieldsTest() {
        Settings settings = new Settings();
        Field[][] fields = new Field[3][3];
        Field[][] fields_after = new Field[3][3];
        settings.setHeight(3);
        settings.setWidth(3);

        Prepare count =new Prepare(settings);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = new Field();
                fields_after[i][j] = new Field();
            }
        }

        fields[0][0].setValue(9);
        fields[1][0].setValue(9);
        fields[1][2].setValue(9);

        fields_after[0][0].setValue(9);
        fields_after[0][1].setValue(3);
        fields_after[0][2].setValue(1);
        fields_after[1][0].setValue(9);
        fields_after[1][1].setValue(3);
        fields_after[1][2].setValue(9);
        fields_after[2][0].setValue(1);
        fields_after[2][1].setValue(2);
        fields_after[2][2].setValue(1);


        fields = count.countFields(fields);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println(fields[i][j].getValue()+ " "+fields_after[i][j].getValue());
                Assert.assertEquals(fields_after[i][j].getValue(), fields[i][j].getValue());
            }
        }
    }

    @Test
    public void getRandomBombsTest()
    {
        Settings settings = new Settings();
        Field[][] fields = new Field[10][10];
        settings.setHeight(10);
        settings.setWidth(10);
        settings.setBombs(25);
        int counter = 0;
        Prepare drawn_field =new Prepare(settings);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                fields[i][j] = new Field();
            }
        }

        fields = drawn_field.getRandomBombs(0, 1, fields);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if(fields[i][j].getValue() == CONSTANS.BOMB)
                    counter++;
            }
        }
        Assert.assertEquals(25, counter);
    }
}