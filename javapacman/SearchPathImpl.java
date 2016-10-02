import java.util.*;

/**
 * Created by oleh_kurpiak on 02.10.2016.
 */
public class SearchPathImpl {

    private static final int WALL = 0;

    public static final ISearchPath BREADTH_FIRST_SEARCH = new ISearchPath() {
        @Override
        public List<Cell> getPath(int[][] world, int xPlayer, int yPlayer) {
        	int[] point = getPosition(world, 2);
            if(point == null)
                return null;

        	int xPellet = point[0];
        	int yPellet = point[1];
            System.out.println("Player: " + xPlayer + ", " + yPlayer);
            System.out.println("Pellet: " + xPellet + ", " + yPellet);
            //print2dArray(world);

            boolean[][] visited = new boolean[world.length][world[0].length];
            visited[xPlayer][yPlayer] = true;

            Queue<Cell> cells = new ArrayDeque<>();
            cells.add(new Cell(xPlayer, yPlayer, null));

            while(!cells.isEmpty()){
                Cell cell = cells.poll();
                if(cell.i == xPellet && cell.j == yPellet){
                    List<Cell> path = new ArrayList<>();
                    if(cell.parents != null){
                        for(Cell c : cell.parents){
                            path.add(c);
                        }
                    }
                    path.add(cell);
                    return path;
                }
                for(Cell c : getChildren(world, cell)){
                    if(!visited[c.i][c.j]){
                        visited[c.i][c.j] = true;
                        cells.add(c);
                    }
                }
            }

            return null;
        }
    };

    public static final ISearchPath DEPTH_FIRST_SEARCH = new ISearchPath() {
        @Override
        public List<Cell> getPath(int[][] world, int xPlayer, int yPlayer) {
        	int[] point = getPosition(world, 2);
            if(point == null)
                return null;
        	int xPellet = point[0];
        	int yPellet = point[1];
            System.out.println("Player: " + xPlayer + ", " + yPlayer);
            System.out.println("Pellet: " + xPellet + ", " + yPellet);
            //print2dArray(world);

            boolean[][] visited = new boolean[world.length][world[0].length];
            visited[xPlayer][yPlayer] = true;

            Stack<Cell> cells = new Stack<>();
            cells.add(new Cell(xPlayer, yPlayer, null));

            while(!cells.isEmpty()){
                Cell cell = cells.pop();
                if(cell.i == xPellet && cell.j == yPellet){
                    List<Cell> path = new ArrayList<>();
                    if(cell.parents != null){
                        for(Cell c : cell.parents){
                            path.add(c);
                        }
                    }
                    path.add(cell);
                    return path;
                }
                for(Cell c : getChildren(world, cell)){
                    if(!visited[c.i][c.j]){
                        visited[c.i][c.j] = true;
                        cells.add(c);
                    }
                }
            }

            return null;
        }
    };

    public static final ISearchPath GREEDY_SEARCH = new ISearchPath() {
        boolean[][] visited;
        @Override
        public List<Cell> getPath(int[][] world, int xPlayer, int yPlayer) {
            int[] point = getPosition(world, 2);
            if(point == null)
                return null;

            int xPellet = point[0];
            int yPellet = point[1];
            System.out.println("Player: " + xPlayer + ", " + yPlayer);
            System.out.println("Pellet: " + xPellet + ", " + yPellet);
            //print2dArray(world);

            visited = new boolean[world.length][world[0].length];
            //visited[xPlayer][yPlayer] = true;

            Queue<Cell> cells = new ArrayDeque<>();
            cells.add(new Cell(xPlayer, yPlayer, null));

            while(!cells.isEmpty()){
                Cell cell = getMin(cells.iterator(), xPellet, yPellet);
                if(visited[cell.i][cell.j])
                    continue;

                visited[cell.i][cell.j] = true;
                cells.remove(cell);
                if(cell.i == xPellet && cell.j == yPellet){
                    List<Cell> path = new ArrayList<>();
                    if(cell.parents != null){
                        for(Cell c : cell.parents){
                            path.add(c);
                        }
                    }
                    path.add(cell);
                    return path;
                }

                for(Cell c : getChildren(world, cell)){
                    if(!visited[c.i][c.j]){
                        cells.add(c);
                    }
                }
            }

            return null;
        }

        private Cell getMin(Iterator<Cell> iterator, int pelletX, int pelletY){
            Cell result = iterator.next();
            int min = mark(result, pelletX, pelletY);
            while(iterator.hasNext()){
                Cell currentCell = iterator.next();
                if(!visited[currentCell.i][currentCell.j]) {
                    int currentMin = mark(currentCell, pelletX, pelletY);
                    if (currentMin < min) {
                        result = currentCell;
                        min = currentMin;
                    }
                }
            }
            return result;
        }

        private int mark(Cell c, int pelletX, int pelletY){
            return Math.abs(c.i - pelletX) + Math.abs(c.j - pelletY);
        }
    };

    private static List<Cell> getChildren(int[][] world, Cell cell){
        List<Cell> result = new ArrayList<>();
        int i = cell.i;
        int j = cell.j;

        // check right cell
        if(i < world.length - 1){
            if(world[i+1][j] != WALL){
                result.add(new Cell(i+1, j, cell.parents).add(cell));
            }
        }
        // check left cell
        if(i > 0){
            if(world[i-1][j] != WALL){
                result.add(new Cell(i-1, j, cell.parents).add(cell));
            }
        }
        // check up cell
        if(j > 0){
            if(world[i][j-1] != WALL){
                result.add(new Cell(i, j-1, cell.parents).add(cell));
            }
        }
        // check down cell
        if(j < world[0].length - 1){
            if(world[i][j+1] != WALL)
                result.add(new Cell(i, j+1, cell.parents).add(cell));
        }

        return result;
    }

    private static void print2dArray(int[][] array){
        for(int i = 0; i < array.length; ++i){
            System.out.print("{");
            for(int j = 0; j < array[i].length; ++j){
                System.out.print(array[j][i] + ", ");
            }
            System.out.print("}\n");
        }
    }

    public static int[] getPosition(int[][] array, int val){
        for(int i = 0; i < array.length; ++i){
            for(int j = 0; j < array[i].length; ++j){
                if(array[i][j] == val){
                    return new int[]{i, j};
                }
            }
        }

        return null;
    }
}
