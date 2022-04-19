package com.example.android_nc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAuth extends AppCompatActivity {

    private FirebaseAuth mAuth;
    final String TAG = "LoginAuth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_auth);

        mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.buttonLogin);
        final EditText usernameInput = findViewById(R.id.usernameInput);
        final EditText passwordInput = findViewById(R.id.passwordInput);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(usernameInput.getText().toString(), passwordInput.getText().toString());
            }
        });

        TextView registerLink = findViewById(R.id.registrationLink);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAuth.this, RegistrationActivity.class));
            }
        });
    }


    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginAuth.this, email,Toast.LENGTH_SHORT).show();
                            if(email.compareTo("admin@gmail.com")==0){
                                startActivity(new Intent(LoginAuth.this, MainAdmin.class));
                                finish();
                            }else{
                                Toast.makeText(LoginAuth.this, "ok", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginAuth.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginAuth.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("yeuthich", "Activity_yeuthich");
            startActivity(intent);
            finish();
        }
    }
}