package android.example.com.attendanceapp.fragments;

import android.content.Context;
import android.example.com.attendanceapp.StudentActivity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.example.com.attendanceapp.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class StudentAddCourseFragment extends Fragment {
   Button btnAddCourse;
   EditText etLecEmail, etCourseCode;
   String lecEmail, courseCode, email, name, reg;
   StudentActivity studentActivity;

   FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        studentActivity = (StudentActivity) getActivity();
        email = studentActivity.email;
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_student_add_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddCourse = view.findViewById(R.id.btn_add_course);
        etCourseCode = view.findViewById(R.id.et_course_code);
        etLecEmail = view.findViewById(R.id.et_lec_email);
        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    courseCode =etCourseCode.getText().toString().toLowerCase();
                    lecEmail = etLecEmail.getText().toString();
                    addCourseToStudent();
                    addStudenttoLecturer();
                }catch (Exception e){
                    Toast.makeText(getContext(), "Fields must not be Empty", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    void addCourseToStudent(){
        Map<String, Integer> course = new HashMap<>();
        course.put("attendance", 0);
        course.put("total attendance", 0);
        db.collection("Students").document(email).collection("Courses").document(courseCode).set(course).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
    void addStudenttoLecturer(){
        db.collection("Students").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name = documentSnapshot.getString("name").toString();
                reg = documentSnapshot.getString("Reg").toString();
                Map<String, Object> course = new HashMap<>();
                course.put("attendance", 0);
                course.put("name", name);
                course.put("reg", reg);
                db.collection("Staff").document(lecEmail).collection("Courses").document(courseCode).collection("Students").document(email).set(course).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


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

