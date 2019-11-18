package android.example.com.attendanceapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText mRegEmail, mRegPassword,mRegConPassword, mName,mReg;
    String email, password, conPassword, name,regNumber;
    CheckBox checkBox;
    Button btnRegister;
// ...
// Initialize Firebase Auth


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        mRegEmail = findViewById(R.id.et_reg_email);
        mRegPassword = findViewById(R.id.et_reg_password);
        mRegConPassword = findViewById(R.id.et_reg_con_password);
        checkBox =findViewById(R.id.ch_staff);
        btnRegister = findViewById(R.id.btn_register);
        mName = findViewById(R.id.et_reg_name);
        mReg = findViewById(R.id.et_reg_number);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    mName.setVisibility(View.VISIBLE);
                    mReg.setVisibility(View.VISIBLE);
                }
                else {
                    mName.setVisibility(View.GONE);
                    mReg.setVisibility(View.GONE);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (checkBox.isChecked()){
                        email = mRegEmail.getText().toString();
                        password = mRegPassword.getText().toString();
                        conPassword = mRegConPassword.getText().toString();
                        regNumber = mReg.getText().toString();
                        name = mName.getText().toString();
                        if (password.equals(conPassword)){
                            signUp(email,password);
                        }else {
                            Toast.makeText(RegistrationActivity.this,"Password mismatch", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        email = mRegEmail.getText().toString();
                        password = mRegPassword.getText().toString();
                        conPassword = mRegConPassword.getText().toString();
                        if (password.equals(conPassword)){
                            signUp(email,password);
                        }else {
                            Toast.makeText(RegistrationActivity.this,"Password mismatch", Toast.LENGTH_LONG).show();
                        }
                    }

                }catch (Exception ex){
                    Toast.makeText(RegistrationActivity.this, "Fields must not be Empty", Toast.LENGTH_LONG).show();
                }
            }
        });

    }



    void signUp(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (checkBox.isChecked()){
                                registerAsStudent(email,regNumber,name);
                            }else{
                                registerAsStaff(email);

                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("","createUserWithEmail:failure");
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
    void registerAsStaff(String mEmail){
        Map<String, String> user = new HashMap<>();
        user.put("is Staff", "true");
        db.collection("Staff").document(mEmail).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }
        });

    }
    void registerAsStudent(String mEmail, String reg, String name){

        Map<String, String> user = new HashMap<>();
        user.put("name", name);
        user.put("Reg",reg);
        db.collection("Students").document(mEmail).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Intent intent = new Intent(RegistrationActivity.this, StudentActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();

            }
        });
    }
}
