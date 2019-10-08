package com.example.firestorequiz.Adapters;

import android.content.Context;
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
import com.example.firestorequiz.R;
import com.example.firestorequiz.ViewHolders.StageViewHolder;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import org.w3c.dom.Text;

import java.util.List;


public class StageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {



    Context mContext;
    List<Stage> StageDataList;
    public StageAdapter(List<Stage> studentDataList,Context context) {
        this.mContext=context;
        this.StageDataList = studentDataList;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

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






    }


    @Override
    public int getItemCount() {
        return StageDataList.size();
    }



}
