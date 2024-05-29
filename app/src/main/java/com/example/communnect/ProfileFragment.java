package com.example.communnect;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    private TextView setTextFullName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button changeSomeThingSaProfile;
        setTextFullName = view.findViewById(R.id.txt_CompleteName);
      //  changeSomeThingSaProfile = view.findViewById(R.id.btnEditProfile1);

//        changeSomeThingSaProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Button clicked", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), EditProfile.class);
//                startActivity(intent);
//            }
//        });

        UserProfile userProfile = new UserProfile();
        userProfile.getFullname(new UserProfile.FullNameCallback() {
            @Override
            public void onCallback(String fullName) {
                if (fullName != null) {
                    setTextFullName.setText(fullName);
                } else {
                    System.out.println("Failed to retrieve full name.");
                }
            }
        });

        return view;
    }
}
