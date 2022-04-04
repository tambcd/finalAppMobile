package com.example.btlappgiaitri.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlappgiaitri.Models.MessageData;
import com.example.btlappgiaitri.R;
import com.example.btlappgiaitri.message.MessageActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterNotify extends RecyclerView.Adapter<AdapterNotify.NotifyViewMain>{

    private MessageActivity activity;
    ArrayList<MessageData> data;
    private Context context;
    public AdapterNotify(Context context){
        this.context = context;
    }

    public  void  setdata(ArrayList<MessageData> list){
        this.data = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotifyViewMain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new NotifyViewMain(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewMain holder, int position) {
        MessageData notifi = data.get(position);
        if(notifi ==null){
            return;
        }
        holder.txtFullName.setText(notifi.getFullName()); ;
        Picasso.get().load(notifi.getAvartar()).into(holder.avatarNoti);
        holder.txtTextContent.setText(notifi.getTextContent());
        holder.txtTime.setText(notifi.getTextTime()); ;



    }

    @Override
    public int getItemCount() {
        if(this.data !=null){
            return data.size();
        }
        return 0;
    }

    public class NotifyViewMain  extends RecyclerView.ViewHolder {

        TextView txtFullName ;
        ImageView avatarNoti ;
        TextView txtTextContent ;
        TextView txtTime ;

        public NotifyViewMain(@NonNull View itemView) {
            super(itemView);
            //tham chiếu đến từng phần tử và trình bày dữ liệu lên view
           txtFullName = itemView.findViewById(R.id.txtnament);
             avatarNoti = itemView.findViewById(R.id.avatarnt);
             txtTextContent = itemView.findViewById(R.id.txtnt);
             txtTime = itemView.findViewById(R.id.txttiment);
        }
    } {
    }
}

