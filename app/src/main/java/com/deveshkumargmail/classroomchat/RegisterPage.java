package com.deveshkumargmail.classroomchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.google.firebase.auth.UserProfileChangeRequest;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;






public class RegisterPage extends AppCompatActivity implements
        View.OnClickListener{
    private static final String TAG = "EmailPassword";

    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private EditText mEmailField;
    private EditText mPasswordField;

    EditText username, password;
    Button registerButton;
    String user, pass;
    TextView login;


    private EditText mUsername;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);



            mUsername = findViewById(R.id.display_name);

            mEmailField = findViewById(R.id.username);
            mPasswordField = findViewById(R.id.password);
            // Buttons
            findViewById(R.id.registerButton).setOnClickListener(this);
            // [START initialize_auth]
            mAuth = FirebaseAuth.getInstance();

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();}
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        final ProgressDialog pd = new ProgressDialog(RegisterPage.this);
        pd.setMessage("Creating Accoun... Please wait...");
        pd.show();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();

           /*/            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mUsername.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
*/
                            UserDetails.username=mUsername.getText().toString();
final String usernames=mUsername.getText().toString();
final String pass=mPasswordField.getText().toString();

                            String url = "https://classroomchat2-815d5.firebaseio.com/users.json";

                            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                                @Override
                                public void onResponse(String s) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroomchat2-815d5.firebaseio.com/users");

                                    if(s.equals("null")) {
                                        reference.child(usernames).child("password").setValue(pass);
                                        Toast.makeText(RegisterPage.this, "registration successful", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        try {
                                            JSONObject obj = new JSONObject(s);

                                            if (!obj.has(usernames)) {
                                                reference.child(usernames).child("password").setValue(pass);
                                                Toast.makeText(RegisterPage.this, "registration successful", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(RegisterPage.this, "username already exists", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

//                                    pd.dismiss();
                                }

                            },new Response.ErrorListener(){
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                   Toast.makeText(RegisterPage.this, "ERROR VOLIE1: "+ volleyError , Toast.LENGTH_LONG).show();
                                    System.out.println("" + volleyError );
                                   pd.dismiss();
                                }
                            });

                            RequestQueue rQueue = Volley.newRequestQueue(RegisterPage.this);
                            rQueue.add(request);
                    //        pd.dismiss();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterPage.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                       //     pd.dismiss();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                       // pd.hide();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
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

        String Username = mUsername.getText().toString();
        if (TextUtils.isEmpty(Username)) {
            mUsername.setError("Required.");
            valid = false;
        } else {
            mUsername.setError(null);
        }


        return valid;
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), user.class);
            startActivity(intent);


        } else {
            Toast.makeText(RegisterPage.this, "Authentication failed. Try Again ",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.registerButton) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
    }



}
