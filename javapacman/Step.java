import java.util.*;


public class Step {

	private Character direction;
	
	private int x;
	
	private int y;
	
	private List<Step> parents;

	
	public Step(Character direction, int x, int y, List<Step> parents) {
		this.direction = direction;
		this.x = x;
		this.y = y;
		this.parents = parents;
	}

	public Character getDirection() {
		return direction;
	}

	public void setDirection(Character direction) {
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public List<Step> getParents() {
		return parents;
	}

	public void setParents(List<Step> parents) {
		this.parents = parents;
	}
	
	
}
