package com.example.android_nc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class Themtruyenpdf extends AppCompatActivity {

    ImageView upload;
    TextView textStatus;
    Uri imageuri = null;
    Button btnUpload;
    EditText inputTentruyen;
    LoaiTruyen loai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themtruyenpdf);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            loai = (LoaiTruyen) bundle.getSerializable("loaitruyen");
        }
        Toast.makeText(Themtruyenpdf.this, loai.getName(), Toast.LENGTH_SHORT).show();
        textStatus = findViewById(R.id.textStatus);

        upload = findViewById(R.id.uploadpdf);
        btnUpload = findViewById(R.id.btnUpload);
        inputTentruyen = findViewById(R.id.inputTentruyen);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("application/pdf");
                startActivityForResult(galleryIntent, 1);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload(loai);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            imageuri = data.getData();
            textStatus.setText("Đã upload");
        }
    }

    ProgressDialog dialog;

    public void upload(LoaiTruyen loaitruyen) {
        if (imageuri != null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Uploading");
            dialog.show();
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final String messagePushID = timestamp;
            final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
            filepath.putFile(imageuri).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        dialog.dismiss();
                        Uri uri = task.getResult();
                        String myurl = uri.toString();
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                        String tentruyen = inputTentruyen.getText().toString();
                        Truyen truyen = new Truyen(timestamp.getTime(), tentruyen, myurl, loaitruyen.getMa(), loaitruyen.getName());
                        Map<String,Object> map = new HashMap<>();
                        map.put("ma",timestamp.getTime());
                        map.put("tentruyen",tentruyen);
                        map.put("noidung",myurl);
                        map.put("tenloaitruyen",loaitruyen.getName());
                        map.put("maloai",loaitruyen.getMa());
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tbl_truyen");
                        mDatabase.child(String.valueOf(timestamp.getTime())).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Themtruyenpdf.this,"thêm loại truyện thành công",Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(Themtruyenpdf.this, "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}