package cn.bingoogolapple.androidcommon.adapter.demo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.ChatFromItem;
import cn.bingoogolapple.androidcommon.adapter.demo.adapter.ChatToItem;
import cn.bingoogolapple.androidcommon.adapter.demo.engine.DataEngine;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.ChatModel;
import cn.bingoogolapple.androidcommon.adapter.muti.AdapterItem;
import cn.bingoogolapple.androidcommon.adapter.muti.BGARecyclerViewMutiAdapter;


/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/5/22 10:06
 * 描述:
 */
public class RecyclerChatMutiDemoFragment extends BaseFragment {
    private static final String TAG = RecyclerChatMutiDemoFragment.class.getSimpleName();
    private BGARecyclerViewMutiAdapter<ChatModel> mAdapter;
    private List<ChatModel> mDatas;
    private RecyclerView mDataRv;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_recyclerview);
        mDataRv = getViewById(R.id.rv_recyclerview_data);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataRv.setLayoutManager(layoutManager);

        mAdapter = new BGARecyclerViewMutiAdapter<ChatModel>(mDataRv) {


            @Override
            public Object getItemViewType(ChatModel item) {
                return item.mUserType;
            }

            @NonNull
            @Override
            public AdapterItem<ChatModel> getItemView(Object type) {

                ChatModel.UserType mUserType = (ChatModel.UserType)type;

                if (mUserType == ChatModel.UserType.From){
                    return new ChatFromItem();
                }

                return new ChatToItem();
            }
        };

        mDatas = DataEngine.loadChatModelDatas();
        mAdapter.setDatas(mDatas);
        mDataRv.setAdapter(mAdapter);

        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                Log.d(TAG, "message:" + mAdapter.getItem(position).mMsg);
            }
        });
    }

    @Override
    protected void onUserVisible() {
    }
}