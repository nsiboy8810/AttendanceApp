package android.example.com.attendanceapp.adapters;

import android.content.Context;
import android.example.com.attendanceapp.R;
import android.example.com.attendanceapp.pojo.OverallAttendance;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class OverallAttendanceAdapter extends RecyclerView.Adapter<OverallAttendanceAdapter.OverallAttendanceAdapterViewHolder>{
    public List<OverallAttendance> overallList;
    @NonNull
    @Override
    public OverallAttendanceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int officeXml = R.layout.overall_attendance_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(officeXml, parent, false);
        return new OverallAttendanceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OverallAttendanceAdapterViewHolder holder, int position) {

        holder.tvReg.setText(overallList.get(position).getReg().toString());
        holder.tvTotal.setText(overallList.get(position).getTotal() + "");
        holder.tvPercent.setText(overallList.get(position).getAtttendance() + "%");
    }

    @Override
    public int getItemCount() {
        if (overallList != null){
            return overallList.size();
        }
        return 0;
    }

    class OverallAttendanceAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvReg, tvTotal, tvPercent;
        public OverallAttendanceAdapterViewHolder(View itemView) {
            super(itemView);
            tvReg = itemView.findViewById(R.id.tv_ov_reg);
            tvTotal = itemView.findViewById(R.id.tv_ov_total);
            tvPercent = itemView.findViewById(R.id.tv_ov_percent);
        }
    }
}
