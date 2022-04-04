package com.example.btlappgiaitri.Home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.btlappgiaitri.Adapter.AdapterComment;
import com.example.btlappgiaitri.Adapter.AdapterMedia;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.Comment;
import com.example.btlappgiaitri.Models.DataVideo;
import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.vanniktech.emoji.EmojiPopup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class CommentsActivity extends AppCompatActivity {

    ImageView avatar,btnsend;
    EditText txtComment;
    public static RecyclerView Comments;
    boolean check = false;
    private ProgressDialog progressDialogUpdata;
    AdapterComment adapterComment;
    private List<Comment> CommentList ;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        toolbar = (Toolbar)findViewById(R.id.header_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comments");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        avatar = findViewById(R.id.avatar_MessageItem);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    if(user.getIdtk().equals(LoginActivity.ID_Tk)){
                        Picasso.get().load(user.getAvatar()).into(avatar);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btnsend = findViewById(R.id.post);
        txtComment = findViewById(R.id.txtcomment);
        Comments = findViewById(R.id.Comments);
        EmojiPopup popup = EmojiPopup.Builder.fromRootView(findViewById(R.id.root_view_cmt)).build(txtComment);
        progressDialogUpdata  = new ProgressDialog(this);
        progressDialogUpdata.setTitle("Xin chờ ");
        progressDialogUpdata.setMessage("Đang tải bình luận ");
        progressDialogUpdata.setCanceledOnTouchOutside(false);
        adapterComment = new AdapterComment(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        Comments.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(Comments);
        adapterComment.setdata(getlistComment());

        txtComment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Comments.scrollToPosition(CommentList.size()-1);
            }
        });


        txtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() > 0){
                    btnsend.setVisibility(View.VISIBLE);
                }
                else{
                    btnsend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txtComment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if(motionEvent.getRawX() >= (txtComment.getRight() - txtComment.getCompoundDrawables()[2].getBounds().width())) {
                        // your action here
                        if(!check){
                            txtComment.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_keyboard_alt_24,0);
                        }
                        else{
                            txtComment.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_baseline_tag_faces_24,0);
                        }
                        popup.toggle();
                        check = !check;
                        return true;
                    }
                }

                return false;
            }
        });

        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AdapterMedia.idVideo!=null)
                     PostComment();


                PostNotification();


            }
        });

        Comments.setAdapter(adapterComment)   ;

    }

    private void PostNotification() {


            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

            String timestamp = "" + System.currentTimeMillis();

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference reference1 = firebaseDatabase.getReference();

            reference1.child("Videos").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        DataVideo videodata = ds.getValue(DataVideo.class);
                        if(AdapterMedia.idVideo.equals(videodata.getID())){
                            HashMap<String,Object> hashMap = new HashMap<>();
                            hashMap.put("ID",""+ timestamp);
                            hashMap.put("IDTKG",""+ LoginActivity.ID_Tk);
                            hashMap.put("Type","1");
                            hashMap.put("textContent",""+ txtComment.getText());
                            hashMap.put("thoiGian",""+ currentDate);
                            hashMap.put("IDTKN",""+ videodata.getID_TK());
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("thongBao");
                            reference.child(timestamp)
                                    .setValue(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            progressDialogUpdata.dismiss();
                                            Toast.makeText(CommentsActivity.this, "đã xong  ", Toast.LENGTH_SHORT).show();
                                                                                 }
                                    });
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }


    private List<Comment> getlistComment() {
        CommentList = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();

        reference.child("binhLuan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                assert comment != null;
                if(comment.getIdVideo().equals(AdapterMedia.idVideo)){
                    reference.child("User").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds : snapshot.getChildren()) {
                                User user = ds.getValue(User.class);
                                if (comment.getID_TK().equals(user.getIdtk())) {
                                    CommentList.add(new Comment(comment.getID_TK(), comment.getID_TK(), comment.getIdVideo(), comment.getNoiDung(), user.getName(), user.getAvatar()));
                                    if (user.getIdtk().equals(LoginActivity.ID_Tk)) {
                                        Picasso.get().load(user.getAvatar()).into(avatar);
                                    }
                                }

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                assert comment != null;
                CommentList.add(comment);
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Comment comment = snapshot.getValue(Comment.class);
                assert comment != null;
                CommentList.add(comment);
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment comment = snapshot.getValue(Comment.class);
                assert comment != null;
                CommentList.add(comment);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        return  CommentList;
    }

    private void PostComment() {
        String timestamp = "" + System.currentTimeMillis();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("IDBL",""+ timestamp);
        hashMap.put("NoiDung",""+ txtComment.getText());
        hashMap.put("IdVideo",""+ AdapterMedia.idVideo);
        hashMap.put("ID_TK",""+ LoginActivity.ID_Tk);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("binhLuan");
        reference.child(timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialogUpdata.dismiss();
                        txtComment.setText("");
                        Comments.scrollToPosition(CommentList.size()-1);
                    }



                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialogUpdata.dismiss();
                        Toast.makeText(CommentsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}