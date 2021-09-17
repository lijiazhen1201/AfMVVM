package cn.appoa.afmvvm.activity;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.activity.github.AndroidAutoSizeActivity;
import cn.appoa.afmvvm.activity.github.AndroidUtilCodeActivity;
import cn.appoa.afmvvm.activity.github.AppUpdateActivity;
import cn.appoa.afmvvm.activity.github.MPAndroidChartActivity;
import cn.appoa.afmvvm.activity.github.NCalendarActivity;
import cn.appoa.afmvvm.activity.github.OkGoActivity;
import cn.appoa.afmvvm.activity.github.PRDownloaderActivity;
import cn.appoa.afmvvm.activity.github.QMUI_AndroidActivity;
import cn.appoa.afmvvm.activity.github.SmartTableActivity;
import cn.appoa.afmvvm.activity.github.SuperButtonActivity;
import cn.appoa.afmvvm.activity.github.SuperTextViewActivity;
import cn.appoa.afmvvm.bean.MainMenu;

/**
 * Github优秀开源库
 */
public class GithubProjectActivity extends MainMenuActivity {

    @Override
    protected String initTitle() {
        return "Github优秀开源库";
    }

    @Override
    protected List<MainMenu> initMainMenuList() {
        List<MainMenu> dataList = new ArrayList<>();
        dataList.add(new MainMenu("AndroidAutoSize\n【今日头条屏幕适配方案终极版，一个极低成本的 Android 屏幕适配方案。】", AndroidAutoSizeActivity.class));
        dataList.add(new MainMenu("AndroidUtilCode\n【AndroidUtilCode是一个强大易用的安卓工具类库，它合理地封装了安卓开发中常用的函数，具有完善的 Demo 和单元测试，利用其封装好的 APIs 可以大大提高开发效率。】", AndroidUtilCodeActivity.class));
        dataList.add(new MainMenu("AppUpdate\n【Android 版本更新 a library for android version update】", AppUpdateActivity.class));
        dataList.add(new MainMenu("MPAndroidChart\n【A powerful Android chart view / graph view library, supporting line- bar- pie- radar- bubble- and candlestick charts as well as scaling, dragging and animations.】", MPAndroidChartActivity.class));
        dataList.add(new MainMenu("NCalendar\n【一款安卓日历，仿miui，钉钉，华为的日历，万年历、365、周日历，月日历，月视图、周视图滑动切换，农历，节气，Andriod Calendar , MIUI Calendar,小米日历】", NCalendarActivity.class));
        dataList.add(new MainMenu("OkGo\n【OkGo - 3.0 震撼来袭，该库是基于 Http 协议，封装了 OkHttp 的网络请求框架，比 Retrofit 更简单易用，支持 RxJava，RxJava2，支持自定义缓存，支持批量断点下载管理和批量上传管理功能】", OkGoActivity.class));
        dataList.add(new MainMenu("PRDownloader\n【A file downloader library for Android with pause and resume support】", PRDownloaderActivity.class));
        dataList.add(new MainMenu("QMUI_Android\n【QMUI Android 的设计目的是用于辅助快速搭建一个具备基本设计还原效果的 Android 项目，同时利用自身提供的丰富控件及兼容处理，让开发者能专注于业务需求而无需耗费精力在基础代码的设计上。不管是新项目的创建，或是已有项目的维护，均可使开发效率和项目质量得到大幅度提升。】", QMUI_AndroidActivity.class));
        dataList.add(new MainMenu("SmartTable\n【一款android自动生成表格框架---An Android automatically generated table framework】", SmartTableActivity.class));
        dataList.add(new MainMenu("SuperButton\n【一个使用简单且能满足日常各种需求的按钮。】", SuperButtonActivity.class));
        dataList.add(new MainMenu("SuperTextView\n【SuperTextView 的与众不同在于，它只是一个简单的控件元素，但却不仅仅是一个控件。它生而灵动多变，强大的内嵌逻辑，为你持续提供丰富多彩却异常简单的开发支持。】", SuperTextViewActivity.class));
        return dataList;
    }
}
