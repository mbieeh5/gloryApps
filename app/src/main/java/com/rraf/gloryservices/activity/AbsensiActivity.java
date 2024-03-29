package com.rraf.gloryservices.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rraf.gloryservices.R;
import com.rraf.gloryservices.adaptor.UploadClass;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class AbsensiActivity extends AppCompatActivity {

    ImageView mImageView;
    ArrayAdapter<String> adapter;
    private ProgressBar uBar;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private AutoCompleteTextView autoComplete;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    boolean isUploading;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        Objects.requireNonNull(getSupportActionBar()).hide();
        mStorageRef = FirebaseStorage.getInstance().getReference("Data").child("dataAbsen");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Data").child("dataAbsen").child("absensi");
        uBar = findViewById(R.id.loading_upload);
        autoComplete = findViewById(R.id.autoCompleteAbsen);
        mImageView = findViewById(R.id.image_view);
        uBar.setVisibility(View.GONE);
        Button mTakePictureButton = findViewById(R.id.take_picture_button);
        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (ContextCompat.checkSelfPermission(AbsensiActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(AbsensiActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    int MY_PERMISSIONS_REQUEST_CAMERA_AND_STORAGE = 1;
                    ActivityCompat.requestPermissions(AbsensiActivity.this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CAMERA_AND_STORAGE);
                } else {
                    activityResultLauncher.launch(takePictureIntent);
                }
            }
        });


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Bundle extra = result.getData().getExtras();
                Bitmap img = (Bitmap) extra.get("data");
                Bitmap res1 = Bitmap.createScaledBitmap(img, img.getWidth() * 2, img.getHeight() * 2, true);
                mImageUri = SaveImage(res1, AbsensiActivity.this);
                    mImageView.setImageURI(mImageUri);
            }
        });


        final List<String> items = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Users").child("dataPenerima").orderByChild("nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        String name = ds.child("nama").getValue(String.class);
                        items.add(name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter = new ArrayAdapter<String>(this, R.layout.dropdown_list, items);
        autoComplete.setAdapter(adapter);

        Button upload = findViewById(R.id.btnAbsenSimpan);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UploadAbsen();
                }
            });
    }
    private Uri SaveImage(Bitmap image, Context context){
        File imagesFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
            try {
                imagesFolder.mkdirs();
                File file = new File(imagesFolder, "captured_images.jpg");
                FileOutputStream stream = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                stream.flush();
                stream.close();
                uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.rraf.gloryservices"+".provider",file);
            } catch (FileNotFoundException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return uri;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the task you need to do.
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activityResultLauncher.launch(takePictureIntent);
        } else {
            // permission denied, boo! Disable the functionality that depends on this permission.
            Toast.makeText(this, "Permission ditolak", Toast.LENGTH_SHORT).show();
        }
    }

    private void UploadAbsen(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss", Locale.CANADA);
        Date tgl = new Date();
        String fileName = formatter.format(tgl);
        if(mImageUri != null){
            StorageReference fileRef = mStorageRef.child("absensi/"+fileName);
            fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    String tanggal = formater.format(date);
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    String jam = hour+":"+minute;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            uBar.setProgress(0);
                        }
                    }, 500);
                    mStorageRef.child("absensi/"+fileName).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            final String urlFotoAbsen = task.getResult().toString();
                            Toast.makeText(AbsensiActivity.this, "Absen Berhasil", Toast.LENGTH_SHORT).show();
                            UploadClass upload = new UploadClass(autoComplete.getText().toString().trim(), urlFotoAbsen, tanggal, jam);
                            String uploadId = mDatabaseRef.push().getKey();
                            uBar.setVisibility(View.GONE);
                            mDatabaseRef.child(uploadId).setValue(upload);
                            isUploading = false;
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AbsensiActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    uBar.setProgress((int) progress);
                    uBar.setVisibility(View.VISIBLE);
                    isUploading = true;
                }
            });
        }else{
            Toast.makeText(this, "Tidak ada Foto Yang Di Absen", Toast.LENGTH_SHORT).show();
        }
        }
        @Override
        public void onBackPressed() {
            if (isUploading) {
                Toast.makeText(AbsensiActivity.this, "Absen belum selesai terkirim", Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
    }
}
