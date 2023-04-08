package com.xf.psychology.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.bean.UserBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.util.PreferenceUtil;
import com.xf.psychology.util.SomeUtil;

public class LoginActivity extends AppCompatActivity {
    private EditText phoneEdit;
    private EditText pwdEdit;
    private TextView loginBtn;
    private ImageView backBtn;
    private View phoneContent;
    private View pwdContent;
    private TextView goRegisterBtn;

    private boolean isCheck = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        findViewsById();
        initListener();

    }

    private void initListener() {
        goRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, Pair.create(phoneContent, "phone"), Pair.create(pwdContent, "pwdContent")).toBundle());
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void login() {
        String phone = phoneEdit.getText().toString().trim();
        String pwd = pwdEdit.getText().toString().trim();
        if (phone.isEmpty()) {
            phoneEdit.setError("请输入手机号");
            return;
        }
        if (!SomeUtil.isPhone(phone)) {
            phoneEdit.setError("请输入正确的手机号");
            return;
        }
        UserBean registeredUser = DBCreator.getUserDao().queryUserByPhone(phone);
        Log.d("TAG", "registeredUser: " + registeredUser);
        if (pwd.isEmpty()) {
            pwdEdit.setError("请输入密码");
            return;
        }
        if (!SomeUtil.isTruePwd(pwd)) {
            pwdEdit.setError("请输入正确的密码格式");
            Toast.makeText(this, "密码格式必须是长度为6位的数字", Toast.LENGTH_SHORT).show();
            return;
        }
        if (registeredUser == null) {
            Toast.makeText(this, "该账号未注册，请先注册", Toast.LENGTH_SHORT).show();
        } else {
            //密码正确登录成功
            if (pwd.equals(registeredUser.pwd)) {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                App.user = DBCreator.getUserDao().queryUserByPhone(phone);
                PreferenceUtil.getInstance().save("logger", phone);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "账号或密码错误请重新输入", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void findViewsById() {
        phoneEdit = findViewById(R.id.phoneEdit);
        pwdEdit = findViewById(R.id.pwdEdit);
        loginBtn = findViewById(R.id.loginBtn);
        goRegisterBtn = findViewById(R.id.goRegisterBtn);
        backBtn = findViewById(R.id.backBtn);
        phoneContent = findViewById(R.id.phoneContent);
        pwdContent = findViewById(R.id.pwdContent);


    }
}