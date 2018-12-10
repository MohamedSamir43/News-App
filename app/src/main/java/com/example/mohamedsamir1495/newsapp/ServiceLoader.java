package com.example.mohamedsamir1495.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


public class ServiceLoader extends AsyncTaskLoader<List<NewsModel> >{

    String mURL ;

    public ServiceLoader(Context context, String url) {
        super(context);
        this.mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsModel> loadInBackground() {
        if (mURL == null) {
            return null;
        }

        List<NewsModel> newsList = WebServiceManager.fetchNewsData(mURL);
        return newsList;
    }
}
