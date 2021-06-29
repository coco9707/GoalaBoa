package org.techtown.goalaboa;

public class ClothesPostData {
    // clothes post data
    private String ctitle;  // 제목
    private String cdate;  // 작성날짜
    private String ctext;  // 내용
    private String cemail;  // 작성자 이메일

    //private int ccost; // 가격
    //private int ccategory1; // 대분류 - 아우터, 상의, 하의, 신발
    //private int ccategory2; // 하위분류


    // 옷 사진 (구현)
    // private String csize; // 사이즈 데이터 (배열 구현)

    public ClothesPostData(){}
    public ClothesPostData(String title, String date, String text, String email){
        //int cost, int category1, int category2
        this.ctitle = title;
        this.cdate = date;
        this.ctext = text;
        this.cemail = email;
       // this.ccost = cost;
        //this.ccategory1 = category1;
        //this.ccategory2 = category2;

    }

    public String getMtitle() {
        return ctitle;
    }

    public void setMtitle(String mtitle) {
        this.ctitle = mtitle;
    }

    public String getMdate() {
        return cdate;
    }

    public void setMdate(String mdate) {
        this.cdate = mdate;
    }

    public String getMtext() {
        return ctext;
    }

    public void setMtext(String mtext) {
        this.ctext = mtext;
    }

    public String getMemail() {
        return cemail;
    }

/*
    public int getCcost() {
        return ccost;
    }

    public void setCcost(int ccost) {
        this.ccost = ccost;
    }

    public int getCcategory1() {
        return ccategory1;
    }

    public void setCcategory1(int ccategory1) {
        this.ccategory1 = ccategory1;
    }

    public int getCcategory2() {
        return ccategory2;
    }

    public void setCcategory2(int ccategory2) {
        this.ccategory2 = ccategory2;
    }
*/
}
