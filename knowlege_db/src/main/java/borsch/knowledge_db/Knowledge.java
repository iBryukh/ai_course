package borsch.knowledge_db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.util.*;

/**
 * Created by oleh_kurpiak on 13.09.2016.
 */
public class Knowledge {

    private List<KnowledgeObject> objects;

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Knowledge(){
        this.objects = new ArrayList<KnowledgeObject>();
    }

    public Knowledge(String pathToFile){
        this();
        String json = FileUtils.read(pathToFile);
        if(!json.isEmpty()) {
            List<LinkedTreeMap> list = gson.fromJson(json, List.class);
            if(list != null && !list.isEmpty()){
                for(LinkedTreeMap map : list){
                    json = gson.toJson(map);
                    this.objects.add(gson.fromJson(json, KnowledgeObject.class));
                }
            }
        }
    }

    public void saveToFile(String pathToFIle){
        String json = gson.toJson(this.objects);
        FileUtils.write(pathToFIle, json);
    }

    public List<KnowledgeObject> getObjects() {
        return objects;
    }

    public void addObject(KnowledgeObject object){
        this.objects.add(object);
    }

    public void setObjects(List<KnowledgeObject> objects) {
        this.objects = objects;
    }

    public KnowledgeObject getByName(String name){
        for(KnowledgeObject o : objects)
            if(o.getName().equalsIgnoreCase(name))
                return o;

        return null;
    }

    public Map<Integer, Set<KnowledgeObject>> getRelated(String name){
        Map<Integer, Set<KnowledgeObject>> similar = new HashMap<Integer, Set<KnowledgeObject>>();
        KnowledgeObject object = getByName(name);
        if(object == null)
            return similar;

        for(int i = 0; i <= KnowledgeObject.NUMBER_OF_FIELDS_TO_COMPARE; ++i){
            similar.put(i, new HashSet<KnowledgeObject>());
        }

        // how many similar fields object contains
        int countOfSimilarFields = 0;

        for(KnowledgeObject o : this.objects){
            if(o.getName().equalsIgnoreCase(object.getName()))
                continue;

            countOfSimilarFields = 0;

            if(hasSimilarValues(o.getAlive(), object.getAlive()))
                ++countOfSimilarFields;

            if(hasSimilarValues(o.getColors(), object.getColors()))
                ++countOfSimilarFields;

            if(hasSimilarValues(o.getForms(), object.getForms()))
                ++countOfSimilarFields;

            if(hasSimilarValues(o.getSizes(), object.getSizes()))
                ++countOfSimilarFields;

            if(hasSimilarValues(o.getTypes(), object.getTypes()))
                ++countOfSimilarFields;

            if(countOfSimilarFields > 0){
                Set<KnowledgeObject> set = similar.get(countOfSimilarFields);
                set.add(o);
                similar.put(countOfSimilarFields, set);
                System.out.println(countOfSimilarFields);
            }
        }

        return similar;
    }

    private <T> boolean hasSimilarValues(List<T> list1, List<T> list2){
        if(list1 == null || list2 == null)
            return false;
        Set<T> set = new HashSet<T>();
        set.addAll(list1);
        set.addAll(list2);

        return set.size() < list1.size() + list2.size();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Knowledge{");
        sb.append("objects=").append(objects);
        sb.append('}');
        return sb.toString();
    }
}
