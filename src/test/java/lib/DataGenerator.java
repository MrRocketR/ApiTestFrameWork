package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnqa" + timestamp + "@example.com";
    }

    public static Map<String, String> getRegistrationData() {
        Map<String, String> defaultValues = new HashMap<>();
        defaultValues.put("username", "learnqa");
        defaultValues.put("firstName", "learnqa");
        defaultValues.put("lastName", "learnqa");
        defaultValues.put("email", DataGenerator.getRandomEmail());
        defaultValues.put("password", "1234");
        return defaultValues;
    }

    public static Map<String, String> getRegistrationData(Map<String, String> notDefaultValues) {
        Map<String, String> defaultValues = getRegistrationData();
        Map<String, String> userData = new HashMap<>();
        String[] keys = {"username", "firstName", "lastName", "email", "password"};
        for (String key : keys) {
            if (notDefaultValues.containsKey(key)) {
                userData.put(key, notDefaultValues.get(key));
            } else {
                userData.put(key, defaultValues.get(key));
            }
        }
        return userData;
    }
}
