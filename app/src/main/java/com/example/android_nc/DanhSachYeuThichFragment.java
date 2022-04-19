package com.example.android_nc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachYeuThichFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachYeuThichFragment extends Fragment {
    DatabaseReference reference;
    ArrayList<Truyenyeuthich> list;
    ArrayList<Truyenyeuthich> listtruyen;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;
    private String mParam1;
    private String mParam2;

    public DanhSachYeuThichFragment() {
        // Required empty public constructor
    }

    public static DanhSachYeuThichFragment newInstance(String param1, String param2) {
        DanhSachYeuThichFragment fragment = new DanhSachYeuThichFragment();
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
        View view = inflater.inflate(R.layout.fragment_danh_sach_yeu_thich, container, false);
        Button logoutButton =  view.findViewById(R.id.buttonLogout);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser email = mAuth.getCurrentUser();
        MainActivity main = (MainActivity) getActivity();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(main, LoginAuth.class));
            }
        });

        ListView gr = view.findViewById(R.id.listtruyen);
        list = new ArrayList<Truyenyeuthich>();
        listtruyen = new ArrayList<Truyenyeuthich>();
        reference = FirebaseDatabase.getInstance().getReference().child("tbl_truyenyeuthich");
        reference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Truyenyeuthich truyen = dataSnapshot1.getValue(Truyenyeuthich.class);
                    list.add(truyen);
                }

                for(int i =0 ;i<list.size();i++){
                    if(list.get(i).getEmail().compareTo(email.getEmail())==0)
                        listtruyen.add(list.get(i));
                }

                Adaptertruyenyeuthich adaptertruyen = new Adaptertruyenyeuthich(listtruyen, new Adaptertruyenyeuthich.IClickitermTruyen() {
                    @Override
                    public void onClickItermUser(Truyenyeuthich truyen, String trangthai) {
//                        Toast.makeText(main, "1", Toast.LENGTH_SHORT).show();
                    }
                });
                gr.setAdapter(adaptertruyen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}