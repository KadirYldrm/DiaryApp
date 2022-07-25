package com.example.morira;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class PhotoListAdapter extends BaseAdapter {

    private final Context context;
    private final int layout;
    private final ArrayList<Photo> photosList;

    public PhotoListAdapter(Context context, int layout, ArrayList<Photo> photosList) {
        this.context = context;
        this.layout = layout;
        this.photosList = photosList;
    }

    @Override
    public int getCount() {
        return photosList.size();
    }

    @Override
    public Object getItem(int position) {
        return photosList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView txtNote, txtDate;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtNote = (TextView) row.findViewById(R.id.txtNote);
            holder.txtDate = (TextView) row.findViewById(R.id.txtDate);
            holder.imageView = (ImageView) row.findViewById(R.id.imgPhoto);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Photo photo = photosList.get(position);

        holder.txtNote.setText(photo.getNote());
        holder.txtDate.setText(photo.getDate());

        byte[] Image = photo.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
