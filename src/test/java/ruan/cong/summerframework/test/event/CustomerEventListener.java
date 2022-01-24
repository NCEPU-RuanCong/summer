package ruan.cong.summerframework.test.event;

import ruan.cong.summerframework.beans.context.ApplicationListener;

public class CustomerEventListener implements ApplicationListener<CustomerEvent> {
    @Override
    public void onApplicationEvent(CustomerEvent event) {
        System.out.println("发生CustomerEvent事件：\n" + "operator:" + event.getOperator() + "\noperateTime:" + event.getDateTime()
        + "\neventName:" + event.getEventName() + "\nsource:" + event.getSource());
    }
}
