package com.android.demos.activities.docbrowser;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.demos.R;
import com.android.demos.activities.other.OnRecyclerItemClicked;

import java.util.ArrayList;
import java.util.List;

public class AdapterFileList extends RecyclerView.Adapter<AdapterFileList.DataHolder>
{
    Activity context;
    ArrayList<FileDetailsBean> listFileDetails;
    OnRecyclerItemClicked listener;
    int recvId;

    public AdapterFileList(Activity context, ArrayList<FileDetailsBean> listFileDetails,
                           OnRecyclerItemClicked listener, int recvId)
    {
        this.context = context;
        this.listFileDetails = listFileDetails;
        this.listener = listener;
        this.recvId = recvId;
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount()
    {
        return listFileDetails.size();
    }

    @NonNull
    @Override
    public AdapterFileList.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_file_details, parent, false);
        return new AdapterFileList.DataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, final int position)
    {
        holder.txtFileName.setText(listFileDetails.get(position).fileName);

        boolean isDir = listFileDetails.get(position).isDirectory();
        if(isDir)
        {
            holder.imgIcon.setImageResource(R.drawable.ic_folder);

            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    listener.onRecyclerItemClicked(recvId, listFileDetails.get(position), position);
                }
            });
        }
        else
        {
            holder.imgIcon.setImageResource(R.drawable.ic_file);
        }
    }

    public class DataHolder extends RecyclerView.ViewHolder
    {
        ImageView imgIcon;
        TextView txtFileName;

        public DataHolder(@NonNull View itemView)
        {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtFileName = itemView.findViewById(R.id.txtFileName);
        }
    }
}