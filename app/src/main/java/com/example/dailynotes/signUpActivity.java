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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signUpActivity extends AppCompatActivity {

    private EditText signUpEmailEt, signUpPassEt;
    private Button signUpBtn;
    private TextView gotoLoginPageTv;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpEmailEt = findViewById(R.id.signUpEmailEtId);
        signUpPassEt = findViewById(R.id.singUpPasswordEtId);
        signUpBtn = findViewById(R.id.signUpBtnId);
        gotoLoginPageTv = findViewById(R.id.gotoLoginPageId);

        firebaseAuth = FirebaseAuth.getInstance();

       gotoLoginPageTv.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(signUpActivity.this, MainActivity.class);
               startActivity(intent);
           }
       });

       signUpBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String mail = signUpEmailEt.getText().toString().trim();
               String pass = signUpPassEt.getText().toString().trim();

               if (mail.isEmpty() || pass.isEmpty()) {
                   Toast.makeText(getApplicationContext(), "Enter email and password properly", Toast.LENGTH_SHORT).show();
               }
               else if (pass.length() < 7) {
                   Toast.makeText(getApplicationContext(), "Password length should be greater than 7", Toast.LENGTH_SHORT).show();
               }
               else {
                   //Registered the user to firebase
                   firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                               sendEmailVerification();
                           }
                           else {
                               Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
           }
       });
    }

    //send email verification
    private void sendEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
             firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     Toast.makeText(getApplicationContext(), "Verification email is sent\nVerify and login again", Toast.LENGTH_SHORT).show();
                     firebaseAuth.signOut();
                     finish();
                     startActivity(new Intent(signUpActivity.this, MainActivity.class));
                 }
             });
        }
        else {
            Toast.makeText(getApplicationContext(), "Failed to sent the verification mail", Toast.LENGTH_SHORT).show();
        }
    }
}