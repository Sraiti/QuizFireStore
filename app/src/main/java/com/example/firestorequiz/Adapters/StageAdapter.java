package com.example.firestorequiz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.Model.Stage;
import com.example.firestorequiz.Playing;
import com.example.firestorequiz.Quiz;
import com.example.firestorequiz.R;
import com.example.firestorequiz.ViewHolders.StageViewHolder;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import org.w3c.dom.Text;

import java.util.List;


public class StageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    Context mContext;
    List<Stage> StageDataList;
    int CateID;
    public StageAdapter(List<Stage> studentDataList,Context context,int CategoryID) {
        this.mContext=context;
        this.StageDataList = studentDataList;
        this.CateID=CategoryID;
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

        int id =getItemViewType(position);


        stageViewHolder.StageName.setText(StageDataList.get(position).getStageText());

        int ImageResId = mContext.getResources().getIdentifier("lock", "drawable",
              mContext.getPackageName());


        switch (id){
            case 0 :
                stageViewHolder.StageImage.setImageResource(ImageResId);
                break;
            case 1:
                default:
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(mContext, Playing.class);
                intent2.putExtra("Level",StageDataList.get(position).getStageId());
                mContext.startActivity(intent2);
            }
        });



    }


    @Override
    public int getItemCount() {
        return StageDataList.size();
    }



}
