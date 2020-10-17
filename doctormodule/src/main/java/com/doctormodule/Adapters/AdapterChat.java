package com.doctormodule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.doctormodule.Interfaces.onClickChatListener;
import com.doctormodule.Models.ModelChat;
import com.doctormodule.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChat extends BaseAdapter {
    Context mContext;
    ArrayList<ModelChat> data;
    private onClickChatListener onClick;

    public AdapterChat(Context mContext, ArrayList<ModelChat> data) {
        this.mContext = mContext;
        this.data = data;
    }
    public AdapterChat setOnClick(onClickChatListener onClick){
        this.onClick=onClick;
        return this;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(mContext).inflate(R.layout.layout_chat,viewGroup,false);
        CircleImageView profile=view.findViewById(R.id.profile);
        TextView count=view.findViewById(R.id.count);
        TextView name=view.findViewById(R.id.name);
        TextView last_message=view.findViewById(R.id.last_message);
        ImageView img_delete=view.findViewById(R.id.img_delete);
        Picasso.get().load(data.get(i).getImage()).placeholder(R.drawable.default_user).into(profile);
        count.setText(data.get(i).getNo_of_message());
        count.setVisibility(data.get(i).getNo_of_message().equals("0")? View.GONE: View.VISIBLE);
        name.setText(data.get(i).getName());
        last_message.setText(data.get(i).getLast_message());
        view.setOnClickListener(v->onClick.ViewChat(i));
        img_delete.setOnClickListener(v->onClick.DeleteConversation(i));
        return view;
    }
}
