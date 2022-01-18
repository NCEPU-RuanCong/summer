package ruan.cong.summerframework.test.bean;

import ruan.cong.summerframework.test.domain.User;

public class UserService {

    private String name;
    private String number;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UserService(String name) {
        this.name = name;
    }

    public UserService(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public UserService(String name, String number, User user) {
        this.name = name;
        this.number = number;
        this.user = user;
    }

    public void printInfo(){
        System.out.println("UserService!");
    }

    public void printUsername(){
        System.out.println("UserService---userName:" + name + "\nnumber:" + number);
    }

    @Override
    public String toString() {
        return "UserService{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", user=" + user +
                '}';
    }
}
