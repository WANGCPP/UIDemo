package com.uidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.uidemo.view.RoundProgressBar;

public class MainActivity extends AppCompatActivity {

    private Button btnPlayView = null;

    private RoundProgressBar roundProgressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        roundProgressBar = findViewById(R.id.roundprogressbar);
        btnPlayView = findViewById(R.id.btn_playview);
    }

    private void initEvent() {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_playview:
                        roundProgressBar.startScan(0, 80);
                        break;
                    default:
                        break;
                }
            }
        };

        btnPlayView.setOnClickListener(clickListener);
    }
}
