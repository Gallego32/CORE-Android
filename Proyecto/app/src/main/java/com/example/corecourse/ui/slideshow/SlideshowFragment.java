package com.example.corecourse.ui.slideshow;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.corecourse.R;
import com.example.corecourse.TinyDB;
import com.example.corecourse.User;
import com.example.corecourse.UserAdapter;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private RecyclerView recycler;

    private TextView userListTxt;

    private ArrayList<Object> userList = new ArrayList<Object>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        recycler = (RecyclerView) root.findViewById(R.id.user_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));

        //userListTxt = root.findViewById(R.id.show_user_list_info_text);

        TinyDB tinyDB = new TinyDB(getContext());

        userList = tinyDB.getListObject("user_list", User.class);

        UserAdapter adapter = new UserAdapter(userList);
        recycler.setAdapter(adapter);

        /*if (userList != null) {
            String users = "";
            for (int i = 0; i < userList.size(); i++) {
                users = users.concat(userList.get(i).toString()) + "\n\n";
            }
            userListTxt.setText(users);
            //userListTxt.setText("" + userList.size());
        }*/

        return root;
    }
}