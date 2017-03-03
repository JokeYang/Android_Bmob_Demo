package com.njupt.school.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.njupt.R;
import com.njupt.school.model.User;
import com.njupt.utils.Utils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by YYT on 2015/11/30.
 */
/*
* 登录界面*/
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private EditText edit_account ,edit_password;
    private ImageView img_visible;
    private  TextView text_forget,text_register;
    private Button btn_login,btn_find_password,btn_login_message,btn_cancel;
    private boolean isHidden=true;
    private static final String BMOB_APPLICATION_ID = "409a688e75fad970e232617fc15da253";
    private PopupWindow popupWindow;
    private View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        Bmob.initialize(this, BMOB_APPLICATION_ID);
    }
    private void initView()
    {
        edit_account = (EditText) findViewById(R.id.edit_account);
        edit_password = (EditText) findViewById(R.id.edit_password);
        img_visible = (ImageView) findViewById(R.id.password_visible);
        text_forget = (TextView) findViewById(R.id.text_forget);
        text_register = (TextView) findViewById(R.id.text_register);
        btn_login = (Button) findViewById(R.id.btn_login);
        img_visible.setOnClickListener(this);
        text_forget.setOnClickListener(this);
        text_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }
    private void initPopWindow()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.popupwindow,null);
        popupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha=0.7f;//设置popWindow弹出时,背景变淡
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(LoginActivity.this.findViewById(R.id.login), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        popupWindow.setFocusable(true);//popWindow获得焦点
        popupWindow.setOutsideTouchable(true);//点击popWindow区域外popWindow消失
        popupWindow.setAnimationStyle(R.style.animation);
        popupWindow.update();
        btn_find_password = (Button) view.findViewById(R.id.btn_find_password);
        btn_login_message = (Button) view.findViewById(R.id.btn_message_login);
        btn_cancel = (Button)view.findViewById(R.id.btn_cancel);
        btn_find_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDismiss();
                Intent findPassword = new Intent(LoginActivity.this,FindPasswordActivity.class);
                startActivity(findPassword);
                finish();
            }
        });
        btn_login_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnDismiss();
                Intent loginMessage = new Intent(LoginActivity.this,LoginMesageActivity.class);
                startActivity(loginMessage);
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               OnDismiss();
            }
        });
        view.setFocusableInTouchMode(true);
        view.setOnClickListener(this);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    OnDismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setOnDismissListener(new onDismissListener());
    }
    class onDismissListener implements PopupWindow.OnDismissListener{
        @Override
        public void onDismiss() {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 1f;//恢复背景亮度
            getWindow().setAttributes(lp);
        }
    }
    private void OnDismiss()
    {
        if (popupWindow!=null&&popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow=null;
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 1f;//恢复背景亮度
            getWindow().setAttributes(lp);
        }
    }
    private void login()
    {
        String account = edit_account.getText().toString();
        String password = edit_password.getText().toString();
        if (!Utils.checkNetwork(this))
        {
            Utils.toast(LoginActivity.this,"当前无网络连接，请连接网络楼重试");
        }else
         if (Utils.isPhoneNum(account))
        {
            if (TextUtils.isEmpty(account))
            {
                Utils.toast(LoginActivity.this, "手机号码不能为空!");
            }else
            if (TextUtils.isEmpty(password))
            {
                Utils.toast(LoginActivity.this, "密码不能为空!");
            }
            BmobUser.loginByAccount(this, account, password, new LogInListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        Utils.toast(LoginActivity.this, "登录成功!");
                        Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(toMain);
                        finish();
                    } else {
                        Utils.toast(LoginActivity.this, "登录失败!");
                    }
                }
            });
        }else {
            Utils.toast(LoginActivity.this,"请输入正确的手机号码!");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.password_visible:
                if(isHidden) {
                    img_visible.setImageResource(R.drawable.password_show);
                    edit_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    img_visible.setImageResource(R.drawable.password_hide);
                    edit_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                break;
            case R.id.btn_login:
                    login();
                break;
            case R.id.text_forget:
                initPopWindow();
                break;
            case R.id.text_register:
                Intent toRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(toRegister);
                finish();
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
