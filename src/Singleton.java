class SingletonInstance {
    private static SingletonInstance instance = null;

    private SingletonInstance() {
    }

    public static SingletonInstance getInstance() {
        if (instance == null) {
            instance = new SingletonInstance();
        }
        return instance;
    }
}

public class Singleton {
}
