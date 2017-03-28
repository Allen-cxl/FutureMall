package com.futuremall.android.http;


public class ApiException extends Throwable{

    public int state;
    public String srvMsg;

    public ApiException(int state,String srvMsg)
    {
        super(srvMsg);
        this.state = state;
        this.srvMsg = srvMsg;
    }
}
