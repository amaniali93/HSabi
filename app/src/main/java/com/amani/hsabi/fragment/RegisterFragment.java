package com.amani.hsabi.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amani.hsabi.Activites.FunctionActivity;
import com.amani.hsabi.Interfaces.MediaInterface;
import com.amani.hsabi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    EditText emailid ,password,phoneNo;
    Button btnregister;
    FirebaseAuth mFirebaseAuth;


    public RegisterFragment() {
        // Required empty public constructor
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    private MediaInterface mListener;

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

        View parentView = inflater.inflate(R.layout.fragment_register, container, false);
        emailid = parentView.findViewById(R.id.et_email);
        password = parentView.findViewById(R.id.et_password);
        phoneNo = parentView.findViewById(R.id.et_mobilnumber);
        btnregister = parentView.findViewById(R.id.btn_register);
        TextView btnNext = parentView.findViewById(R.id.tologin);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= emailid.getText().toString();
                String passWord = password.getText().toString();
                String mobileNo = phoneNo.getText().toString();
                if(email.isEmpty()){
                    emailid.setError("Please enter emailId ");
                    emailid.requestFocus();
                }
                else if (passWord.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }
                else if (mobileNo.isEmpty()){
                    phoneNo.setError("Please enter Phone Number");
                    phoneNo.requestFocus();
                }
                else if (email.isEmpty() && passWord.isEmpty() && mobileNo.isEmpty()){
                    Toast.makeText(getActivity(), "Fields are Empty!", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && passWord.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email , passWord).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getActivity(), "Register UnSuccessful, Please try again!", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Intent intent = new Intent(getActivity().getApplication(), FunctionActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Error ocurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.changeFragmentTo(new LoginFragment(), LoginFragment.class.getSimpleName());
                }
            }
        });