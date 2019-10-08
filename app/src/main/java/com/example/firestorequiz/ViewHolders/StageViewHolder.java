package com.example.firestorequiz.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.R;

public class StageViewHolder extends RecyclerView.ViewHolder {


    private View mView;
    private int StageId;
    public TextView StageName;
    public ImageView StageImage ;


    public StageViewHolder(@NonNull View itemView) {
        super(itemView);

        StageName=itemView.findViewById(R.id.txt_StageName);
        StageImage=itemView.findViewById(R.id.img_Stage);
        mView =itemView;


    }

    public View getmView() {
        return mView;
    }

    public int getStageId() {
        return StageId;
    }
}
