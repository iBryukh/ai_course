import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleh_kurpiak on 02.10.2016.
 */
public class Main {

    public static int [][] initState = {{1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1},
            {0, 0, 1, 0, 0, 0, 1, 1, 2, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1},
            {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    public static void main(String[] args){
        int x = 4;
        int y = 1;
        System.out.println("BFS");
        List<Cell> path = SearchPathImpl.GREEDY_SEARCH.getPath(initState, x, y);
        System.out.println(path);
        print2dArray(apply(path));

        System.out.println("DFS");
        //path = SearchPathImpl.DEPTH_FIRST_SEARCH.getPath(initState, x, y);
        //print2dArray(apply(path));
    }

    private static String[][] apply(List<Cell> path){
        String[][] array = copy(initState);
        if(path == null)
            return array;


        for(int i = 0; i < path.size(); ++i){
            Cell c = path.get(i);
            if(i == 0){
                array[c.i][c.j] = "*";
            } else if(array[c.i][c.j].equals("|") || array[c.i][c.j].equals("-")){
                array[c.i][c.j] = "+";
            } else if(i == path.size() - 1){
                array[c.i][c.j] = "$";
            } else {
                Cell p = path.get(i-1);
                if(Math.abs(p.i - c.i) != 0){
                    array[c.i][c.j] = "-";
                } else if(Math.abs(p.j - c.j) != 0){
                    array[c.i][c.j] = "|";
                }
            }
        }
        return array;
    }


    private static String[][] copy(int[][] array){
        String[][] res = new String[array.length][array[0].length];
        for(int i = 0; i < array.length; ++i){
            for(int j = 0; j < array[i].length; ++j){
                res[i][j] = String.valueOf(array[i][j]);
            }
        }
        return res;
    }

    public static void print2dArray(String[][] array){
        for(int i = 0; i < array.length; ++i){
            for(int j = 0; j < array[i].length; ++j){
                System.out.print(array[j][i] + " ");
            }
            System.out.println();
        }
    }
    
    public static List<Character> convertPath (List<Cell> path) {
    	if (path==null || path.size()==0)
    		return null;
    	ArrayList<Character> result = new ArrayList<>(); 
        for (int i = 0; i < path.size()-1; i++) {
        	result.add(detectDirection(path.get(i), path.get(i+1)));
        }
        System.out.println();
		return result;
    }
    
    public static char detectDirection (Cell from, Cell to) {
    	if (from.equals(to))
    		return 'O';
		if (from.i > to.i) {
			return 'L';
		} else if (from.i < to.i) {
			return 'R';
		}
		if (from.j > to.j) {
			return 'U';
		} else {
			return 'D';
		}
    }
}
