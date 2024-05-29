package com.example.communnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

public class ProfileFragment extends Fragment {

    private TextView setTextFullName;
    private Button logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button changeSomeThingSaProfile;
        setTextFullName = view.findViewById(R.id.txt_CompleteName);
        changeSomeThingSaProfile = view.findViewById(R.id.btnEdit);
        logoutBtn = view.findViewById(R.id.btnLogout);

        changeSomeThingSaProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Edit Profile", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), EditProfile.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Logging out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });


        changeSomeThingSaProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Edit Profile", Toast.LENGTH_SHORT).show();

                // Get userId from arguments
                Bundle args = getArguments();
                if (args != null) {
                    String userId = args.getString("userId");
                    if (userId != null) {
                        // Pass userId to EditProfile activity
                        Intent intent = new Intent(getActivity(), EditProfile.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "User ID not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            for (UserInfo profile : currentUser.getProviderData()) {
                if (profile.getProviderId().equals(GoogleAuthProvider.PROVIDER_ID)) {
                    setTextFullName.setText(currentUser.getDisplayName());
                    break;
                } else {
                    UserProfile userProfile = new UserProfile();
                    Bundle args = getArguments();
                    if (args != null) {
                        String userId = args.getString("userId");
                        if (userId != null) {
                            userProfile.getFullname(userId, new UserProfile.FullNameCallback() {
                                @Override
                                public void onCallback(String fullName) {
                                    if (fullName != null) {
                                        setTextFullName.setText(fullName);
                                    } else {
                                        System.out.println("Failed to retrieve full name.");
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }else{
            UserProfile userProfile = new UserProfile();
            Bundle args = getArguments();
            if (args != null) {
                String userId = args.getString("userId");
                if (userId != null) {
                    userProfile.getFullname(userId, new UserProfile.FullNameCallback() {
                        @Override
                        public void onCallback(String fullName) {
                            if (fullName != null) {
                                setTextFullName.setText(fullName);
                            } else {
                                System.out.println("Failed to retrieve full name.");
                            }
                        }
                    });
                }
            }
        }



        return view;
    }
}