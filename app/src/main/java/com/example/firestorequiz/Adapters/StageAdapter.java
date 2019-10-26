package com.example.firestorequiz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.Activities.Playing;
import com.example.firestorequiz.Model.Stage;
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

        if (StageDataList.get(position).isOpen() == 1) {
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


        stageViewHolder.StageName.setText(" Level " + StageDataList.get(position).getStageText());
        int ImageResIdlock = mContext.getResources().getIdentifier("lock", "drawable",
                mContext.getPackageName());
        int ImageResIdunlock = mContext.getResources().getIdentifier("ic_lock_open_black_24dp", "drawable",
                mContext.getPackageName());

        if (StageDataList.get(position).isOpen() == 0) {
            stageViewHolder.StageImage.setImageResource(ImageResIdlock);
        } else {
            stageViewHolder.StageImage.setImageResource(ImageResIdunlock);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StageDataList.get(position).isOpen() == 1) {
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
