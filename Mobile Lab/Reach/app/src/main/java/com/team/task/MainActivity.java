package com.team.task;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsAccessorAdapter myTabsAccessorAdapter;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private String currentUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance(); //Firebase Authorization
        currentUser = mAuth.getCurrentUser(); //Authorising and getting the current user
        currentUserID = mAuth.getCurrentUser().getUid(); //Authorising and getting the current user ID
        RootRef = FirebaseDatabase.getInstance().getReference();


        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Reach"); //Title of the application


        myViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        myTabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessorAdapter);


        myTabLayout = (TabLayout) findViewById(R.id.main_tabs); //Tab Layout
        myTabLayout.setupWithViewPager(myViewPager);
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        if (currentUser == null) //if user is equals to null redirect to login screen
        {
            SendUserToLoginActivity();
        }
        else
        {
            updateUserStatus("online"); // else online status of the user

            VerifyUserExistance(); // Verifying the user existance
        }
    }


    @Override
    protected void onStop()
    {
        super.onStop();

        if (currentUser != null)
        {
            updateUserStatus("offline"); //Updating the user status to offline
        }
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (currentUser != null)
        {
            updateUserStatus("offline");//Updating the user status to offline
        }
    }



    private void VerifyUserExistance() //Verifying the user existance
    {
        String currentUserID = mAuth.getCurrentUser().getUid(); //fetching and storing the current user id

        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.child("name").exists())) //if the user exists then "Welcome" toast is displayed on the screen
                {
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SendUserToSettingsActivity(); //if the user doesn't exist then User profile needs to be created
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) //Selecting an option from the Item Menu
    {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.main_logout_option) //Logout Option
        {
            updateUserStatus("offline");
            mAuth.signOut();
            SendUserToLoginActivity();
        }
        if (item.getItemId() == R.id.main_settings_option) //Setting Option to update the user details
        {
            SendUserToSettingsActivity(); //Sending to User update the details
        }
        if (item.getItemId() == R.id.main_create_group_option) //Create Group Option
        {
            RequestNewGroup(); //Request New Group
        }
        if (item.getItemId() == R.id.main_find_friends_option) //Find Friends Option in the Items Menu
        {
            SendUserToFindFriendsActivity();
        }

        return true;
    }


    private void RequestNewGroup()//requesting to create a new group
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialog);
        builder.setTitle("Enter Group Name :"); //group name

        final EditText groupNameField = new EditText(MainActivity.this);
        groupNameField.setHint("e.g Space Group");
        builder.setView(groupNameField);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                String groupName = groupNameField.getText().toString();

                if (TextUtils.isEmpty(groupName)) //if the group is empty a message will be displayed with the help of toast
                {
                    Toast.makeText(MainActivity.this, "Please write Group Name...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    CreateNewGroup(groupName); //else create a group
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }



    private void CreateNewGroup(final String groupName)//creating a new group with group name as a string
    {
        RootRef.child("Groups").child(groupName).setValue("")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())// if the task is successful then a toast is displayed with "group is created successfuly"
                        {
                            Toast.makeText(MainActivity.this, groupName + " group is Created Successfully...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void SendUserToLoginActivity() //Starting to login activity
    {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent); //starting the login activity

    }

    private void SendUserToSettingsActivity()
    {
        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(settingsIntent); //Starting the Settings Activity if user clicks on Settings and to update the profile
    }


    private void SendUserToFindFriendsActivity()
    {
        Intent findFriendsIntent = new Intent(MainActivity.this, FindFriendsActivity.class);
        startActivity(findFriendsIntent); //Starting the Find Friends Activity
    }



    private void updateUserStatus(String state)
    {
        String saveCurrentTime, saveCurrentDate; //Variables for storing current date and time

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime()); //Saving the Current Date for an activty

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime()); // Saving the current time for an activity

        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", saveCurrentTime); //Storing the current time in the firebase
        onlineStateMap.put("date", saveCurrentDate);//Storing the current date in the firebase
        onlineStateMap.put("state", state);//Storing the current state in the firebase

        RootRef.child("Users").child(currentUserID).child("userState")
                .updateChildren(onlineStateMap);//Storing the data in Users in firebase

    }
}
