package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

import java.util.Random;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {

    /* Inherits public fields:
    Maze maze;
    int[] distTo;
    int[] edgeTo;
    protected boolean[] marked;
    */

    private int[] cameFrom;
    private boolean foundCircle = false;

    public MazeCycles(Maze m) {
        super(m);
    }

    private void dfs(int s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        while(!stack.isEmpty()) {
            if (foundCircle) {
                break;
            }
            int v = stack.pop();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    cameFrom[w] = v;
                    //System.out.println(w+"camefrom"+v);
                    stack.push(w);
                } else if (w != cameFrom[v]) { // When `w` is not the parent of `v` (circle found)
                    int u = cameFrom[w];
                    cameFrom[u] = w;
                    cameFrom[w] = v;
                    //System.out.println(w+"camefrom"+v);
                    int cur = v;    // Reconstruct circle
                    edgeTo[cur] = cameFrom[cur];
                    while (cur != w) {
                        //System.out.println(cur+"camefrom"+cameFrom[cur]);
                        cur = cameFrom[cur];
                        edgeTo[cur] = cameFrom[cur];
                    }
                    foundCircle = true;
                    //System.out.println("here");
                    break;
                }
            }
        }
        //System.out.println("finish");
    }

    @Override
    public void solve() {

        /* Serves like `edgeTo`, created because I don't want to use `edgeTo` until circle found */
        cameFrom = new int[maze.V()];

        /* Set point where circle search starts */
        Random rand = new Random();
        int startX = rand.nextInt(maze.N() - 1) + 1;
        int startY = rand.nextInt(maze.N() - 1) + 1;
        int s = maze.xyTo1D(startX, startY);
        marked[s] = true;
        cameFrom[s] = s;

        dfs(s);
        announce();             // Render the results of DFS
    }

}

