package Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public interface JSONable {
    HashMap<String, Object> toDict();
    // Ref: https://stackoverflow.com/questions/21720759/convert-a-json-string-to-a-hashmap
    public static HashMap<String, Object> toMap(JSONObject object) {
        HashMap<String, Object> map = new HashMap<String, Object>();

        for (String key : (Iterable<String>) object.keySet()) {
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static ArrayList<Object> toList(JSONArray array) {
        ArrayList<Object> list = new ArrayList<Object>();
        for (Object value : array) {
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}
