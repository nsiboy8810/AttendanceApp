package android.example.com.attendanceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    String mEmail, mPassword;
    ProgressDialog pbDialog;
    EditText etPassword, editText;
    TextView tvRegister;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    Button mBtnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pbDialog = new ProgressDialog(this);
        pbDialog.setMessage("Logging in...");
        tvRegister = findViewById(R.id.tv_register);
        etPassword = findViewById(R.id.et_password);
        editText = findViewById(R.id.et_email);
        mBtnLogin = findViewById(R.id.btn_login);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mEmail = editText.getText().toString().trim();
                    mPassword = etPassword.getText().toString().trim();
                    if (mEmail != null && mPassword != null) {
                        authenticate(mEmail, mPassword);
                    } else {
                        Toast.makeText(LoginActivity.this, "Email and Password must not be empty", Toast.LENGTH_LONG).show();
                    }


                }catch (Exception ex){
                    Toast.makeText(LoginActivity.this, "Email and Password must not be empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser user1 = mAuth.getCurrentUser();
//        if (user1 != null){
//            mEmail = user1.getEmail();
//            Intent intent = new Intent(LoginActivity.this, SelectUserActivity.class);
//            intent.putExtra("email",mEmail);
//            startActivity(intent);
//            finish();
//
//        }
//    }

    void authenticate(String email, String password){
        showProgressBar();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d( "signInWithEmail:success", "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, SelectUserActivity.class);
                            intent.putExtra("email",mEmail);
                            startActivity(intent);
                            finish();
                        } else {
                            hideProgressBar();
                            // If sign in fails, display a message to the user.
                            Log.w( "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Incorrect email or password.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
    void showProgressBar(){
        pbDialog.show();
    }
    void hideProgressBar(){
        pbDialog.hide();
    }
}
