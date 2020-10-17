package com.doctormodule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctormodule.Models.ModelConversion;
import com.doctormodule.R;
import com.squareup.picasso.Picasso;
import com.utils.Utils.Tools;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterConversion extends BaseAdapter {

    private Context activity;
    private ArrayList<ModelConversion> messages;

    public AdapterConversion(Context context, ArrayList<ModelConversion> objects) {
        this.activity = context;
        this.messages = objects;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelConversion chat=messages.get(position);
        if (!chat.isMyMessage()) {
            convertView= LayoutInflater.from(activity).inflate(R.layout.left_chat_bubble,parent,false);
        } else {
            convertView= LayoutInflater.from(activity).inflate(R.layout.right_chat_bubble,parent,false);
            ImageView img_seen=convertView.findViewById(R.id.img_seen);
            img_seen.setVisibility(chat.getStatus().equals("SEEN")? View.VISIBLE: View.GONE);
        }
        TextView txt_msg=convertView.findViewById(R.id.txt_msg);
        TextView txt_time=convertView.findViewById(R.id.txt_time);
        CircleImageView chat_image=convertView.findViewById(R.id.chat_image);
        txt_msg.setText(chat.getChat_message());
        txt_time.setText(Tools.getTimeAgo(chat.getDate()));
        Picasso.get().load(chat.getSender_image()).placeholder(R.drawable.default_user).into(chat_image);

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

}