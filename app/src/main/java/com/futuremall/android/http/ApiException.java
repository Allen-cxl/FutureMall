package com.futuremall.android.http;


public class ApiException extends Throwable{

    public ApiException(String srvMsg)
    {
        super(srvMsg);
    }
}
