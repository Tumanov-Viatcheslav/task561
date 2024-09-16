import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Towers {

    public static boolean isSmaller(int[] tower1, int[] tower2) {
        Power10Tower tower1ByPower10 = new Power10Tower(tower1.length - 1, tower1[tower1.length - 1]),
                tower2ByPower10 = new Power10Tower(tower2.length - 1, tower2[tower2.length - 1]);

        for (int i = tower1.length - 2; i >= 0; i--) {
            tower1ByPower10.topValue = tower1ByPower10.topValue * Math.log10(tower1[i]);
        }
        for (int i = tower2.length - 2; i >= 0; i--) {
            tower2ByPower10.topValue = tower2ByPower10.topValue * Math.log10(tower2[i]);
        }

        if (tower1ByPower10.height == tower2ByPower10.height)
            return tower1ByPower10.topValue < tower2ByPower10.topValue;

        double value1 = tower1ByPower10.topValue, value2 = tower2ByPower10.topValue;

        if (tower1ByPower10.height > tower2ByPower10.height) {
            //value2 = log10PowerN(tower2ByPower10.topValue, tower1ByPower10.height - tower2ByPower10.height);
            for (int i = 0; i < tower1ByPower10.height - tower2ByPower10.height; i++) {
                if (value2 < 0)
                    return false;
                value2 = Math.log10(value2);
            }
        }
        else {
            //value1 = log10PowerN(tower1ByPower10.topValue, tower2ByPower10.height - tower1ByPower10.height);
            for (int i = 0; i < tower2ByPower10.height - tower1ByPower10.height; i++) {
                if (value1 < 0)
                    return true;
                value1 = Math.log10(value1);
            }
        }
        return value1 < value2;
    }

    public static int[] sortTowers(int[][] towers) {
        int maxIndex, buff;
        int[] towersIndexes = new int[towers.length];
        for (int i = 0; i < towersIndexes.length; i++) {
            towersIndexes[i] = i;
        }
        for (int i = 0; i < towersIndexes.length; i++) {
            maxIndex = i;
            for (int j = 0; j < towersIndexes.length - i; j++) {
                if (isSmaller(towers[towersIndexes[maxIndex]], towers[towersIndexes[j]]))
                    maxIndex = j;
            }
            buff = towersIndexes[towersIndexes.length - 1 - i];
            towersIndexes[towersIndexes.length - 1 - i] = towersIndexes[maxIndex];
            towersIndexes[maxIndex] = buff;
        }
        return towersIndexes;
    }

    public static void main(String[] args) {
        int numberOfTowers, heightOfTower;
        int[] result;
        int[][] towers = null;
        try(BufferedReader input = new BufferedReader(new FileReader("input.txt"))) {
            numberOfTowers = Integer.parseInt(input.readLine());
            towers = new int[numberOfTowers][];
            for (int i = 0; i < numberOfTowers; i++) {
                heightOfTower = input.read() - '0' + 1;
                towers[i] = new int[heightOfTower];
                for (int j = 0; j < heightOfTower; j++) {
                    input.read();
                    towers[i][j] = input.read() - '0';
                }
                if ((char)input.read() == '\r')
                    input.read();
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        result = Towers.sortTowers(towers);

        try(FileWriter output = new FileWriter("output.txt")) {

            for (int i = 0; i < result.length; i++) {
                output.write(result[i] + " ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}