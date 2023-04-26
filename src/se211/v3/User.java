package se211.v3;

import java.net.InetAddress;

public class User {

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    String nickName;

    public InetAddress getInetadd() {
        return inetadd;
    }

    public void setInetadd(InetAddress inetadd) {
        this.inetadd = inetadd;
    }

    InetAddress inetadd;


}
