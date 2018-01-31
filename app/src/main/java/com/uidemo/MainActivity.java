package com.uidemo;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.uidemo.view.DynamicLayoutView;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar = null;

    private Button btnPlayView = null;

    private ImageButton ibSearch = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        ibSearch = findViewById(R.id.ib_search);
        btnPlayView = findViewById(R.id.btn_playview);
    }

    private void initEvent() {

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ib_search:
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.right, R.anim.left);
                        break;
                    case R.id.btn_playview:
                        break;
                    default:
                        break;
                }
            }
        };

        ibSearch.setOnClickListener(clickListener);
        btnPlayView.setOnClickListener(clickListener);
    }
}
