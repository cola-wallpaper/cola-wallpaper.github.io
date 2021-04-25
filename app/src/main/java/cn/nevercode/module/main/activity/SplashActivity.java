package cn.nevercode.module.main.activity;

import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;

import cn.nevercode.base.BaseActivity;
import cn.nevercode.cola.R;

public class SplashActivity extends BaseActivity {

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        next = findViewById(R.id.next);
        next.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK);
            finish();
        });
    }
}