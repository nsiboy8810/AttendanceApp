package android.example.com.attendanceapp.adapters;

import android.content.Context;
import android.example.com.attendanceapp.R;
import android.example.com.attendanceapp.pojo.StudentClass;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TakeAttendanceAdapter extends RecyclerView.Adapter<TakeAttendanceAdapter.TakeAttendanceAdapterViewHolder>{
    public List<StudentClass> studentsList;
    public List<StudentClass> checkedList = new ArrayList<>();

    ListItemClickListener listItemClickListener;
    public TakeAttendanceAdapter(ListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public TakeAttendanceAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int officeXml = R.layout.attendance_taking_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(officeXml, parent, false);
        return new TakeAttendanceAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TakeAttendanceAdapterViewHolder holder, int position) {

        holder.tvReg.setText(studentsList.get(position).getReg().toString());
        holder.tvName.setText(studentsList.get(position).getName().toString());

    }

    @Override
    public int getItemCount() {

        if (studentsList != null){
            return studentsList.size();
        }
        return 0;
    }
    public interface ListItemClickListener{
        void onListItemClickListener(int clickIndex);
    }

    class TakeAttendanceAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName, tvReg;
        CheckBox checkBox;

        public TakeAttendanceAdapterViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_tk_name);
            tvReg = itemView.findViewById(R.id.tv_tk_reg);
            checkBox = itemView.findViewById(R.id.cb_tk_attendance);
            checkBox.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterIndex = getAdapterPosition();

            if (checkBox.isChecked()){
                checkedList.add(studentsList.get(adapterIndex));
            }else if(!checkBox.isChecked()){
                checkedList.remove(studentsList.get(adapterIndex));
            }
            listItemClickListener.onListItemClickListener(adapterIndex);
        }
    }
}
