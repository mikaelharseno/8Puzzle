package hw3.puzzle;
import java.util.LinkedList;

public final class Board implements WorldState {
    private final int[][] values;
    private final int size;
    public Board(int[][] tiles) {
        int a = tiles.length;
        values = new int[a][a];
        for (int i = 0; i < a; i = i + 1) {
            for (int j = 0; j < a; j = j + 1) {
                values[i][j] = tiles[i][j];
            }
        }
        size = a;
    }
    public int tileAt(int i, int j) {
        if (i > size - 1 || i < 0 || j > size - 1 || j < 0) {
            throw new IndexOutOfBoundsException();
        }
        return values[i][j];
    }
    public int size() {
        return size;
    }
    private int[][] copy() {
        int[][] a = new int[size][size];
        for (int i = 0; i < size; i = i + 1) {
            for (int j = 0; j < size; j = j + 1) {
                a[i][j] = values[i][j];
            }
        }
        return a;
    }
    public Iterable<WorldState> neighbors() {
        LinkedList<WorldState> neighbor = new LinkedList<>();
        int iZero = zeroLoc()[0];
        int jZero = zeroLoc()[1];
        int[][] curr;
        int temp;
        if (iZero > 0) {
            curr = copy();
            temp = curr[iZero - 1][jZero];
            curr[iZero - 1][jZero] = 0;
            curr[iZero][jZero] = temp;
            neighbor.addFirst(new Board(curr));
        }
        if (iZero < size - 1) {
            curr = copy();
            temp = curr[iZero + 1][jZero];
            curr[iZero + 1][jZero] = 0;
            curr[iZero][jZero] = temp;
            neighbor.addFirst(new Board(curr));
        }
        if (jZero > 0) {
            curr = copy();
            temp = curr[iZero][jZero - 1];
            curr[iZero][jZero - 1] = 0;
            curr[iZero][jZero] = temp;
            neighbor.addFirst(new Board(curr));
        }
        if (jZero < size - 1) {
            curr = copy();
            temp = curr[iZero][jZero + 1];
            curr[iZero][jZero + 1] = 0;
            curr[iZero][jZero] = temp;
            neighbor.addFirst(new Board(curr));
        }
        return neighbor;
    }

    private int[] zeroLoc() {
        int[] res;
        for (int i = 0; i < size; i = i + 1) {
            for (int j = 0; j < size; j = j + 1) {
                if (values[i][j] == 0) {
                    res = new int[2];
                    res[0] = i;
                    res[1] = j;
                    return res;
                }
            }
        }
        return null;
    }

    public int hamming() {
        int ham = 0, a = 0;
        for (int i = 0; i < size; i = i + 1) {
            for (int j = 0; j < size; j = j + 1) {
                a = a + 1;
                if (values[i][j] == a || values[i][j] == 0) {
                    ham = ham + 0;
                } else {
                    ham = ham + 1;
                }
            }
        }
        return ham;
    }
    public int manhattan() {
        int man = 0, num;
        int[] goal;
        for (int i = 0; i < size; i = i + 1) {
            for (int j = 0; j < size; j = j + 1) {
                num = values[i][j];
                goal = goaltile(num);
                if (num != 0) {
                    man = man + Math.abs(goal[0] - i) + Math.abs(goal[1] - j);
                }
            }
        }
        return man;
    }

    private int[] goaltile(int num) {
        int[] a = new int[2];
        a[0] = (num - 1) / size;
        a[1] = (num - 1) % size;
        return a;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    @Override
    public int hashCode() {
        return 111;
    }

    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board ar = (Board) y;
        if (ar.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size; i = i + 1) {
            for (int j = 0; j < size; j = j + 1) {
                if (ar.tileAt(i, j) != values[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }



    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
