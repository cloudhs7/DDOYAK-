package com.example.caucse.ddoyak_g;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;

    @Override
        public void onTokenRefresh() {
            // Get updated InstanceID token.
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            if(MainActivity.patient_items.size()!=0) {
                for(int i=0;i<MainActivity.patient_items.size();i++){
                    myRef = database.getReference(MainActivity.patient_items.get(i).id);
                    myRef.child("TOKEN_GUARDIAN").setValue(refreshedToken);
                }
            }

            Log.d("TAG", "Refreshed token: " + refreshedToken);
             // If you want to send messages to this application instance or
            // manage this apps subscriptions on the server side, send the
            // Instance ID token to your app server.
            //sendRegistrationToServer(refreshedToken);
        }
}
