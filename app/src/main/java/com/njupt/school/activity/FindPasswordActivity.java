package com.njupt.school.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.njupt.R;
import com.njupt.utils.Utils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;

/**
 * Created by YYT on 2015/12/10.
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener{
    private EditText email_edit;
    private ImageView img_email;
    private Button btn_find_password;
    private RelativeLayout rlResetPsdFinished;
    private Button btn_back_toLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
    }
    private void initView()
    {
        img_email = (ImageView) findViewById(R.id.img_mail);
        email_edit = (EditText) findViewById(R.id.email_edit);
        btn_find_password = (Button) findViewById(R.id.btn_find_password);
        rlResetPsdFinished = (RelativeLayout) findViewById(R.id.rl_reset_psd_finished);
        btn_back_toLogin = (Button) findViewById(R.id.btn_back_to_login);
        btn_find_password.setOnClickListener(this);
        btn_back_toLogin.setOnClickListener(this);
    }
    private void sendVerifiedEmail() //找回密码：给邮箱发送验证消息
    {
        String email = email_edit.getText().toString().trim();
        if (!Utils.checkNetwork(this))
        {
            Utils.toast(FindPasswordActivity.this,"当前无网络连接，请连接网络楼重试");
        }else
        if (TextUtils.isEmpty(email))
        {
            Utils.toast(FindPasswordActivity.this,"请输入邮箱号!");
        }else if (!Utils.isEmailValid(email))
        {
            Utils.toast(FindPasswordActivity.this,"请输入正确的邮箱号!");
            return;
        }
        BmobUser.resetPasswordByEmail(this,email, new ResetPasswordByEmailListener() {
            @Override
            public void onSuccess() {
                img_email.setVisibility(View.GONE);
                email_edit.setVisibility(View.GONE);
                btn_find_password.setVisibility(View.GONE);
                rlResetPsdFinished.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(int i, String s) {
                Utils.toast(FindPasswordActivity.this,"重置密码失败!");
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_find_password:
                sendVerifiedEmail();
                break;
            case R.id.btn_back_to_login:
                Intent toLoginActivity = new Intent(FindPasswordActivity.this,LoginActivity.class);
                startActivity(toLoginActivity);
                break;
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
