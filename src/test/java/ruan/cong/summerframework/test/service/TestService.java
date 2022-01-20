package ruan.cong.summerframework.test.service;

import ruan.cong.summerframework.annotation.Value;

public class TestService {
    @Value("bean.post.process")
    private String test;

    public void say(){
        System.out.println("=====================================" + test + "====================================");
    }
}
