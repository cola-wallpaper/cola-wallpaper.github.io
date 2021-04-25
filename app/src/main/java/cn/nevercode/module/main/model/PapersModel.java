package cn.nevercode.module.main.model;

import java.util.List;

public class PapersModel {

    /**
     * data : [{"url":"123456"},{"url":"123456"}]
     * code : 0
     * msg : null
     */

    private int code;
    private Object msg;
    private String tips;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    /**
     * url : 123456
     */

    private List<DataBean> wallpaper;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return wallpaper;
    }

    public void setData(List<DataBean> data) {
        this.wallpaper = data;
    }

    public static class DataBean {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
