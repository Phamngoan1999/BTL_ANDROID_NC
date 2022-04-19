package com.example.android_nc;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Truyenchitiet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Truyenchitiet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final long MAX_BYTES_PDF = 5000000;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Truyenchitiet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Truyenchitiet.
     */
    // TODO: Rename and change types and number of parameters
    public static Truyenchitiet newInstance(String param1, String param2) {
        Truyenchitiet fragment = new Truyenchitiet();
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
        View view = inflater.inflate(R.layout.fragment_truyenchitiet, container, false);
        Bundle bundle = getArguments();
        MainActivity main = (MainActivity) getActivity();
        if(bundle != null){
            Truyen truyen  = (Truyen) bundle.getSerializable("object");
            TextView tentruyen= view.findViewById(R.id.tentruyen);
            tentruyen.setText(truyen.tentruyen);

            PDFView pdfView= (PDFView) view.findViewById(R.id.pdfView);
            StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/android-nc-5d1c8.appspot.com/o/1650216427026.pdf?alt=media&token=910a033c-f567-4503-a1c7-1c881c1a9983");
            reference.getBytes(MAX_BYTES_PDF)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            pdfView.fromBytes(bytes)
                                    .onError(new OnErrorListener() {
                                        @Override
                                        public void onError(Throwable t) {
                                            Toast.makeText(main, "ko ok", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .onPageError(new OnPageErrorListener() {
                                        @Override
                                        public void onPageError(int page, Throwable t) {
                                            Toast.makeText(main, "ko ok 1", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .load();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(main, "ko ok 12", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        return view;
    }
}