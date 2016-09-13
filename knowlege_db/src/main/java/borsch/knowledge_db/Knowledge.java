package borsch.knowledge_db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

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
        if(!json.isEmpty())
            this.objects = gson.fromJson(json, List.class);
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Knowledge{");
        sb.append("objects=").append(objects);
        sb.append('}');
        return sb.toString();
    }
}
