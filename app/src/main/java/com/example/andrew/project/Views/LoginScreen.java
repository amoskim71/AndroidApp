package com.example.andrew.project.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andrew.project.Model.Availability;
import com.example.andrew.project.R;
import com.example.andrew.project.Model.ServiceProvider;
import com.example.andrew.project.Model.Admin;
import com.example.andrew.project.Model.HomeOwner;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private EditText textUsername;
    private EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    public void login(View view) {
        String password = textPassword.getText().toString().trim();
        String username = textUsername.getText().toString().trim();
        signIn(username, password);
    }

    private void signIn(final String username, final String password) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("admin").child(username).exists()) {
                    Toast.makeText(LoginScreen.this, "Yes", Toast.LENGTH_SHORT).show();
                    if (!username.isEmpty()) {
                        Admin login = dataSnapshot.child("admin").child(username).getValue(Admin.class);
                        if (login.getPassword().equals(password)){
                            Toast.makeText(LoginScreen.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreen.this, WelcomeScreen.class);
                            intent.putExtra("User", login);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginScreen.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginScreen.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }

                } else if (dataSnapshot.child("homeOwners").child(username).exists()) {
                    if (!username.isEmpty()) {
                        HomeOwner login = dataSnapshot.child("homeOwners").child(username).getValue(HomeOwner.class);
                        if (login.getPassword().equals(password)){
                            Toast.makeText(LoginScreen.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreen.this, WelcomeScreen.class);
                            intent.putExtra("User", login);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginScreen.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginScreen.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }

                } else if (dataSnapshot.child("serviceProviders").child(username).exists()) {
                    if (!username.isEmpty()) {
                        ServiceProvider login = dataSnapshot.child("serviceProviders").child(username).getValue(ServiceProvider.class);
                        if (login.getPassword().equals(password)){
                            login.setAvailability(dataSnapshot.child("serviceProviders").child(username).child("availability").getValue(Availability.class));
                            Toast.makeText(LoginScreen.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginScreen.this, WelcomeScreen.class);
                            intent.putExtra("User", login);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginScreen.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginScreen.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
