package com.xf.psychology.ui.activity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

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

    private boolean isFingerprintSucceeded = false;
    private boolean isCheck = false;

    private FingerprintManager fingerprintManager;
    private FingerprintManager.AuthenticationCallback authenticationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        findViewsById();
        initListener();
        initFingerprint();
    }

    private void initFingerprint() {
        fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        authenticationCallback = new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(LoginActivity.this, "指纹认证失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);
                Toast.makeText(LoginActivity.this, "指纹认证失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                //指纹识别成功，保存用户信息并进入主界面
                UserBean registeredUser = DBCreator.getUserDao().queryUserByPhone(phoneEdit.getText().toString().trim());
                App.user = registeredUser;
                PreferenceUtil.getInstance().save("logger", registeredUser.phone);
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                //指纹识别失败，提示用户再次尝试指纹识别
                Toast.makeText(LoginActivity.this, "指纹识别失败，请再次尝试", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void findViewsById() {
        phoneEdit = findViewById(R.id.phoneEdit);
        pwdEdit = findViewById(R.id.pwdEdit);
        loginBtn = findViewById(R.id.loginBtn);
        backBtn = findViewById(R.id.backBtn);
        phoneContent = findViewById(R.id.phoneContent);
        pwdContent = findViewById(R.id.pwdContent);
        goRegisterBtn = findViewById(R.id.goRegisterBtn);
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
        //获取用户输入的手机号和密码
        String phone = phoneEdit.getText().toString().trim();
        String pwd = pwdEdit.getText().toString().trim();

        //校验手机号是否合法
        if (phone.isEmpty()) {
            phoneEdit.setError("请输入手机号");
            return;
        }
        if (!SomeUtil.isPhone(phone)) {
            phoneEdit.setError("请输入正确的手机号");
            return;
        }

        //查询用户是否已经注册
        UserBean registeredUser = DBCreator.getUserDao().queryUserByPhone(phone);
        if (registeredUser == null) {
            Toast.makeText(this, "该账号未注册，请先注册", Toast.LENGTH_SHORT).show();
            return;
        }

        //校验密码是否正确
        if (pwd.isEmpty()) {
            pwdEdit.setError("请输入密码");
            return;
        }
        if (!SomeUtil.isTruePwd(pwd)) {
            pwdEdit.setError("请输入正确的密码格式");
            Toast.makeText(this, "密码格式必须是长度为6位的数字", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!pwd.equals(registeredUser.pwd)) {
            Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        //密码输入正确，提示用户使用指纹进行登录
        Toast.makeText(this, "请使用指纹认证登录", Toast.LENGTH_SHORT).show();

        //检查设备是否支持指纹识别
        if (fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
            //启动指纹识别
            fingerprintManager.authenticate(null, null, 0, authenticationCallback, null);
        } else {
            //设备不支持指纹识别，提示用户使用其他方式登录
            Toast.makeText(this, "设备不支持指纹识别，请使用其他方式登录", Toast.LENGTH_SHORT).show();
        }
    }

    //指纹识别回调





    // 在指纹认证的回调函数中设置认证状态

}



