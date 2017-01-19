package talentica.com.chatapp;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import talentica.com.androidstarterkit.R;

/**
 * Created by amitk on 19/01/17.
 */


public class MessageAdapter extends BaseAdapter {

    List<Message> messageList;
    Context context;
    LayoutInflater inflater;

    public MessageAdapter(Context context, List<Message> messages) {
        messageList = messages;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_list_item, parent, false);
            mViewHolder = new MessageViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MessageViewHolder) convertView.getTag();
        }

        Message message = (Message) getItem(position);

        mViewHolder.tvUserName.setText(message.user_name);
        mViewHolder.tvChatMessage.setText(message.chat_message);

        return convertView;

    }

    public void add(Message message){
        messageList.add(message);
        notifyDataSetChanged();
    }

    private class MessageViewHolder {
        TextView tvUserName;
        TextView tvChatMessage;

        public MessageViewHolder(View item) {
            this.tvUserName = (TextView) item.findViewById(R.id.tv_user_name);
            this.tvChatMessage = (TextView) item.findViewById(R.id.tv_message);
        }
    }
}
