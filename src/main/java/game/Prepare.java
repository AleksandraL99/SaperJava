package game;

import external_elements.CONSTANS;
import external_elements.Field;
import external_elements.Settings;

import java.util.Arrays;
import java.util.Random;

public class Prepare {

    private int window_height;
    private int window_width;
    private int bombs;

    public Prepare(Settings settings) {
        this.window_height = settings.getHeight();
        this.window_width = settings.getWidth();
        this.bombs = settings.getBombs();
    }

    public Field[][] getRandomBombs(int coordinate_1, int coordinate_2, Field[][] fields) {


        int shot;
        int range;
        int row;
        int column;
        int coordinate;
        range = window_height * window_width;
        int bombs_table[] = new int[bombs];
        Random generator = new Random();

        shot = coordinate_2 * window_width + coordinate_1;

        bombs_table[0] = generator.nextInt(range);
        boolean state = false;
        for (int i = 1; i < bombs; i++){
            int rand = generator.nextInt(range);
            state = true;
            for (int j = 0; j < i; j++) {
                if(bombs_table[j]==rand || shot == rand){
                    state = false;
                }
            }
            if(state){
                bombs_table[i] = rand;
            }else{
                i--;
            }
        }
        Arrays.sort(bombs_table);
        for (int i = 0; i < bombs; i++) {
            System.out.print(bombs_table[i] + " ");
            coordinate = bombs_table[i];
            row = (Integer) (coordinate / window_width);
            column = coordinate - row * window_width;
            fields[column][row].setValue(9);
        }
        System.out.println(" ");

        return fields;
    }

    public Field[][] countFields(Field[][] fields) {
        int number_of_bombs = 0;

        for (int i = 0; i < window_width; i++)
            for (int j = 0; j < window_height; j++) {
                number_of_bombs = 0;
                if (fields[i][j].getValue() == CONSTANS.BOMB)
                    continue;
                if (i == 0 && j == 0) {
                    if (fields[i + 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                } else if (i == window_width - 1 && j == window_height - 1) {
                    if (fields[i - 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                } else if (i == window_width - 1 && j == 0) {
                    if (fields[i - 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                } else if (i == 0 && j == window_height - 1) {
                    if (fields[i + 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                } else if (i == 0) {
                    if (fields[i + 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                } else if (i == window_width - 1) {
                    if (fields[i - 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                } else if (j == 0) {
                    if (fields[i + 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                } else if (j == window_height - 1) {
                    if (fields[i + 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                } else {
                    if (fields[i][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i + 1][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j - 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                    if (fields[i - 1][j + 1].getValue() == CONSTANS.BOMB)
                        number_of_bombs++;
                }
                fields[i][j].setValue(number_of_bombs);
            }
        return fields;
    }
}
