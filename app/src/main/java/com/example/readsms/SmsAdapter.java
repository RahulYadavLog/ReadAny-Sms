package com.example.readsms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsView> {
    List<SmsModel> smsModels;

    public SmsAdapter(List<SmsModel> smsModels, Context context) {
        this.smsModels = smsModels;
        this.context = context;
    }

    Context context;

    @NonNull
    @Override
    public SmsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater=LayoutInflater.from(context);
        view=inflater.inflate(R.layout.sms_list,parent,false);

        return new  SmsAdapter.SmsView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsView holder, int position) {
        holder.columanName.setText(smsModels.get(position).getColumanName());
        holder.id.setText(smsModels.get(position).getId());
        holder.threadId.setText(smsModels.get(position).getThreadId());
        holder.address.setText(smsModels.get(position).getAddress());
        holder.person.setText(smsModels.get(position).getPerson());
        holder.date.setText(smsModels.get(position).getDate());
        holder.protocol.setText(smsModels.get(position).getProtocol());
        holder.read.setText(smsModels.get(position).getRead());
        holder.status.setText(smsModels.get(position).getStatus());
        holder.type.setText(smsModels.get(position).getType());
        holder.read_path_parent.setText(smsModels.get(position).getRead_path_parent());
        holder.subject.setText(smsModels.get(position).getSubject());
        holder.body.setText(smsModels.get(position).getBody());
        holder.service_center.setText(smsModels.get(position).getService_center());
        holder.locked.setText(smsModels.get(position).getLocked());

    }

    @Override
    public int getItemCount() {
        return smsModels.size();
    }

    public void filterList(List<SmsModel> filterdNames) {
        smsModels=filterdNames;
        notifyDataSetChanged();
    }

    public class SmsView extends RecyclerView.ViewHolder {
        TextView columanName,id,threadId,address,person,date, protocol,read,status,type,read_path_parent,subject,body ,service_center,locked;
        public SmsView(@NonNull View itemView) {
            super(itemView);
            address=itemView.findViewById(R.id.number);
            body=itemView.findViewById(R.id.body);
            date=itemView.findViewById(R.id.time);
            id=itemView.findViewById(R.id.id);
            columanName=itemView.findViewById(R.id.column_name);
            threadId=itemView.findViewById(R.id.threadID);
            person=itemView.findViewById(R.id.person);
            protocol=itemView.findViewById(R.id.protocol);
            read=itemView.findViewById(R.id.read);
            status=itemView.findViewById(R.id.status);
            type=itemView.findViewById(R.id.type);
            read_path_parent=itemView.findViewById(R.id.read_path_status);
            subject=itemView.findViewById(R.id.subject);
            service_center=itemView.findViewById(R.id.service_center);
            locked=itemView.findViewById(R.id.locked);
        }
    }
}
