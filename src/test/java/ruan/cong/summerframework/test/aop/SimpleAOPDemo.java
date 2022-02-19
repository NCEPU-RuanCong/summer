package ruan.cong.summerframework.test.aop;

public class SimpleAOPDemo implements SimpleAOPDemoInterface {
    public void sayHello() {
        System.out.println("========>hello方法调用");
    }

    public void sayHello_2() {
        System.out.println("=========>hello_2方法调用");
    }
}
