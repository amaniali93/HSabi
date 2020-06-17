package com.amani.hsabi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amani.hsabi.Interfaces.MediaInterface;
import com.amani.hsabi.R;
import com.amani.hsabi.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth; // allow to use auth functions like sign-in, sign-up
    private FirebaseUser mFirebaseUser; // allow to access mFirebaseUser info like userId
    private Context mContext;
    private MediaInterface mMeditorCallback;


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mMeditorCallback = (MediaInterface) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parentView = inflater.inflate(R.layout.fragment_register, container, false);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase User
        mFirebaseUser = mAuth.getCurrentUser();


        final TextInputEditText emailid = parentView.findViewById(R.id.et_email);
        final TextInputEditText password = parentView.findViewById(R.id.et_password);// at least 6 chars, A-Z @ 1-9
        final TextInputEditText phoneNo = parentView.findViewById(R.id.et_mobilnumber);
        final Button btnregister = parentView.findViewById(R.id.btn_register);
        final TextView btnNext = parentView.findViewById(R.id.tologin);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailid.getText().toString();
                String pass_word = password.getText().toString();
                String phone = phoneNo.getText().toString();


                if (email.isEmpty()) {
                    emailid.setError("Please write your email");
                } else if (!isEmailValid(email)) {
                    emailid.setError("Please write your email correctly!");
                }else if (phone.isEmpty()) {
                    phoneNo.setError("Please write your phone number");
                } else if (pass_word.isEmpty()) {
                    password.setError("Please write your password");
                } else if (pass_word.length() < 6) {
                    password.setError("Password must be 6 chars at least");
                } else {
                    registerUser(email, phone, pass_word);
                }

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMeditorCallback != null) {
                    mMeditorCallback.changeFragmentTo(new LoginFragment(), LoginFragment.class.getSimpleName());
                }
            }
        });

        return parentView;
    }

    private void registerUser(final String email, final String phone, final String password) {
        //TODO : register user to firebase auth

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(mContext, "Thank you for Registering with us!", Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();

                            User u = new User();
                            u.setEmail(email);
                            u.setPhone(phone);
                            u.setPassword(password);
                            u.setId(firebaseUser.getUid());
                            u.setRegisterDate(getCurrentDate());
                            addUserToDB(u);//store data to firebase db


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

    private void addUserToDB(User u) {
        //TODO : write user to firebase DB

        // Write a message to the mDatabase
        FirebaseDatabase database = FirebaseDatabase.getInstance();// allow to access firebase mDatabase
        DatabaseReference myRef = database.getReference();//allow to crate refrance == table
        myRef.child(u.getId()).setValue(u).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // move to login page
                    if (mMeditorCallback != null) {
                        mMeditorCallback.changeFragmentTo(new LoginFragment(), LoginFragment.class.getSimpleName());
                    }

                } else {
                    Toast.makeText(mContext, "Error happened while writing data to firebase!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String getCurrentDate() {
        //get current date
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

       return sdf.format(c);
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}