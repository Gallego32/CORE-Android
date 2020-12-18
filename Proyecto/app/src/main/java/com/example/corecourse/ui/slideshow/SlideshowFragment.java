package com.example.corecourse.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private RecyclerView recycler;

    private TextView userListTxt;

    private ArrayList<Object> userList = new ArrayList<Object>();
    private ArrayList<Object> DBUserList = new ArrayList<Object>();
    private Map<String, User> userMap = new HashMap<>();

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Users");

        // Iteramos sobre la lista de usuarios y los insertamos dentro de
        // la recycled view
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data: snapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    System.out.println(data.getKey());
                    userMap.put(data.getKey(), user);
                    DBUserList.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error en la lectura de datos", Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        });

        //UserAdapter adapter = new UserAdapter(userList);
        //UserAdapter adapter = new UserAdapter(DBUserList);
        UserAdapter adapter = new UserAdapter(userMap, DBUserList);
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