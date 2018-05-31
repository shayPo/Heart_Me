package com.test.heart.heartme.presenter;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Sahar on 31/05/2018.
 */

public class VolleyPresenter
{
    private static RequestQueue mRequestQueue = null;

    public VolleyPresenter(Context context)
    {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void addRequest(StringRequest request)
    {
        mRequestQueue.add(request);
    }
}
