package android.example.com.attendanceapp.fragments;


import android.content.Intent;
import android.example.com.attendanceapp.AttendanceActivity;
import android.example.com.attendanceapp.HomeActivity;
import android.example.com.attendanceapp.adapters.SavedAttendanceAdapter;
import android.example.com.attendanceapp.pojo.DateClass;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.attendanceapp.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavedAttendanceFragment extends Fragment implements SavedAttendanceAdapter.ListItemClickListener{


    RecyclerView rvSavedAttendance;
    SavedAttendanceAdapter savedAttendanceAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String course,date, courseCode;
    HomeActivity myActivity;
    List<DateClass> dateList = new ArrayList<>();
    public SavedAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myActivity  = (HomeActivity) getActivity();
        courseCode = myActivity.courseCode;
        return inflater.inflate(R.layout.fragment_saved_atttendance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvSavedAttendance = view.findViewById(R.id.rv_save_attendance);
        savedAttendanceAdapter = new SavedAttendanceAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        rvSavedAttendance.hasFixedSize();
        rvSavedAttendance.setLayoutManager(layoutManager);
        rvSavedAttendance.setAdapter(savedAttendanceAdapter);
        getDate();
    }

    @Override
    public void onListItemClickListener(int clickIndex) {
    }
    void getDate(){
        db.collection("Date").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot doc: documentSnapshots){
                    course = doc.getString("course").toString();
                    date = doc.getString("date").toString();
                    if (course.equals(courseCode)){
                        dateList.add(new DateClass(course,date));
                    }

                }
               savedAttendanceAdapter.myDateList = dateList;
                savedAttendanceAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                myActivity.finish();
                return true;
        }
        return true;
    }
}
