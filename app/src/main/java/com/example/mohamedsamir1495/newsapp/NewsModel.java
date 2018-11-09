package com.example.mohamedsamir1495.newsapp;

class NewsModel {

    String mArticleTitle, mArticleSection, mArticlePublicationDate, mArticleAuthorName, mArticleUrl;

    public NewsModel(String articleTitle, String articleSection, String articlePublicationDate, String articleAuthorName, String articleURL) {
        this.mArticleTitle = articleTitle;
        this.mArticleAuthorName = articleAuthorName;
        this.mArticlePublicationDate = articlePublicationDate;
        this.mArticleSection = articleSection;
        this.mArticleUrl = articleURL;
    }

    public String getArticleTitle() {
        return mArticleTitle;
    }

    public String getArticleSection() {
        return mArticleSection;
    }

    public String getArticlePublicationDate() {
        return mArticlePublicationDate;
    }

    public String getArticleAuthorName() {
        return mArticleAuthorName;
    }

    public String getArticleURL() {
        return mArticleUrl;
    }


}
