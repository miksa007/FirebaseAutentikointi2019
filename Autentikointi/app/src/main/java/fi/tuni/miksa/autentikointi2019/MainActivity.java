package fi.tuni.miksa.autentikointi2019;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //osa 1
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    private final String TAG = "softa";

    //osa 2
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //osa 1
        mAuth = FirebaseAuth.getInstance();


        //osa2
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d(TAG, "tilamuuttui");
                if (user != null) {

                    // Sign in logic here.
                }
            }
        };

    }
    //osa1
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    //osa 1
    public void kirjauduSisaan(View view) {
        Log.d(TAG, "kirjaudu nappulaa painettu");
        createSignInIntent();

    }

    //osa 1
    public void kirjauduUlos(View view) {
        Log.d(TAG, "kirjaudu ulos nappulaa painettu");
        signOut();

    }

    //osa 1
    public void createSignInIntent() {
        Log.d(TAG, "autetikointia alkoi");
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());
//                new AuthUI.IdpConfig.PhoneBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build(),
//                new AuthUI.IdpConfig.FacebookBuilder().build(),
//                new AuthUI.IdpConfig.TwitterBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    //osa 1
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "resulttia pukkaa");
        if (requestCode == RC_SIGN_IN) {
            Log.d(TAG, "resulttia pukkaa RC_SIGN_IN ok");
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                Log.d(TAG, "resulttia pukkaa RESULT ok");
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                Log.d(TAG, "resulttia pukkaa - ei onnistunut");
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    //osa 1
    public void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Log.d(TAG, "uloskirjauduttu");
                    }
                });
        // [END auth_fui_signout]
    }

    //osa 2
    public void kirjautumisenTila(View view) {
        Log.d(TAG, "tilakysely");
        checkCurrentUser();
    }

    //osa 2
    public void checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        TextView textView = findViewById(R.id.textView);
        if (user != null) {
            textView.setText("sisällä "+user.getEmail());
            Log.d(TAG, user.getEmail());
            Log.d(TAG, user.getUid());
            // User is signed in
        } else {
            textView.setText("Ei kirjautunut");
            // No user is signed in
        }
        // [END check_current_user]
    }
}
