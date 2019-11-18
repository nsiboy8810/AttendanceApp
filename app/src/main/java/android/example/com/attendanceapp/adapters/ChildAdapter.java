package android.example.com.attendanceapp.adapters;

import android.content.Context;
import android.example.com.attendanceapp.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildAdapterViewHolder>{
    ListItemClickListener listItemClickListener;
//    public ChildAdapter(ListItemClickListener listItemClickListener){
//        this.listItemClickListener = listItemClickListener;
//    }
    @NonNull
    @Override
    public ChildAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int officeXml = R.layout.course_attendance_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(officeXml, parent, false);
        return new ChildAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public interface ListItemClickListener{
        void onListItemClickListener(int clickIndex);
    }
    class ChildAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ChildAdapterViewHolder(View itemView) {
            super(itemView);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterIndex = getAdapterPosition();
            listItemClickListener.onListItemClickListener(adapterIndex);
        }
    }
}
