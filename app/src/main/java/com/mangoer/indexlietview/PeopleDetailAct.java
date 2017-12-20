package com.mangoer.indexlietview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName PeopleDetailAct
 * @Description TODO()
 * @author Mangoer
 * @Date 2017/12/20 13:51
 */
public class PeopleDetailAct extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.img_shot)
    public void clickShot() {

        //View是你需要截图的View
        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap != null) {
            try {
                // 获取系统相册路径
                String sdCardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
                // 图片文件路径
                String filePath = sdCardPath +File.separator + "camera"+File.separator + "screenshot.jpg";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
                os.flush();
                os.close();
                Toast.makeText(this,"截图保存在系统相册 "+filePath,Toast.LENGTH_LONG).show();
            } catch (Exception e) {
            }
        }
        // 获取状态栏高度
        /*Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int statusBarHeight = rect.top;
        //获取屏幕宽高
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        //去掉标题栏获取截图
        Bitmap bitmap = Bitmap.createBitmap(b, 0, statusBarHeight, width, height-statusBarHeight);
        view.destroyDrawingCache();*/

    }

    Ringtone r;
    //提示音
    private void startAlarm() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (notification == null) return;
        r = RingtoneManager.getRingtone(this, notification);
        r.play();
    }

    @OnClick(R.id.iv_sendemail)
    public void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:1754575493@qq.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "这是邮件的主题部分");
        intent.putExtra(Intent.EXTRA_TEXT, "这是邮件的正文部分");
        startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (r != null && r.isPlaying()) {
            r.stop();
        }
        ButterKnife.unbind(this);
    }
}
