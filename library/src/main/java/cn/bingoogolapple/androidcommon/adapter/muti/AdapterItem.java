package cn.bingoogolapple.androidcommon.adapter.muti;

import android.content.Context;
import android.support.annotation.LayoutRes;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by lixuchun on 15/11/5.
 */
public abstract class AdapterItem<T> {

    public Context mContext;

    /**
     * @return item布局文件的layoutId
     */
    public abstract @LayoutRes
    int getLayoutResId();

    public abstract void fillData(BGAViewHolderHelper viewHolderHelper, int position, T model);

    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper){

    }
}
