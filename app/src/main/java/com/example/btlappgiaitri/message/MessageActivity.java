package com.example.btlappgiaitri.message;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlappgiaitri.Adapter.AdapterNotify;
import com.example.btlappgiaitri.Adapter.AdapterSpinnerFilter;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.MessageData;
import com.example.btlappgiaitri.Models.Notifycation;
import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView listViewNotify;
    Spinner notifyFilter;
    ImageButton btnListMessage;


    AdapterNotify lstNotifyAdapter;
    ArrayList<MessageData> lstNotify;

    String[] contextNotifyFilter={"Tất cả hoạt động","Thích","Bình luận","Hỏi đáp","Nhắc đến","Follower","Từ TikTok"};
    int iconsNotifyFilter[] = { R.drawable.ic_baseline_notifications_active_24,
                    R.drawable.ic_baseline_favorite_24,
                    R.drawable.ic_baseline_comment_24,
                    R.drawable.ic_baseline_question_24,
                    R.drawable.ic_baseline_alternate_email_24,
                    R.drawable.ic_baseline_remove_red_eye_24,
                    R.drawable.ic_baseline_music_note_24};



    public MessageActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageActivity newInstance(String param1, String param2) {
        MessageActivity fragment = new MessageActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //spinner loại thông báo
        notifyFilter =  (Spinner) getView().findViewById(R.id.notifyFilter);
        AdapterSpinnerFilter customAdapter=new AdapterSpinnerFilter(MessageActivity.this,iconsNotifyFilter,contextNotifyFilter);
        notifyFilter.setAdapter(customAdapter);
        listViewNotify = getView().findViewById(R.id.listMessage);

        lstNotifyAdapter = new AdapterNotify(this.getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        listViewNotify.setLayoutManager(layoutManager);
        lstNotifyAdapter.setdata(getdata());
        listViewNotify.setAdapter(lstNotifyAdapter);


        btnListMessage = (ImageButton) getView().findViewById(R.id.btnOption);
        btnListMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ListMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    private ArrayList<MessageData> getdata() {
        lstNotify =new ArrayList<>();
        lstNotify.add(new MessageData("1","THT app","Đã thích video của bạn" ,"30/03/2022","https://i.pinimg.com/originals/2f/01/57/2f0157b3d3b536f74915eee6e512e056.jpg"));

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();

        reference.child("thongBao").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Notifycationdata(snapshot);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            private void Notifycationdata(DataSnapshot snapshot) {
                Notifycation notifycation = snapshot.getValue(Notifycation.class);
                assert notifycation != null;
                if(LoginActivity.ID_Tk.equals(notifycation.getIDTKN())){
                    reference.child("User").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot1, @Nullable String previousChildName) {
                            AppdataNotification(snapshot1);
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot1, @Nullable String previousChildName) {
                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot1) {
                        }


                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot1, @Nullable String previousChildName) {

                        }

                        private void AppdataNotification(DataSnapshot snapshot1) {
                            User user = snapshot1.getValue(User.class);
                            assert user != null;
                            if(user.getIdtk().equals(notifycation.getIDTKG()) && !notifycation.getIDTKG().equals(LoginActivity.ID_Tk)){
                                if(notifycation.getType().equals("1")){
                                    lstNotify.add(new MessageData(notifycation.getID(),user.getName(),"Đã bình luận :  " + notifycation.getTextContent(),notifycation.getThoiGian(),user.getAvatar()));
                                    lstNotifyAdapter.notifyDataSetChanged();
                                }
                                else if(notifycation.getType().equals("2")){
                                    lstNotify.add(new MessageData(notifycation.getID(),user.getName(),"Đã thích video của bạn" ,notifycation.getThoiGian(),user.getAvatar()));
                                    lstNotifyAdapter.notifyDataSetChanged();
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    return lstNotify;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_message, container, false);
    }
}