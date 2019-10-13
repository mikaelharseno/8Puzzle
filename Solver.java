package hw3.puzzle;

import java.util.Iterator;
import java.util.LinkedList;
import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by dendypratamaputra on 3/22/17.
 */
public class Solver {
    private int moves;
    private Iterable<WorldState> a;
    private MinPQ<DataPoint> base;
    public Solver(WorldState initial) {
        base = new MinPQ<>();
        base.insert(new DataPoint(initial, 0, null));
        DataPoint curr = base.delMin();
        boolean goal = curr.getState().isGoal();
        int curMoves = curr.getNumMoves();
        WorldState b;
        while (!goal) {
            if (curr.getPrevState() == null) {
                for (Iterator<WorldState> w = curr.getState()
                        .neighbors().iterator(); w.hasNext(); ) {
                    base.insert(new DataPoint(w.next(), curMoves + 1, curr));
                }
            } else {
                for (Iterator<WorldState> w = curr.getState()
                        .neighbors().iterator(); w.hasNext(); ) {
                    b = w.next();
                    if (!b.equals(curr.getPrevState().getState())) {
                        base.insert(new DataPoint(b, curMoves + 1, curr));
                    }
                }
            }
            curr = base.delMin();
            goal = curr.getState().isGoal();
            curMoves = curr.getNumMoves();
        }
        moves = curr.getNumMoves();
        a = curr;
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return a;
    }

    private class DataPoint implements Comparable<DataPoint>, Iterable<WorldState> {
        private WorldState state;
        private int edtg;
        private int numMoves;
        private DataPoint prevstate;

        DataPoint(WorldState data, int moves, DataPoint prev) {
            state = data;
            numMoves = moves;
            prevstate = prev;
            edtg = data.estimatedDistanceToGoal();
        }

        public WorldState getState() {
            return state;
        }

        public int getNumMoves() {
            return numMoves;
        }

        public DataPoint getPrevState() {
            return prevstate;
        }

        public int estimatedDistanceToGoal() {
            return edtg;
        }

        public int compareTo(DataPoint b) {
            return (numMoves + edtg) - (b.getNumMoves() + b.estimatedDistanceToGoal());
        }

        public Iterator<WorldState> iterator() {
            LinkedList<WorldState> queue = new LinkedList<>();
            DataPoint currPoint = this;
            while (currPoint != null) {
                queue.addFirst(currPoint.getState());
                currPoint = currPoint.getPrevState();
            }
            return queue.iterator();
        }
    }
}
