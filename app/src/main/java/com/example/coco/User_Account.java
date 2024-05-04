package com.example.coco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Account extends AppCompatActivity {

    TextView userName, phoneNumber, userEmail;
    ImageButton delete_btn;
    FloatingActionButton editUserButton;
    FloatingActionMenu floating_action_menu;
    ImageView hamburgerIcon,backIcon, userImage;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        userName = findViewById(R.id.userName);
        phoneNumber = findViewById(R.id.phoneNumber);
        userEmail = findViewById(R.id.userEmail);
        userImage = findViewById(R.id.userImage);

        delete_btn = findViewById(R.id.delete_btn);

        editUserButton = findViewById(R.id.editUserButton);

//        hamburgerIcon = findViewById(R.id.hamburgerIcon);
        backIcon = findViewById(R.id.backIcon);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user role from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
                String role = sharedPreferences.getString("role", "");

                // Determine the activity to redirect based on the user's role
                Class<?> targetActivity = (role != null && role.equals("admin")) ? Users_list.class : Home.class;

                // Start the corresponding activity
                startActivity(new Intent(User_Account.this, targetActivity));
                finish(); // Close current activity
            }
        });

        floating_action_menu = findViewById(R.id.floating_action_menu);

        // Retrieve user role from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");

        // Check if the role is admin
        if (role.equals("admin")) {
            // Hide the FloatingActionMenu button
            floating_action_menu.setVisibility(View.GONE);
        }

        if (role.equals("admin")) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null){
                userName.setText(bundle.getString("Name"));
                phoneNumber.setText(bundle.getString("Phone"));
                userEmail.setText(bundle.getString("Email"));
                key = bundle.getString("Key");
                imageUrl = bundle.getString("Image");
                Glide.with(this).load(bundle.getString("Image")).into(userImage);
            }
        }else{
            // Get current logged in user
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                // Retrieve user's UID
                String userId = currentUser.getUid();

                // Reference to user data in Firebase Realtime Database
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

                // Retrieve user's data
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Retrieve user data
                            String username = dataSnapshot.child("username").getValue(String.class);
                            String useremail = dataSnapshot.child("useremail").getValue(String.class);
                            String userphone = dataSnapshot.child("userphone").getValue(String.class);
                            imageUrl = dataSnapshot.child("userImage").getValue(String.class);

                            // Populate TextViews with user data
                            userName.setText(username);
                            userEmail.setText(useremail);
                            phoneNumber.setText(userphone);

                            Glide.with(User_Account.this)
                                    .load(imageUrl)
                                    .placeholder(R.drawable.avatar) // Placeholder image while loading
                                    .error(R.drawable.avatar) // Error image if loading fails
                                    .into(userImage);
                        } else {
                            Toast.makeText(User_Account.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(User_Account.this, "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(User_Account.this);
                builder.setTitle("Delete Account");
                builder.setMessage("Are you sure you want to delete your account?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (role.equals("admin")) {
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(key);
                            userRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(key);
                                        userRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Account deleted successfully from real-time database
                                                    Toast.makeText(User_Account.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();

                                                    // Determine the activity to redirect based on the user's role
                                                    SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
                                                    String role = sharedPreferences.getString("role", "");
                                                    Class<?> targetActivity = (role != null && role.equals("admin")) ? Users_list.class : MainActivity.class;

                                                    startActivity(new Intent(User_Account.this, targetActivity)); // Redirect to corresponding activity
                                                    finish(); // Close current activity
                                                } else {
                                                    Toast.makeText(User_Account.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(User_Account.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            // Delete the user's account from Firebase
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (currentUser != null) {
                                String userId = currentUser.getUid();
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                                userRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Account deleted successfully from real-time database
                                            Toast.makeText(User_Account.this, "Account deleted successfully", Toast.LENGTH_SHORT).show();
                                            // Now delete the user from Firebase Authentication
                                            currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // User deleted from Authentication
                                                        FirebaseAuth.getInstance().signOut(); // Sign out the user

                                                        // Determine the activity to redirect based on the user's role
                                                        SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
                                                        String role = sharedPreferences.getString("role", "");
                                                        Class<?> targetActivity = (role != null && role.equals("admin")) ? Users_list.class : MainActivity.class;

                                                        startActivity(new Intent(User_Account.this, targetActivity)); // Redirect to corresponding activity
                                                        finish(); // Close current activity
                                                    } else {
                                                        // Failed to delete user from Authentication
                                                        Toast.makeText(User_Account.this, "Failed to delete account from Authentication", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(User_Account.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        editUserButton.setOnClickListener(new View.OnClickListener() {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String userId = currentUser.getUid();

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Account.this, User_Edit.class)
                        .putExtra("Name", userName.getText().toString())
                        .putExtra("Email", userEmail.getText().toString())
                        .putExtra("Phone", phoneNumber.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("Key", userId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Retrieve user role from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");

            // Determine the activity to redirect based on the user's role
            Class<?> targetActivity = (role != null && role.equals("admin")) ? Users_list.class : Home.class;

            // Start the corresponding activity
            startActivity(new Intent(User_Account.this, targetActivity));
            finish(); // Close current activity
            return true; // Consume the event
        }
        return super.onKeyUp(keyCode, event);
    }



}