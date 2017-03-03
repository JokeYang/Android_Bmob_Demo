package com.njupt.school.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.njupt.R;
import com.njupt.school.model.User;
import com.njupt.utils.Utils;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.RequestSMSCodeListener;

/**
 * Created by YYT on 2015/12/10.
 */
public class LoginMesageActivity extends BaseActivity implements View.OnClickListener{
    private EditText phone_edit,code_edit;
    private Button btn_getCode,btn_message_login;
    private CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpswd_login_message);
        initView();
    }
    private void initView()
    {
        phone_edit = (EditText) findViewById(R.id.et_phone);
        code_edit = (EditText) findViewById(R.id.et_verify_code);
        btn_getCode = (Button) findViewById(R.id.btn_send);
        btn_message_login = (Button) findViewById(R.id.btn_message_login);
        btn_getCode.setOnClickListener(this);
        btn_message_login.setOnClickListener(this);
    }
    private void requestSmsCode() {
        String phone =phone_edit.getText().toString().trim();
        if (!Utils.checkNetwork(this))
        {
            Utils.toast(LoginMesageActivity.this,"当前无网络连接，请连接网络楼重试");
        }else
        if (TextUtils.isEmpty(phone)) {
            Utils.toast(LoginMesageActivity.this, "请先输入手机号!");
        }else
        if (Utils.isPhoneNum(phone)) {
                countDownTimer = new CountDownTimer(60000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        btn_getCode.setText((millisUntilFinished / 1000) + "秒后重发");
                    }
                    @Override
                    public void onFinish() {
                        btn_getCode.setText("重新发送验证码");
                    }
                }.start();
            BmobSMS.requestSMSCode(LoginMesageActivity.this, phone_edit.getText().toString().trim(), "用户登录", new RequestSMSCodeListener() {
                @Override
                public void done(Integer integer, BmobException e) {
                    if (e == null) {
                    }
                }
            });
        }else {
            Utils.toast(LoginMesageActivity.this, "请输入正确的手机号!");
        }
    }
    private void login_message()
    {
        String phone =phone_edit.getText().toString().trim();
        String code = code_edit.getText().toString().trim();
        if (!Utils.checkNetwork(LoginMesageActivity.this)) {
            Utils.toast(LoginMesageActivity.this,"当前无网络连接，请连接网络楼重试");
        }else
        if (TextUtils.isEmpty(phone)) {
            Utils.toast(LoginMesageActivity.this, "请先输入手机号!");
        }else
        if (TextUtils.isEmpty(code)) {
            Utils.toast(LoginMesageActivity.this,"验证码不能为空");
        }
        BmobUser.loginBySMSCode(this,phone,code, new LogInListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (user != null) {
                    Intent main = new Intent(LoginMesageActivity.this,MainActivity.class);
                    startActivity(main);
                    finish();
                }
            }
        });
    }
    @Override
     public void onClick(View v) {
        switch (v.getId())
        {
           case  R.id.btn_send:
               requestSmsCode();
            break;
            case R.id.btn_message_login:
                login_message();
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