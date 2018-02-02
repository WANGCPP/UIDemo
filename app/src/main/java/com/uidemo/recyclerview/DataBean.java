package com.uidemo.recyclerview;

/**
 * Created by WANGCPP on 2018/2/2.
 * recyclerview 的数据容器
 */
public class DataBean {

    /**
     * group id
     */
    private int groupId;

    /**
     * group 标题
     */
    private String groupTitle = null;

    /**
     * sub 标题
     */
    private String subTitle = null;


    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupId() {
        return groupId;
    }
}
