package com.test.heart.heartme.presenter;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by Sahar on 30/05/2018.
 */

public class MainPresenter
{

    private HashMap<String, Long> mDataMap = new HashMap<>();

    public MainPresenter()
    {

    }

    public String loadJSONFromAsset(Context context)
    {
        String json = null;
        try
        {
            InputStream is = context.getAssets().open("bloodTestConfig.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public void init(Context context)
    {
        String jsonData = loadJSONFromAsset(context);
        try
        {
            JSONObject allData =  new JSONObject(jsonData);
            JSONArray testConfig = allData.getJSONArray("bloodTestConfig");
            int size = testConfig.length();
            for(int i = 0; i < size; i++)
            {
                JSONObject data = testConfig.getJSONObject(i);
                mDataMap.put(data.getString("name"), data.getLong("threshold"));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public final int OK = 1;
    public final int UNKNOWN = 2;
    public final int BAD = 3;

    public int Check(String name, long result)
    {
        name = name.toUpperCase();
        if(mDataMap.containsKey(name))
        {
            long testThreshold = mDataMap.get(name);
            return testThreshold < result ? 3 : 1;
        }
        else
        {
             return UNKNOWN;
        }
    }
}
