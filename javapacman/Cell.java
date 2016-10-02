import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleh_kurpiak on 02.10.2016.
 */
public class Cell {

    int i;

    int j;

    List<Cell> parents;

    public Cell(int i, int j, List<Cell> parents) {
        this.i = i;
        this.j = j;
        if(parents != null){
            this.parents = new ArrayList<>();
            for(Cell c : parents){
                this.parents.add(c);
            }
        }
    }

    public Cell add(Cell c){
        if(parents == null)
            parents = new ArrayList<>();
        parents.add(c);

        return this;
    }

    @Override
    public String toString() {
        return "(" + i + ", " + j + ")";
    }
}
