package cn.appoa.afbase.event;

/**
 * EventBus接收者
 */
public class AfEvent {

    public String className;

    public AfEvent(String className) {
        this.className = className;
    }
}
