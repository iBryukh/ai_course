import java.util.*;

/**
 * Created by oleh_kurpiak on 02.10.2016.
 */
public class SearchPathImpl {

    private static final int WALL = 0;

    public static final ISearchPath BREADTH_FIRST_SEARCH = new ISearchPath() {
        @Override
        public List<Cell> getPath(int[][] world, int xPlayer, int yPlayer, int xPellet, int yPellet) {
            System.out.println("Player: " +xPlayer + ", " + yPlayer);
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
        public List<Cell> getPath(int[][] world, int xPlayer, int yPlayer, int xPellet, int yPellet) {
            System.out.println("Player: " +xPlayer + ", " + yPlayer);
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
        int[] res = new int[2];
        for(int i = 0; i < array.length; ++i){
            for(int j = 0; j < array[i].length; ++j){
                if(array[i][j] == val){
                    res[0] = i;
                    res[1] = j;

                    break;
                }
            }
        }

        return res;
    }
}
