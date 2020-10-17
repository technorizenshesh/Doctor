package com.doctormodule.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.doctormodule.Interfaces.onClickRequestListener;
import com.doctormodule.Models.ModelRequest;
import com.doctormodule.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRequest extends RecyclerView.Adapter<AdapterRequest.mViewHolder> {
    Context mContext;
    ArrayList<ModelRequest>data;
    private onClickRequestListener listener;

    public AdapterRequest(Context mContext, ArrayList<ModelRequest> data) {
        this.mContext = mContext;
        this.data = data;
    }
    public AdapterRequest Callback(onClickRequestListener listener){
        this.listener=listener;
        return this;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_request,parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {
        holder.tv_name.setText(data.get(position).getUser_name());
        holder.tv_status.setText(data.get(position).getStatus());
        holder.tv_date.setText(data.get(position).getDate_time());
        Picasso.get().load(data.get(position).getImage()).placeholder(R.drawable.default_user).into(holder.user_image);
        holder.img_call.setOnClickListener(v->listener.onCall(data.get(position)));
        holder.img_chat.setOnClickListener(v->listener.onChat(data.get(position)));
        if (data.get(position).getStatus().equals("pedding")){
            holder.cancel.setVisibility(View.VISIBLE);
            holder.cancel.setOnClickListener(v->listener.onCancel(data.get(position)));
        }if (data.get(position).getStatus().equals("accept")){
            holder.cancel.setVisibility(View.GONE);
        }else {
            holder.cancel.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        CircleImageView user_image;
        CardView cancel;
        TextView tv_name,tv_date,tv_status;
        ImageView img_call,img_chat;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            user_image=itemView.findViewById(R.id.user_image);
            tv_name=itemView.findViewById(R.id.tv_name);
            img_call=itemView.findViewById(R.id.img_call);
            img_chat=itemView.findViewById(R.id.img_chat);
            tv_date=itemView.findViewById(R.id.tv_date);
            tv_status=itemView.findViewById(R.id.tv_status);
            cancel=itemView.findViewById(R.id.cancel);
        }
    }
}
