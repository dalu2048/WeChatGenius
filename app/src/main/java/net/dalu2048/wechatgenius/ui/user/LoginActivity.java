/*
 * ************************************************************
 * 文件：LoginActivity.java  模块：app  项目：WeChatGenius
 * 当前修改时间：2018年08月20日 17:50:43
 * 上次修改时间：2018年08月20日 17:50:42
 * 作者：大路
 * Copyright (c) 2018
 * ************************************************************
 */

package net.dalu2048.wechatgenius.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIKeyboardHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import net.dalu2048.wechatgenius.MainActivity;
import net.dalu2048.wechatgenius.R;
import net.dalu2048.wechatgenius.ui.AboutActivity;
import net.dalu2048.wechatgenius.util.RegexUtils;
import net.dalu2048.wechatgenius.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity {
    @BindView(R.id.topbar) QMUITopBar mTopBar;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        ButterKnife.bind(this, root);
        //初始化状态栏
        initTopBar();
        //设置view
        setContentView(root);

    }

    //初始化状态栏
    private void initTopBar() {
        mTopBar.addRightImageButton(R.mipmap.icon_topbar_about, R.id.empty_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(LoginActivity.this, AboutActivity.class);
                        startActivity(intent);
                    }
                });
        mTopBar.setTitle(getResources().getString(R.string.activity_title_main));
    }

    //登录按钮
    @OnClick({R.id.button_login})
    void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                onClickLogin(view);
                break;
        }
    }

    //关于、联系我们，文字点击事件处理
    @OnClick({R.id.textview_about})
    void onClickTextView(View view) {
        Intent intent = null;
//        Log.d(TAG, "登录页面，文字：" + ((TextView) view).getText() + " 被点击了。");
        switch (view.getId()) {
            case R.id.textview_about:
                intent = new Intent(LoginActivity.this, AboutActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    //登录按钮事件
    private void onClickLogin(View view) {
        String strName = ((EditText) findViewById(R.id.edittext_username)).getText().toString().trim();
        String strPassword = ((EditText) findViewById(R.id.edittext_password)).getText().toString().trim();

        if (!RegexUtils.isUsername(strName)) {
            Toast.makeText(this, "账号格式不正确！", Toast.LENGTH_SHORT).show();
            QMUIKeyboardHelper.showKeyboard((EditText) findViewById(R.id.edittext_username), 1500);
            return;
        }
        if (strPassword.length() < 6 || strPassword.length() > 20) {
            Toast.makeText(this, "密码长度为6到20位！", Toast.LENGTH_SHORT).show();
            QMUIKeyboardHelper.showKeyboard((EditText) findViewById(R.id.edittext_password), 1500);
            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        //结束本页面
        finish();
    }
}
