package com.xf.psychology.ui.activity;

import androidx.annotation.Nullable;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.BookBean;
import com.xf.psychology.event.UpBookSuccessEvent;
import com.xf.psychology.db.DBCreator;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UpBookActivity extends BaseActivity {

    private ImageView faceIv;
    private EditText bookNameEdit;
    private EditText authorEdit;
    private EditText whyWantEdit;
    private TextView uploadTv;
    private String facePicPath = "";

    @Override
    protected void initListener() {
        uploadTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = bookNameEdit.getText().toString();
                String author = authorEdit.getText().toString();
                String whyWant = whyWantEdit.getText().toString();
                if (facePicPath.isEmpty()) {
                    toast("请选择书的封面");
                    return;
                }
                if (bookName.isEmpty()) {
                    toast("请输入书名");
                    return;
                }
                if (author.isEmpty()) {
                    toast("请输入作者名");
                    return;
                }
                if (whyWant.isEmpty()) {
                    toast("请输入你的推荐理由");
                    return;
                }
                BookBean bookBean = new BookBean();
                bookBean.author = author;
                bookBean.bookName = bookName;
                bookBean.whyWant = whyWant;
                bookBean.upId = App.user.id;
                bookBean.upName = App.user.name;
                bookBean.facePicPath = facePicPath;
                DBCreator.getBookDao().insert(bookBean);
                toast("推荐成功");
                EventBus.getDefault().post(new UpBookSuccessEvent());
                finish();
            }
        });
        faceIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode == 100) {
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
                        facePicPath = file.getPath();
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_up_book;
    }

    @Override
    protected void findViewsById() {
        faceIv = findViewById(R.id.faceIv);
        authorEdit = findViewById(R.id.authorEdit);
        uploadTv = findViewById(R.id.uploadTv);
        whyWantEdit = findViewById(R.id.whyWantEdit);
        bookNameEdit = findViewById(R.id.bookNameEdit);
    }
}