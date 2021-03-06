package com.uidemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.uidemo.view.DragLayout;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar toolbar = null;

    private Button btnPlayView = null;

    private ImageButton ibSearch = null;

    private NavigationView navigationView = null;

    private DragLayout dragLayout = null;


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
        navigationView = findViewById(R.id.nav);

        dragLayout = findViewById(R.id.draglayout);


    }

    private void initEvent() {

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.ib_search:
                        Intent intent = new Intent(MainActivity.this, SwipeRefreshActivity.class);
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

        dragLayout.getDragView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick()");
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.popup_add:
                        Toast.makeText(MainActivity.this, "添加", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.popup_delete:
                        Toast.makeText(MainActivity.this, "删除", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.popup_more:
                        Toast.makeText(MainActivity.this, "更多", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
