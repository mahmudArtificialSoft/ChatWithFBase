package com.artificialsoft.chatappwithfireb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText userName, userPass, userEmail;
    Button buttonRegister;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        userName = findViewById(R.id.userName);
        userPass = findViewById(R.id.userPassword);
        userEmail = findViewById(R.id.userEmail);

        buttonRegister = findViewById(R.id.btn_register);

        auth = FirebaseAuth.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempUserName = userName.getText().toString().trim();
                String tempEmail = userEmail.getText().toString().trim();
                String tempPass = userPass.getText().toString().trim();

                if (TextUtils.isEmpty(tempUserName) || TextUtils.isEmpty(tempEmail) || TextUtils.isEmpty(tempPass)){
                    Toast.makeText(RegisterActivity.this, "All field required!", Toast.LENGTH_SHORT).show();
                } else if (tempPass.length()<6){
                    Toast.makeText(RegisterActivity.this, "password must be 6 character.", Toast.LENGTH_SHORT).show();
                } else {
                    register(tempUserName, tempEmail, tempPass);
                }
            }
        });
    }

    public void register(final String userName, String userEmail, String userPassword){
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    assert firebaseUser != null;
                    String userID =  firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", userID);
                    hashMap.put("username", userName);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "You can't register woth this email or password!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
