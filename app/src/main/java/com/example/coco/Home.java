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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    ImageButton account_btn, diseases_btn, identifyDiseases_btn;
    ImageView hamburgerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        account_btn = findViewById(R.id.account_btn);
        diseases_btn = findViewById(R.id.diseases_btn);
        identifyDiseases_btn = findViewById(R.id.identifyDiseases_btn);
        hamburgerIcon = findViewById(R.id.hamburgerIcon);

        String role = "user"; // Example user role

        SharedPreferences sharedPreferences = getSharedPreferences("user_role", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("role", role);
        editor.apply();

        hamburgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Navigation.class);
                startActivity(intent);
            }
        });

        account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, User_Account.class);
                startActivity(intent);
            }
        });

        diseases_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Diseases_list.class);
                startActivity(intent);
            }
        });

        identifyDiseases_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Prediction.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // Show a dialog to confirm logout
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to log out?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Log out the user
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Home.this, MainActivity.class));
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