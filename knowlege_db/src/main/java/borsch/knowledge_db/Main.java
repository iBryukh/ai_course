package borsch.knowledge_db;

import borsch.knowledge_db.enums.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

/**
 * Created by oleh_kurpiak on 13.09.2016.
 */
public class Main {

    public static final String PATH_TO_KNOWLEDGE_FILE = "test.json";

    public static void main(String[] args){
        defaultJSON();
        Knowledge knowledge = new Knowledge(PATH_TO_KNOWLEDGE_FILE);
        Map<Integer, Set<KnowledgeObject>> map = knowledge.getRelated("apple");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileUtils.write("similar.json", gson.toJson(map));
    }


    public static void defaultJSON(){
        Knowledge knowledge = new Knowledge();

        KnowledgeObject object = new KnowledgeObject();
        object.setName("apple");
        object.setAlive(Arrays.asList(Alive.ALIVE, Alive.DIFFICULT_TO_SAY));
        object.setColors(Arrays.asList(Color.ORANGE, Color.RED, Color.YELLOW));
        object.setForms(Arrays.asList(Form.CIRCLE, Form.OVAL));
        object.setSizes(Arrays.asList(Size.SMALL));
        object.setTypes(Arrays.asList(Type.FOOD));
        knowledge.addObject(object);

        object = new KnowledgeObject();
        object.setName("pear");
        object.setAlive(Arrays.asList(Alive.ALIVE, Alive.DIFFICULT_TO_SAY));
        object.setColors(Arrays.asList(Color.ORANGE, Color.RED, Color.YELLOW));
        object.setForms(Arrays.asList(Form.CIRCLE, Form.OVAL));
        object.setSizes(Arrays.asList(Size.SMALL));
        object.setTypes(Arrays.asList(Type.FOOD));
        knowledge.addObject(object);

        object = new KnowledgeObject();
        object.setName("yellow ball");
        object.setAlive(Arrays.asList(Alive.NOT_ALIVE));
        object.setColors(Arrays.asList(Color.YELLOW));
        object.setForms(Arrays.asList(Form.CIRCLE));
        object.setSizes(Arrays.asList(Size.SMALL));
        object.setTypes(Arrays.asList(Type.OTHER));
        knowledge.addObject(object);

        object = new KnowledgeObject();
        object.setName("car");
        object.setAlive(Arrays.asList(Alive.NOT_ALIVE));
        object.setColors(Arrays.asList(Color.values()));
        object.setForms(Arrays.asList(Form.OTHER));
        object.setSizes(Arrays.asList(Size.LARGE, Size.MEDIUM));
        object.setTypes(Arrays.asList(Type.TRANSPORT));
        knowledge.addObject(object);

        object = new KnowledgeObject();
        object.setName("branch");
        object.setAlive(Arrays.asList(Alive.NOT_ALIVE));
        object.setColors(Arrays.asList(Color.RED, Color.WHITE, Color.BLACK));
        object.setForms(Arrays.asList(Form.OTHER));
        object.setSizes(Arrays.asList(Size.LARGE, Size.MEDIUM));
        object.setTypes(Arrays.asList(Type.OTHER));
        knowledge.addObject(object);

        object = new KnowledgeObject();
        object.setName("dog");
        object.setAlive(Arrays.asList(Alive.ALIVE));
        object.setColors(Arrays.asList(Color.RED, Color.WHITE, Color.BLACK));
        object.setForms(Arrays.asList(Form.OTHER));
        object.setSizes(Arrays.asList(Size.SMALL, Size.MEDIUM));
        object.setTypes(Arrays.asList(Type.ANIMAL));
        knowledge.addObject(object);

        knowledge.saveToFile(PATH_TO_KNOWLEDGE_FILE);
    }
}
