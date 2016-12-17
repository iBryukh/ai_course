import java.util.ArrayList;
import java.util.List;


public interface IAntagoSearchPath {
	
	List<ArrayList<Cell>> getPath(int[][] world, int xPlayer, int yPlayer, int xGhost, int yGhost);
	
}
