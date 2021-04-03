package lab11.graphs;

import java.security.KeyPair;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private class Pair implements Comparable {
        int item;
        int weight;
        Pair(int i, int w) {
            item = i;
            weight = w;
        }

        @Override
        public int compareTo(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                throw new IllegalArgumentException();
            }
            Pair other = (Pair) o;
            return weight - other.weight;
        }
    }

    private void astar(int s) {
        // TODO
        PriorityQueue<Pair> q = new PriorityQueue<>();
        q.add(new Pair(s, 0));
        while (!q.isEmpty()) {
            Pair curr = q.remove();
            int v = curr.item;
            if (v == t) {
                break;
            } else {
                for (int w : maze.adj(v)) {
                    if (!marked[w]) {
                        marked[w] = true;
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        announce();
                        q.add(new Pair(w, distTo[w] + h(w)));
                    }
                }
            }

        }
    }
    /*private class Node {
        private int v;
        private int priority;

        public Node(int v) {
            this.v = v;
            this.priority = distTo[v] + h(v);
        }
    }

    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.priority - o2.priority;
        }
    }
    private void astar(int src) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new NodeComparator());
        Node curNode = new Node(src);
        pq.add(curNode);
        marked[src] = true;
        while (!pq.isEmpty()) {
            curNode = pq.poll();
            for (int w : maze.adj(curNode.v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = curNode.v;
                    distTo[w] = distTo[curNode.v] + 1;
                    announce();
                    if (w == t) {
                        return;
                    } else {
                        pq.add(new Node(w));
                    }
                }
            }
        }
    }*/

    @Override
    public void solve() {
        astar(s);
    }

}

