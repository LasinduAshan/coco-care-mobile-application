package com.example.coco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Navigation extends AppCompatActivity {

    ImageView closeIcon;

    ImageButton UsersNav, DiseasesNav, AccountNav, identifyDiseasesNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        closeIcon = findViewById(R.id.closeIcon);

        UsersNav = findViewById(R.id.UsersNav);
        DiseasesNav = findViewById(R.id.DiseasesNav);
        AccountNav = findViewById(R.id.AccountNav);
        identifyDiseasesNav = findViewById(R.id.identifyDiseasesNav);

        // Check user role and hide/show buttons accordingly
        checkUserRole();

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check the user's role
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("role");

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String role = dataSnapshot.getValue(String.class);
                            // Determine the activity to redirect based on the user's role
                            Class<?> targetActivity = (role != null && role.equals("admin")) ? Admin_Home.class : Home.class;
                            // Start the corresponding activity
                            startActivity(new Intent(Navigation.this, targetActivity));
                            finish(); // Close the current activity
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle database error
                        Toast.makeText(Navigation.this, "Failed to retrieve user role", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void checkUserRole() {
        // Retrieve user role from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");

        // If user is an admin, hide buttons
        if (role.equals("admin")) {
            AccountNav.setVisibility(View.GONE);
            identifyDiseasesNav.setVisibility(View.GONE);
        }
    }
}