package android.example.com.attendanceapp.fragments;

import android.app.ProgressDialog;
import android.example.com.attendanceapp.HomeActivity;
import android.example.com.attendanceapp.adapters.TakeAttendanceAdapter;
import android.example.com.attendanceapp.pojo.StudentClass;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.attendanceapp.R;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentFragment extends Fragment implements TakeAttendanceAdapter.ListItemClickListener {

    RecyclerView recyclerView;
    TakeAttendanceAdapter takeAttendanceAdapter;
    List<StudentClass> studentList = new ArrayList();
    ProgressDialog pbDialog;
    public List<StudentClass> myCheckedList = new ArrayList<>();
    int totalAttendance;
    HomeActivity myActivity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar pb_load_students;
    String email, courseCode;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myActivity = (HomeActivity) getActivity();
        email = myActivity.email;
        courseCode = myActivity.courseCode;
        pbDialog = new ProgressDialog(getContext());
        pbDialog.setMessage("Uploading...");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_student, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        takeAttendanceAdapter = new TakeAttendanceAdapter(this);
        pb_load_students = view.findViewById(R.id.pb_tk_attendance);
        recyclerView = view.findViewById(R.id.rv_take_attendance);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(takeAttendanceAdapter);
        getStudents();
    }

    void getStudents() {
        showProgressBar();
        db.collection("Staff").document(email).collection("Courses").document(courseCode).collection("Students").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot d : documentSnapshots) {
                    studentList.add(new StudentClass(d.get("name").toString(), d.get("reg").toString(), d.getId()));
                }
                takeAttendanceAdapter.studentsList = studentList;
                takeAttendanceAdapter.notifyDataSetChanged();
                hideProgressbar();

            }
        });


    }

    public void updateTotalAttendance() {
        final DocumentReference docRef = db.collection("Staff").document(email).collection("Courses").document(courseCode);
        db.runTransaction(new Transaction.Function<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Nullable
            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot documentSnapshot = transaction.get(docRef);
                totalAttendance = Math.toIntExact(documentSnapshot.getLong("totalAttendnce"));
                transaction.update(docRef, "totalAttendnce", totalAttendance + 1);
                return null;
            }
        });
        Toast.makeText(getContext(), "Success",Toast.LENGTH_LONG).show();
    }

    public void updateIndividualAttendance() {

        for (StudentClass ch : takeAttendanceAdapter.checkedList) {
            final DocumentReference docRef = db.collection("Staff").document(email).collection("Courses").document(courseCode).collection("Students").document(ch.getId());
            final DocumentReference docRef2 = db.collection("Students").document(ch.getId()).collection("Courses").document(courseCode);
            db.runTransaction(new Transaction.Function<Void>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Nullable
                @Override
                public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                    DocumentSnapshot documentSnapshot = transaction.get(docRef);
                    int attendance = Math.toIntExact(documentSnapshot.getLong("attendance"));
                    transaction.update(docRef, "attendance", attendance + 1);
                    return null;
                }
            });
            db.runTransaction(new Transaction.Function<Void>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Nullable
                @Override
                public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                    DocumentSnapshot documentSnapshot1 = transaction.get(docRef2);
                    int attended = Math.toIntExact(documentSnapshot1.getLong("attendance"));
                    //int totalAttendance = Math.toIntExact(documentSnapshot1.getLong("total attendance"));
                    transaction.update(docRef2, "attendance", attended + 1);
                    transaction.update(docRef2, "total attendance", totalAttendance + 1);
                    return null;
                }
            });

        }
    }
    void showProgressBar() {
        pb_load_students.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    void hideProgressbar() {
        pb_load_students.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClickListener(int clickIndex) {
    }
    void uploadDate(){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String simpleDate =dateFormat.format(date);
        Map<String, String> addDate = new HashMap<>();
        addDate.put("course", courseCode);
        addDate.put("date", simpleDate);
        db.collection("Date").add(addDate);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.it_upload:
                updateTotalAttendance();
                updateIndividualAttendance();
                uploadDate();
                return true;
            case android.R.id.home:
                myActivity.finish();
                return true;
        }
        return true;
    }

}
