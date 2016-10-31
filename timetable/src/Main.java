import java.util.Arrays;

import org.jacop.core.*; 
import org.jacop.constraints.*;
import org.jacop.search.*; 
 
public class Main { 
	
	public static int LESSONS_PER_WEEK = 24;
	public static int LESSONS_PER_DAY = 6;
 
    public static void main (String[] args) { 
        Store store = new Store();
        int size = Subject.values().length-1; 
        IntVar[] v = new IntVar[LESSONS_PER_WEEK]; 
        for (int i=0; i<size; i++) 
            v[i] = new IntVar(store, Subject.values()[i].name(), 1, LESSONS_PER_WEEK);
        
        for (int i=size; i < LESSONS_PER_WEEK; i++) {
        	v[i] = new IntVar(store, Subject.values()[size].name(), 1, LESSONS_PER_WEEK);
        }
        
        IntVar temp1 = new IntVar(store, "temp", 1, 1);
        IntVar temp2 = new IntVar(store, "temp", 2, 2);
        IntVar temp3 = new IntVar(store, "temp", 3, 3);
        IntVar temp4 = new IntVar(store, "temp", 4, 4);
        
        for (int i=0; i < size; i++) {
        	for (int j = 0; j < LESSONS_PER_WEEK/LESSONS_PER_DAY; j++) {
	        	store.impose(new XneqC(v[i], 1 + j*LESSONS_PER_DAY));
	        	store.impose(new XneqC(v[i], 5 + j*LESSONS_PER_DAY));
	        	store.impose(new XneqC(v[i], 6 + j*LESSONS_PER_DAY));
        	}
        }
        
        for (int i = 0; i < v.length; i++) {
        	String name = v[i].id;
        	if (name.endsWith("_L")) {
        		for (int j = 0; j < v.length; j++) {
        			if (v[j].id.endsWith("_P")) {
	        			String newname = name.substring(0, name.length()-2) + "_P";
	        			if (newname.equals(v[j].id)) {
	        				store.impose(new XgtY(v[j], v[i]));
	        				store.impose(new XgtY(v[j], v[i]));
	        				PrimitiveConstraint[] c = {new Distance(v[j], v[i], temp1), new Distance(v[j], v[i], temp2), new Distance(v[j], v[i], temp3), new Distance(v[j], v[i], temp4)};
	        				store.impose(new Or(c));
	        			}
        			}
        		}
        	}
        }
        
        store.impose(new Alldiff(v));
 
        Search<IntVar> search = new DepthFirstSearch<IntVar>(); 
        SelectChoicePoint<IntVar> select = 
            new InputOrderSelect<IntVar>(store, v, 
                                         new IndomainMin<IntVar>()); 
        boolean result = search.labeling(store, select); 
 
        if ( result ) 
            System.out.println("Solution:\n" + Arrays.toString(v).replaceAll(", ", ",\n\t").replaceAll("[\\[\\]]", "\t"));
        else 
            System.out.println("*** No"); 
    } 
}