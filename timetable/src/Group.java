import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.jacop.constraints.Alldiff;
import org.jacop.constraints.IfThen;
import org.jacop.constraints.XeqC;
import org.jacop.constraints.XeqY;
import org.jacop.constraints.XgtY;
import org.jacop.constraints.XneqC;
import org.jacop.constraints.XneqY;
import org.jacop.core.IntVar;
import org.jacop.core.Store;

import enums.Room;
import enums.Subject;
import static java.lang.System.out;

public class Group {

	private HashMap<Subject, IntVar> mapSubjectRoom;
	private HashMap<Subject, IntVar> mapSubjectTime;
	private IntVar[] subjectsWhere;
	private IntVar[] subjectsWhen;
	private String name;
	private Store store;
	private Scedule scedule;
	
	public Group(String name, Subject[] subjects, Scedule scedule) {
		this.name = name;
		this.scedule = scedule;
		
		subjectsWhen = new IntVar[subjects.length];
		subjectsWhere = new IntVar[subjectsWhen.length];
		
		store = scedule.getStore();
		
		mapSubjectTime = new HashMap<>();
        for (int i=0; i<subjectsWhen.length; i++) {
            subjectsWhen[i] = new IntVar(store, subjects[i].name(), 1, Scedule.LESSONS_PER_WEEK);
        	mapSubjectTime.put(subjects[i], subjectsWhere[i]);
		}
	
        mapSubjectRoom = new HashMap<>();
        for (int i=0; i<subjectsWhere.length; i++) {
            subjectsWhere[i] = new IntVar(store, subjects[i].name(), 0, scedule.getRooms().length-1);
            mapSubjectRoom.put(subjects[i], subjectsWhere[i]);
        }
        
        addMainConstraints();
        addRoomConstraints();
        addSubjectRoomConstraint();
        scedule.addGroup(this);
	}

	public void addMainConstraints() {
        store.impose(new Alldiff(subjectsWhen));
        
        for (int i = 0; i < subjectsWhen.length; i++) {
        	String name = subjectsWhen[i].id;
        	if (name.endsWith("_L")) {
        		for (int j = 0; j < subjectsWhen.length; j++) {
        			if (subjectsWhen[j].id.endsWith("_P")) {
	        			String newname = name.substring(0, name.length()-2) + "_P";
	        			if (newname.equals(subjectsWhen[j].id)) {
	        				//lection earlier then practice
	        				store.impose(new XgtY(subjectsWhen[j], subjectsWhen[i]));
	        				//lection and practice in the same room
	        				store.impose(new XeqY(
	        						getVarRoomBySubjectString(subjectsWhen[j].id), 
	        						getVarRoomBySubjectString(subjectsWhen[i].id)));
	        			}
        			}
        		}
        	}
        }
	}
	
	public void addRoomConstraints () {
		//this group have a constraint with all elements in other groups
		for (Group g: scedule.getGroups()) {
			IntVar[] other = g.getSubjectsWhen();
			for (int i = 0; i < other.length; i++) {
				for (int j = 0; j < subjectsWhen.length; j++) {
					//if group1time==group2time -> they should be in different rooms
					store.impose(new IfThen(
							new XeqY(other[i], subjectsWhen[j]), 
							new XneqY(g.getVarRoomBySubjectString(other[i].id), 
										getVarRoomBySubjectString(subjectsWhen[j].id))));
				}
			}
		}
	}
	
	public void addSubjectRoomConstraint () {
		store.impose(new XneqC(getVarRoomBySubject(Subject.JAVA_SCRIPT_L), Room._1_225.ordinal()));
		store.impose(new XeqC(getVarRoomBySubject(Subject.HELL_OF_MYKHAILEVYCH_L), Room._1_310.ordinal()));
		store.impose(new XeqC(getVarRoomBySubject(Subject.THEORY_OF_MANAGEMENT_L), Room._3_302.ordinal()));
		store.impose(new XneqC(getVarRoomBySubject(Subject.JAVA_SCRIPT_L), Room._3_302.ordinal()));
	}
	
	public String getName() {
		return name;
	}

	public IntVar[] getSubjectsWhere() {
		return subjectsWhere;
	}

	public IntVar[] getSubjectsWhen() {
		return subjectsWhen;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		Arrays.sort(subjectsWhen, 0, subjectsWhen.length, new Comparator<IntVar>() {

			@Override
			public int compare(IntVar o1, IntVar o2) {
				return o1.value() - o2.value();
			}
    		
		});
		
		String res =  "Group " + name + "\n";
		
		int real = 1;
		for (IntVar v:subjectsWhen) {
			while (real < v.value()) {
				int realn = real%Scedule.LESSONS_PER_DAY;
				if (realn==1) res += "\n";
				if (realn==0) realn = Scedule.LESSONS_PER_DAY;
				res += realn + "\n";
				real++;	
			}
			int n = v.value()%Scedule.LESSONS_PER_DAY;
			if (n==0) n = Scedule.LESSONS_PER_DAY;
			if (n==1) res += "\n";
			res += n + " " +  v.id + " "  + getRoomBySubject(v.id) + "\n";
			real++;
		}
		return res;
	}
	
	private Room getRoomBySubject (String s) {
		return Room.values()[getVarRoomBySubjectString(s).value()];
	}
	
	private IntVar getVarRoomBySubjectString (String s) {
		return getVarRoomBySubject(Subject.valueOf(s));
	}

	private IntVar getVarRoomBySubject (Subject s) {
		return mapSubjectRoom.get(s);
	}
	
	public IntVar getVarTimeBySubject (Subject s) {
		return mapSubjectTime.get(s);
	}
	
	public IntVar getVarTimeBySubjectString (String s) {
		return getVarTimeBySubject(Subject.valueOf(s));
	}

}
