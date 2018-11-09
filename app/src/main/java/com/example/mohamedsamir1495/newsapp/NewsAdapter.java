package com.example.mohamedsamir1495.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsVH> {
    List<NewsModel> dataModel;
    Context mParentContext;

    public NewsAdapter(List<NewsModel> dataModel, Context parentContext) {
        this.dataModel = dataModel;
        this.mParentContext = parentContext;
    }

    @NonNull
    @Override
    public NewsVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new NewsVH(LayoutInflater.from(mParentContext).inflate(R.layout.card_news_cell, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsVH newsVH, int i) {
        NewsModel objectModel = dataModel.get(i);
        newsVH.storyAuthorNameTv.setText(objectModel.getArticleAuthorName());
        newsVH.storyPublishDateTv.setText(objectModel.getArticlePublicationDate());
        newsVH.storySectionTv.setText(objectModel.getArticleSection());
        newsVH.storyTitleTv.setText(objectModel.getArticleTitle());
        newsVH.index = i;

        newsVH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsModel currentNews = dataModel.get(newsVH.index);
                Uri newsUri = Uri.parse(currentNews.getArticleURL());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                mParentContext.startActivity(websiteIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataModel != null) ? dataModel.size() : 0;
    }

    public void updateAdapter(List<NewsModel> dataModels) {
        this.dataModel = dataModels;
        this.notifyDataSetChanged();
    }

    public class NewsVH extends RecyclerView.ViewHolder {

        @BindView(R.id.story_card_title_tv)
        TextView storyTitleTv;

        @BindView(R.id.story_card_section_tv)
        TextView storySectionTv;

        @BindView(R.id.story_card_author_tv)
        TextView storyAuthorNameTv;

        @BindView(R.id.story_card_date_tv)
        TextView storyPublishDateTv;

        int index;

        public NewsVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
