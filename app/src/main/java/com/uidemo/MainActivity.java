package com.uidemo;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.uidemo.view.DynamicLayoutView;


public class MainActivity extends AppCompatActivity {

    private Button btnPlayView = null;

    private DynamicLayoutView dynamicLayoutView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        btnPlayView = findViewById(R.id.btn_playview);
        dynamicLayoutView = findViewById(R.id.dynamic_layout_view);
    }

    private void initEvent() {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_playview:
                        dynamicLayoutView.setIvUser(R.drawable.user);
                        dynamicLayoutView.setTvUser("Jack");
                        dynamicLayoutView.setStar(1);
                        break;
                    default:
                        break;
                }
            }
        };

        btnPlayView.setOnClickListener(clickListener);
    }
}
