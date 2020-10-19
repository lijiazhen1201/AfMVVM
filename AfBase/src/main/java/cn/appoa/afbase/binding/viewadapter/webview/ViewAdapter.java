package cn.appoa.afbase.binding.viewadapter.webview;

import android.text.TextUtils;
import android.webkit.WebView;

import androidx.databinding.BindingAdapter;

public class ViewAdapter {

    /**
     * 富文本style
     */
    public static final String addData =
            "<style> img { max-width:100% ; height:auto;} </style>"
                    + "<div style=\"margin:0 8px;\">";

    @BindingAdapter({"render"})
    public static void loadHtml(WebView webView, final String html) {
        if (!TextUtils.isEmpty(html)) {
            webView.loadDataWithBaseURL(null, addData + html, "text/html", "UTF-8", null);
        }
    }
}
