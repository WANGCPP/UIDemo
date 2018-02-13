package com.uidemo.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = MyRecyclerViewAdapter.class.getSimpleName();

    private Context mContext = null;

    /**
     * 代表group item
     */
    private final int ITEM_TYPE_GROUP = 0;

    /**
     * 代表sub item
     */
    private final int ITEM_TYPE_SUB = 1;

    /**
     * 数据表----一级数据
     */
    private List<DataBean> mDataBeanList = new ArrayList<>();

    /**
     * item 状态信息
     */
    private List<ItemStatus> mItemStatus = new ArrayList<>();

    public MyRecyclerViewAdapter(Context context) {
        this.mContext = context;
        initDataBeanList();
    }

    private void initDataBeanList() {
        for (int i = 0; i < 10; i++) {
            DataBean dataBean = new DataBean();
            dataBean.setGroupId(i);
            dataBean.setGroupTitle("group " + i);
            dataBean.setSubTitle("sub " + i);
            mDataBeanList.add(dataBean);
        }

        for (int i = 0; i < 10; i++) {
            ItemStatus itemStatus = new ItemStatus();
            itemStatus.setGroupId(i);
            itemStatus.setViewType(ITEM_TYPE_GROUP);
            mItemStatus.add(itemStatus);
        }
    }

    /**
     * 根据viewtype 创建不同类型的viewholder
     * @param parent parent
     * @param viewType viewtype
     * @return viewholder对象
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");
        View view;
        if (viewType == ITEM_TYPE_GROUP) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycview_group_item, parent, false);
            return new GroupViewHolder(view);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycview_sub_item, parent, false);
            return new SubViewHolder(view);
        }
    }

    /**
     * viewholder绑定数据
     * @param holder viewholder对象
     * @param position item 位置
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final DataBean dataBean = mDataBeanList.get(mItemStatus.get(position).getGroupId()); // 获取对应group的数据
        if (holder.getItemViewType() == ITEM_TYPE_GROUP) {
            Log.d(TAG, "group position == " + position);
            final GroupViewHolder groupViewHolder = (GroupViewHolder) holder;
            groupViewHolder.setData(dataBean);
            groupViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemStatus.get(groupViewHolder.getAdapterPosition()).getIsOpen()) { // 如果该group已经打开
                        mItemStatus.get(groupViewHolder.getAdapterPosition()).setIsOpen(false);
                        mItemStatus.remove(groupViewHolder.getAdapterPosition() + 1);
                        notifyItemRemoved(groupViewHolder.getAdapterPosition() + 1); // 关闭子项
                    } else {
                        ItemStatus itemStatus = new ItemStatus();
                        itemStatus.setGroupId(dataBean.getGroupId());
                        itemStatus.setViewType(ITEM_TYPE_SUB);
                        mItemStatus.get(groupViewHolder.getAdapterPosition()).setIsOpen(true);
                        mItemStatus.add(groupViewHolder.getAdapterPosition() + 1, itemStatus);
                        notifyItemInserted(groupViewHolder.getAdapterPosition() + 1); // 打开子项
                    }
                }
            });

        } else {
            Log.d(TAG, "sub position == " + position);
            final SubViewHolder subViewHolder = (SubViewHolder) holder;
            subViewHolder.setData(dataBean);
        }
    }


    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount()");
        return mItemStatus.size();
    }

    /**
     * 获取对应item位置的viewtype
     * @param position item位置
     * @return viewtype
     */
    @Override
    public int getItemViewType(int position) {
//        Log.d(TAG, "getItemViewType() = " + position);
        return mItemStatus.get(position).getViewType();
    }

    /**
     * recyclerview 中GroupItem对应的一级viewholder
     */
    class GroupViewHolder extends RecyclerView.ViewHolder {

        private TextView textView = null;

        private ImageButton pullDown_btn = null;

        GroupViewHolder(View view) {
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


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d(TAG,"onLongClick()");
                    return true; // 此处返回true,消费点击事件
                }
            });
        }

        public void setData(DataBean dataBean) {
            textView.setText(dataBean.getGroupTitle());
        }

    }

    /**
     * recyclerview 中SubItem对应的二级viewholder
     */
    class SubViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSubTitle = null;

        public SubViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
            initEvent();
        }

        private void initView(View view) {
            tvSubTitle = view.findViewById(R.id.subtitle);
        }

        private void initEvent() {
        }

        public void setData(DataBean dataBean) {
            tvSubTitle.setText(dataBean.getSubTitle());
        }
    }
}
