package com.example.btlappgiaitri.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlappgiaitri.Models.txtContent;
import com.example.btlappgiaitri.R;

import java.util.List;

public class AdapterContext extends RecyclerView.Adapter<AdapterContext.Contextholder> {
    List<txtContent> list;

    public void setList(List<txtContent> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Contextholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.context,parent,false);
        return new Contextholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Contextholder holder, int position) {
        txtContent txtContent = list.get(position);
        if(txtContent==null){
            return;
        }
        holder.txtcontext.setText(txtContent.getTxt());
    }

    @Override
    public int getItemCount() {
        if(list!=null){
            return  list.size();
        }
        return 0;
    }

    public class Contextholder extends RecyclerView.ViewHolder {
        TextView txtcontext;
        public Contextholder(@NonNull View itemView) {
            super(itemView);
            txtcontext = itemView.findViewById(R.id.txtcontext);
        }
    }
}
