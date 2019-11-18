package android.example.com.attendanceapp.fragments;


import android.example.com.attendanceapp.HomeActivity;
import android.example.com.attendanceapp.R;
import android.example.com.attendanceapp.adapters.OverallAttendanceAdapter;
import android.example.com.attendanceapp.pojo.OverallAttendance;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverallAttendanceFragment extends Fragment {


    RecyclerView rvOverallAttendance;
    ProgressBar pbLoadOverall;
    OverallAttendanceAdapter overallAttendanceAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<OverallAttendance> overallList = new ArrayList<>();
    int overall = 0;
    int percent = 0;
    HomeActivity myActivity;
    String email, courseCode;
    public OverallAttendanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myActivity = (HomeActivity) getActivity();
        email = myActivity.email;
        courseCode = myActivity.courseCode;
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_overall_attendance, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvOverallAttendance = view.findViewById(R.id.rv_overall_attendance);
        pbLoadOverall = view.findViewById(R.id.pb_ov_attendance);
        overallAttendanceAdapter = new OverallAttendanceAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvOverallAttendance.hasFixedSize();
        rvOverallAttendance.setLayoutManager(layoutManager);
        rvOverallAttendance.setAdapter(overallAttendanceAdapter);
        getOverallAttendanceScore();
    }

    void getOverallAttendanceScore(){

        showProgressBar();
        db.collection("Staff").document(email).collection("Courses").document(courseCode).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                overall = Math.toIntExact(documentSnapshot.getLong("totalAttendnce"));
                getOverallAttendance();

            }
        });
    }

    void getOverallAttendance() {
        db.collection("Staff").document(email).collection("Courses").document(courseCode).collection("Students").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot d : documentSnapshots) {
                    int total = Math.toIntExact(d.getLong("attendance"));
                    try {
                        percent = total*100/overall;
                    }catch (Exception e){
                        percent =0;
                    }
                    overallList.add(new OverallAttendance(d.getString("reg"), total ,percent));
                }
                overallAttendanceAdapter.overallList = overallList;
                overallAttendanceAdapter.notifyDataSetChanged();
                hideProgressbar();
            }
        });
    }
    void showProgressBar(){
        pbLoadOverall.setVisibility(View.VISIBLE);
        rvOverallAttendance.setVisibility(View.INVISIBLE);
    }
    void hideProgressbar(){
        pbLoadOverall.setVisibility(View.INVISIBLE);
        rvOverallAttendance.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.it_upload).setVisible(false);
    }
}
