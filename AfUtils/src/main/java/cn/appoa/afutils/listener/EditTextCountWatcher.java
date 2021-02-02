package cn.appoa.afutils.listener;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.TextView;

/**
 * 输入数量检测
 */
public class EditTextCountWatcher implements TextWatcher {

    private TextView tv;
    private int max;

    public EditTextCountWatcher(TextView tv, int max) {
        this.tv = tv;
        this.max = max;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        int count = TextUtils.isEmpty(s) ? 0 : s.length();
        tv.setText(count + "/" + max);
    }
}
