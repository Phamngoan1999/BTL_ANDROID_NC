package com.example.android_nc;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemLoaiTruyenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemLoaiTruyenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThemLoaiTruyenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThemLoaiTruyenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThemLoaiTruyenFragment newInstance(String param1, String param2) {
        ThemLoaiTruyenFragment fragment = new ThemLoaiTruyenFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_them_loai_truyen, container, false);
        // initialise views
        Button btnSelect = view.findViewById(R.id.btnChoose);
        Button btnUpload = view.findViewById(R.id.btnUpload);
        Button imageView = view.findViewById(R.id.imgView);

        MainAdmin main = (MainAdmin)getActivity();
        // on pressing btnSelect SelectImage() is called

        return view;
    }
}