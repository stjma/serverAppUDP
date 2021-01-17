/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientserverudp;

/**
 *
 * @author M-A
 */
public class User {

    private String nickName, name;

    public User(String nickName, String name) {
        this.setNickName(nickName);
        this.setName(name);
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toSting(){
        return String.format("NickName %s name %s",this.nickName,this.name);
    }
}
