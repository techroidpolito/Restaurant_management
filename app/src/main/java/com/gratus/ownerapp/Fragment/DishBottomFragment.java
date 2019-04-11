package com.gratus.ownerapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.gratus.ownerapp.R;

public class DishBottomFragment extends BottomSheetDialogFragment {
    private TextView save_tv, name_tv, description_tv;
    private EditText price_et, quantity_et;
    private ImageView dish_iv;

    public DishBottomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_dish_bottom_dialog, container, false);
        save_tv = v.findViewById(R.id.bottom_save_button);
        name_tv = v.findViewById(R.id.bottom_dish_name);
        description_tv = v.findViewById(R.id.dish_bottom_description);
        price_et = v.findViewById(R.id.bottom_price);
        quantity_et = v.findViewById(R.id.bottom_quantity);
        dish_iv = v.findViewById(R.id.bottom_quantity);

        save_tv.setOnClickListener( new View.OnClickListener(){
               @Override
               public void onClick(View v) {

               }
           }
        );

        return v;
    }
}
