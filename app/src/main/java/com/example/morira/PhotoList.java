package com.example.morira;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class PhotoList extends AppCompatActivity {
    GridView gridView;
    ArrayList<Photo> list;
    PhotoListAdapter adapter = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_list_activity);


        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new PhotoListAdapter(this, R.layout.photo_items, list);
        gridView.setAdapter(adapter);


        Cursor cursor = FifthPage.sqLiteHelper.getData("SELECT * FROM PHOTO");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Photo(name, price, image, id));
        }
        adapter.notifyDataSetChanged();


        gridView.setOnItemLongClickListener((parent, view, position, id) -> {

            CharSequence[] items = {"Update", "Delete"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(PhotoList.this);


            dialog.setTitle("Choose an action");
            dialog.setItems(items, (dialog1, item) -> {
                if (item == 0) {

                    Cursor c = FifthPage.sqLiteHelper.getData("SELECT id FROM PHOTO");
                    ArrayList<Integer> arrID = new ArrayList<>();
                    while (c.moveToNext()) {
                        arrID.add(c.getInt(0));
                    }

                    showDialogUpdate(PhotoList.this, arrID.get(position));

                } else {

                    Cursor c = FifthPage.sqLiteHelper.getData("SELECT id FROM PHOTO");
                    ArrayList<Integer> arrID = new ArrayList<>();
                    while (c.moveToNext()) {
                        arrID.add(c.getInt(0));
                    }
                    showDialogDelete(arrID.get(position));
                }
            });
            dialog.show();
            return true;
        });


        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );


    }

    ImageView imageViewPhoto;

    private void showDialogUpdate(Activity activity, final int position) {

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_photo_activity);
        dialog.setTitle("Update");

        imageViewPhoto = (ImageView) dialog.findViewById(R.id.imageViewPhoto);
        final EditText edtNote = (EditText) dialog.findViewById(R.id.edtNote);
        final EditText date = (EditText) dialog.findViewById(R.id.date);
        ImageView btnUpdate = (ImageView) dialog.findViewById(R.id.btnUpdate);


        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);

        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imageViewPhoto.setOnClickListener(v -> ActivityCompat.requestPermissions(
                PhotoList.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                888
        ));

        btnUpdate.setOnClickListener(v -> {
            try {
                FifthPage.sqLiteHelper.updateData(
                        edtNote.getText().toString().trim(),
                        date.getText().toString().trim(),
                        FifthPage.imageViewToByte(imageViewPhoto),
                        position
                );
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Update successfully!!!", Toast.LENGTH_SHORT).show();
            } catch (Exception error) {
                Log.e("Update error", error.getMessage());
            }
            updatePhotoList();
        });
    }

    private void showDialogDelete(final int idPhoto) {
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(PhotoList.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", (dialog, which) -> {
            try {
                FifthPage.sqLiteHelper.deleteData(idPhoto);
                Toast.makeText(getApplicationContext(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }
            updatePhotoList();
        });

        dialogDelete.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        dialogDelete.show();
    }

    private void updatePhotoList() {

        Cursor cursor = FifthPage.sqLiteHelper.getData("SELECT * FROM PHOTO");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String price = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Photo(name, price, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 888) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 888 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageViewPhoto.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}