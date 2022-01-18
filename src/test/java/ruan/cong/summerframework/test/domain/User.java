package ruan.cong.summerframework.test.domain;

public class User {
    private Integer number;
    private String name;
    private String title;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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

    public User(Integer number, String name, String title) {
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
