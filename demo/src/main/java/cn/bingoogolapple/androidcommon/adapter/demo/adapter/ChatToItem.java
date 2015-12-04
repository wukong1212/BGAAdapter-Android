package cn.bingoogolapple.androidcommon.adapter.demo.adapter;

import android.support.annotation.LayoutRes;

import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.androidcommon.adapter.demo.R;
import cn.bingoogolapple.androidcommon.adapter.demo.mode.ChatModel;
import cn.bingoogolapple.androidcommon.adapter.muti.AdapterItem;


/**
 * Created by lixuchun on 15/11/5.
 */
public class ChatToItem extends AdapterItem<ChatModel> {

    @Override
    public @LayoutRes
    int getLayoutResId() {
        return R.layout.item_chat_to;
    }

    @Override
    public void fillData(BGAViewHolderHelper viewHolderHelper, int position, ChatModel model) {
        String htmlMsg = String.format(mContext.getString(R.string.color_msg_to), model.mMsg);
        viewHolderHelper.setHtml(R.id.tv_item_chat_to_msg, htmlMsg);
    }

    @Override
    public void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {

    }
}
