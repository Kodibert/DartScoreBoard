package com.darts.dartscoreboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FragmentAddPlayer extends Fragment {

    View view;
    Button btnAdd, delete;
    EditText et_name, et_check;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_player, container, false);

        // get the Reference of Button
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        delete = (Button) view.findViewById(R.id.btn_done);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_check = (EditText) view.findViewById(R.id.et_check);


        btnAdd.setOnClickListener(view -> {

            PlayerModel playerModel;
            try {
                playerModel = new PlayerModel(-1, et_name.getText().toString(), Integer.parseInt(et_check.getText().toString()));
                Toast.makeText(getActivity(), playerModel.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error, creating player. Only Numbers are allowed in 'Checkout field'", Toast.LENGTH_SHORT).show();
                playerModel = new PlayerModel();

            }
            DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());
            boolean success = dataBaseHelper.addPlayer(playerModel);

            Toast.makeText(getActivity(), "success = " + success, Toast.LENGTH_SHORT).show();


        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }
}