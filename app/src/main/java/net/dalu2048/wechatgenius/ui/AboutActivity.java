/*
 * ************************************************************
 * 文件：AboutActivity.java  模块：app  项目：WeChatGenius
 * 当前修改时间：2018年08月20日 19:34:19
 * 上次修改时间：2018年08月20日 19:34:18
 * 作者：大路
 * Copyright (c) 2018
 * ************************************************************
 */

package net.dalu2048.wechatgenius.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import net.dalu2048.wechatgenius.MainActivity;
import net.dalu2048.wechatgenius.R;
import net.dalu2048.wechatgenius.entity.AppInfo;
import net.dalu2048.wechatgenius.util.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends Activity {
    @BindView(R.id.topbar) QMUITopBar mTopBar;
    @BindView(R.id.version) TextView mVersionTextView;
    @BindView(R.id.about_list) QMUIGroupListView mAboutGroupListView;
    @BindView(R.id.copyright) TextView mCopyrightTextView;

    final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_about, null);
        ButterKnife.bind(this, root);
        //初始化状态栏
        initTopBar();
        //设置view
        setContentView(root);

        //初始化about列表内容
        initAboutList();
    }

    //初始化状态栏
    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTopBar.setTitle(getString(R.string.activity_title_about));
    }

    //列表项点击事件处理
    View.OnClickListener mOnClickListenerListItem = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //region 列表项点击事件
            QMUICommonListItemView listItemView = (QMUICommonListItemView) view;
            int tag = (int) listItemView.getTag();
            Intent intent;

            switch (tag) {
                case R.id.listitem_tag_1:  //复制微信
                    copyToClipboard("微信", listItemView.getDetailText().toString());
                    break;
                case R.id.listitem_tag_2:  //复制QQ
                    copyToClipboard("QQ", listItemView.getDetailText().toString());
                    break;
                case R.id.listitem_tag_3:  //访问GitHub网址
                    openWebsite(listItemView.getDetailText().toString());
                    break;
            }
            //endregion
        }
    };

    //访问网址
    private void openWebsite(String websiteUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(websiteUrl));
        startActivity(intent);
    }

    //复制数据到剪贴板
    private void copyToClipboard(String label, String string) {
        //复制
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(label, string);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this, "成功复制" + label, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "获取剪贴板失败，无法复制", Toast.LENGTH_SHORT).show();
        }
    }


    //初始化QMUIGroupListView
    private void initAboutList() {
        //初始化LOGO下面的名称和版本号
        mVersionTextView.setText(String.format("%s V%s", getResources().getString(R.string.activity_title_main),
                AppUtils.getVersionName(this)));

        //作者
        QMUICommonListItemView itemAuthor = mAboutGroupListView.createItemView("作者");
        itemAuthor.setDetailText("大路");
        //微信
        QMUICommonListItemView itemWechat = mAboutGroupListView.createItemView("微信");
        itemWechat.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        itemWechat.setDetailText("dalu2048");
        itemWechat.setTag(R.id.listitem_tag_1);
        //QQ
        QMUICommonListItemView itemQQ = mAboutGroupListView.createItemView("QQ");
        itemQQ.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        itemQQ.setDetailText("1801099979");
        itemQQ.setTag(R.id.listitem_tag_2);
        //GitHub主页
        QMUICommonListItemView itemGitHub = mAboutGroupListView.createItemView("GitHub主页");
        itemGitHub.setOrientation(QMUICommonListItemView.VERTICAL);
        itemGitHub.setDetailText("https://github.com/dalu2048/WeChatGenius");
        itemGitHub.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        itemGitHub.setTag(R.id.listitem_tag_3);
        QMUIGroupListView.newSection(this)
                .setDescription(getString(R.string.about_description))
                .addItemView(itemAuthor, null)
                .addItemView(itemWechat, mOnClickListenerListItem)
                .addItemView(itemQQ, mOnClickListenerListItem)
                .addItemView(itemGitHub, mOnClickListenerListItem)
                .addTo(mAboutGroupListView);

        //初始化页面底部的版权
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        String currentYear = dateFormat.format(new java.util.Date());
        mCopyrightTextView.setText(String.format(getResources().getString(R.string.about_copyright), currentYear));

    }
}
