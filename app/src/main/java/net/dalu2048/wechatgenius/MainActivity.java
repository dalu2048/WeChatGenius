package net.dalu2048.wechatgenius;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import net.dalu2048.wechatgenius.entity.AppInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @BindView(R.id.topbar) QMUITopBar mTopBar;
    @BindView(R.id.groupListView) QMUIGroupListView mGroupListView;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        ButterKnife.bind(this, root);
        //初始化状态栏
        initTopBar();
        //设置view
        setContentView(root);

        //检测环境
        AppInfo.getInstance().ValidateEnvironment(this);
        //初始化QMUIGroupListView
        initMainContentView();
    }

    //初始化状态栏
    private void initTopBar() {
        mTopBar.setTitle(getResources().getString(R.string.activity_title_main));
    }

    //初始化QMUIGroupListView
    private void initMainContentView() {
        AppInfo appInfo = AppInfo.getInstance();
        boolean boolResult;
        String strResult = "";

        //region Xposed框架状态
        //Xposed版本
        QMUICommonListItemView listItemXposed = mGroupListView.createItemView("Xposed版本");
        boolResult = appInfo.isXposedInstall();
        listItemXposed.setDetailText(boolResult ? "V" + appInfo.getXposedVersionName() : "未安装");
        listItemXposed.setImageDrawable(getResources().getDrawable(boolResult ? R.drawable.qmui_icon_checkbox_checked : R.mipmap.icon_error));
        //Xposed模块激活
        QMUICommonListItemView listItemXposedActive = mGroupListView.createItemView("Xposed模块激活");
        boolResult = appInfo.isXposedActive();
        listItemXposedActive.setDetailText(boolResult ? "已激活" : "未激活");
        listItemXposedActive.setImageDrawable(getResources().getDrawable(boolResult ? R.drawable.qmui_icon_checkbox_checked : R.mipmap.icon_error));
        QMUIGroupListView.newSection(this)
                .setTitle("Xposed框架状态")
                .addItemView(listItemXposed, null)
                .addItemView(listItemXposedActive, null)
                .addTo(mGroupListView);
        //endregion
    }
}
