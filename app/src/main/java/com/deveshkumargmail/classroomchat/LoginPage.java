package com.deveshkumargmail.classroomchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);




        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }





    private void updateUI(FirebaseUser user) {

        if (user != null) {


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), user.class);
                    startActivity(intent);
                }
            }, 5000);



        } else {


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginPage2.class);
                    startActivity(intent);  // yourMethod();
                }
            }, 5000);







        }
    }



}
