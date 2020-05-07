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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    private Button LoginButton, PhoneLoginButton;
    private EditText UserEmail, UserPassword;
    private TextView NeedNewAccountLink, ForgetPasswordLink;

    private DatabaseReference UsersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance(); //Firebase Authorization
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users"); //getting the reference of users


        InitializeFields();


        NeedNewAccountLink.setOnClickListener(new View.OnClickListener() { //Creating a new Account
            @Override
            public void onClick(View view)
            {
                SendUserToRegisterActivity(); //Sending to register Activity to register the user
            }
        });


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AllowUserToLogin(); // allow user to login into the application
            }
        });

        PhoneLoginButton.setOnClickListener(new View.OnClickListener() { //Allowing the user to logn using phone
            @Override
            public void onClick(View view)
            {
                Intent phoneLoginIntent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                startActivity(phoneLoginIntent); //Starting the Phone Login Activity
            }
        });
    }




    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();//fetching the current user
        if (currentUser != null)
        {
            SendUserToMainActivity(); // if the user is present, sending the user to main activity
        }
    }



    private void AllowUserToLogin()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please enter email...", Toast.LENGTH_SHORT).show(); // if the email field is empty, a toast will display a message about it
        }
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password...", Toast.LENGTH_SHORT).show(); // if the password field is empty, a toast will display a message about it
        }
        else
        {
            loadingBar.setTitle("Sign In");
            loadingBar.setMessage("Please wait....");
            loadingBar.setCanceledOnTouchOutside(true);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password) //Signing with email and password
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) //if the task being succesfull
                            {
                                String currentUserId = mAuth.getCurrentUser().getUid();//fetching and storing the user id
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();//fetching and storing the device token from firebase

                                UsersRef.child(currentUserId).child("device_token")
                                        .setValue(deviceToken)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                if (task.isSuccessful()) //if the task being succesfull
                                                {
                                                    SendUserToMainActivity(); //Sending user to main activity
                                                    Toast.makeText(LoginActivity.this, "Logged in Successful...", Toast.LENGTH_SHORT).show(); //toasting a message of loin succesfull
                                                    loadingBar.dismiss(); //dismiss the loading bar
                                                }
                                            }
                                        });
                            }
                            else
                            {
                                String message = task.getException().toString(); //fetching the exception message
                                Toast.makeText(LoginActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show(); //toasting an error
                                loadingBar.dismiss(); //dismiss the oading bar
                            }
                        }
                    });
        }
    }



    private void InitializeFields()//Initializing the fields
    {
        LoginButton = (Button) findViewById(R.id.login_button);
        PhoneLoginButton = (Button) findViewById(R.id.phone_login_button);
        UserEmail = (EditText) findViewById(R.id.login_email); //Email Button
        UserPassword = (EditText) findViewById(R.id.login_password);
        NeedNewAccountLink = (TextView) findViewById(R.id.need_new_account_link); // Need New Account Link
        ForgetPasswordLink = (TextView) findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this); //Loading Bar
    }



    private void SendUserToMainActivity()//Sending the user to main activity
    {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent); // Starting the Main Activity
        finish();
    }

    private void SendUserToRegisterActivity()//Sending user to register activity
    {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent); //starting the Register Activity
    }
}
