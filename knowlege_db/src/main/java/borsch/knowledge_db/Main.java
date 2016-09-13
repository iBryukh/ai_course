package borsch.knowledge_db;

import borsch.knowledge_db.enums.Color;
import borsch.knowledge_db.enums.Form;
import borsch.knowledge_db.enums.Size;

import java.util.Arrays;

/**
 * Created by oleh_kurpiak on 13.09.2016.
 */
public class Main {

    public static final String PATH_TO_KNOWLEDGE_FILE = "test.json";

    public static void main(String[] args){
        KnowledgeObject object = new KnowledgeObject();
        object.setColors(Arrays.asList(Color.BLACK, Color.ORANGE, Color.WHITE));
        object.setForms(Arrays.asList(Form.CIRCLE, Form.SQUARE));
        object.setSizes(Arrays.asList(Size.LARGE, Size.SMALL));
        object.setName("other");

        Knowledge knowledge = new Knowledge(PATH_TO_KNOWLEDGE_FILE);
        knowledge.addObject(object);
        knowledge.saveToFile(PATH_TO_KNOWLEDGE_FILE);
        System.out.println(knowledge);
    }

}
