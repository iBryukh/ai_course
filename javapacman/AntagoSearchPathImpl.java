import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AntagoSearchPathImpl {
	public static final IAntagoSearchPath MINI_MAX_SEARCH = new IAntagoSearchPath() {
		
		@Override
		public List<ArrayList<Cell>> getPath(int[][] world, int xPlayer, int yPlayer,
				int xGhost, int yGhost) {
			
			Cell player = new Cell(xPlayer, yPlayer, null);
			Cell ghost = new Cell(xGhost, yGhost, null);
			
			List<ArrayList<Cell>> res = new ArrayList<ArrayList<Cell>>();
			res.add(new ArrayList<Cell>());
			res.add(new ArrayList<Cell>());
			res.get(0).add(player);
			res.get(1).add(ghost);
			int i = 0;
			while (i<100 && !isTerminalState(world, player, ghost, 2)) {
				Cell[] cells = max(world, player, ghost, 2);
				cells[0].parents.remove(0);
				cells[1].parents.remove(0);
				res.get(0).addAll((ArrayList<Cell>) cells[0].add(cells[0]).parents);
				res.get(1).addAll((ArrayList<Cell>) cells[1].add(cells[1]).parents);
				player = cells[0];
				ghost = cells[1];
				i++;
			}
			boolean b = isTerminalState(world, player, ghost, 2);
			return res;
		}
		
		private Cell[] max (int[][] world, Cell player, Cell ghost, int depth) {
			Cell[] max = null;
			for (Cell c:SearchPathImpl.getChildren(world, player)) {
				Cell[] temp = new Cell[] {c, ghost};
				if (!isTerminalState(world, c, ghost, depth)) {
					temp = min(world, c, ghost, depth-1);
				}
				temp[0].value = score(world, temp[0], temp[1]);
				if (max==null || temp[0].value>max[0].value) {
					max = temp;
				}
			}	
			return max;
		}
		
		private Cell[] min (int[][] world, Cell player, Cell ghost, int depth) {
			Cell[] min = null;
			for (Cell c:SearchPathImpl.getChildren(world, ghost)) {
				Cell[] temp = new Cell[] {player, c};
				if (!isTerminalState(world, player, c, depth)) {
					temp = min(world, player, c, depth-1);
				}
				temp[1].value = score(world, temp[0], temp[1]);
				if (min==null || temp[1].value<min[1].value) {
					min = temp;
				}
			}	
			return min;
		}
		
		private boolean isTerminalState (int[][] world, Cell player, Cell ghost, int depth) {
			if (depth<=1)
				return true;
			if (player.equals(ghost))
				return true;
			int[] pellet = SearchPathImpl.getPosition(world, 2);
			if (player.i==pellet[0] && player.j==pellet[1])
				return true;
			return false;
		}
		
		private int score (int[][] world, Cell player, Cell ghost) {
			if (player.equals(ghost))
				return -100;
			int[] pellet = SearchPathImpl.getPosition(world, 2);
			if (player.i==pellet[0] && player.j==pellet[1])
				return 100;
			return mark(world, player, ghost);
		}
		
		private int mark(int[][] world, Cell a, Cell b){
			int[] pellet = SearchPathImpl.getPosition(world, 2);
			int distToPellet = (Math.abs(a.i-pellet[0]) + Math.abs(a.j-pellet[1]));
			int distBetweenThem = 10*(Math.abs(a.i-b.i) + Math.abs(a.j-b.j));
            return (distBetweenThem - distToPellet);
        }
	};
}
