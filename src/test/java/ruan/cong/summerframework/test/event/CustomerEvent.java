package ruan.cong.summerframework.test.event;

import java.time.LocalDate;
import java.util.Date;
import ruan.cong.summerframework.beans.context.ApplicationEvent;

public class CustomerEvent extends ApplicationEvent {
    private String eventName;
    private LocalDate dateTime;
    private String operator;

    public CustomerEvent(Object source, String eventName, LocalDate dateTime, String operator) {
        super(source);
        this.eventName = eventName;
        this.dateTime = dateTime;
        this.operator = operator;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDate dateTime) {
        this.dateTime = dateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "CustomerEvent{" +
                "eventName='" + eventName + '\'' +
                ", dateTime=" + dateTime +
                ", operator='" + operator + '\'' +
                '}';
    }
}
