package com.example.btlappgiaitri.personal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlappgiaitri.Adapter.AdapterFriend;
import com.example.btlappgiaitri.Adapter.AdapterVideoPersional;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.Models.BanBe;
import com.example.btlappgiaitri.Models.DataVideo;
import com.example.btlappgiaitri.Models.User;
import com.example.btlappgiaitri.R;
import com.example.btlappgiaitri.navigation_bar.navigationBarActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView avatar,avatar1;
    TextView txtname;
    ImageView btnmenu,btnback;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    AdapterFriend adapterFriend;
    RecyclerView videos;
    AdapterVideoPersional adapterVideoPersional;
    List<BanBe> banBeList;
    ArrayList<DataVideo> dataVideoList;
    RecyclerView listfriend;
    Uri imgavatar = null;


    public PersonalActivity() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static PersonalActivity newInstance(String param1, String param2) {
        PersonalActivity fragment = new PersonalActivity();
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

    private static final int IMG_PICK_GALLEY_CODE =103;
    private static final int IMG_PICK_CAMERA_CODE =104;
    private static final int CAMERA_REQUEST_CODE =105;

    private String[] cameraPermiss;
    private ProgressDialog progressDialogUpdata;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        avatar = getView().findViewById(R.id.avatar);
        avatar1 = getView().findViewById(R.id.avatarNarba);
        txtname = getView().findViewById(R.id.txtName);
        btnmenu= getView().findViewById(R.id.menupagecn);
        btnback = getView().findViewById(R.id.btnback);
        drawerLayout = getView().findViewById(R.id.Drawer);
        navigationView = getView().findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        listfriend = getView().findViewById(R.id.ListFriend);
        videos = getView().findViewById(R.id.Listvideo);



        cameraPermiss = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        progressDialogUpdata  = new ProgressDialog(this.getContext());
        progressDialogUpdata.setTitle("Xin chờ ");
        progressDialogUpdata.setMessage("Đang tải ảnh");
        progressDialogUpdata.setCanceledOnTouchOutside(false);
        dataUser();


        videos.setLayoutManager(new GridLayoutManager(this.getActivity(),3));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        listfriend.setLayoutManager(layoutManager);
        adapterFriend = new AdapterFriend(this.getActivity());
        adapterVideoPersional = new AdapterVideoPersional(this.getActivity());
        adapterVideoPersional.setDataVideoList(getdataVideoUser());
        adapterFriend.setdata(DataFriend());
        listfriend.setAdapter(adapterFriend);
        videos.setAdapter(adapterVideoPersional);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDetach();
            }
        });

        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.open();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videopickDLG();
            }
        });


    }

    private ArrayList<DataVideo> getdataVideoUser() {
        dataVideoList = new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("Videos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    DataVideo videodata = ds.getValue(DataVideo.class);
                    assert videodata != null;
                    if(videodata.getID_TK().equals(LoginActivity.ID_Tk) ){
                        dataVideoList.add(new DataVideo(videodata.getVideourl()));
                        adapterVideoPersional.notifyDataSetChanged();
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        return dataVideoList;


    }

    private List<BanBe> DataFriend() {
        banBeList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("BanBe").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                loadlistbanbe(snapshot.getValue(BanBe.class));

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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            private void loadlistbanbe(BanBe banBe) {
                String y;

                if(navigationBarActivity.id_user.equals(banBe.getIDTK1())){
                    y = banBe.getIDTK2();
                }
                else if(navigationBarActivity.id_user.equals(banBe.getIDTK2())){
                    y = banBe.getIDTK1();
                }
                else{
                    return;
                }
                reference.child("User").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        User user    = snapshot.getValue(User.class);
                        if(y.equals(user.getIdtk())){
                            banBeList.add(new BanBe(user.getIdtk(),banBe.getIDTK1(),banBe.getIDTK2(),user.getName(),user.getAvatar()));
                            adapterFriend.notifyDataSetChanged();
                        }
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

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
        return banBeList;
    }

    private  void dataUser(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference();
        reference.child("User").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if(navigationBarActivity.id_user.equals(user.getIdtk())){

                    Picasso.get().load(user.getAvatar()).into(avatar);
                    txtname.setText(user.getName());
                    navigationView.getMenu().findItem(R.id.phone).setTitle(user.getSdt());
                    navigationView.getMenu().findItem(R.id.message).setTitle(user.getEmail());
                    TextView txtnamee = navigationView.getHeaderView(0).findViewById(R.id.txtname1);
                    TextView que = navigationView.getHeaderView(0).findViewById(R.id.QueQuan1);
                    ImageView avatar1 = navigationView.getHeaderView(0).findViewById(R.id.avatarNarba);
                    txtnamee.setText(user.getName());
                    que.setText(user.getQuequan());
                    Picasso.get().load(user.getAvatar()).into(avatar1);

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if(navigationBarActivity.id_user.equals(user.getIdtk())){
                    Picasso.get().load(user.getAvatar()).into(avatar);
                    txtname.setText(user.getName());
                    navigationView.getMenu().findItem(R.id.phone).setTitle(user.getSdt());
                    navigationView.getMenu().findItem(R.id.message).setTitle(user.getEmail());
                    TextView txtnamee = navigationView.getHeaderView(0).findViewById(R.id.txtname1);
                    TextView que = navigationView.getHeaderView(0).findViewById(R.id.QueQuan1);
                    ImageView avatar1 = navigationView.getHeaderView(0).findViewById(R.id.avatarNarba);
                    txtnamee.setText(user.getName());
                    que.setText(user.getQuequan());
                    Picasso.get().load(user.getAvatar()).into(avatar1);

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(navigationBarActivity.id_user.equals(user.getIdtk())){
                    Picasso.get().load(user.getAvatar()).into(avatar);
                    txtname.setText(user.getName());
                    navigationView.getMenu().findItem(R.id.phone).setTitle(user.getSdt());
                    navigationView.getMenu().findItem(R.id.message).setTitle(user.getEmail());
                    TextView txtnamee = navigationView.getHeaderView(0).findViewById(R.id.txtname1);
                    TextView que = navigationView.getHeaderView(0).findViewById(R.id.QueQuan1);
                    ImageView avatar1 = navigationView.getHeaderView(0).findViewById(R.id.avatarNarba);
                    txtnamee.setText(user.getName());
                    que.setText(user.getQuequan());
                    Picasso.get().load(user.getAvatar()).into(avatar1);
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                User user = snapshot.getValue(User.class);
                if(navigationBarActivity.id_user.equals(user.getIdtk())){
                    Picasso.get().load(user.getAvatar()).into(avatar);
                    txtname.setText(user.getName());
                    navigationView.getMenu().findItem(R.id.phone).setTitle(user.getSdt());
                    navigationView.getMenu().findItem(R.id.message).setTitle(user.getEmail());
                    TextView txtnamee = navigationView.getHeaderView(0).findViewById(R.id.txtname1);
                    TextView que = navigationView.getHeaderView(0).findViewById(R.id.QueQuan1);
                    ImageView avatar1 = navigationView.getHeaderView(0).findViewById(R.id.avatarNarba);
                    txtnamee.setText(user.getName());
                    que.setText(user.getQuequan());
                    Picasso.get().load(user.getAvatar()).into(avatar1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_personal, container, false);
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.phone:
                String s = "tel:" + item.getTitle().toString();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData( Uri.parse(s));
                startActivity(callIntent);
                break;

            case R.id.message:
                Intent email = new Intent(Intent.ACTION_SEND);
                String[] toemail =  item.getTitle().toString().split(",");
                email.putExtra(Intent.EXTRA_EMAIL,toemail);
                email.putExtra(Intent.EXTRA_SUBJECT, item.getTitle().toString());
                email.putExtra(Intent.EXTRA_TEXT, " 234");//need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));
                break;
            case R.id.logout:
                Intent intent = new Intent(this.getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.Setting:
                Intent intent1 = new Intent(this.getActivity(), EdipPersonalActivity.class);
                startActivity(intent1);
                break;

        }
        return true;
    }
    private void videopickDLG() {
        String[] options = {"máy ảnh ","thư viện"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle("Chọn video từ")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            if(!IsCameraPermiss()){
                                RequestCameraPermiss();
                            }
                            else{
                                videoPickCamera();
                            }
                        }
                        else if(i==1){
                            videoPickGalley();
                        }
                    }
                })
                .show();
    }
    private void RequestCameraPermiss(){
        ActivityCompat.requestPermissions(this.getActivity(),cameraPermiss,CAMERA_REQUEST_CODE);

    }
    private  boolean IsCameraPermiss(){
        boolean  res1 = ContextCompat.checkSelfPermission(this.getContext(),Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean  res2 = ContextCompat.checkSelfPermission(this.getContext(),Manifest.permission.WAKE_LOCK)== PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }
    private void videoPickGalley(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"chọn video"),IMG_PICK_GALLEY_CODE);

    }
    private void videoPickCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,IMG_PICK_CAMERA_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_REQUEST_CODE:
                if(grantResults.length>0){
                    boolean camerAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean StoredAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(camerAccepted && StoredAccepted){
                        videoPickCamera();
                    }
                    else{
                        Toast.makeText(this.getContext(), "máy ảnh và giấy phép lưu trữ là bắt buộc", Toast.LENGTH_SHORT).show();
                    }

                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(resultCode== Activity.RESULT_OK){
            if(requestCode == IMG_PICK_GALLEY_CODE){
                assert data != null;
                imgavatar = data.getData();
                Toast.makeText(this.getActivity(), imgavatar.toString(), Toast.LENGTH_SHORT).show();
                setVideoURI();
            }
            else if(requestCode == IMG_PICK_CAMERA_CODE){
                assert data != null;
                Bitmap urichage = (Bitmap) data.getExtras().get("data");

                Toast.makeText(this.getActivity(), urichage.toString(), Toast.LENGTH_SHORT).show();
                avatar.setImageBitmap(urichage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                urichage.compress(Bitmap.CompressFormat.PNG,100,bytes);
                String path =  MediaStore.Images.Media.insertImage(this.getActivity().getContentResolver(), urichage, "Title", null);
                imgavatar = Uri.parse(path);

                setVideoURI();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void setVideoURI() {
        avatar.setImageURI(imgavatar);
        ImageView avatar1 = navigationView.getHeaderView(0).findViewById(R.id.avatarNarba);
        avatar1.setImageURI(imgavatar);
        uploadImgstorega();

    }

    private void uploadImgstorega() {

            progressDialogUpdata.show();

            String timestamp = "" + System.currentTimeMillis();
            String filePathAndName = "Avatart/" + "Img" + timestamp;

            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

            storageReference.putFile(imgavatar)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            Uri dowload = uriTask.getResult();

                            if(uriTask.isSuccessful()) {
                                progressDialogUpdata.dismiss();
                                Toast.makeText(PersonalActivity.this.getContext(), dowload.toString(), Toast.LENGTH_SHORT).show();
                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference reference = firebaseDatabase.getReference();
                                reference.child("User").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            User user = ds.getValue(User.class);
                                            assert user != null;
                                            if(user.getIdtk().equals(LoginActivity.ID_Tk)){
                                                reference.child("User").child(user.getID().toString()).child("avatar").setValue(uriTask.getResult().toString().trim());

                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialogUpdata.dismiss();
                            Toast.makeText(PersonalActivity.this.getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


    }
}