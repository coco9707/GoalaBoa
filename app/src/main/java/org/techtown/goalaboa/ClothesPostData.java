package org.techtown.goalaboa;

public class ClothesPostData {
    // clothes post data
    private String ctitle;  // 제목
    private String cdate;  // 작성날짜
    private String ctext;  // 내용
    private String cemail;  // 작성자 이메일

    //private int ccost; // 가격
    private String ccategory; // 대분류 - 아우터, 상의, 하의, 신발
    //private int ccategory2; // 하위분류


    // 옷 사진 (구현)
    // private String csize; // 사이즈 데이터 (배열 구현)

    public ClothesPostData(){}
    public ClothesPostData(String title, String date, String text, String email, String category){
        //int cost, int category1, int category2
        this.ctitle = title;
        this.cdate = date;
        this.ctext = text;
        this.cemail = email;
       // this.ccost = cost;
        this.ccategory = category;
        //this.ccategory2 = category2;

    }

    public String getCtitle() {
        return ctitle;
    }

    public void setCtitle(String ctitle) {
        this.ctitle = ctitle;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getCtext() {
        return ctext;
    }

    public void setCtext(String ctext) {
        this.ctext = ctext;
    }

    public String getCemail() {
        return cemail;
    }

    public String getCcategory() {
        return ccategory;
    }

    public void setCcategory(String ccategory) {
        this.ccategory = ccategory;
    }
    /*
    public int getCcost() {
        return ccost;
    }

    public void setCcost(int ccost) {
        this.ccost = ccost;
    }



    public int getCcategory2() {
        return ccategory2;
    }

    public void setCcategory2(int ccategory2) {
        this.ccategory2 = ccategory2;
    }
*/
}
