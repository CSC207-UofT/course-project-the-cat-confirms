package Repo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class Repo {
    private final String path;
    protected HashMap<String, Object> repo;
    protected boolean loaded;

    public Repo(String path) {
        this.path = path;
        this.repo = new HashMap();

        loaded = loadRepo();
    }

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

    protected void initRepo() {
        throw new IllegalAccessError("You must override this method. ");
    }

    private boolean loadRepo() {
        boolean loaded = false;
        System.out.println("Reading from " + this.path);

        JSONParser jsonParser = new JSONParser();

        try {
            FileReader reader = new FileReader(this.path);
            JSONObject json = (JSONObject) jsonParser.parse(reader);
            this.repo = toMap(json);
            loaded = true;
        } catch (IOException | ParseException e) {
//            e.printStackTrace();
        }

        return loaded;
    }

    public void saveRepo() {
        String jsonText = JSONValue.toJSONString(repo);
        System.out.println("Writing to " + this.path + ":" + jsonText);
        try {
            FileWriter myWriter = new FileWriter(this.path);
            myWriter.write(jsonText);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isLoaded() {
        return loaded;
    }
}