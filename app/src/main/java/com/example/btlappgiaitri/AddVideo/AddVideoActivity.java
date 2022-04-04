package com.example.btlappgiaitri.AddVideo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.btlappgiaitri.Home.HomeActivity;
import com.example.btlappgiaitri.Login.LoginActivity;
import com.example.btlappgiaitri.R;
import com.example.btlappgiaitri.entertain.MusicActivity;
import com.example.btlappgiaitri.navigation_bar.navigationBarActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddVideoActivity extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddVideoActivity() {
        // Required empty public constructor
    }


    public static MusicActivity newInstance(String param1, String param2) {
        MusicActivity fragment = new MusicActivity();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_add_video, container, false);
    }

    EditText tile;
    Uri Videouri =null;
    Button btnadd;
    VideoView newvideo;
    FloatingActionButton pickVideo;
    String title;
    private static final int VIDEO_PICK_GALLEY_CODE =100;
    private static final int VIDEO_PICK_CAMERA_CODE =101;
    private static final int CAMERA_REQUEST_CODE =102;

    private String[] cameraPermiss;
    private ProgressDialog progressDialogUpdata;




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tile = getActivity().findViewById(R.id.txtadd);
        newvideo = getActivity().findViewById(R.id.videoAdd);
        btnadd = getActivity().findViewById(R.id.btnvideoadd);
        pickVideo = getActivity().findViewById(R.id.pickvideo);
        cameraPermiss = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        progressDialogUpdata  = new ProgressDialog(this.getContext());
        progressDialogUpdata.setTitle("Xin chờ ");
        progressDialogUpdata.setMessage("Đang tải video");
        progressDialogUpdata.setCanceledOnTouchOutside(false);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 title = tile.getText().toString().trim();
             if(TextUtils.isEmpty(title)){
                 Toast.makeText(AddVideoActivity.this.getContext(), "Mời nhập nội dung", Toast.LENGTH_SHORT).show();
             }
             else if(Videouri==null){
                 Toast.makeText(AddVideoActivity.this.getContext(), "Mời chọn video ", Toast.LENGTH_SHORT).show();

             }
             else{
                 uploadVideoFirebase();
             }
            }
        });
        pickVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videopickDLG();
            }
        });


    }

    private void uploadVideoFirebase() {
        progressDialogUpdata.show();

        String timestamp = "" + System.currentTimeMillis();
        String filePathAndName = "Videos/" + "video_" + timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);

        storageReference.putFile(Videouri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful());
                                Uri dowload = uriTask.getResult();

                                if(uriTask.isSuccessful()){

                                    HashMap<String,Object> hashMap = new HashMap<>();
                                    hashMap.put("ID",timestamp);
                                    hashMap.put("title",title);
                                    hashMap.put("Timestamo",""+ timestamp);
                                    hashMap.put("videourl",""+ dowload);
                                    hashMap.put("ID_TK",""+ LoginActivity.ID_Tk);


                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Videos");
                                    reference.child(timestamp)
                                           .setValue(hashMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialogUpdata.dismiss();
                                                    Toast.makeText(AddVideoActivity.this.getContext(), "đã xong  ", Toast.LENGTH_SHORT).show();
                                                    navigationBarActivity.chipNavigationBar.dismissBadge(R.id.home);
                                                    HomeActivity fragment = new HomeActivity();
                                                    if (fragment != null) {

                                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                                                    }
                                                    Videouri =null;
                                                    tile.setText("");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialogUpdata.dismiss();
                                                    Toast.makeText(AddVideoActivity.this.getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialogUpdata.dismiss();
                        Toast.makeText(AddVideoActivity.this.getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
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
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"chọn video"),VIDEO_PICK_GALLEY_CODE);

    }
    private void videoPickCamera(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent,VIDEO_PICK_CAMERA_CODE);

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
            if(requestCode == VIDEO_PICK_GALLEY_CODE){
                assert data != null;
                Videouri = data.getData();
                Toast.makeText(this.getActivity(), Videouri.toString(), Toast.LENGTH_SHORT).show();

                setVideoURI();
            }
            else if(requestCode == VIDEO_PICK_CAMERA_CODE){
                assert data != null;
                Videouri = data.getData();
                Toast.makeText(this.getActivity(), Videouri.toString(), Toast.LENGTH_SHORT).show();
                setVideoURI();

            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setVideoURI() {
        MediaController mediaController = new MediaController(this.getContext());
        mediaController.setAnchorView(newvideo);
        newvideo.setMediaController(mediaController);

        newvideo.setVideoURI(Videouri);
        newvideo.requestFocus();
        newvideo.start();
        newvideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                newvideo.start();
            }
        });
    }
}