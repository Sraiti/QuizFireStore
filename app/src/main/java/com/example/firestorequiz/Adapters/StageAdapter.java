package com.example.firestorequiz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.Model.Stage;
import com.example.firestorequiz.Playing;
import com.example.firestorequiz.R;
import com.example.firestorequiz.ViewHolders.StageViewHolder;

import java.util.List;


public class StageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context mContext;
    List<Stage> StageDataList;
    ProgressBar progressBar;
    int CateID;

    public StageAdapter(List<Stage> studentDataList, Context context, int CategoryID, ProgressBar progressBar) {
        this.mContext = context;
        this.StageDataList = studentDataList;
        this.CateID = CategoryID;
        this.progressBar = progressBar;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.stage_container, viewGroup, false);
        return new StageViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {

        if (StageDataList.get(position).isOpen()) {
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public long getItemId(int position) {

        return StageDataList.get(position).getStageId();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        StageViewHolder stageViewHolder = (StageViewHolder) holder;


        stageViewHolder.StageName.setText(StageDataList.get(position).getStageText());

        int ImageResId = mContext.getResources().getIdentifier("lock", "drawable",
                mContext.getPackageName());


        if (!StageDataList.get(position).isOpen()) {
            stageViewHolder.StageImage.setImageResource(ImageResId);
        }

        int opened = 0;
        for (int i = 0; i < 9; i++) {
            if (StageDataList.get(position).isOpen()) {
                ++opened;
            }
        }

        progressBar.setProgress(opened);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StageDataList.get(position).isOpen()) {
                    Intent intent2 = new Intent(mContext, Playing.class);
                    intent2.putExtra("Level", StageDataList.get(position).getStageId());
                    mContext.startActivity(intent2);
                } else {
                    //Toast.makeText(mContext, "You don't have enough points !!", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    @Override
    public int getItemCount() {
        return StageDataList.size();
    }


}
