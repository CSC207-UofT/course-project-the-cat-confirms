package Adapters.Gateways.Repo;

import java.util.HashMap;


/**
 * Can extend this to use a real database in the future
 */
public abstract class Repo {
    protected HashMap<String, Object> repo;
    protected boolean ready;

    public Repo() {
        this.repo = new HashMap<>();
    }

    protected abstract boolean loadRepo();

    public abstract void saveRepo();

    public abstract boolean set(String key, Object data);

    public abstract void overwrite(String key, Object data);

    public abstract Object get(String key);

    public boolean isReady() {
        return ready;
    }
}
