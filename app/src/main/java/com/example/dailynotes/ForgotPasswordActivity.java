package com.example.dailynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText forgotPass;
    private Button passRecoverBtn, goBackToLoginPage;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPass = findViewById(R.id.forgotPassEmailEtId);
        passRecoverBtn = findViewById(R.id.recoverMailBtnId);
        goBackToLoginPage = findViewById(R.id.goBackToLoginBtnId);

        firebaseAuth = FirebaseAuth.getInstance();

        goBackToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        passRecoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String mail = forgotPass.getText().toString().trim();
                 if (mail.isEmpty()) {
                     Toast.makeText(getApplicationContext(), "Enter your mail first", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     //We have to send password to recovery mail (udvash+unmesh)

                     firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             if (task.isSuccessful()) {
                                 Toast.makeText(getApplicationContext(), "Mail sent, You can recover your password using mail", Toast.LENGTH_SHORT).show();
                                 finish();
                                 startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                             }
                             else {
                                 Toast.makeText(getApplicationContext(), "Account doesn't exist or mail is wrong", Toast.LENGTH_SHORT).show();
                             }
                         }

                     });
                 }
            }
        });
    }
}