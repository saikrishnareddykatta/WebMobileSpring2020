package com.team.task;

import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class RegisterActivity extends AppCompatActivity
{
    private Button CreateAccountButton;
    private EditText UserEmail, UserPassword;
    private TextView AlreadyHaveAccountLink;
    
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); //Set Content View of Register Activity
        
        
        mAuth = FirebaseAuth.getInstance();//firebase authorization
        RootRef = FirebaseDatabase.getInstance().getReference();//fetching and storing the root reference


        InitializeFields();


        AlreadyHaveAccountLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SendUserToLoginActivity(); //if the user has an account already send user to login activity
            }
        });


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateNewAccount(); //if the user wants to create a new account
            }
        });
    }

    private void CreateNewAccount()//creating a new account
    {
        String email = UserEmail.getText().toString(); //email id
        String password = UserPassword.getText().toString();// password
        
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show();//toasting a message if the email field is empty
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show();////toasting a message if the password field is empty
        }
        else
        {
            loadingBar.setTitle("Creating New Account"); //setting the title for creating a new account
            loadingBar.setMessage("Please wait, while we wre creating new account for you..."); //setting messages in the loading bar
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) // if the task being successfull
                            {
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();//fetching the device token

                                String currentUserID = mAuth.getCurrentUser().getUid();//getting the current user id
                                RootRef.child("Users").child(currentUserID).setValue("");


                                RootRef.child("Users").child(currentUserID).child("device_token")
                                        .setValue(deviceToken);// setting the value for device token

                                SendUserToMainActivity(); //sending user to main activity
                                Toast.makeText(RegisterActivity.this, "Account Created Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss(); //dismiss the loasing bar after account created successfully
                            }
                            else 
                            {
                                String message = task.getException().toString(); //fetch and store the exception
                                Toast.makeText(RegisterActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show(); //displaying error message with the help of toast
                                loadingBar.dismiss(); // dismiss the loading bar after the error displayed
                            }
                        }
                    });
        }
    }




    private void InitializeFields()
    {
        CreateAccountButton = (Button) findViewById(R.id.register_button);
        UserEmail = (EditText) findViewById(R.id.register_email);
        UserPassword = (EditText) findViewById(R.id.register_password);
        AlreadyHaveAccountLink = (TextView) findViewById(R.id.already_have_account_link);

        loadingBar = new ProgressDialog(this);
    }


    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
    }


    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}
