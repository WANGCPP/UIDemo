package com.uidemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.uidemo.recyclerview.MyRecyclerViewAdapter;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar = null;

    private Button btnPlayView = null;

    private ImageButton ibSearch = null;

    private NavigationView navigationView = null;

    private RecyclerView recyclerView = null;


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

        recyclerView = findViewById(R.id.recycler_view);

        /**
         * RecyclerView.LayoutManager是一个抽象类，系统为我们提供了三个实现类
         *LinearLayoutManager即线性布局，这个是在上面的例子中我们用到的布局
         *GridLayoutManager即表格布局
         *StaggeredGridLayoutManager即流式布局，如瀑布流效果
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(new MyRecyclerViewAdapter(MainActivity.this));

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
