package com.example.coco;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coco.model.DiseaseData;
import com.example.coco.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.FirebaseAuthException;

public class User_Edit extends AppCompatActivity {


    ImageView updateUserImage, backIcon;
    ImageButton updateUserButton,imageUploadbtn;
    EditText updateUserName, updateUserPhone;
    TextView updateUserEmail;
    String name,phone, email;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        updateUserButton = findViewById(R.id.updateUserButton);
        updateUserImage = findViewById(R.id.updateUserImage);
        updateUserName = findViewById(R.id.updateUserName);
        updateUserPhone = findViewById(R.id.updateUserPhone);
        updateUserEmail = findViewById(R.id.updateUserEmail);
        imageUploadbtn = findViewById(R.id.imageUploadbtn);

        backIcon = findViewById(R.id.backIcon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a confirmation dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(User_Edit.this);
                builder.setTitle("Leave Page");
                builder.setMessage("Are you sure you want to leave this page?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Redirect to Users_list activity
                        startActivity(new Intent(User_Edit.this, User_Account.class));
                        finish(); // Close current activity
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog and stay on the User_Account page
                        dialog.dismiss();
                    }
                });
                // Create and show the dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updateUserImage.setImageURI(uri);
                        } else {
                            Toast.makeText(User_Edit.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            // Load the image using Glide
            Glide.with(User_Edit.this).load(imageUrl).into(updateUserImage);
            Glide.with(User_Edit.this).load(bundle.getString("Image")).into(updateUserImage);
            updateUserName.setText(bundle.getString("Name"));
            updateUserEmail.setText(bundle.getString("Email"));
            updateUserPhone.setText(bundle.getString("Phone"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("Image");
            Log.d("User_Edit_Image", "Image URL: " + oldImageURL); // Log the image URL
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(key);

        updateUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        imageUploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });



    }

    public void saveData(){

        AlertDialog.Builder builder = new AlertDialog.Builder(User_Edit.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        if (uri != null) {
            storageReference = FirebaseStorage.getInstance().getReference().child("users").child(uri.getLastPathSegment());

            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    updateData();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                }
            });

        }else {
            imageUrl = oldImageURL; // use old image URL as no new image is selected
            updateData();
        }
    }
    public void updateData(){
        name = updateUserName.getText().toString().trim();
        phone = updateUserPhone.getText().toString().trim();
        email = updateUserEmail.getText().toString();

        String role = "user";


        UserData dataClass = new UserData(name, phone,email ,role,imageUrl);

        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    if (!oldImageURL.equals(imageUrl)) {
                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                        reference.delete();
                    }
                    Toast.makeText(User_Edit.this, "User Updated", Toast.LENGTH_SHORT).show();
                    // Redirect to User_Account activity
                    Intent intent = new Intent(User_Edit.this, User_Account.class);
                    startActivity(intent);
                    finish(); // Close current activity
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(User_Edit.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Create a confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(User_Edit.this);
            builder.setTitle("Leave Page");
            builder.setMessage("Are you sure you want to leave this page?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Redirect to Users_list activity
                    startActivity(new Intent(User_Edit.this, User_Account.class));
                    finish(); // Close current activity
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog and stay on the User_Account page
                    dialog.dismiss();
                }
            });
            // Create and show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
            return true; // Consume the event
        }
        return super.onKeyUp(keyCode, event);
    }

}