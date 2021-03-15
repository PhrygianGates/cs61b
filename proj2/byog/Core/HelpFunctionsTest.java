package byog.Core;
import byog.TileEngine.TETile;
import org.junit.Assert;
import org.junit.Test;

public class HelpFunctionsTest {
    //@Test
    public void FilltheMapTest() {
        HelpFunctions helpFunctions = new HelpFunctions(123);
        int[][] world = helpFunctions.FilltheMap(3,3);
        System.out.println(world[0][0]);
        System.out.println(world[0][1]);
        System.out.println(world[1][1]);
    }
    //@Test
    public void DrawRectest() {
        HelpFunctions helpFunctions = new HelpFunctions(123);
        int[][] world = helpFunctions.FilltheMap(7,7);
        HelpFunctions.Position p1 = new HelpFunctions.Position(1, 1);
        HelpFunctions.Position p2 = new HelpFunctions.Position(3, 3);
        helpFunctions.DrawRec(world, p1, 5, 3);
        helpFunctions.DrawRec(world, p2, 3, 3);
        System.out.println(world[0][0]);
        System.out.println(world[0][1]);
        System.out.println(world[1][1]);
    }
    //@Test
    public void RandomInitTest() {
        HelpFunctions helpFunctions = new HelpFunctions(123);
        int[][] world = helpFunctions.FilltheMap(25,25);
        helpFunctions.RandomInit(world);
        for (int i = 0; i < world.length ; i++) {
            for (int j = 0; j < world[0].length; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    //@Test
    public void SearchPathTest() {
        HelpFunctions helpFunctions = new HelpFunctions(123);
        int[][] world = helpFunctions.FilltheMap(25,25);
        helpFunctions.RandomInit(world);
        helpFunctions.SearchPath(world);
        for (int i = 0; i < world.length ; i++) {
            for (int j = 0; j < world[0].length; j++) {
                System.out.print(world[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
    public static void main(String args[]) {
        HelpFunctions helpFunctions = new HelpFunctions(1534);
        helpFunctions.draw(25, 27);
    }
}
