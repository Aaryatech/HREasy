package com.ats.hreasy.model;

public class Info {

    private String msg;
    private Boolean error;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Info{" +
                "msg='" + msg + '\'' +
                ", error=" + error +
                '}';
    }
}
