package com.example.task_71p;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.task_71p.database.Database;
import com.example.task_71p.model.Items;

public class AdvertFrag extends Fragment {

    private static Items itemsItem;

    public AdvertFrag() {
    }

    public static AdvertFrag newInstance(Items itemsItem) {
        AdvertFrag fragment = new AdvertFrag();
        AdvertFrag.itemsItem = itemsItem;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advert_post, container, false);

        TextView LabelAdvertNameAndType = view.findViewById(R.id.textTypeItem);
        TextView LabelAdvertPhone = view.findViewById(R.id.textPhoneNum);
        TextView LabelAdvertDescription = view.findViewById(R.id.textDes);
        TextView LabelAdvertDate = view.findViewById(R.id.textDate);
        TextView LabelAdvertLocation = view.findViewById(R.id.textLocation);
        Button ButtonRemove = view.findViewById(R.id.buttonRemove);

        LabelAdvertNameAndType.setText(itemsItem.getLostOrFound()+" "+ itemsItem.getName());
        LabelAdvertPhone.setText(String.valueOf(itemsItem.getPhone()));
        LabelAdvertDescription.setText(itemsItem.getDescription());
        LabelAdvertDate.setText(itemsItem.getDate());
        LabelAdvertLocation.setText(itemsItem.getLocation());

        Database db = new Database(getContext());

        ButtonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.DeleteAdvert(itemsItem);
                Fragment fragment = com.example.task_71p.AdvertShow.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("AdvertFrag"));
                transaction.add(R.id.FragmentContainer, fragment,"AdvertShow");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}