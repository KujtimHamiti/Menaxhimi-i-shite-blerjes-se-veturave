package com.androidks.vetura.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidks.vetura.R;
import com.androidks.vetura.beans.Vetura;

import java.util.ArrayList;

public class CarListAdapter extends BaseAdapter {


//Një aplikacion Android ka aktivitete. Konteksti është nje në të cilën aplikacioni yt është duke u futur
    private Context context;
    private  int layout;
    private ArrayList<Vetura> veturaArrayList;

    public CarListAdapter(Context context,ArrayList<Vetura> foodsList) {
        this.context = context;
        this.veturaArrayList = foodsList;
    }

    @Override
    public int getCount() {
        return veturaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return veturaArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }


    private class ViewHolder{
        ImageView imageView;
        TextView txtTitulli, txtPershkrimi;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Adaptori përdore  convertView si një mënyrë për riciklimin e objekteve të vjetra View që nuk përdoren më.
        View row = convertView;
        ViewHolder holder = new ViewHolder();


        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_custom_search_list, null);

            //holder eshte klase qe i mbane qato te dhena(variable);
            holder.txtTitulli = (TextView) row.findViewById(R.id.tvTitulli);
            holder.txtPershkrimi = (TextView) row.findViewById(R.id.tvPershkrimi);
            holder.imageView = (ImageView) row.findViewById(R.id.ivFoto);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Vetura vetura = veturaArrayList.get(position);

        holder.txtTitulli.setText(vetura.getTitulli());
        holder.txtPershkrimi.setText(vetura.getPershkrimi());

        byte[] veturaImg = vetura.getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(veturaImg, 0, veturaImg.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;

    }
}
