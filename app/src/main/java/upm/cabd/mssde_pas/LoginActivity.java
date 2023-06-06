package upm.cabd.mssde_pas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String LOG_TAG = "LoginActivity";

    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
            setContentView(R.layout.activity_login);

            editTextEmail = findViewById(R.id.editTextEmail);
            editTextPassword = findViewById(R.id.editTextPassword);
            final Button buttonSignUp = findViewById(R.id.buttonSignUp);
            final Button buttonSignIn = findViewById(R.id.buttonSignIn);

            buttonSignUp.setOnClickListener(this::singUp);
            buttonSignIn.setOnClickListener(this::signIn);
        } else {
            wakeMainActivity ();
        }
    }

    private void signIn(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(LOG_TAG, "Login Successful " + email);
                            wakeMainActivity ();
                        } else {
                            Log.e(LOG_TAG, "Login Failed", task.getException());
                        }
                    }
                });
    }

    private void singUp(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        //TODO: Missing implementation for adding users from the Login Screen. Tested by adding user in Firebase Console.
    }

    private void wakeMainActivity () {
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.putExtra("userEmail", email);
        startActivity(mainActivity);
    }
}