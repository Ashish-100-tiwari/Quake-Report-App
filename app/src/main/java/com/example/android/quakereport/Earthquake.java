package com.example.android.quakereport;

public class Earthquake {
    private Double mMagnitude;
    private String mdate;
    private String mlocation;
    private long mTimeInMillisecond;
    private String murl;

public Earthquake(Double magnitude , String location,long timeInMilliseconds,String url){
    mMagnitude = magnitude;
    mlocation = location;
    mTimeInMillisecond = timeInMilliseconds;
    murl=url;
}
public Double getMagnitude(){
    return mMagnitude;
}
public  String getLocation(){
    return mlocation;
}
public String getDate(){
    return mdate;
}
public long getTimeInMilliseconds(){
    return mTimeInMillisecond;
}
public String getUrl(){
    return murl;
}
}
