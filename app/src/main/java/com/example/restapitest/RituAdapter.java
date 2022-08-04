package com.example.restapitest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class RituAdapter extends RecyclerView.Adapter<RituAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    List<TodoListCopy> todoModelList;
    public RituAdapter(MainActivity context, List<TodoListCopy> todoModelList,RecyclerViewInterface recyclerViewInterface){
        this.context=context;
        this.todoModelList=todoModelList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public RituAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ritulayout,parent,false);
        return new RituAdapter.MyViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RituAdapter.MyViewHolder holder, int position) {
        TodoListCopy todoListCopy=todoModelList.get(position);
        holder.tv2.setText(todoModelList.get(position).getTitle());
        holder.relativeLayout.setVisibility(todoListCopy.isExpanded()?View.VISIBLE:View.GONE);
    }

    @Override
    public int getItemCount() {
        return todoModelList.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv2;
        RelativeLayout relativeLayout;
        ImageView button_del;
        ImageView button_edit;
        public MyViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            button_del=itemView.findViewById(R.id.delete);
            button_edit=itemView.findViewById(R.id.edit);
            tv2=itemView.findViewById(R.id.task);
            relativeLayout=itemView.findViewById(R.id.expandable_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if(recyclerViewInterface!=null){
//                        int pos=getAdapterPosition();
//                        if(pos!=RecyclerView.NO_POSITION){
//                            recyclerViewInterface.onItemClick(pos);
//                        }
//                    }
                    TodoListCopy todoListCopy=todoModelList.get(getAdapterPosition());
                    todoListCopy.setExpanded(!todoListCopy.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            button_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewInterface.onDelete(getAdapterPosition());
                }
            });
            button_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recyclerViewInterface.onEdit(getAdapterPosition());
                }
            });

        }
    }
}




//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Locale;
//
//public class RituAdapter extends ArrayAdapter<TodoModel> {
//    List<TodoModel> data;
//    List<TodoModel> backup;
//
//    public RituAdapter(Context context, List<TodoModel> userArrayList){
//        super(context,R.layout.ritulayout,userArrayList);
//        this.data=userArrayList;
//        backup=new ArrayList<>(userArrayList);
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        TodoModel todoModel=getItem(position);
//
//        if(convertView == null){  convertView= LayoutInflater.from(getContext()).inflate(R.layout.ritulayout,parent,false);}
//
//        TextView tv2=convertView.findViewById(R.id.task);
//        tv2.setText(todoModel.getTitle());
//        return (convertView);
//    }
//
//
//
//}
