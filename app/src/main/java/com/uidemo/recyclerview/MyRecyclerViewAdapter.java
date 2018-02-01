package com.uidemo.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.uidemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WANGCPP on 2018/2/1.
 * RecyclerView适配器
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    private final String TAG = MyRecyclerViewAdapter.class.getSimpleName();

    private Context mContext = null;

    /**
     * 数据表----一级数据
     */
    private List<String> mDataList_Level1 = new ArrayList<>();

    /**
     * 数据表----二级数据
     */
    private List<String> mDataList_Level2 = new ArrayList<>();

    public MyRecyclerViewAdapter(Context context) {
        this.mContext = context;
        initDataList();
    }

    private void initDataList() {
        for (int i = 0; i < 10; i++) {
            mDataList_Level1.add("item " + i);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recyclerview_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.setData(mDataList_Level1.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "position == " + position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDataList_Level1.size();
    }

    /**
     * recyclerview 中item对应的viewholder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView = null;

        private ImageButton pullDown_btn = null;

        MyViewHolder(View view) {
            super(view);
            initView(view);
            initEvent();
        }

        private void initView(View view) {
            textView = view.findViewById(R.id.tv_recyclerview);
            pullDown_btn = view.findViewById(R.id.ib_pulldown);
        }

        private void initEvent() {

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.ib_pulldown:
                            Log.d(TAG, "pulldown");
                            break;
                        default:
                            break;
                    }
                }
            };
            pullDown_btn.setOnClickListener(clickListener);
        }

        public void setData(String string) {
            textView.setText(string);
        }

    }
}
