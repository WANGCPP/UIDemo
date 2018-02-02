package com.uidemo.recyclerview;

/**
 * Created by WANGCPP on 2018/2/2.
 * recyclerview item 状态
 */
public class ItemStatus {

    /**
     * group索引
     */
    private int groupId;

    /**
     * item 类型:group 或者 sub
     */
    private int viewType;

    /**
     * 是否已经打开sub界面，默认没有打开
     */
    private boolean isOpen = false;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void setIsOpen(boolean open) {
        this.isOpen = open;
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }
}
