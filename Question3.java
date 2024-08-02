import java.util.*;

public class Question1 {

    private static final int SIZE = 10;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter the number of soldiers: ");
        int numSoldiers = in.nextInt();
        in.nextLine();  // consume the newline

        Set<Pos> soldiers = new HashSet<>();
        for (int i = 0; i < numSoldiers; i++) {
            System.out.print("Enter coordinates for soldier " + (i + 1) + ": ");
            String[] coords = in.nextLine().split(",");
            int x = Integer.parseInt(coords[0].trim());
            int y = Integer.parseInt(coords[1].trim());
            soldiers.add(new Pos(x, y));
        }

        System.out.print("Enter the coordinates for your “special” castle: ");
        String[] castleCoords = in.nextLine().split(",");
        int cx = Integer.parseInt(castleCoords[0].trim());
        int cy = Integer.parseInt(castleCoords[1].trim());
        Pos castle = new Pos(cx, cy);

        List<List<Step>> paths = getPaths(castle, soldiers);

        System.out.println("\nThanks. There are " + paths.size() + " unique paths for your ‘special_castle’\n");

        for (int i = 0; i < paths.size(); i++) {
            System.out.println("Path " + (i + 1) + ":\n" + "=".repeat(7));
            System.out.println("Start: " + castle);
            for (Step step : paths.get(i)) {
                System.out.println("Kill " + step.pos + ". Turn " + step.dir);
            }
            System.out.println("Arrive " + castle + "\n");
        }
        in.close();
    }

    private static List<List<Step>> getPaths(Pos castle, Set<Pos> soldiers) {
        List<List<Step>> allPaths = new ArrayList<>();
        findPath(castle, soldiers, 'R', new ArrayList<>(), allPaths, castle);
        return allPaths;
    }

    private static void findPath(Pos pos, Set<Pos> soldiers, char dir, List<Step> path, List<List<Step>> allPaths, Pos castle) {
        int[][] moves = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        char[] directions = {'R', 'D', 'L', 'U'};
        Map<Character, Integer> dirMap = Map.of('R', 0, 'D', 1, 'L', 2, 'U', 3);

        int x = pos.x, y = pos.y;
        int dx = moves[dirMap.get(dir)][0];
        int dy = moves[dirMap.get(dir)][1];

        while (0 <= x + dx && x + dx < SIZE && 0 <= y + dy && y + dy < SIZE) {
            x += dx;
            y += dy;
            Pos newPos = new Pos(x, y);

            if (soldiers.contains(newPos)) {
                Set<Pos> newSoldiers = new HashSet<>(soldiers);
                newSoldiers.remove(newPos);
                List<Step> newPath = new ArrayList<>(path);
                newPath.add(new Step(newPos, dir));
                allPaths.add(newPath);

                // Handle jumps
                while (0 <= x + dx && x + dx < SIZE && 0 <= y + dy && y + dy < SIZE) {
                    x += dx;
                    y += dy;
                    if (soldiers.contains(new Pos(x, y))) break;
                }

                char nextDir = directions[(dirMap.get(dir) + 1) % 4];
                findPath(new Pos(x, y), newSoldiers, nextDir, newPath, allPaths, castle);
            }
            if (newPos.equals(castle)) {
                return;
            }
        }
    }
}

class Pos {
    int x, y;

    Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x && y == pos.y;
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

class Step {
    Pos pos;
    char dir;

    Step(Pos pos, char dir) {
        this.pos = pos;
        this.dir = dir;
    }
}
