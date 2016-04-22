package com.ancx.mvdnovel.model;

import com.ancx.mvdnovel.entity.KeyWords;
import com.ancx.mvdnovel.listener.HttpRequestListener;
import com.ancx.mvdnovel.listener.OnKeyWordsListener;
import com.ancx.mvdnovel.util.DatabaseManager;
import com.ancx.mvdnovel.util.GsonUtil;
import com.ancx.mvdnovel.util.HttpUtil;
import com.ancx.mvdnovel.util.MsgUtil;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Ancx on 2016/4/14.
 */
public class ModelKeyWords {

    private List<String> keyWords;

    /**
     * 根据关键字获取关键字列表
     *
     * @param keyWord
     */
    public void getKeyWords(String keyWord) {
        try {
            HttpUtil.addRequest("http://api.zhuishushenqi.com/book/auto-complete?query=" + URLEncoder.encode(keyWord, "UTF-8")
                    , new HttpRequestListener() {
                        @Override
                        public void onSuccess(String response) {
                            KeyWords keyWord = GsonUtil.getObject(response, KeyWords.class);
                            keyWords = keyWord.getKeywords();
                            if (onKeyWordsListener != null)
                                onKeyWordsListener.setKeyWords(keyWords);
                        }

                        @Override
                        public void onError(VolleyError error) {
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            MsgUtil.LogTag("ModelKeyWords -> getKeyWords -> URL编码错误");
            MsgUtil.LogException(e);
        }
    }

    private OnKeyWordsListener onKeyWordsListener;

    public void setOnKeyWordsListener(OnKeyWordsListener onKeyWordsListener) {
        this.onKeyWordsListener = onKeyWordsListener;
    }

    /**
     * 获取搜索历史数据
     */
    public List<String> getHistory() {
        return DatabaseManager.getHistory();
    }

    public void clearHistory() {
        DatabaseManager.delHistory(null);
    }

    public void addHistory(String name) {
        DatabaseManager.addHistory(name);
    }

}
