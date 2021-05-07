package logic;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Session {

    private Map<String,Object> savedObjects;

    public Session() {
        this.savedObjects = new HashMap<>();
    }

    public Object getSavedObjects(String name) {
        return savedObjects.get(name);
    }

    public void saveObject(String name, Object o) {
        this.savedObjects.put(name,o);
    }

    public void invalidate(){

        this.savedObjects.clear();

    }

}
