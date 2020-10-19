package cn.appoa.afbase.binding.command;

public interface BindingConsumer<T> {
    void call(T t);
}
