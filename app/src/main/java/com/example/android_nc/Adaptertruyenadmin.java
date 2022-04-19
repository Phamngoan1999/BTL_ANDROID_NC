package com.example.android_nc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptertruyenadmin extends BaseAdapter {

    final ArrayList<Truyen> listtruyen;
    Context context;
    private IClickitermTruyen iClickitermtruyen;

    public interface IClickitermTruyen {
        void onClickItermTruyen(Truyen user,String trangthai);
    }

    Adaptertruyenadmin(ArrayList<Truyen> listtruyen, IClickitermTruyen iClickitermloai) {
        this.listtruyen = listtruyen;
        this.iClickitermtruyen = iClickitermloai;
    }
    @Override
    public int getCount() {
        return listtruyen.size();
    }

    @Override
    public Object getItem(int position) {
        return listtruyen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listtruyen.get(position).getMa();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.itermtruyenadmininit, null);
        } else viewProduct = convertView;
        Truyen product = (Truyen) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.tentruyen)).setText(String.format(product.tentruyen));
        ((TextView) viewProduct.findViewById(R.id.tenloaitruyen)).setText(String.format(product.tenloaitruyen));
        TextView truyenchitiet = viewProduct.findViewById(R.id.tentruyen);
        Button update = viewProduct.findViewById(R.id.edit);
        Button delete = viewProduct.findViewById(R.id.delete);

        truyenchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitermtruyen.onClickItermTruyen(product,"chitiet");
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitermtruyen.onClickItermTruyen(product,"edit");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitermtruyen.onClickItermTruyen(product,"xoa");
            }
        });
        return viewProduct;
    }
}

