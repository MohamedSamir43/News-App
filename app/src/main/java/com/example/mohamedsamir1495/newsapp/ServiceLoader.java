package com.example.mohamedsamir1495.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ServiceLoader extends AsyncTaskLoader<Object> implements Response.Listener<JSONObject>, Response.ErrorListener {

    final String url = "https://content.guardianapis.com/search?&show-tags=contributor&api-key=6f229d90-3108-4e8b-ac10-0744600f329e&page-size=50";
    List<NewsModel> resultObject = null;
    Context mParentContext;

    public ServiceLoader(@NonNull Context context) {
        super(context);
        this.mParentContext = context;
    }


    @Nullable
    @Override
    public List<NewsModel> loadInBackground() {
        startVolleyService();
        return (resultObject == null) ? new ArrayList<NewsModel>() : resultObject;
    }


    void startVolleyService() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(mParentContext);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, this, this);
        mRequestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {
        resultObject = Deserializer.getList(response, mParentContext);

        Log.e("TAG", resultObject.size() + " ");
        deliverResult(resultObject);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        deliverCancellation();
    }
}
