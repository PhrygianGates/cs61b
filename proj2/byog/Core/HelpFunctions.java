package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;
import java.util.*;

public class HelpFunctions {
    Random random;
    public HelpFunctions(int seed) {
        random = new Random(seed);
    }
    //Fill the maps with 0 and 1, 0 represents wall, 1 represents road.
    public int[][] FilltheMap(int width, int height) {
        int[][] world = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i % 2 != 0 && j % 2 != 0) {
                    world[i][j] = 1;
                } else {
                    world[i][j] = 0;
                }
            }
        }
        return world;
    }
    public static class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    //Use 2 to fill a rectangular area, with random origin and size. Remember the width and height must be odd, the origin must be 1.
    public void DrawRec(int[][] world, Position origin, int width, int height) {
        if (width % 2 == 0 || height % 2 == 0 || world[origin.x][origin.y] != 1) {
            return;
        }
        if (isCollide(world, origin, width, height)) {
            return;
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                world[origin.x + i][origin.y + j] = 2;
            }
        }
    }
    //Make sure that no rectangle will collide before drawing.
    public boolean isCollide(int[][] world, Position origin, int width, int height) {
        int WIDTH = world.length;
        int HEIGHT = world[0].length;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (origin.x + i >= WIDTH || origin.y + j >= HEIGHT || world[origin.x + i][origin.y + j] == 2) {
                    return true;
                }
            }
        }
        return false;
    }
    //Use the input string as a seed to initialize the world randomly.
    public void RandomInit(int[][] world) {
        int WIDTH = world.length;
        int HEIGHT = world[0].length;
        int recNum = Math.min(HEIGHT, WIDTH);
        for (int i = 0; i < recNum * 2; i++) {
            //produce origin
            Position p = ChoosePosition(world);
            //produce width and height
            if (WIDTH - p.x - 1 <= 1 || HEIGHT - p.y - 1 <= 1) {
                continue;
            }
            int width = RandomUtils.uniform(random, 1, WIDTH - p.x - 1);
            if (width % 2 == 0) width = width - 1;
            int height = RandomUtils.uniform(random, 1, HEIGHT - p.y - 1);
            if (height % 2 == 0) height = height - 1;
            //draw a rectangle
            if (width != 1 && height != 1) {
                DrawRec(world, p, width, height);
            }
        }
    }
    //Randomly choose a point whose value is 1.
    public Position ChoosePosition(int[][] world) {
        int WIDTH = world.length;
        int HEIGHT = world[0].length;
        int xOrigin, yOrigin;
        while(true) {
            xOrigin = RandomUtils.uniform(random, 1, WIDTH);
            if (xOrigin % 2 == 0) xOrigin = xOrigin - 1;
            yOrigin = RandomUtils.uniform(random, 1, HEIGHT);
            if (yOrigin % 2 == 0) yOrigin = yOrigin - 1;
            if(world[xOrigin][yOrigin] == 1) {
                return new Position(xOrigin, yOrigin);
            }
        }
    }
    //Get the neighbor position of the current position. The order is up(0),left(1),down(2),right(3). The content of the current position must be 1.
    public ArrayList<Position> getNeighbors(int[][] world, Position p) {
        int WIDTH = world.length;
        int HEIGHT = world[0].length;
        ArrayList<Position> neighbors = new ArrayList<>();
        if (p.y < HEIGHT - 3 && world[p.x][p.y + 2] == 1) neighbors.add(new Position(p.x, p.y + 2));
        if (p.x > 2 && world[p.x - 2][p.y] == 1) neighbors.add(new Position(p.x - 2, p.y));
        if (p.y > 2 && world[p.x][p.y - 2] == 1) neighbors.add(new Position(p.x, p.y - 2));
        if (p.x < WIDTH - 3 && world[p.x + 2][p.y] == 1) neighbors.add(new Position(p.x + 2, p.y));
        return neighbors;
    }
    public boolean isGoal(int[][] world) {
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j++) {
                if (world[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }
    public class Pair{
        Position a;
        Position b;
        public Pair(Position a, Position b) {
            this.a = a;
            this.b = b;
        }
    }
    //Search a path in areas other than rectangles. Use 3 to represent the path.
    public void SearchPath(int[][] world) {
        Stack<Pair> stack = new Stack();
        Position start0 = ChoosePosition(world);
        world[start0.x][start0.y] = 3;
        Position start1 = getNeighbors(world, start0).get(0);
        stack.push(new Pair(start0, start1));
        while (!stack.empty()) {
            Pair pair = stack.pop();
            Position curr = pair.b, from = pair.a;

            /*for (int i = 0; i < world.length ; i++) {
                for (int j = 0; j < world[0].length; j++) {
                    System.out.print(world[i][j] + " ");
                }
                System.out.print("\n");
            }
            System.out.print("\n");*/

            if (isGoal(world)) {
                return;
            }
            if (world[curr.x][curr.y] == 1) {
                //visit
                if (curr.x == from.x) {
                    world[curr.x][curr.y] = world[curr.x][(from.y + curr.y) / 2] = 3;
                } else {
                    world[curr.x][curr.y] = world[(curr.x + from.x) / 2][curr.y] = 3;
                }
                //push
                ArrayList<Position> neighbors = getNeighbors(world, curr);
                while (neighbors.size() != 0) {
                    int index = RandomUtils.uniform(random, neighbors.size());
                    stack.push(new Pair(curr, neighbors.get(index)));
                    neighbors.remove(index);
                }
            }
        }
    }

    //A different version of SearchPath using stack.
    //Randomly choose point to connect rectangles and the road.
    public void connect(int[][] world) {
        int WIDTH = world.length;
        int HEIGHT = world[0].length;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (isJoint(world, new Position(i, j)) && RandomUtils.bernoulli(random, 0.1)){
                    world[i][j] = 3;
                }
            }
        }
    }
    //Check whether Position p is a joint.
    public boolean isJoint(int[][] world, Position p) {
        if (world[p.x][p.y] != 0 || p.x == 0 || p.y == 0 || p.x == world.length - 1 || p.y == world[0].length - 1) {
            return false;
        }
        return world[p.x - 1][p.y] + world[p.x + 1][p.y] == 5 || world[p.x][p.y - 1] + world[p.x][p.y + 1] == 5;
    }
    //Encapsulate.
    public void draw(int width, int height) {
        int[][] world = FilltheMap(width, height);
        RandomInit(world);
        SearchPath(world);
        connect(world);
        //use the game engine
        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        TETile[][] Tiles = new TETile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (world[i][j] == 0) {
                    Tiles[i][j] = Tileset.WALL;
                } else {
                    Tiles[i][j] = Tileset.FLOOR;
                }
            }
        }
        ter.renderFrame(Tiles);
    }
}
