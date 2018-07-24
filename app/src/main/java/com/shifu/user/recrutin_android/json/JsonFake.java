package com.shifu.user.recrutin_android.json;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shifu.user.recrutin_android.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A product entry in the list of products.
 */
public class JsonFake {
    private static final String TAG = JsonFake.class.getSimpleName();

    public final String title;
    public final String description;
    public final String salary;
    public final String company;
    public final String url;

    public JsonFake(String title, String description, String salary, String company, String url) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.company = company;
        this.url = url;
    }

    /**
     * Loads a raw JSON at R.raw.products and converts it into a list of JsonFake objects
     */
    public static List<JsonFake> initEntryList(Resources resources) {
        InputStream inputStream = resources.openRawResource(R.raw.test_data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }

        Type productListType = new TypeToken<ArrayList<JsonFake>>() {}.getType();
        return new Gson().fromJson(writer.toString(), productListType);
    }
}