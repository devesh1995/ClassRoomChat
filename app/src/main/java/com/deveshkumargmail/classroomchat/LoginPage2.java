package com.deveshkumargmail.classroomchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
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
public class LoginPage2 extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page2);



    mEmailField = findViewById(R.id.username);
    mPasswordField = findViewById(R.id.password);

    // Buttons
    findViewById(R.id.loginButton).setOnClickListener(this);
    findViewById(R.id.register).setOnClickListener(this);


    // [START initialize_auth]
    mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }



        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPage2.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {

                        }

                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }



    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), user.class);
            startActivity(intent);

        } else {
            Toast.makeText(LoginPage2.this, "Authentication failed.2 ",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.register) {
            Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
            startActivity(intent);

        } else if (i == R.id.loginButton) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }





}
