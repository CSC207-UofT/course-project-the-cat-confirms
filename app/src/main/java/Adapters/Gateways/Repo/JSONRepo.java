package Adapters.Gateways.Repo;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static Utils.JSONable.toMap;


public class JSONRepo extends Repo {
    private final String path;
    protected boolean loaded;

    /**
     * @param path the path to the json file
     */
    public JSONRepo(String path) {
        super();
        this.path = path;
        this.ready = this.loadRepo();
    }

    /**
     * try to load the given json file and build the database
     * @return true if the load is successful
     */
    @Override
    protected boolean loadRepo() {
        boolean loaded = false;
        System.out.println("Reading from " + this.path);

        JSONParser jsonParser = new JSONParser();

        try {
            FileReader reader = new FileReader(this.path);
            JSONObject json = (JSONObject) jsonParser.parse(reader);
            this.repo = toMap(json);
            loaded = true;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return loaded;
    }

    /**
     * Save all changes from memory to the json file
     */
    @Override
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

    /**
     * Set an element in the database in memory
     * @param key key of the element
     * @param data data to be set
     * @return true if not conflict
     */
    @Override
    public boolean set(String key, Object data) {
        if (this.repo.containsKey(key)) {
            return false;
        }

        this.repo.put(key, data);
        return true;
    }

    /**
     * Set an element in the database in memory
     * @param key key of the element
     * @param data data to be set
     */
    @Override
    public void overwrite(String key, Object data) {
        this.repo.put(key, data);
    }

    /**
     * Get an element in the database in memory
     * @param key key of the element to be gotten
     * @return the value
     */
    @Override
    public Object get(String key) {
        return this.repo.get(key);
    }
}
