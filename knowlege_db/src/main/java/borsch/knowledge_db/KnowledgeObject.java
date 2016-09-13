package borsch.knowledge_db;

import borsch.knowledge_db.enums.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleh_kurpiak on 13.09.2016.
 */
public class KnowledgeObject {

    private String name;

    private List<Form> forms;

    private List<Color> colors;

    private List<Type> types;

    private List<Alive> alive;

    private List<Size> sizes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public List<Alive> getAlive() {
        return alive;
    }

    public void setAlive(List<Alive> alive) {
        this.alive = alive;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    private <T> List<T> list(List<T> list){
        if(list == null)
            return new ArrayList<T>();
        return list;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("KnowledgeObject{");
        sb.append("name='").append(name).append('\'');
        sb.append(", forms=").append(forms);
        sb.append(", colors=").append(colors);
        sb.append(", types=").append(types);
        sb.append(", alive=").append(alive);
        sb.append(", sizes=").append(sizes);
        sb.append('}');
        return sb.toString();
    }
}
