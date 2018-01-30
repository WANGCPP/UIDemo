package com.uidemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uidemo.R;

/**
 * Created by WANGCPP on 2018/1/30.
 * 动态布局测试
 */
public class DynamicLayoutView extends ConstraintLayout {

    private final String TAG = DynamicLayoutView.class.getSimpleName();

    /**
     * 上下文
     */
    private Context mContext = null;

    /**
     * 用户星星的数量
     */
    private int mStarCounts;

    /**
     * 用户头像
     */
    private ImageView ivUser = null;

    /**
     * 用户昵称
     */
    private TextView tvUser = null;

    public DynamicLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DynamicLayoutView);
        mStarCounts = typedArray.getInt(R.styleable.DynamicLayoutView_star_count, 5);
        typedArray.recycle();

        mContext = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.dynamic_layout, this);

        ivUser = findViewById(R.id.iv_user);
        tvUser = findViewById(R.id.tv_user);
    }

    public void setIvUser(int resId) {
        ivUser.setImageResource(resId);
    }

    public void setTvUser(String name) {
        tvUser.setText(name);
    }

    public void setStar(int stars) {
        mStarCounts = stars;
        displayStars();
    }

    private void displayStars() {

        for (int i = 0; i < mStarCounts; i++) {
            Log.d(TAG, "displayStars");
            ImageView imageView = new ImageView(mContext);
            ConstraintLayout.LayoutParams layoutParams = new LayoutParams(141, 141);
            layoutParams.startToStart = LayoutParams.PARENT_ID;
            layoutParams.topToTop = LayoutParams.PARENT_ID;
            layoutParams.leftMargin = 400;
            layoutParams.topMargin = 100;
            imageView.setLayoutParams(layoutParams);
            imageView.setImageResource(R.drawable.user);

            addView(imageView);
        }

    }
}
