import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.jacop.core.*; 
import org.jacop.constraints.*;
import org.jacop.search.*; 

import enums.Room;
import enums.Subject;
 
public class Main { 
	
	public static int LESSONS_PER_WEEK = 30;
	public static int LESSONS_PER_DAY = 6;
	public static HashMap<Subject, Integer> mapLessonIndexForRooms;
	
    public static void main (String[] args) { 
        Store store = new Store();
        int size = Subject.values().length-1; 
        IntVar[] v = new IntVar[LESSONS_PER_WEEK+size]; 
        
        //subjects
        for (int i=0; i<size; i++) 
            v[i] = new IntVar(store, Subject.values()[i].name(), 1, LESSONS_PER_WEEK);
        
        //gaps
        for (int i=size; i < LESSONS_PER_WEEK; i++) {
        	v[i] = new IntVar(store, Subject.values()[size].name(), 1, LESSONS_PER_WEEK);
        }
        
        mapLessonIndexForRooms = new HashMap<Subject, Integer>();
        for (int i=0; i < size; i++) {
        	//one more graph of subjects to search for rooms 
        	v[LESSONS_PER_WEEK+i] = new IntVar(store, Subject.values()[i].name(), 0, Room.values().length);
        	mapLessonIndexForRooms.put(Subject.values()[i], LESSONS_PER_WEEK+i);
        }
        
        //we need them to compare
        IntVar temp1 = new IntVar(store, "temp", 1, 1);
        IntVar temp2 = new IntVar(store, "temp", 2, 2);
        IntVar temp3 = new IntVar(store, "temp", 3, 3);
        IntVar temp4 = new IntVar(store, "temp", 4, 4);
        
        //students don't like 1st, 5th and 6th lessons
        for (int i=0; i < size; i++) {
        	for (int j = 0; j < LESSONS_PER_WEEK/LESSONS_PER_DAY; j++) {
	        	store.impose(new XneqC(v[i], 1 + j*LESSONS_PER_DAY));
	        	store.impose(new XneqC(v[i], 5 + j*LESSONS_PER_DAY));
	        	store.impose(new XneqC(v[i], 6 + j*LESSONS_PER_DAY));
        	}
        }
        
        //lection should be before practice, distance no more than 4
        for (int i = 0; i < LESSONS_PER_WEEK; i++) {
        	String name = v[i].id;
        	if (name.endsWith("_L")) {
        		for (int j = 0; j < LESSONS_PER_WEEK; j++) {
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
        
        store.impose(new Alldiff(Arrays.copyOfRange(v, 0, LESSONS_PER_WEEK)));
        
        //javascript can be just in 10_1
        store.impose(new XeqC(v[mapLessonIndexForRooms.get(Subject.JAVA_SCRIPT_P)], Room._10_1.ordinal()));
        //lection and practice in the same room
        store.impose(new XeqY(v[mapLessonIndexForRooms.get(Subject.JAVA_SCRIPT_L)], v[mapLessonIndexForRooms.get(Subject.JAVA_SCRIPT_P)]));
        //3_220a is to small for lection 
        store.impose(new XneqC(v[mapLessonIndexForRooms.get(Subject.HELL_OF_MYKHAILEVYCH_L)], Room._3_220a.ordinal()));
        // 3_302 is busy
        store.impose(new XneqC(v[mapLessonIndexForRooms.get(Subject.HELL_OF_MYKHAILEVYCH_L)], Room._3_302.ordinal()));
        //lection and practice in the same room
        store.impose(new XeqY(v[mapLessonIndexForRooms.get(Subject.HELL_OF_MYKHAILEVYCH_L)], v[mapLessonIndexForRooms.get(Subject.HELL_OF_MYKHAILEVYCH_P)]));
        //different rooms for this lessons 
        store.impose(new Alldiff(Arrays.copyOfRange(v, mapLessonIndexForRooms.get(Subject.VIRUSOLOGY_L), mapLessonIndexForRooms.get(Subject.VIRUSOLOGY_L)+4)));
        
        
        //search
        Search<IntVar> search = new DepthFirstSearch<IntVar>(); 
        SelectChoicePoint<IntVar> select = 
            new InputOrderSelect<IntVar>(store, v, 
                                         new IndomainMin<IntVar>()); 
        boolean result = search.labeling(store, select); 
 
        if (result) 
        	//printing the solution
        	printSolution(v);
        else 
            System.out.println("*** No"); 
    } 
    
    public static void printSolution (IntVar[] v) {
    	Arrays.sort(v, 0, LESSONS_PER_WEEK, new Comparator<IntVar>() {

			@Override
			public int compare(IntVar o1, IntVar o2) {
				return o1.value() - o2.value();
			}
    		
		});
    	for (int i=0; i < LESSONS_PER_WEEK; i++) {
    		int n = v[i].value()%LESSONS_PER_DAY;
    		if (n==1) {
    			System.out.println();
    		}
    		if (n==0) n = 6;
    		if (!v[i].id.equals(Subject.GAP.name())) {
    			System.out.print(n + "  " +  v[i].id + "  ");
    			System.out.print(Room.values()[v[mapLessonIndexForRooms.get(Subject.valueOf(v[i].id))].value()]);
    		} else {
    			System.out.print(n);
    		}
    		System.out.println();
    	}
    	 //" " + Room.values()[v[i].value()].name())
    	//System.out.println("Solution:\n" + Arrays.toString(v).replaceAll(", ", ",\n\t").replaceAll("[\\[\\]]", "\t"));
    }
}