package com.example.corecourse.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.corecourse.R;
import com.example.corecourse.TinyDB;
import com.example.corecourse.User;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    private TextView showInfoTxt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        showInfoTxt = root.findViewById(R.id.show_user_info_text);

        TinyDB tinyDB = new TinyDB(getContext());

        User user = tinyDB.getObject("my_user", User.class);

        if (user != null)
            showInfoTxt.setText(user.toString());

        return root;
    }
}