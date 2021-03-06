package us.cmc.android.aitforum;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.cmc.android.aitforum.model.User;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.cmc.android.aitforum.model.User;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btnRegister)
    void registerClick() {
        if (!isFormValid()) {
            return;
        }


        firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                hideProgressDialog();

                                if (task.isSuccessful()) {
                                    FirebaseUser fbUser = task.getResult().getUser();
                                    fbUser.updateProfile(new UserProfileChangeRequest.Builder().
                                            setDisplayName(usernameFromEmail(fbUser.getEmail())).build());

                                    User user = new User(usernameFromEmail(fbUser.getEmail()), fbUser.getEmail());
                                    databaseReference.child("users").child(fbUser.getUid()).setValue(user);

                                    Toast.makeText(LoginActivity.this, "User created", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                );
    }

    @OnClick(R.id.btnLogin)
    void loginClick() {
        //check for crash
        //int i = 6/0;

        if (!isFormValid()) {
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(
                etEmail.getText().toString(),
                etPassword.getText().toString()).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    //if successfully grabs email and password, onComplete method is called
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //open next activity!
                            startActivity(new Intent(LoginActivity.this, PostsActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    private boolean isFormValid() {
        if (TextUtils.isEmpty(etEmail.getText().toString())) {
            etEmail.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            etPassword.setError("Required");
            return false;
        }

        return true;
    }

}

