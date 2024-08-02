import java.util.*;

public class Question3 {

    private static final int BOARD_SIZE = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of soldiers: ");
        int numSoldiers = scanner.nextInt();

        Set<Coordinate> soldiers = new HashSet<>();
        for (int i = 0; i < numSoldiers; i++) {
            System.out.print("Enter coordinates for soldier " + (i + 1) + ": ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            soldiers.add(new Coordinate(x, y));
        }

        System.out.print("Enter the coordinates for your “special” castle: ");
        int castleX = scanner.nextInt();
        int castleY = scanner.nextInt();
        Coordinate castle = new Coordinate(castleX, castleY);

        List<List<Move>> paths = findPaths(castle, soldiers);

        System.out.println("\nThanks. There are " + paths.size() + " unique paths for your ‘special_castle’\n");

        for (int idx = 0; idx < paths.size(); idx++) {
            System.out.println("Path " + (idx + 1) + ":\n" + "=".repeat(7));
            System.out.println("Start: " + castle);
            for (Move move : paths.get(idx)) {
                System.out.println("Kill " + move.coordinate + ". Turn " + move.direction);
            }
            System.out.println("Arrive " + castle + "\n");
        }
        scanner.close();
    }

    private static List<List<Move>> findPaths(Coordinate castle, Set<Coordinate> soldiers) {
        List<List<Move>> pathHistory = new ArrayList<>();
        move(castle, soldiers, 'R', new ArrayList<>(), pathHistory, castle);
        return pathHistory;
    }

    private static void move(Coordinate position, Set<Coordinate> soldiers, char direction,
                             List<Move> path, List<List<Move>> pathHistory, Coordinate castle) {
        int[][] deltas = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        char[] directions = {'R', 'D', 'L', 'U'};
        Map<Character, Integer> directionMap = Map.of('R', 0, 'D', 1, 'L', 2, 'U', 3);

        int x = position.x, y = position.y;
        int dx = deltas[directionMap.get(direction)][0];
        int dy = deltas[directionMap.get(direction)][1];

        while (0 <= x + dx && x + dx < BOARD_SIZE && 0 <= y + dy && y + dy < BOARD_SIZE) {
            x += dx;
            y += dy;
            Coordinate nextPosition = new Coordinate(x, y);

            if (soldiers.contains(nextPosition)) {
                Set<Coordinate> newSoldiers = new HashSet<>(soldiers);
                newSoldiers.remove(nextPosition);
                List<Move> newPath = new ArrayList<>(path);
                newPath.add(new Move(nextPosition, direction));
                pathHistory.add(newPath);

                // Handle jumps
                while (0 <= x + dx && x + dx < BOARD_SIZE && 0 <= y + dy && y + dy < BOARD_SIZE) {
                    x += dx;
                    y += dy;
                    if (soldiers.contains(new Coordinate(x, y))) break;
                }

                char nextDirection = directions[(directionMap.get(direction) + 1) % 4];
                move(new Coordinate(x, y), newSoldiers, nextDirection, newPath, pathHistory, castle);
            }
            if (nextPosition.equals(castle)) {
                return;
            }
        }
    }
}

class Coordinate {
    int x, y;

    Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Move {
    Coordinate coordinate;
    char direction;

    Move(Coordinate coordinate, char direction) {
        this.coordinate = coordinate;
        this.direction = direction;
    }
}
