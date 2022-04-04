package com.example.btlappgiaitri.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlappgiaitri.Home.CommentsActivity;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.Comment;
import com.example.btlappgiaitri.Models.MediaObject;
import com.example.btlappgiaitri.Models.TuongTac;
import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AdapterMedia extends RecyclerView.Adapter<AdapterMedia.MediaViewMain>  {
    List<MediaObject> mediaObjectList;
    private  Context context;
    public static  String idVideo =null;
    String  idNotify=null;


    public AdapterMedia(Context context){
        this.context = context;
    }

    public  void  setdata(List<MediaObject> list){
        this.mediaObjectList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MediaViewMain onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_layout,parent,false);
        return new MediaViewMain(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewMain holder, int position) {
        final String[] IDLike = {""};
        final int[] numberLike = {0};
        final int[] numberCmt = {0};

        MediaObject mediaObject = mediaObjectList.get(position);
        if(mediaObject ==null){
            return;
        }


        Uri uri = Uri.parse(mediaObject.getVideourl());
        holder.videoView.setVideoURI(uri);
        holder.tile.setText(mediaObject.getID());
        holder.textVideoDescription.setText(mediaObject.getTitle());
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaObject.isLike()){
                    String timestamp = "" + System.currentTimeMillis();
                    HashMap<String,Object> hashMap = new HashMap<>();
                    hashMap.put("IDTT",timestamp);
                    hashMap.put("ID_TK",LoginActivity.ID_Tk);
                    hashMap.put("IdVideo",mediaObject.getID());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tuongTac");
                    reference.child(timestamp).setValue(hashMap);
                    idNotify = mediaObject.getID();
                    PostNotification();

                }
                else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tuongTac");
                    reference.child(IDLike[0]).removeValue();

                }
            }


            private void PostNotification() {


                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                String timestamp = "" + System.currentTimeMillis();
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("ID",""+ timestamp);
                hashMap.put("IDTKG",""+ LoginActivity.ID_Tk);
                hashMap.put("Type","2");
                hashMap.put("textContent","");
                hashMap.put("thoiGian",""+ currentDate);
                hashMap.put("IDTKN",""+ mediaObject.getID_TK());
                hashMap.put("IDLink",""+ mediaObject.getID());

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thongBao");
                reference.child(timestamp)
                        .setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                            }
                        });
            }
        });

        holder.fav.setImageResource(R.drawable.ic_like);


        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                holder.pbar.setVisibility(View.GONE);
                mediaPlayer.start();
            }
        });
        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
        if(mediaObject.isIsplay()){
            holder.videoView.start();
            holder.isplay.setVisibility(View.GONE);

        }
        else{
            holder.videoView.pause();
            holder.isplay.setVisibility(View.VISIBLE);

        }

        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaObject.isIsplay()){
                    holder.videoView.pause();
                    holder.isplay.setVisibility(View.VISIBLE);
                    mediaObject.setIsplay(false);
                }
                else{
                    holder.videoView.start();
                    holder.isplay.setVisibility(View.GONE);
                    mediaObject.setIsplay(true);
                }
            }
        });
        holder.cmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idVideo = mediaObject.getID();
                Intent intent = new Intent(context, CommentsActivity.class);
                context.startActivity(intent);
            }
        });


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();

        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    if(user.getIdtk().equals(mediaObject.getID_TK())){
                        Picasso.get().load(user.getAvatar()).into(holder.avatar);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //đếm bình luận
        reference.child("binhLuan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                if(comment.getIdVideo().equals(mediaObject.getID())){
                    numberCmt[0] +=1;
                    holder.numbercomment.setText(String.valueOf(numberCmt[0]));
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Comment comment = snapshot.getValue(Comment.class);
                if(comment.getIdVideo().equals(mediaObject.getID())){
                    numberCmt[0] -=1;
                    holder.numbercomment.setText(String.valueOf(numberCmt[0]));
                }
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        // kiếm tra đã like hay chưa và đếm số like của video position
        reference.child("tuongTac").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TuongTac tuongTac = snapshot.getValue(TuongTac.class);
                if(tuongTac.getIdVideo().equals(mediaObject.getID())){
                    if(tuongTac.getID_TK().equals(LoginActivity.ID_Tk)){
                        holder.fav.setImageResource(R.drawable.ic_fill_favorite);
                        IDLike[0] =tuongTac.getIDTT();
                        mediaObject.setLike(true);
                    }
                    numberLike[0] +=1;
                    holder.numberlike.setText(String.valueOf(numberLike[0]));
                }
            }



            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                TuongTac tuongTac = snapshot.getValue(TuongTac.class);
                if(tuongTac.getIdVideo().equals(mediaObject.getID())){
                    if(tuongTac.getID_TK().equals(LoginActivity.ID_Tk)){
                        holder.fav.setImageResource(R.drawable.ic_like);
                        IDLike[0] ="";
                        mediaObject.setLike(false);
                    }
                    numberLike[0] -=1;
                    holder.numberlike.setText(String.valueOf(numberLike[0]));
                }
            }



            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    @Override
    public int getItemCount() {
        if(this.mediaObjectList !=null){
            return mediaObjectList.size();
        }
        return 0;
    }

    public class MediaViewMain extends RecyclerView.ViewHolder{
        private ImageView fav,isplay,avatar;
        private VideoView videoView;
        TextView tile ,textVideoDescription,numbercomment,numberlike;
        ProgressBar pbar;
        ImageView cmt;


        public MediaViewMain(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatarPost);
            fav = itemView.findViewById(R.id.favorites);
            videoView = itemView.findViewById(R.id.videoView);
            textVideoDescription = itemView.findViewById(R.id.textVideoDescription);
            tile = itemView.findViewById(R.id.textVideoTitle);
            pbar = itemView.findViewById(R.id.videoProgressBar) ;
            isplay = itemView.findViewById(R.id.playMain);
            cmt = itemView.findViewById(R.id.btncm);
            numbercomment = itemView.findViewById(R.id.numbercomment);
            numberlike = itemView.findViewById(R.id.numberlike);

        }
    }



}
