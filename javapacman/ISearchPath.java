import java.util.List;


public interface ISearchPath {
	
	List<Cell> getPath(int[][] world, int xPlayer, int yPlayer, int xPellet, int yPellet);
	
}
