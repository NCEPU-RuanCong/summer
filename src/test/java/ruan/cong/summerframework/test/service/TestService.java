package ruan.cong.summerframework.test.service;


import ruan.cong.summerframework.beans.context.annotation.Component;
import ruan.cong.summerframework.beans.factory.annotation.Autowired;
import ruan.cong.summerframework.beans.factory.annotation.Value;

@Component("userService")
public class TestService {
    @Value("${token}")
    private String test;

    @Autowired
    private User user;

    public void say(){
        System.out.println("=====================================" + test + "====================================");
    }

    public String toString(){
        return "test:" + test + ",【user:" + user.toString() + "】";
    }
}
