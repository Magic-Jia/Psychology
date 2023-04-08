package com.xf.psychology.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.jaeger.library.StatusBarUtil;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.bean.UserBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.util.SomeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 注册
 */
public class RegisterActivity extends AppCompatActivity {
    private View backBtn;
    private EditText phoneEdit;
    private EditText pwdEdit;
    private EditText nameEdit;
    private TextView registerBtn;
    private TextView titleTv;
    private CircleImageView headView;

    private boolean isRegister = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewsById();
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        initListener();
    }

    private void initListener() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });

    }

    private String fmFaceImgPath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri uri = data.getData();
                if (uri != null) {
                    try {
                        headView.setImageURI(uri);
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        byte[] array = new byte[inputStream.available()];
                        inputStream.read(array);
                        String fileName = System.currentTimeMillis() + "_img_head.jpg";
                        File file = new File(getExternalCacheDir(), fileName);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(array);
                        fos.flush();
                        fos.close();
                        inputStream.close();
                        fmFaceImgPath = file.getPath();
                        Log.d("TAG", "onActivityResult: " + file.getPath());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void register() {
        String phone = phoneEdit.getText().toString().trim();
        String pwd = pwdEdit.getText().toString().trim();
        String name = nameEdit.getText().toString().trim();

        if (phone.isEmpty()) {
            phoneEdit.setError("请输入手机号");
            return;
        }
        if (!SomeUtil.isPhone(phone)) {
            phoneEdit.setError("请输入正确的手机号");
            return;
        }
        if (name.isEmpty()) {
            nameEdit.setError("请输入昵称");
            return;
        }
        if (fmFaceImgPath.isEmpty()) {
            Toast.makeText(this, "请选择要上传的头像", Toast.LENGTH_SHORT).show();
            return;
        }
        UserBean registeredUser = null;
        if (isRegister) {
            registeredUser = DBCreator.getUserDao().queryUserByPhone(phone);
        }
        Log.d("TAG", "register: " + (registeredUser == null));
        if (registeredUser != null) {
            Toast.makeText(this, "该手机号已被注册", Toast.LENGTH_SHORT).show();
        } else {
            if (pwd.isEmpty()) {
                pwdEdit.setError("请输入密码");
                return;
            }
            if (!SomeUtil.isTruePwd(pwd)) {
                pwdEdit.setError("请输入正确的密码格式");
                Toast.makeText(this, "密码格式必须是长度为6位的数字", Toast.LENGTH_SHORT).show();
                return;
            }
            UserBean user = new UserBean();
            user.phone = phone;
            user.pwd = pwd;
            user.name = name;
            user.iconPath = fmFaceImgPath;
            DBCreator.getUserDao().registerUser(user);
            Toast.makeText(this, "注册成功快去登录吧", Toast.LENGTH_SHORT).show();
            finish();
        }


    }

    private void findViewsById() {
        backBtn = findViewById(R.id.backBtn);
        phoneEdit = findViewById(R.id.phoneEdit);
        pwdEdit = findViewById(R.id.pwdEdit);
        nameEdit = findViewById(R.id.nameEdit);
        registerBtn = findViewById(R.id.registerBtn);
        titleTv = findViewById(R.id.titleTv);
        headView = findViewById(R.id.headView);

    }
}