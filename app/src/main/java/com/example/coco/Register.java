package com.example.coco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText userName, userPhone,userEmail, userPassword;

    ImageView register_btn;

    FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.userName);
        userPhone = findViewById(R.id.userPhone);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        register_btn = findViewById(R.id.register_btn);


        TextView loginlink = findViewById(R.id.login_page);

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password,username,userphone;
                email = String.valueOf(userEmail.getText());
                password = String.valueOf(userPassword.getText());
                username = String.valueOf(userName.getText());
                userphone = String.valueOf(userPhone.getText());

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Register.this, "Enter Your Username", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(userphone)) {
                    Toast.makeText(Register.this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidSriLankanPhone(userphone)) {
                    Toast.makeText(Register.this, "Enter a valid Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email) || !isValidEmail(email)) {
                    Toast.makeText(Register.this, "Enter a valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(Register.this, "Password should be at least 8 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Inside register_btn.setOnClickListener()

                register_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Your existing code...

                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

                                            FirebaseUser registeredUser = firebaseAuth.getCurrentUser();
                                            if (registeredUser != null) {
                                                String userId = registeredUser.getUid();
                                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

                                                // Instead of just setting the role, now we're creating a userMap to set multiple values at once.
                                                Map<String, String> userMap = new HashMap<>();
                                                userMap.put("role", "user");
                                                userMap.put("useremail", email);
                                                userMap.put("username", username);
                                                userMap.put("userphone", userphone);

                                                // Set the default user image URL
                                                String defaultUserImage = "https://firebasestorage.googleapis.com/v0/b/coco-6ce23.appspot.com/o/Users%2Favatar.png?alt=media&token=bc2629c1-e4a0-45e6-b37c-2ba09764f91a";
                                                userMap.put("userImage", defaultUserImage);

                                                databaseReference.setValue(userMap)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Intent intent = new Intent(Register.this, MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(Register.this, "Error adding user details to Database: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                                Log.e("DatabaseError", "Error adding user details to Database: " + e.getMessage(), e);
                                                            }
                                                        });
                                            }
                                        } else {
                                            Toast.makeText(Register.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

            }
        });
    }

    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public boolean isValidSriLankanPhone(String phone) {
        String regex = "^07[0-9]{8}$";
        return phone.matches(regex);
    }
}