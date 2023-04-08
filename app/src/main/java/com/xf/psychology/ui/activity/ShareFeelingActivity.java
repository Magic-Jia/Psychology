package com.xf.psychology.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.CommonAdapter;
import com.xf.psychology.adapter.ViewHolder;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.ImageBean;
import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.event.ShareSuccessEvent;
import com.xf.psychology.view.LimitEditText;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ShareFeelingActivity extends BaseActivity {
    private LimitEditText edit;
    private TextView submit;
    private RecyclerView imageRecycler;
    private List<ImageBean> imageBeans = new ArrayList<>();
    private CommonAdapter<ImageBean> adapter = new CommonAdapter<ImageBean>(R.layout.item_image, imageBeans) {
        @Override
        public void bind(ViewHolder holder, ImageBean imageBean, int position) {
            ImageView view = holder.getView(R.id.image);
            if (imageBean.filePath == null || imageBean.filePath.equals("")) {
                view.setImageResource(imageBean.addIcon);
            } else {
                Glide.with(ShareFeelingActivity.this).load(imageBean.filePath).into(view);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == imageBeans.size() - 1) {
                        if (imageBeans.size() == 3) {
                            toast("最多只能添加两张图片");
                            return;
                        }
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 100);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (position != imageBeans.size() - 1) {
                        imageBeans.remove(position);
                        adapter.notifyDataSetChanged();
                        toast("删除成功");
                    }
                    return false;
                }
            });
        }
    };

    @Override
    protected void initListener() {
        imageBeans.add(new ImageBean());
        imageRecycler.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.isMax()) {
                    edit.setError("最多可以输入" + edit.getOffset() + "个字符哦");
                    toast("最多可以输入" + edit.getOffset() + "个字符哦");
                    return;
                }
                if (edit.isEmpty()) {
                    edit.setError("请输入内容");
                    toast("请输入内容");
                    return;
                }
                String name;
                name = App.user.name;
                ShareBeanXF shareBeanXF = new ShareBeanXF();
                shareBeanXF.authorId = App.user.id;
                shareBeanXF.authorNickName = name;
                shareBeanXF.authorIcon = App.user.iconPath;
                shareBeanXF.content = edit.getText().toString().trim();
                shareBeanXF.time = System.currentTimeMillis();
                List<String> picPaths = new ArrayList<>();
                for (int i = 0; i < imageBeans.size() - 1; i++) {
                    picPaths.add(imageBeans.get(i).filePath);
                }
                shareBeanXF.picPaths = picPaths;
                DBCreator.getShareDao().insert(shareBeanXF);
                toast("分享成功");
                EventBus.getDefault().post(new ShareSuccessEvent());
                finish();

            }
        });

    }

    private String fileParentPath;

    @Override
    protected void initData() {
        fileParentPath = getExternalCacheDir().getAbsolutePath();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Uri uri = data.getData();
                if (uri != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(uri);
                        byte[] array = new byte[inputStream.available()];
                        inputStream.read(array);
                        String fileName = System.currentTimeMillis() + "_" + App.user.phone + ".jpg";
                        File file = new File(getExternalCacheDir(), fileName);
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(array);
                        fos.flush();
                        fos.close();
                        inputStream.close();
                        String filePath = fileParentPath + "/" + fileName;
                        ImageBean imageBean = new ImageBean();
                        imageBean.filePath = filePath;
                        Log.d("TAG", "onActivityResult: " + filePath);
                        imageBeans.add(imageBeans.size() - 1, imageBean);
                        adapter.notifyDataSetChanged();
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
        return R.layout.activity_share_feeling;
    }

    @Override
    protected void findViewsById() {
        edit = findViewById(R.id.edit);
        submit = findViewById(R.id.submit);
        imageRecycler = findViewById(R.id.imageRecycler);
    }
}
