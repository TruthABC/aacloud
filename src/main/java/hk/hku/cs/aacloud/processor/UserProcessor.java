package hk.hku.cs.aacloud.processor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hk.hku.cs.aacloud.Global;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

@Component
public class UserProcessor {

    private static final String USER_SETTING_ROOT = "aacusers";
    private static final String PASSWORD_JSON = "password.json";

    public boolean checkExistById(String id) throws Exception {
        File file = new File(USER_SETTING_ROOT + "/" + id);
        return file.exists() && file.isDirectory();
    }

    public String readPasswordById(String id) throws Exception {
        File file = new File(USER_SETTING_ROOT + "/" + id + "/" + PASSWORD_JSON);
        Scanner scanner = null;
        String password = null;
        try {
            scanner = Global.getFileScanner(file);
            String jsonStr = "";
            while (scanner.hasNextLine()) {
                jsonStr += scanner.nextLine() +"\n";
            }
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(jsonStr).getAsJsonObject();
            password = jsonObject.get("password").getAsString();
            scanner.close();
        } catch (Exception e) {
            if (scanner != null) {
                scanner.close();
            }
            throw e;
        }

        return password;
    }

    public void writePasswordById(String id, String password) throws Exception {
        File file = new File(USER_SETTING_ROOT + "/" + id);
        if (!(file.exists() && file.isDirectory())) {
            Global.deleteAndMkdirs(file);
        }
        file = new File(USER_SETTING_ROOT + "/" + id + "/" + PASSWORD_JSON);
        if (!file.exists()) {
            file.createNewFile();
        } else if (file.isDirectory()) {
            file.delete();
            file.createNewFile();
        }
        PrintStream out = null;
        try {
            out = Global.getFilePrintStream(file);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", id);
            jsonObject.addProperty("password", password);
            out.print(jsonObject.toString());
            out.close();
        } catch (Exception e) {
            if (out != null) {
                out.close();
            }
            throw e;
        }
    }
}
