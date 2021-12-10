package com.example.taskmaster;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;

    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public Task task;
        public View itemView;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = tasks.get(position);

        TextView title = holder.itemView.findViewById(R.id.titletext);
        TextView body = holder.itemView.findViewById(R.id.body);
        TextView state = holder.itemView.findViewById(R.id.state);

//        ImageView details= holder.itemView.findViewById(R.id.arrow);
        title.setText(holder.task.getTitle());
        body.setText(holder.task.getBody());
        state.setText(holder.task.getState());


        String titleToHandle = title.getText().toString();
        String descToHandle = body.getText().toString();
        String statetodaaaamn = state.getText().toString();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Detail.class);
                i.putExtra("taskTitle", titleToHandle);
                i.putExtra("desc", descToHandle);
                i.putExtra("state", statetodaaaamn);
                i.putExtra("key", holder.task.getId());

                v.getContext().startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

}
