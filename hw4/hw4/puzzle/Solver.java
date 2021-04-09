package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class Solver {
    private class Node {
        WorldState state;
        int cost;
        int heuristic;
        Node previous;
        Node(WorldState s, int c, int h, Node p)  {
            state = s;
            cost = c;
            heuristic = h;
            previous = p;
        }
    }
    private class myComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.cost + o1.heuristic - o2.cost - o2.heuristic;
        }
    }
    private int move;
    private ArrayList<WorldState> path = new ArrayList<>();
    private class Itr implements Iterable<WorldState> {
        private class reverseIterator implements Iterator<WorldState> {
            int curr = path.size() - 1;
            @Override
            public boolean hasNext() {
                return curr >= 0;
            }
            @Override
            public WorldState next() {
                curr -= 1;
                return path.get(curr + 1);
            }
        }
        @Override
        public Iterator<WorldState> iterator() {
            return new reverseIterator();
        }
    }

    public Solver(WorldState initial) {
        MinPQ<Node> q = new MinPQ<>(new myComparator());
        Set<WorldState> visited = new HashSet<>();
        Node start = new Node(initial, 0, initial.estimatedDistanceToGoal(), null);
        q.insert(start);
        visited.add(start.state);
        while (!q.isEmpty()) {
            Node curr = q.delMin();
            //System.out.println("pop: " + curr.state + "," + curr.cost + "," + curr.heuristic);
            if (curr.state.isGoal()) {
                while (curr != null) {
                    path.add(curr.state);
                    curr = curr.previous;
                    move += 1;
                }
                return;
            } else {
                for (WorldState w : curr.state.neighbors()) {
                    if (!visited.contains(w)) {
                        visited.add(w);
                        Node nextNode = new Node(w, curr.cost + 1, w.estimatedDistanceToGoal(), curr);
                        //System.out.println("push: " + nextNode.state + "," + nextNode.cost + "," + nextNode.heuristic);
                        q.insert(nextNode);
                    }
                }
            }
        }
    }
    public int moves() {
        return move - 1;
    }
    public Iterable<WorldState> solution() {
        return new Itr();
    }
}
