package com.amani.hsabi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amani.hsabi.activites.FunctionActivity;
import com.amani.hsabi.Interfaces.MediaInterface;
import com.amani.hsabi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    EditText emailid, password;
    Button btnlogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener aAuthstatelistener;
    // TODO: Rename parameter arguments, choose names that match

    public LoginFragment() {
        // Required empty public constructor
    }

    private MediaInterface mListener;


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MediaInterface) {
            mListener = (MediaInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MediatorInterface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View parentView = inflater.inflate(R.layout.fragment_login, container, false);
        emailid = parentView.findViewById(R.id.edt_email);
        password = parentView.findViewById(R.id.edt_password);
        btnlogin = parentView.findViewById(R.id.btnd_login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        aAuthstatelistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(getActivity(), "you are logged in", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity().getApplication(), FunctionActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getActivity(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailid.getText().toString();
                String passWord = password.getText().toString();
                if (email.isEmpty()) {
                        emailid.setError("Please enter emailId ");
                    emailid.requestFocus();
                } else if (passWord.isEmpty()) {
                    password.setError("Please enter password");
                    password.requestFocus();
                } else {
                    mAuth.signInWithEmailAndPassword(email, passWord)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intefunction = new Intent(getActivity().getApplication(), FunctionActivity.class);
                                        startActivity(intefunction);
                                    } else {
                                        Toast.makeText(getActivity(), "Loggin Error!", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            });
                }

            }
        });

        TextView btnNext = parentView.findViewById(R.id.register);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                mListener.changeFragmentTo(new RegisterFragment(),RegisterFragment.class.getSimpleName());
            }}
        });


        return parentView;
    }

}


