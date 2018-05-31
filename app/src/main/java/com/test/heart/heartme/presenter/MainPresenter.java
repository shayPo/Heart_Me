package com.test.heart.heartme.presenter;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sahar on 30/05/2018.
 */

public class MainPresenter implements Response.ErrorListener, Response.Listener<String>
{

    public final int OK = 1;
    public final int UNKNOWN = 2;
    public final int BAD = 3;

    private HashMap<String, Long> mDataMap = new HashMap<>();
    private ActivityListener mListener;

    public MainPresenter(ActivityListener listener)
    {
        mListener = listener;
    }

    public void onDestroy()
    {
        mListener = null;
        mDataMap = null;
    }

    public void loadJSONData(Context context)
    {
        VolleyPresenter volleyPresenter = new VolleyPresenter(context);
        String url = "https://s3.amazonaws.com/s3.helloheart.home.assignment/bloodTestConfig.json";
        StringRequest stringRequest = new StringRequest( url,this, this);
        volleyPresenter.addRequest(stringRequest);
    }


    private void init(String jsonData)
    {
        try
        {
            JSONObject allData =  new JSONObject(jsonData);
            JSONArray testConfig = allData.getJSONArray("bloodTestConfig");
            int size = testConfig.length();
            for(int i = 0; i < size; i++)
            {
                JSONObject data = testConfig.getJSONObject(i);
                String name = data.getString("name");
                if(name.length() > 3)
                {
                    name = (String) name.subSequence(0,3);
                }
                mDataMap.put(name.toUpperCase(), data.getLong("threshold"));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public int Check(String name, long result)
    {
        name = name.toUpperCase();
        boolean contains = false;
        for (Map.Entry<String, Long> e : mDataMap.entrySet()) {
            if (name.contains(e.getKey())) {
                name = e.getKey();
                contains = true;
                break;
            }
        }

        if(contains)
        {
            long testThreshold = mDataMap.get(name);
            return testThreshold < result ? 3 : 1;
        }
        else
        {
             return UNKNOWN;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error)
    {
        mListener.onError();
    }

    @Override
    public void onResponse(String response)
    {
        init(response);
    }
}
