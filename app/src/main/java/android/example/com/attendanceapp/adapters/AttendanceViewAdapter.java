package android.example.com.attendanceapp.adapters;

import android.content.Context;
import android.example.com.attendanceapp.R;
import android.example.com.attendanceapp.pojo.ViewAttendance;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AttendanceViewAdapter extends RecyclerView.Adapter<AttendanceViewAdapter.AttendanceViewAdapterViewHolder> {
    public List<ViewAttendance> myAttendanceList = new ArrayList<>();
    @NonNull
    @Override
    public AttendanceViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int officeXml = R.layout.attendance_view_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(officeXml, parent, false);
        return new AttendanceViewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewAdapterViewHolder holder, int position) {
        holder.tvCourseReg.setText(myAttendanceList.get(position).getCourseReg().toString());
        holder.tvAttendance.setText(myAttendanceList.get(position).getStudentAttendance()+"");
        holder.tvMissed.setText(myAttendanceList.get(position).getMissed()+" ");
        holder.tvPercent.setText(myAttendanceList.get(position).getPercent() +"%");


    }

    @Override
    public int getItemCount() {
        if (myAttendanceList != null){
            return myAttendanceList.size();
        }
        return 0;
    }

    class AttendanceViewAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView tvAttendance, tvMissed, tvPercent, tvCourseReg;

        public AttendanceViewAdapterViewHolder(View itemView) {
            super(itemView);
            tvAttendance = itemView.findViewById(R.id.tv_vw_attended);
            tvMissed = itemView.findViewById(R.id.tv_vw_missed);
            tvPercent = itemView.findViewById(R.id.tv_vw_percent);
            tvCourseReg = itemView.findViewById(R.id.tv_vw_course_reg);
        }
    }
}
