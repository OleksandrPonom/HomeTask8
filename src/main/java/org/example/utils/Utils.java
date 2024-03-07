package org.example.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class Utils {
    public static final String DB_URL = "dbUrl";
    public static final String DB_USER = "dbUser";
    public static final String DB_PASS = "dbPass";
    public static final String DEFAULT_UTILS_FILE_PATH = "src/main/java/org/example/utils/prefs.json";
    private final Map<String, Object> utils;

    public Utils() {
        this(DEFAULT_UTILS_FILE_PATH);
    }

    public Utils(String filename) {

        try {
            String json = String.join(
                    "\n",
                    Files.readString(Paths.get(filename))
            );

            TypeToken<?> typeToken = TypeToken.getParameterized(
                    Map.class,
                    String.class,
                    Object.class
            );

            utils = new Gson().fromJson(json, typeToken.getType());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getString(String key) {
        return getPref(key).toString();
    }

    public Object getPref(String key) {
        return utils.get(key);
    }

}