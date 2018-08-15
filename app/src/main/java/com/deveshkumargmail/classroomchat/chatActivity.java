package com.deveshkumargmail.classroomchat;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class chatActivity extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    DatabaseReference reference1, reference2;
FirebaseAuth mFirebaseAuth;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mFirebaseAuth = FirebaseAuth.getInstance(Objects.requireNonNull(FirebaseApp.initializeApp(this)));
            }


            layout = findViewById(R.id.layout1);
        layout_2 =findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);


        reference1 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroomchat2-815d5.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroomchat2-815d5.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);


            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String messageText = messageArea.getText().toString();



                    if(!messageText.equals("")){
                       Map<String, String> map = new HashMap<String, String>();
                        //    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();

                        map.put("message", messageText);
                        map.put("user", UserDetails.username);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                        messageArea.setText("");


           /*         if (!messageText.equals("")) {
                        Map<String, String> map = new HashMap<>();
                        map.put("message", messageText);
                        map.put("user", UserDetails.username);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                        messageArea.setText("");*/
                    }
                }
            });

            reference1.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    //      Map map = dataSnapshot.getValue(Map.class);

                  //  Map map = (Map)dataSnapshot.getValue();
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                  //  assert map != null;
                    String message = map.get("message").toString();
                    String userName = map.get("user").toString();

                    if (userName.equals(UserDetails.username)) {
                        addMessageBox("       "+UserDetails.username+":-\n      " + message+"        \n", 1);
                    } else {
                        addMessageBox(UserDetails.chatWith + ":-        \n   " + message, 2);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });

        }catch (Exception e){
            Toast.makeText(this, "Error2:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

   // @SuppressLint("RtlHardcoded")
    public void addMessageBox(String message, int type){


        try{
        TextView textView = new TextView(chatActivity.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }catch (Exception e){

            Toast.makeText(this, "Error3:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
}













