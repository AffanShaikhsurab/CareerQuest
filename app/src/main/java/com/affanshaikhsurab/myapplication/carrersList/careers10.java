package com.affanshaikhsurab.myapplication.carrersList;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.affanshaikhsurab.myapplication.adapter.CareerAdapter;
import com.affanshaikhsurab.myapplication.adapter.Careers;
import com.affanshaikhsurab.myapplication.activity.Information;
import com.affanshaikhsurab.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link careers10#newInstance} factory method to
 * create an instance of this fragment.
 */
public class careers10 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Careers> careersArrayList = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public careers10() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment careers10.
     */
    // TODO: Rename and change types and number of parameters
    public static careers10 newInstance(String param1, String param2) {
        careers10 fragment = new careers10();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_careers10, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView list = view.findViewById(R.id.carrers_10_list);
        FetchingData fetchingData = new FetchingData(view.getContext(), view);
        fetchingData.execute();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), Information.class);
                intent.putExtra("Label" , careersArrayList.get(position).getName());
                 startActivity(intent);
            }
        });


    }


    class FetchingData extends AsyncTask<Void, Void, ArrayList<Careers>> {

        View view;
        Context context;
        ListView listView;

        FetchingData(Context c, View v) {
            context = c;
            view = v;
        }

        @Override
        protected void onPostExecute(ArrayList<Careers> list) {

            super.onPostExecute(list);
        }

        @Override
        protected ArrayList<Careers> doInBackground(Void... voids) {
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dr = fd.getReference("Careers");

            dr.addValueEventListener(new ValueEventListener() {
                private static final String TAG = "a";

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        careersArrayList.add(new Careers(snapshot.child("Name").getValue(String.class), snapshot.child("Image").getValue(String.class)));
                    }

                    CareerAdapter careerAdapter = new CareerAdapter(context, R.layout.list, careersArrayList);
                    listView = view.findViewById(R.id.carrers_10_list);
                    listView.setAdapter(careerAdapter);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
            return null;
        }
    }
}