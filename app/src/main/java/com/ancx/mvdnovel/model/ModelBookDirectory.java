package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.Chapter;
import com.ancx.mvdnovel.entity.DirectoryList;
import com.ancx.mvdnovel.entity.Source;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnBookDirectoryListener;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ancx on 2016/4/16.
 */
public class ModelBookDirectory {

    public void getSource(String _id) {
        HttpUtil.addRequest("http://api.zhuishushenqi.com/toc?view=summary&book=" + _id,
                new HttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        List<Source> sources = GsonUtil.getArray(response, Source.class);
                        if (onBookDirectoryListener != null)
                            onBookDirectoryListener.setSource(sources);
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }

    private OnBookDirectoryListener onBookDirectoryListener;

    public void setOnBookDirectoryListener(OnBookDirectoryListener onBookDirectoryListener) {
        this.onBookDirectoryListener = onBookDirectoryListener;
    }

    /**
     * @param _id 小说源id
     */
    public void getDirectory(String _id) {
        HttpUtil.addRequest("http://api.zhuishushenqi.com/toc/" + _id + "?view=chapters",
                new HttpRequestListener() {
                    @Override
                    public void onSuccess(String response) {
                        DirectoryList directoryList = GsonUtil.getObject(response, DirectoryList.class);
                        if (directoryList != null) {
                            List<Chapter> chapters = directoryList.getChapters();
                            if (onBookDirectoryListener != null) {
                                if (chapters == null)
                                    onBookDirectoryListener.setDirectory(new ArrayList<Chapter>());
                                else
                                    onBookDirectoryListener.setDirectory(chapters);
                            }
                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });
    }
}
