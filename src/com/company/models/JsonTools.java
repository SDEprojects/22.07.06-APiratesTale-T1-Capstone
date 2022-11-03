package com.company.models;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class JsonTools {

    private FileGetter fileGetter = new FileGetter();


    public ArrayList<Map<String, Object>> readJson(String file) {
            Gson gson = new Gson();
            InputStream path = fileGetter.fileGetter(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(path));
            //creating a new buffered reader to read in json as stream.
            ArrayList<Map<String, Object>> data = gson.fromJson(reader, ArrayList.class);
            return data;
    }

    public void writeJson(String file, String fileKey, Map<String, Object> entry) {
        // Get json data
        ArrayList<Map<String, Object>> data = readJson(file);
        // add to json object
        data.add(entry);
        try (FileWriter overWrite = new FileWriter(file)) {
            //We can write any JSONArray or JSONObject instance to the file
            overWrite.write(String.valueOf(data));
            overWrite.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}