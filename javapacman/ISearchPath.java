import java.util.List;


public interface ISearchPath {

	void startPacmanCoords(int x, int y);
	
	void pelletCoords(int x, int y);
	
	List<Step> getPath(int[][] world);
	
}
