package cn.nevercode.module.main.activity;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Date;

import cn.nevercode.base.BaseActivity;
import cn.nevercode.cola.R;
import cn.nevercode.utils.DownloadUtils;

public class AlbumActivity extends BaseActivity {
    TextView download;
    TextView copy;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        PhotoView photoView = findViewById(R.id.image);
        download = findViewById(R.id.download);
        copy = findViewById(R.id.copy);

        url = getIntent().getStringExtra("img");
        Glide.with(this).load(url).into(photoView);

        download.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
            new DownloadUtils(AlbumActivity.this, url.replaceAll("_mini", ""), new Date().getTime() + ".jpg");

        });
        copy.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData clipData = ClipData.newPlainText(null, url.replaceAll("_mini", ""));

            clipboard.setPrimaryClip(clipData);
        });

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

}