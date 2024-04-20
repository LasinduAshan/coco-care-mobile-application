package com.example.coco;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Admin_Home extends AppCompatActivity {

    ImageButton diseases, usersList;
    ImageView adminHamburger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        diseases = findViewById(R.id.diseasesList_btn);
        usersList = findViewById(R.id.userList_btn);
        adminHamburger = findViewById(R.id.adminHamburger);

        String role = "admin"; // Example user role

        SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("role", role);
        editor.apply();

        diseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home.this, Diseases_list.class);
                startActivity(intent);
            }
        });

        usersList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Home.this, Users_list.class);
                startActivity(intent);
            }
        });

        adminHamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Home.this, Navigation.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Show a dialog to confirm logout
            AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Home.this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to log out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Log out the user
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Admin_Home.this, MainActivity.class));
                    finish(); // Close current activity
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true; // Consume the event
        }
        return super.onKeyUp(keyCode, event);
    }
}