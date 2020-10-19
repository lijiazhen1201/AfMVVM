package cn.appoa.afbase.binding.viewadapter.view;


import android.view.View;

import androidx.databinding.BindingAdapter;
import cn.appoa.afbase.binding.command.BindingCommand;
import cn.appoa.afutils.app.FastClickUtils;

public class ViewAdapter {

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @BindingAdapter(value = {"onClickCommand", "isThrottleFirst"}, requireAll = false)
    public static void onClickCommand(View view, final BindingCommand clickCommand, final boolean isThrottleFirst) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isThrottleFirst) {
                    if (FastClickUtils.isFastClick()) {
                        return;
                    }
                    if (clickCommand != null) {
                        clickCommand.execute();
                    }
                } else {
                    if (clickCommand != null) {
                        clickCommand.execute();
                    }
                }
            }
        });

    }

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     * onClickValue 回调的数据
     */
    @BindingAdapter(value = {"onClickCommandValue", "isThrottleFirst", "onClickValue"}, requireAll = false)
    public static <T> void onClickCommandValue(View view, final BindingCommand<T> clickCommand,
                                               final boolean isThrottleFirst, final T onClickValue) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isThrottleFirst) {
                    if (FastClickUtils.isFastClick()) {
                        return;
                    }
                    if (clickCommand != null) {
                        clickCommand.execute(onClickValue);
                    }
                } else {
                    if (clickCommand != null) {
                        clickCommand.execute(onClickValue);
                    }
                }
            }
        });

    }

    /**
     * view的onLongClick事件绑定
     */
    @BindingAdapter(value = {"onLongClickCommand"}, requireAll = false)
    public static void onLongClickCommand(View view, final BindingCommand clickCommand) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (clickCommand != null) {
                    clickCommand.execute();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * view的onLongClick事件绑定
     */
    @BindingAdapter(value = {"onLongClickCommandValue", "onLongClickValue"}, requireAll = false)
    public static <T> void onLongClickCommandValue(View view, final BindingCommand<T> clickCommand
            , final T onClickValue) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (clickCommand != null) {
                    clickCommand.execute(onClickValue);
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 回调控件本身
     *
     * @param currentView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"currentView"}, requireAll = false)
    public static void replyCurrentView(View currentView, BindingCommand bindingCommand) {
        if (bindingCommand != null) {
            bindingCommand.execute(currentView);
        }
    }

    /**
     * view是否需要获取焦点
     */
    @BindingAdapter({"requestFocus"})
    public static void requestFocusCommand(View view, final Boolean needRequestFocus) {
        if (needRequestFocus) {
            view.setFocusableInTouchMode(true);
            view.requestFocus();
        } else {
            view.clearFocus();
        }
    }

    /**
     * view的焦点发生变化的事件绑定
     */
    @BindingAdapter({"onFocusChangeCommand"})
    public static void onFocusChangeCommand(View view, final BindingCommand<Boolean> onFocusChangeCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (onFocusChangeCommand != null) {
                    onFocusChangeCommand.execute(hasFocus);
                }
            }
        });
    }

    /**
     * view的显示隐藏
     */
    @BindingAdapter(value = {"isVisible"}, requireAll = false)
    public static void isVisible(View view, final Boolean visibility) {
        if (visibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
