package ruan.cong.summerframework.test.service;

import ruan.cong.summerframework.beans.context.annotation.Component;
import ruan.cong.summerframework.beans.factory.annotation.Value;

@Component
public class User {
    @Value("${number}")
    private String number;

    @Value("${name}")
    private String name;

    @Value("${title}")
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

    @Override
    public String toString() {
        return "User{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
