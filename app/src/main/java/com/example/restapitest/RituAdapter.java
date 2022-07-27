package com.example.restapitest;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Callback;
//
//public class RituAdapter extends RecyclerView.Adapter<RituAdapter.MyViewHolder> {
//    Context context;
//    List<TodoModel> todoModelList;
//    public RituAdapter(MainActivity context, List<TodoModel> todoModelList){
//        this.context=context;
//        this.todoModelList=todoModelList;
//    }
//
//    @NonNull
//    @Override
//    public RituAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ritulayout,parent,false);
//        return new RituAdapter.MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RituAdapter.MyViewHolder holder, int position) {
//        holder.tv1.setText(todoModelList.get(position).getId());
//        holder.tv2.setText(todoModelList.get(position).getTitle());
//    }
//
//    @Override
//    public int getItemCount() {
//        return todoModelList.size();
//    }
//
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//        TextView tv1,tv2;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tv1=itemView.findViewById(R.id.index);
//            tv2=itemView.findViewById(R.id.task);
//
//        }
//    }
//}
//



import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class RituAdapter extends ArrayAdapter<TodoModel> {
    List<TodoModel> data;
    List<TodoModel> backup;

    public RituAdapter(Context context, List<TodoModel> userArrayList){
        super(context,R.layout.ritulayout,userArrayList);
        this.data=userArrayList;
        backup=new ArrayList<>(userArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TodoModel todoModel=getItem(position);

        if(convertView == null){  convertView= LayoutInflater.from(getContext()).inflate(R.layout.ritulayout,parent,false);}

        TextView tv2=convertView.findViewById(R.id.task);
        tv2.setText(todoModel.getTitle());
        return (convertView);
    }



}
