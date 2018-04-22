package com.example.prashant_admin.raspnotifier;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chilkatsoft.CkCrypt2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private SharedPreferences mSharedPreferences;
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mProgressBar;
    private DatabaseReference mFirebaseDatabaseReference;
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TemperatureDataAdapter temperatureDataAdapter;
    private List<TemperatureData> temperatureDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference("data");

        temperatureDataList = new ArrayList<TemperatureData>();
        recyclerView = (RecyclerView)findViewById(R.id.messageRecyclerView);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        CkCrypt2 crypt = new CkCrypt2();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }


        // [END handle_data_extras]

    }


    @Override
    protected void onStart() {
        super.onStart();
        temperatureDataList.clear();
        mFirebaseDatabaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);
                TemperatureData temperatureData = dataSnapshot.getValue(TemperatureData.class);
                temperatureDataList.add(temperatureData);
                temperatureDataAdapter = new TemperatureDataAdapter(MainActivity.this, temperatureDataList);
                recyclerView.setAdapter(temperatureDataAdapter);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notification:
                // User chose the "Settings" item, show the app settings UI...
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                // [END subscribe_topics]

                // Log and toast
                String msg1 = "Added Notification to the App";
                Log.d(TAG, msg1);
                Toast.makeText(MainActivity.this, msg1, Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_token:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                String token = FirebaseInstanceId.getInstance().getToken();

                // Log and toast
                String msg2 = getString(R.string.msg_token_fmt, token);
                Log.d(TAG, msg2);
                Toast.makeText(MainActivity.this, msg2, Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    static {
        // IMPORTANT: If one of the Chilkat subset shared libs is used, the name
        // passed to loadLibrary must match the share lib name.  For example, if the
        // shared lib is libchilkatcrypt.so, then pass "chilkatcrypt" to System.loadLibrary.

        System.loadLibrary("chilkat");
    }
}


