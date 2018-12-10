package com.example.mohamedsamir1495.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.content.Loader;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<NewsModel>> {

    @BindView(R.id.news_recycler_view)
    RecyclerView newsRecyclerView;

    @BindView(R.id.noPostsYet_Tv)
    TextView noPostsView;


    @BindView(R.id.containerView)
    CoordinatorLayout mParentView;

    NewsAdapter mRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        mRecyclerAdapter = new NewsAdapter(new ArrayList<NewsModel>(), this);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setAdapter(mRecyclerAdapter);
        notifyVisablityController();

        if (isConnected()) {
            {
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(0, null, this);
            }
        } else
            Snackbar.make(mParentView, getString(R.string.connectionError), Snackbar.LENGTH_SHORT).show();

    }


    boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netWorkInfo = cm.getActiveNetworkInfo();
        return (netWorkInfo != null && netWorkInfo.isConnected());
    }

    @NonNull
    @Override
    public Loader<List<NewsModel>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String REQUEST_URL = "http://content.guardianapis.com/search?";
        String API_KEY = "6f229d90-3108-4e8b-ac10-0744600f329e";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String minNews = sharedPreferences.getString(getString(R.string.settings_min_news_key), getString(R.string.settings_min_news_default));
        String orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));
        String section = sharedPreferences.getString(getString(R.string.settings_section_news_key), getString(R.string.settings_section_news_default));

        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api-key", API_KEY);
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", minNews);
        uriBuilder.appendQueryParameter("order-by", orderBy);

        if (!section.equals(getString(R.string.settings_section_news_default))) {
            uriBuilder.appendQueryParameter("section", section);
        }

        return new ServiceLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsModel>> loader, List<NewsModel> data) {
        if (data != null && !data.isEmpty()) {
            mRecyclerAdapter.updateAdapter(data);
        }
        notifyVisablityController();
    }

    @Override
    public void onLoaderReset(Loader<List<NewsModel>> loader) {
            mRecyclerAdapter.dataModel.clear();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }



    void notifyVisablityController() {
        if (mRecyclerAdapter.getItemCount() > 0) {
            newsRecyclerView.setVisibility(View.VISIBLE);
            noPostsView.setVisibility(View.GONE);
        } else {
            newsRecyclerView.setVisibility(View.GONE);
            noPostsView.setVisibility(View.VISIBLE);
        }
    }
}
