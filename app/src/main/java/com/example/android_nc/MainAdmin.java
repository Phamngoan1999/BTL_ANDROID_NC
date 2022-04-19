package com.example.android_nc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class MainAdmin extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 22;
    Timestamp timestamp;
    public static final long MAX_BYTES_PDF = 5000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DanhSachLoaiTruyenAdmin themloai = new DanhSachLoaiTruyenAdmin();
        fragmentTransaction.replace(R.id.container, themloai);
        fragmentTransaction.commit();

        BottomNavigationView navigation = findViewById(R.id.button_nav);
        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        DanhSachLoaiTruyenAdmin themloai = new DanhSachLoaiTruyenAdmin();
                        fragmentTransaction.replace(R.id.container, themloai);
                        fragmentTransaction.commit();
                    }
                    break;
                    case R.id.navigation_sms: {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        DanhSachTruyenAdmin themloai = new DanhSachTruyenAdmin();
                        fragmentTransaction.replace(R.id.container, themloai);
                        fragmentTransaction.commit();
                    }
                    break;
                    case R.id.navigation_notifications:
                        startActivity(new Intent(MainAdmin.this, themloaitruyen.class));
                        break;
                }
            }
        });
    }

    public void ThemTruyen(LoaiTruyen loaiTruyen,String click)
    {

        if(click.equals("themtruyen"))
        {
            Intent intent = new Intent(MainAdmin.this, Themtruyenpdf.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("loaitruyen", loaiTruyen);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if(click.equals("danhsach"))
        {
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            DanhSachTruyenTheoLoaiAdmin loaitruyen = new DanhSachTruyenTheoLoaiAdmin();
            Bundle bundle = new Bundle();
            bundle.putSerializable("loaitruyen",loaiTruyen);
            loaitruyen.setArguments(bundle);
            fragmentTransaction1.replace(R.id.container,loaitruyen);
            fragmentTransaction1.commit();
        }
    }

    public void goChitiet(Truyen truyen,String trangthai)
    {

        if(trangthai.equals("chitiet"))
        {
            Intent intent = new Intent(MainAdmin.this, TruyenActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("truyen", truyen);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if(trangthai.equals("edit"))
        {
            FragmentTransaction  fragmentTransaction=getSupportFragmentManager().beginTransaction();
            UpdatetruyenFragment updateuser = new UpdatetruyenFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("object",truyen);
            updateuser.setArguments(bundle);
            fragmentTransaction.replace(R.id.container,updateuser);
            fragmentTransaction.commit();
        }
        if(trangthai.equals("xoa"))
        {
            FragmentTransaction  fragmentTransaction=getSupportFragmentManager().beginTransaction();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tbl_truyen");
            mDatabase.child(String.valueOf(truyen.getMa())).removeValue();

            DanhSachLoaiTruyenAdmin themloai = new DanhSachLoaiTruyenAdmin();
            fragmentTransaction.replace(R.id.container, themloai);
            fragmentTransaction.commit();
        }
    }

}