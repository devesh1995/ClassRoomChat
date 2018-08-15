package com.deveshkumargmail.classroomchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.firebase.auth.FirebaseAuth;

public class user extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;





    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
try {
    Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(myToolbar);
}catch (Exception e){
    Toast.makeText(user.this, "inflate1: "+e.getMessage(),Toast.LENGTH_SHORT).show();
}
        usersList = (ListView)findViewById(R.id.usersList);
        noUsersText = (TextView)findViewById(R.id.noUsersText);

        pd = new ProgressDialog(user.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://classroomchat2-815d5.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(user.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(user.this, chatActivity.class));
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try{
        getMenuInflater().inflate(R.menu.logoutmenu, menu);

    }catch (Exception e){
            Toast.makeText(user.this, "inflate4: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try{
        switch (item.getItemId()) {
            case R.id.action_sign_out: {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginPage2.class);
                startActivity(intent);
                // do your sign-out stuff
                break;
            }
            // case blocks for other MenuItems (if any)
        }

    }catch(Exception e){

            Toast.makeText(user.this, "inflate2: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        return false;
    }
    
    
    
    
    
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {
                    al.add(key);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }

}
