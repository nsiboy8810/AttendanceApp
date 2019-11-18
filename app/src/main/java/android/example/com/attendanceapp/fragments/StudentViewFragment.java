package android.example.com.attendanceapp.fragments;

import android.content.Context;
import android.example.com.attendanceapp.StudentActivity;
import android.example.com.attendanceapp.adapters.AttendanceViewAdapter;
import android.example.com.attendanceapp.adapters.TakeAttendanceAdapter;
import android.example.com.attendanceapp.pojo.ViewAttendance;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.attendanceapp.R;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StudentViewFragment extends Fragment {

    AttendanceViewAdapter attendanceViewAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar pb_load_view;
    RecyclerView recyclerView;
    StudentActivity studentActivity;
    String email;
    List<ViewAttendance> studentAttendanceList = new ArrayList<>();
    int attendance, totalAttendance, missed, percent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        studentActivity = (StudentActivity) getActivity();
        email = studentActivity.email;
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_student_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attendanceViewAdapter = new AttendanceViewAdapter();
        pb_load_view = view.findViewById(R.id.pb_vw_attendance);
        recyclerView = view.findViewById(R.id.rv_view_attendance);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(attendanceViewAdapter);
        getAttendanceRecord();
    }

    void getAttendanceRecord(){
        showProgressBar();
        db.collection("Students").document(email).collection("Courses").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot doc: documentSnapshots){
                    attendance = Math.toIntExact(doc.getLong("attendance"));
                    totalAttendance = Math.toIntExact(doc.getLong("total attendance"));
                    try{percent = attendance * 100 / totalAttendance;}catch (Exception e){
                        percent = 0;
                    }

                    missed = totalAttendance - attendance;
                    String courseId = doc.getId();
                    studentAttendanceList.add(new ViewAttendance(courseId,attendance,missed,percent));
                }
                attendanceViewAdapter.myAttendanceList = studentAttendanceList;
                attendanceViewAdapter.notifyDataSetChanged();
                hideProgressBar();
            }
        });
    }
    void showProgressBar(){

        pb_load_view.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }
    void  hideProgressBar(){
        pb_load_view.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                studentActivity.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
