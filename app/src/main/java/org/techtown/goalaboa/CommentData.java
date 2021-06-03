package org.techtown.goalaboa;

public class CommentData {
    private String mid;
    private String mcomment;
    private String mdate;
    private String mEmail;

    public CommentData(){

    }
    public CommentData(String id,String comment,String adate,String email){
        this.mid = id;
        this.mcomment = comment;
        this.mdate = adate;
        this.mEmail = email;


    }


    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMcomment() {
        return mcomment;
    }

    public void setMcomment(String mcomment) {
        this.mcomment = mcomment;
    }

    public String getMdate() {
        return mdate;
    }

    public void setMdate(String mdate) {
        this.mdate = mdate;
    }

    public String getmEmail() {
        return mEmail;
    }
}
