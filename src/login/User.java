package login;

import java.io.Serializable;
import java.util.Objects;

// 회원 정보를 String으로 받음
// ID, PW, NAME, NICKNAME, PHONENUMBER
// SMS 수신동의는 Yes OR No 로 선택 가능

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String pw;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String smsOk;

    public User(String id, String pw, String name, String nickName, String phoneNumber, String smsOk) {
        setId(id);
        setPw(pw);
        setName(name);
        setNickName(nickName);
        setPhoneNumber(phoneNumber);
        setSmsOk(smsOk);
    }

    public User(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPw() {
        return pw;
    }
    public void setPw(String pw) {
        this.pw = pw;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getnickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsOk() {
        return smsOk;
    }

    public void setSmsOk(String smsOk) {
        this.smsOk = smsOk;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        String info = "아이디: " + id + "\n";
        info += "암호: " + pw + "\n";
        info += "이름: " + name + "\n";
        info += "닉네임: " + nickName + "\n";
        info += "연락처: " + phoneNumber + "\n";
        info += "SMS 수신 동의: " + smsOk + "\n";
        return info;
    }
}