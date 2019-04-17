package com.fracessoftware.authenticationfirebase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.util.ExtraConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //Firebase Authentication
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        //check to see if there is a valid current user
        if (firebaseUser == null){
            startActivity(MainActivity.createIntent(this));
            finish();
            return;
        }



        TextView userEmail= findViewById(R.id.user_email);
        TextView userName= findViewById(R.id.user_display_name);

        userEmail.setText(firebaseUser.getEmail());
        userName.setText(firebaseUser.getDisplayName());

        Button btnSignOut = findViewById(R.id.btnSignOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    public void signOut() {
        AuthUI.getInstance().signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            startActivity(MainActivity.createIntent(SignIn.this));
                            finish();
                        }else{
                            //Sign out failed
                        }
                    }
                });
    }

    public static Intent createIntent(Context context, IdpResponse idpResponse){
        Intent intent = new Intent().setClass(context, SignIn.class)
                .putExtra(ExtraConstants.IDP_RESPONSE, idpResponse);
        return intent;
    }
}
