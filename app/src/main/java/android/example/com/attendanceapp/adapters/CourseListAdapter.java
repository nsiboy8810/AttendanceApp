package android.example.com.attendanceapp.adapters;

import android.content.Context;
import android.example.com.attendanceapp.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CourseListAdapter  extends RecyclerView.Adapter<CourseListAdapter.CourseListAdapterViewHolder>{
    public List<String> coursesList;
    ListItemClickListener listItemClickListener;

    public CourseListAdapter(ListItemClickListener listItemClickListener){
        this.listItemClickListener = listItemClickListener;
    }

    @NonNull
    @Override
    public CourseListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int officeXml = R.layout.course_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(officeXml, parent, false);
        return new CourseListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseListAdapterViewHolder holder, int position) {
        holder.tvCourse.setText(coursesList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        if (coursesList != null){
            return coursesList.size();
        }
        return 0;
    }

    public interface ListItemClickListener{
        void onListItemClickListener(int clickIndex);
    }
    class CourseListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvCourse;
        public CourseListAdapterViewHolder(View itemView) {
            super(itemView);
            tvCourse = itemView.findViewById(R.id.tv_course);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterIndex = getAdapterPosition();
            listItemClickListener.onListItemClickListener(adapterIndex);
        }
    }
}
