package com.example.android_nc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

public class MainActivity extends AppCompatActivity {

    Timestamp timestamp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFlagment updateuser = new HomeFlagment();
        fragmentTransaction.replace(R.id.container, updateuser);
        fragmentTransaction.commit();

        BottomNavigationView navigation = findViewById(R.id.button_nav);
        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home: {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        HomeFlagment updateuser = new HomeFlagment();
                        fragmentTransaction.replace(R.id.container, updateuser);
                        fragmentTransaction.commit();
                    }
                    break;
                        //login
                    case R.id.navigation_notifications:
                    {
                        mAuth = FirebaseAuth.getInstance();
                        FirebaseUser user = mAuth.getCurrentUser();
                        if(user != null)
                        {
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            DanhSachYeuThichFragment updateuser = new DanhSachYeuThichFragment();
                            fragmentTransaction.replace(R.id.container, updateuser);
                            fragmentTransaction.commit();
                            return;
                        }
                        startActivity(new Intent(MainActivity.this, LoginAuth.class));
                    }break;
                        //tìm kiếm
                    case R.id.navigation_sms:{
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        TimkiemFragment updateuser = new TimkiemFragment();
                        fragmentTransaction.replace(R.id.container, updateuser);
                        fragmentTransaction.commit();
                    }break;
                }
            }
        });
    }

    public void danhSachTruyenTheoloai(LoaiTruyen loaiTruyen)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DanhSachTruyenTheoLoai flagment = new DanhSachTruyenTheoLoai();
        Bundle bundle = new Bundle();
        bundle.putSerializable("loaitruyen",loaiTruyen);
        flagment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, flagment);
        fragmentTransaction.commit();
    }

    public void goChitiet(Truyen truyen)
    {
        Intent intent = new Intent(MainActivity.this, TruyenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("truyen", truyen);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    //thêm truyện yêu thích
    public void gotologin(Truyen truyen){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if( user == null){
            startActivity(new Intent(MainActivity.this, LoginAuth.class));
            finish();
        }else {
            timestamp = new Timestamp(System.currentTimeMillis());
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("tbl_truyenyeuthich");
            Truyenyeuthich truyenyeuthich = new Truyenyeuthich(timestamp.getTime(), truyen.getTentruyen(), truyen.getNoidung(), truyen.getMaloai(), truyen.getTenloaitruyen(), user.getEmail());
            mDatabase.child(String.valueOf(timestamp.getTime())).setValue(truyenyeuthich);
            Toast.makeText(MainActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
            //load danh sách truyện yêu thích
            FragmentTransaction  fragmentTransaction=getSupportFragmentManager().beginTransaction();
            DanhSachYeuThichFragment updateuser = new DanhSachYeuThichFragment();
            fragmentTransaction.replace(R.id.container,updateuser);
            fragmentTransaction.commit();
        }
    }
}