package com.xf.psychology.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.FMBean;
import com.xf.psychology.bean.ImageBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.FMUpSuccessEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

public class FmUpActivity extends BaseActivity {

    private EditText titleEdit;
    private EditText secTitleEdit;
    private EditText fmAuthorEdit;
    private TextView fileTv;
    private TextView uploadTv;
    private ImageView faceIv;
    private RadioGroup typeRG;
    private int type = -1;
    private String fmFilePath = "";
    private String fmFaceImgPath = "";
    private String fileParentPath;

    @Override
    protected void initListener() {
        uploadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEdit.getText().toString();
                String secTitle = secTitleEdit.getText().toString();
                String fmAuthor = fmAuthorEdit.getText().toString();
                if (title.isEmpty()) {
                    toast("请输入FM标题");
                    return;
                }
                if (secTitle.isEmpty()) {
                    toast("请输入FM副标题");
                    return;
                }
                if (fmAuthor.isEmpty()) {
                    toast("请输入FM主播名称");
                    return;
                }
                if (fmFilePath.isEmpty()) {
                    toast("请选择需要上传的FM音频");
                    return;
                }
                if (fmFaceImgPath.isEmpty()) {
                    toast("请选择FM的封面");
                    return;
                }
                if (type == -1) {
                    toast("请选择FM分类");
                    return;
                }
                FMBean bean = new FMBean();
                bean.fmAuthor = fmAuthor;
                bean.fmFilePath = fmFilePath;
                bean.faceFilePath = fmFaceImgPath;
                bean.fmTitle = title;
                bean.fmSecTitle = secTitle;
                bean.up = App.user.name;
                bean.upId = App.user.id;
                bean.type = type;
                DBCreator.getFMDao().insert(bean);
                EventBus.getDefault().post(new FMUpSuccessEvent());
                finish();
                toast("上传成功");
            }
        });

        typeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rbQxgl:
                        type = 1;
                        break;
                    case R.id.rbRjgx:
                        type = 2;
                        break;
                    case R.id.rbXlkp:
                        type = 3;
                        break;
                    case R.id.rbKcjz:
                        type = 4;
                        break;
                }
            }
        });

        fileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("audio/*");
                startActivityForResult(intent, 100);
            }
        });

        faceIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 101);
            }
        });
    }

    @Override
    protected void initData() {
        fileParentPath = getExternalCacheDir().getAbsolutePath();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fm_up;
    }

    @Override
    protected void findViewsById() {
        titleEdit = findViewById(R.id.titleEdit);
        secTitleEdit = findViewById(R.id.secTitleEdit);
        fmAuthorEdit = findViewById(R.id.fmAuthorEdit);
        fileTv = findViewById(R.id.fileTv);
        faceIv = findViewById(R.id.faceIv);
        typeRG = findViewById(R.id.typeRG);
        uploadTv = findViewById(R.id.uploadTv);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri uri = data.getData();
                if (uri != null) {
                    try {
                        Log.d("TAG", "onActivityResult: " + uri);
                        String s = uri.toString();
                        fileTv.setText(URLDecoder.decode(s.substring(s.lastIndexOf("/")), "utf-8"));
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        byte[] array = new byte[inputStream.available()];
                        inputStream.read(array);
                        String fileName = System.currentTimeMillis() + "_fm_" + App.user.phone + ".mp3";
                        File file = new File(getExternalCacheDir(), fileName);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(array);
                        fos.flush();
                        fos.close();
                        inputStream.close();
                        fmFilePath = file.getPath();
                        Log.d("TAG", "onActivityResult: " + fmFilePath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == 101) {
                Uri uri = data.getData();
                if (uri != null) {
                    try {
                        faceIv.setImageURI(uri);
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        byte[] array = new byte[inputStream.available()];
                        inputStream.read(array);
                        String fileName = System.currentTimeMillis() + "_img_" + App.user.phone + ".jpg";
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
}