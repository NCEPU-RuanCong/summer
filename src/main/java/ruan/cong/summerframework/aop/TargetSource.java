package ruan.cong.summerframework.aop;

public class TargetSource {
    private final Object Target;

    public TargetSource(Object target) {
        Target = target;
    }

    public Object getTarget() {
        return Target;
    }

    public Class<?>[] getTargetClass(){
        return Target.getClass().getInterfaces();
    }
}
