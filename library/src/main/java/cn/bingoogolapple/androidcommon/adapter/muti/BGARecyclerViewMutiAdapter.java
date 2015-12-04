package cn.bingoogolapple.androidcommon.adapter.muti;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildCheckedChangeListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewHolder;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by lixuchun on 15/12/4.
 */
public abstract class BGARecyclerViewMutiAdapter<M> extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<M> mDatas;
    protected BGAOnItemChildClickListener mOnItemChildClickListener;
    protected BGAOnItemChildLongClickListener mOnItemChildLongClickListener;
    protected BGAOnItemChildCheckedChangeListener mOnItemChildCheckedChangeListener;
    protected BGAOnRVItemClickListener mOnRVItemClickListener;
    protected BGAOnRVItemLongClickListener mOnRVItemLongClickListener;

    protected RecyclerView mRecyclerView;

    private Object mItemType;

    private AdapterItemUtil mUtil = new AdapterItemUtil();

    public BGARecyclerViewMutiAdapter(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = mRecyclerView.getContext();
        mDatas = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BGARecyclerViewMutiHolder viewHolder = new BGARecyclerViewMutiHolder(mRecyclerView, parent,getItemView(mItemType), mOnRVItemClickListener, mOnRVItemLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildClickListener(mOnItemChildClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildLongClickListener(mOnItemChildLongClickListener);
        viewHolder.getViewHolderHelper().setOnItemChildCheckedChangeListener(mOnItemChildCheckedChangeListener);
        viewHolder.getItem().setItemChildListener(viewHolder.getViewHolderHelper());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BGARecyclerViewMutiHolder viewHolder = (BGARecyclerViewMutiHolder)holder;
        viewHolder.getItem().fillData(viewHolder.getViewHolderHelper(),position,getItem(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Deprecated
    @Override
    public int getItemViewType(int position) {
        mItemType = getItemViewType(mDatas.get(position));
        return mUtil.getIntType(mItemType);
    }

    public Object getItemViewType(M m) {
        return null;
    }

    public abstract @NonNull AdapterItem<M> getItemView(Object type);



    /**
     * 为item的孩子节点设置监听器，并不是每一个数据列表都要为item的子控件添加事件监听器，所以这里采用了空实现，需要设置事件监听器时重写该方法即可
     *
     * @param viewHolderHelper
     */
    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
    }



    /**
     * 设置item的点击事件监听器
     *
     * @param onRVItemClickListener
     */
    public void setOnRVItemClickListener(BGAOnRVItemClickListener onRVItemClickListener) {
        mOnRVItemClickListener = onRVItemClickListener;
    }

    /**
     * 设置item的长按事件监听器
     *
     * @param onRVItemLongClickListener
     */
    public void setOnRVItemLongClickListener(BGAOnRVItemLongClickListener onRVItemLongClickListener) {
        mOnRVItemLongClickListener = onRVItemLongClickListener;
    }

    /**
     * 设置item中的子控件点击事件监听器
     *
     * @param onItemChildClickListener
     */
    public void setOnItemChildClickListener(BGAOnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    /**
     * 设置item中的子控件长按事件监听器
     *
     * @param onItemChildLongClickListener
     */
    public void setOnItemChildLongClickListener(BGAOnItemChildLongClickListener onItemChildLongClickListener) {
        mOnItemChildLongClickListener = onItemChildLongClickListener;
    }

    /**
     * 设置item子控件选中状态变化事件监听器
     *
     * @param onItemChildCheckedChangeListener
     */
    public void setOnItemChildCheckedChangeListener(BGAOnItemChildCheckedChangeListener onItemChildCheckedChangeListener) {
        mOnItemChildCheckedChangeListener = onItemChildCheckedChangeListener;
    }

    public M getItem(int position) {
        return mDatas.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<M> getDatas() {
        return mDatas;
    }

    /**
     * 在集合头部添加新的数据集合（下拉从服务器获取最新的数据集合，例如新浪微博加载最新的几条微博数据）
     *
     * @param datas
     */
    public void addNewDatas(List<M> datas) {
        if (datas != null) {
            mDatas.addAll(0, datas);
            notifyItemRangeInserted(0, datas.size());
        }
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param datas
     */
    public void addMoreDatas(List<M> datas) {
        if (datas != null) {
            mDatas.addAll(mDatas.size(), datas);
            notifyItemRangeInserted(mDatas.size(), datas.size());
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param datas
     */
    public void setDatas(List<M> datas) {
        if (datas != null) {
            mDatas = datas;
        } else {
            mDatas.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除指定数据条目
     *
     * @param model
     */
    public void removeItem(M model) {
        removeItem(mDatas.indexOf(model));
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param model
     */
    public void addItem(int position, M model) {
        mDatas.add(position, model);
        notifyItemInserted(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param model
     */
    public void addFirstItem(M model) {
        addItem(0, model);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param model
     */
    public void addLastItem(M model) {
        addItem(mDatas.size(), model);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param newModel
     */
    public void setItem(int location, M newModel) {
        mDatas.set(location, newModel);
        notifyItemChanged(location);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldModel
     * @param newModel
     */
    public void setItem(M oldModel, M newModel) {
        setItem(mDatas.indexOf(oldModel), newModel);
    }

    /**
     * 移动数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        mDatas.add(toPosition, mDatas.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
    }

    private class BGARecyclerViewMutiHolder extends BGARecyclerViewHolder{

        private AdapterItem<M> mItem;

        public BGARecyclerViewMutiHolder(RecyclerView recyclerView,ViewGroup parent,AdapterItem<M> item, BGAOnRVItemClickListener onRVItemClickListener, BGAOnRVItemLongClickListener onRVItemLongClickListener) {
            super(recyclerView, LayoutInflater.from(BGARecyclerViewMutiAdapter.this.mContext).inflate(item.getLayoutResId(), parent, false), onRVItemClickListener, onRVItemLongClickListener);
            mItem = item;
            mItem.mContext = BGARecyclerViewMutiAdapter.this.mContext;
        }

        protected AdapterItem<M> getItem() {
            return mItem;
        }
    }
}
