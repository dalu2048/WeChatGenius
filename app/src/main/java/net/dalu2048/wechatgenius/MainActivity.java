package net.dalu2048.wechatgenius;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import net.dalu2048.wechatgenius.entity.AppInfo;
import net.dalu2048.wechatgenius.net.HttpRequest;

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

        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求网络
                HttpRequest httpRequest = new HttpRequest();
                String strResponse = httpRequest.getData();
                Log.d(TAG, strResponse);
            }
        }).start();

    }

    //初始化状态栏
    private void initTopBar() {
        mTopBar.setTitle(getResources().getString(R.string.activity_title_main));
    }

    //初始化QMUIGroupListView
    private void initMainContentView() {
        AppInfo appInfo = AppInfo.getInstance();
        boolean boolResult;
        String strResult;

        //region 系统版本与root状态
        //系统版本
        QMUICommonListItemView listItemSystem = mGroupListView.createItemView("系统版本");
        listItemSystem.setDetailText("Android V" + appInfo.getAndroidVersionName());
        boolResult = appInfo.isSupportAndroid();
        listItemSystem.setImageDrawable(ContextCompat.getDrawable(this, boolResult ? R.drawable.qmui_icon_checkbox_checked : R.mipmap.icon_error));
        //是否已ROOT
        QMUICommonListItemView listItemRoot = mGroupListView.createItemView("是否ROOT");
        boolResult = appInfo.isDeviceRooted();
        strResult = boolResult ? "已ROOT" : "未ROOT";
        listItemRoot.setDetailText(strResult);
        listItemRoot.setImageDrawable(ContextCompat.getDrawable(this, boolResult ? R.drawable.qmui_icon_checkbox_checked : R.mipmap.icon_error));
        QMUIGroupListView.newSection(this)
                .setTitle("系统状态")
                .addItemView(listItemSystem, null)
                .addItemView(listItemRoot, null)
                .addTo(mGroupListView);
        //endregion

        //region 微信版本状态
        //微信安装版本
        QMUICommonListItemView listItemWechatVersion = mGroupListView.createItemView("微信版本");
        boolResult = appInfo.isWechatInstall();
        listItemWechatVersion.setDetailText(boolResult ? "V" + appInfo.getWechatVersionName() : "未安装");
        boolResult = appInfo.isSupportWechat();
        listItemWechatVersion.setImageDrawable(ContextCompat.getDrawable(this, boolResult ? R.drawable.qmui_icon_checkbox_checked : R.mipmap.icon_error));
        QMUIGroupListView.newSection(this)
                .setTitle("微信状态")
                .addItemView(listItemWechatVersion, null)
                .addTo(mGroupListView);
        //endregion

        //region Xposed框架状态
        //Xposed版本
        QMUICommonListItemView listItemXposed = mGroupListView.createItemView("Xposed版本");
        boolResult = appInfo.isXposedInstall();
        listItemXposed.setDetailText(boolResult ? "V" + appInfo.getXposedVersionName() : "未安装");
        listItemXposed.setImageDrawable(ContextCompat.getDrawable(this, boolResult ? R.drawable.qmui_icon_checkbox_checked : R.mipmap.icon_error));
        //Xposed模块激活
        QMUICommonListItemView listItemXposedActive = mGroupListView.createItemView("Xposed模块激活");
        boolResult = appInfo.isXposedActive();
        listItemXposedActive.setDetailText(boolResult ? "已激活" : "未激活");
        listItemXposedActive.setImageDrawable(ContextCompat.getDrawable(this, boolResult ? R.drawable.qmui_icon_checkbox_checked : R.mipmap.icon_error));
        QMUIGroupListView.newSection(this)
                .setTitle("Xposed框架状态")
                .addItemView(listItemXposed, null)
                .addItemView(listItemXposedActive, null)
                .addTo(mGroupListView);
        //endregion

    }
}
