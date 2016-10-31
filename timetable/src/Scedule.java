import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.jacop.constraints.In;
import org.jacop.core.IntVar;
import org.jacop.core.Store;
import org.jacop.search.DepthFirstSearch;
import org.jacop.search.IndomainMin;
import org.jacop.search.InputOrderSelect;
import org.jacop.search.Search;
import org.jacop.search.SelectChoicePoint;

import enums.Room;
import enums.Subject;
import static enums.Room.*;
import static enums.Subject.*;


public class Scedule {
	public static int LESSONS_PER_WEEK = 30;
	public static int LESSONS_PER_DAY = 6;
	private ArrayList<Group> groups;
	private Room[] rooms;
	private Store store;
	public static HashMap<Subject, Integer> mapLessonIndexForRooms;
	
	public Scedule() {
		store = new Store();
		groups = new ArrayList<Group>();
	}
	
	public void generate() {
		IntVar[] arr = getGroupsArray();
		shuffleArray(arr);
		
		Search<IntVar> search = new DepthFirstSearch<IntVar>(); 
        SelectChoicePoint<IntVar> select = 
            new InputOrderSelect<IntVar>(store, arr, 
                                         new IndomainMin<IntVar>()); 
        boolean result = search.labeling(store, select); 
	}
	
	public String toString() {
		String res = "";
		for (Group g: groups)
			res += g + "\n";
		return res;
	}
	
	public static <T> void shuffleArray  (T[] ar)
	{
		Random rnd = ThreadLocalRandom.current();
		for (int i = ar.length - 1; i > 0; i--)
		{
			 int index = rnd.nextInt(i + 1);
			 T a = ar[index];
			 ar[index] = ar[i];
			 ar[i] = a;
		}
	}
	
	public void addGroup (Group group) {
		groups.add(group);
	}

	public Store getStore() {
		return store;
	}

	public Room[] getRooms() {
		return rooms;
	}
	
	
	public void setRooms(Room[] rooms) {
		this.rooms = rooms;
	}

	public IntVar[] getGroupsArray () {
		IntVar[] groupsIntVars = new IntVar[0];
		
		for (Group g: groups) {
			groupsIntVars = concatenate(groupsIntVars, g.getSubjectsWhen());
			groupsIntVars = concatenate(groupsIntVars, g.getSubjectsWhere());
		}
		return groupsIntVars;
	}
	
	
	public ArrayList<Group> getGroups() {
		return groups;
	}

	public static <T> T[] concatenate (T[] a, T[] b) {
	    int aLen = a.length;
	    int bLen = b.length;

	    @SuppressWarnings("unchecked")
	    T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen+bLen);
	    System.arraycopy(a, 0, c, 0, aLen);
	    System.arraycopy(b, 0, c, aLen, bLen);

	    return c;
	}
	
	public void show () {
		System.out.print(this);
	}
	
	public static void main (String... args) {
		Scedule scedule = new Scedule();
		Subject[] subjects_primath_4 =  {	JAVA_SCRIPT_P,	
											HELL_OF_MYKHAILEVYCH_P,
											ARTIFICIAL_INTELLIGENCE_P,
											ARTIFICIAL_INTELLIGENCE_L,  
											HISTORY_OF_UKRAINIAN_CULTURE_P, 
											THEORY_OF_MANAGEMENT_L, 
											THEORY_OF_MANAGEMENT_P, 
											HELL_OF_MYKHAILEVYCH_L, 
											VIRUSOLOGY_L,
											VIRUSOLOGY_P,
											HISTORY_OF_UKRAINIAN_CULTURE_L,
											JAVA_SCRIPT_L	};
		scedule.setRooms(Room.values());
		new Group("primath-4", subjects_primath_4, scedule);
		new Group("primath-3", subjects_primath_4, scedule);
		new Group("primath-2", subjects_primath_4, scedule);
		new Group("primath-1", subjects_primath_4, scedule);
		//scedule.setSubjectRoomConstraint(Subject.);
		//group.addMainConstraints();
		scedule.generate();
		scedule.show();
	}
	
}
