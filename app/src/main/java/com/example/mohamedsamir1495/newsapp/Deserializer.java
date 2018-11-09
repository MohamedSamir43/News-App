package com.example.mohamedsamir1495.newsapp;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class Deserializer {
    public static List<NewsModel> getList(JSONObject response, Context mParentContext) {


        String authorFullName = mParentContext.getString(R.string.empty_string);
        List<NewsModel> responseModels = new ArrayList<>();
        try {
            JSONArray mJsonArray = response.getJSONObject(mParentContext.getString(R.string.API_RESPONSE_KEY)).getJSONArray(mParentContext.getString(R.string.API_RESPONSE_ARRAY_KEY));

            for (int i = 0; i < mJsonArray.length(); i++) {
                JSONObject mDataObject = (JSONObject) mJsonArray.get(i);
                String sectionName = mDataObject.has(mParentContext.getString(R.string.API_SECTION_NAME_KEY)) ?
                        (String) mDataObject.get(mParentContext.getString(R.string.API_SECTION_NAME_KEY)) : mParentContext.getString(R.string.empty_string);

                String articleName = mDataObject.has(mParentContext.getString(R.string.API_TITLE_KEY)) ?
                        (String) mDataObject.get(mParentContext.getString(R.string.API_TITLE_KEY)) : mParentContext.getString(R.string.empty_string);

                String url = mDataObject.getString(mParentContext.getString(R.string.API_URL_NAME_KEY));

                String articlePublishDate = mDataObject.has(mParentContext.getString(R.string.API_PUBLISH_DATE_KEY)) ?
                        (String) mDataObject.get(mParentContext.getString(R.string.API_PUBLISH_DATE_KEY)) : mParentContext.getString(R.string.empty_string);


                if (mDataObject.has(mParentContext.getString(R.string.API_TAGS_KEY))) {
                    JSONArray tagsArray = mDataObject.getJSONArray(mParentContext.getString(R.string.API_TAGS_KEY));
                    authorFullName = getAuthorName(tagsArray, mParentContext);
                }
                responseModels.add(new NewsModel(articleName, sectionName, articlePublishDate, authorFullName, url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return responseModels;


    }

    private static String getAuthorName(JSONArray tagsArray, Context mParentContext) {
        String authorFirstName, authorLastName;
        if (tagsArray.length() > 0) {
            try {
                JSONObject bioObject = tagsArray.getJSONObject(0);
                authorFirstName = bioObject.has(mParentContext.getString(R.string.API_FIRST_NAME_KEY)) ?
                        (String) bioObject.get(mParentContext.getString(R.string.API_FIRST_NAME_KEY)) : mParentContext.getString(R.string.empty_string);

                authorLastName = bioObject.has(mParentContext.getString(R.string.API_LAST_NAME_KEY)) ?
                        (String) bioObject.get(mParentContext.getString(R.string.API_LAST_NAME_KEY)) : mParentContext.getString(R.string.empty_string);


                return authorFirstName + " " + authorLastName;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mParentContext.getString(R.string.empty_string);
    }
}
