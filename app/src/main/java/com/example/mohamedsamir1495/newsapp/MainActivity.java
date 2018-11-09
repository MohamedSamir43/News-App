package com.example.mohamedsamir1495.newsapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

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
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
            Log.e("TAG", "Initiated");
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
    public Loader<Object> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new ServiceLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Object> loader, Object o) {
        Log.e("TAG", "Load Finished");
        mRecyclerAdapter.updateAdapter((List<NewsModel>) o);
        notifyVisablityController();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Object> loader) {
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
