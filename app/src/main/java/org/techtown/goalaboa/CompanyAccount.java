package org.techtown.goalaboa;

/**
 * 사용자 계정 정보 모델 클래스
 */

public class CompanyAccount {

    private String idToken;     // Firebase Uid (고유 토큰정보)
    private String emailId;     // 이메일 아이디
    private String password;    // 비밀번호
    private boolean company;

    // 가장 먼저 나오는 생성자 함수
    // Firebase를 이용하여 모델 클래스를 가져올 때는 빈 생성자를 만들어야 한다.
    public CompanyAccount() { }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCompany() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }
}