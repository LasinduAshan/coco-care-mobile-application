package com.example.coco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coco.model.DiseaseData;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.List;

public class Diseases_view extends AppCompatActivity {

    TextView detailName,detailTreat;
    ImageView diseaseImage, backIcon;
    FloatingActionButton deleteButton, editButton;

    FloatingActionMenu floating_action_menu;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_view);

        diseaseImage = findViewById(R.id.disease_image);
        detailName = findViewById(R.id.disease_name);
        detailTreat = findViewById(R.id.disease_treatments);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        backIcon = findViewById(R.id.backIcon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Diseases_view.this, Diseases_list.class);
                startActivity(intent);
            }
        });

        floating_action_menu = findViewById(R.id.floating_action_menu);

        // Retrieve user role from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");

        // Check if the role is admin
        if (role.equals("user")) {
            // Hide the FloatingActionMenu button
            floating_action_menu.setVisibility(View.GONE);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailName.setText(bundle.getString("Name"));
            detailTreat.setText(bundle.getString("Treatments"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(diseaseImage);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Diseases_view.this);
                builder.setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteItem();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Do nothing or dismiss the dialog
                            }
                        })
                        .show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Diseases_view.this, Disease_Update.class)
                        .putExtra("Name", detailName.getText().toString())
                        .putExtra("Treatments", detailTreat.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });

    }

    private void deleteItem() {
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Diseases");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                reference.child(key).removeValue();
                Toast.makeText(Diseases_view.this, "Disease Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Diseases_list.class));
                finish();
            }
        });

    }


}