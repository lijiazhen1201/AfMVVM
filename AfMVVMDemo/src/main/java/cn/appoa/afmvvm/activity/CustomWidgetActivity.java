package cn.appoa.afmvvm.activity;

import java.util.ArrayList;
import java.util.List;

import cn.appoa.afmvvm.activity.widget.BannerActivity;
import cn.appoa.afmvvm.activity.widget.CenterSquareImageViewActivity;
import cn.appoa.afmvvm.activity.widget.FlowLayoutActivity;
import cn.appoa.afmvvm.activity.widget.GridPasswordViewActivity;
import cn.appoa.afmvvm.activity.widget.HeaderGridViewActivity;
import cn.appoa.afmvvm.activity.widget.HeightWrapViewPagerActivity;
import cn.appoa.afmvvm.activity.widget.MaxHeightLayoutActivity;
import cn.appoa.afmvvm.activity.widget.NoScrollViewActivity;
import cn.appoa.afmvvm.activity.widget.NumberAnimTextViewActivity;
import cn.appoa.afmvvm.activity.widget.RatioRelativeLayoutActivity;
import cn.appoa.afmvvm.activity.widget.SideBarActivity;
import cn.appoa.afmvvm.activity.widget.SuperImageViewActivity;
import cn.appoa.afmvvm.activity.widget.SwipeMenuDelLayoutActivity;
import cn.appoa.afmvvm.activity.widget.UPMarqueeViewActivity;
import cn.appoa.afmvvm.activity.widget.WheelViewActivity;
import cn.appoa.afmvvm.bean.MainMenu;

/**
 * 自定义控件
 */
public class CustomWidgetActivity extends MainMenuActivity {

    @Override
    protected String initTitle() {
        return "自定义控件";
    }

    @Override
    protected List<MainMenu> initMainMenuList() {
        List<MainMenu> dataList = new ArrayList<>();
        dataList.add(new MainMenu("BannerView\n【轮播图】", BannerActivity.class));
        dataList.add(new MainMenu("HeaderGridView\n【可添加头布局和脚布局的GridView】", HeaderGridViewActivity.class));
        dataList.add(new MainMenu("FlowLayout\n【自动换行布局，流式布局，商品规格选择】", FlowLayoutActivity.class));
        dataList.add(new MainMenu("GridPasswordView\n【支付密码输入框】", GridPasswordViewActivity.class));
        dataList.add(new MainMenu("CenterSquareImageView\n【图片截取正中间的正方形部分的ImageView】", CenterSquareImageViewActivity.class));
        dataList.add(new MainMenu("SuperImageView\n【圆形，圆角，带边框的ImageView】", SuperImageViewActivity.class));
        dataList.add(new MainMenu("MaxHeightLayout\n【可设置最大高度的FrameLayout】", MaxHeightLayoutActivity.class));
        dataList.add(new MainMenu("RatioRelativeLayout\n【可设置宽高比的RelativeLayout】", RatioRelativeLayoutActivity.class));
        dataList.add(new MainMenu("SwipeMenuDelLayout\n【Item侧滑删除菜单Layout】", SwipeMenuDelLayoutActivity.class));
        dataList.add(new MainMenu("NoScrollView\n【ScrollView嵌套滑动布局】", NoScrollViewActivity.class));
        dataList.add(new MainMenu("HeightWrapViewPager\n【自动适应图片高度的ViewPager】", HeightWrapViewPagerActivity.class));
        dataList.add(new MainMenu("UPMarqueeView\n【仿淘宝头条，向上翻页】", UPMarqueeViewActivity.class));
        dataList.add(new MainMenu("SideBar\n【字母表导航，城市选择】", SideBarActivity.class));
        dataList.add(new MainMenu("NumberAnimTextView\n【数字增加和减小动画TextView】", NumberAnimTextViewActivity.class));
        dataList.add(new MainMenu("WheelView\n【滚轮控件，各种底部滚轮选择框】", WheelViewActivity.class));
        return dataList;
    }
}
