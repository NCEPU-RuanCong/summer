package ruan.cong.summerframework.test.domain;

import ruan.cong.summerframework.beans.context.annotation.Component;

@Component
public class User {
    private String number;
    private String name;
    private String title;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User() {
    }

    public User(String number, String name, String title) {
        this.number = number;
        this.name = name;
        this.title = title;
    }

    public void sayHello(){
        System.out.println("hello a");
    }

    @Override
    public String toString() {
        return "User{" +
                "number=aaa" + number +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
