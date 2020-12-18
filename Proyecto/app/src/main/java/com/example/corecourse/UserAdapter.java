package com.example.corecourse;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    ArrayList<Object> userArrayList;
    Map<String, User> userMap;
    TinyDB tinyDB;

    FirebaseDatabase database;
    DatabaseReference myRef;

    public UserAdapter(ArrayList<Object> userArrayList) {
        this.userArrayList = userArrayList;
    }
    public UserAdapter(Map<String, User> userMap) {
        this.userMap = userMap;
    }
    public UserAdapter(Map<String, User> userMap, ArrayList<Object> userArrayList) {
        this(userMap);
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list,parent,false);

        tinyDB = new TinyDB(parent.getContext());

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Users");

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String userInfo = userArrayList.get(position).toString();
        holder.showUser.setText(userInfo);

        // Funcionamiento del primer botón para eliminar el usuario
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditPopup(v, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView showUser;
        ImageButton button;
        ImageButton editButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showUser = itemView.findViewById(R.id.user_list_txt);
            button = itemView.findViewById(R.id.delete_button);
            editButton = itemView.findViewById(R.id.edit_button);
        }
    }

    public void removeAt(int position) {
        User user = (User)userArrayList.get(position);

        userArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position, userArrayList.size());
        notifyItemRangeChanged(position, userArrayList.size());
        tinyDB.putListObject("user_list", userArrayList);

        String key = myRef.push().getKey();

        for(String keyM: userMap.keySet())
            if (userMap.get(keyM) == user)
                key = keyM;

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + key, null);

        myRef.updateChildren(childUpdates);
    }

    // Forma aproximada de mostrar un layout con unos edit text
    public void showEditPopup(View v, int position) {
        Dialog editPopup = new Dialog(v.getContext());
        editPopup.setContentView(R.layout.edit_popup);
        User user = (User)userArrayList.get(position);

        Button cancelBtn, submitBtn;
        submitBtn = editPopup.findViewById(R.id.edit_submit);
        cancelBtn = editPopup.findViewById(R.id.edit_cancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPopup.dismiss();
            }
        });

        EditText name, gender, age;
        name = editPopup.findViewById(R.id.edit_name);
        gender = editPopup.findViewById(R.id.edit_gender);
        age = editPopup.findViewById(R.id.edit_age);

        name.setText(user.getName() + " " + user.getSurname());
        gender.setText(user.getGender());
        age.setText("" + user.getAge());

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] fullName = name.getText().toString().split(" ", 2);
                user.setName(fullName[0]);
                user.setSurname(fullName[1]);

                user.setGender(gender.getText().toString());

                int newAge = Integer.parseInt(age.getText().toString());
                if (newAge > 3 && newAge < 150)
                    user.setAge(newAge);

                userArrayList.set(position, user);

                tinyDB.putListObject("user_list", userArrayList);

                notifyItemChanged(position);

                String key = myRef.push().getKey();

                for(String keyM: userMap.keySet())
                    if (userMap.get(keyM) == user)
                        key = keyM;

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put("/" + key, user);
                
                myRef.updateChildren(childUpdates);

                editPopup.dismiss();
            }
        });
        editPopup.show();
    }

}
