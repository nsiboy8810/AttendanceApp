package android.example.com.attendanceapp.adapters;

import android.content.Context;
import android.example.com.attendanceapp.R;
import android.example.com.attendanceapp.pojo.DateClass;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SavedAttendanceAdapter extends RecyclerView.Adapter<SavedAttendanceAdapter.SavedAttendanceViewHolder> {

    ListItemClickListener listItemClickListener;
    public List<DateClass> myDateList;
    public SavedAttendanceAdapter(ListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }
    @NonNull
    @Override
    public SavedAttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int officeXml = R.layout.taken_attendance_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(officeXml, parent, false);
        return new SavedAttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAttendanceViewHolder holder, int position) {
        holder.mCourse.setText(myDateList.get(position).getCourse()+"");
        holder.mDate.setText(myDateList.get(position).getDate()+"");
    }

    @Override
    public int getItemCount() {

        if (myDateList != null){
            return myDateList.size();
        }
        return 0;
    }

    public interface ListItemClickListener{
        void onListItemClickListener(int clickIndex);
    }

    class SavedAttendanceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mDate, mCourse;

        public SavedAttendanceViewHolder(View itemView) {
            super(itemView);
            mCourse = itemView.findViewById(R.id.tv_dt_course);
            mDate = itemView.findViewById(R.id.tv_dt_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterIndex = getAdapterPosition();
            listItemClickListener.onListItemClickListener(adapterIndex);
        }
    }
}
