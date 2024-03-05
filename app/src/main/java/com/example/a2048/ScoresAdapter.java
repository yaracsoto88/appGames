package com.example.a2048;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {
    private ArrayList<Score> mScoresData;
    private Context mContext;


    ScoresAdapter(Context context, ArrayList<Score> scoresData) {
        this.mScoresData = scoresData;
        this.mContext = context;
    }

    @Override
    public ScoresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoresAdapter.ViewHolder holder, int position) {
        Score currentScore = mScoresData.get(position);
        holder.bindTo(currentScore);
    }

    @Override
    public int getItemCount() {
        return mScoresData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNamePlayerText;
        private TextView mScoreText;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mNamePlayerText = itemView.findViewById(R.id.title);
            mScoreText = itemView.findViewById(R.id.subTitle);
        }

        void bindTo(Score currentScore) {
            mNamePlayerText.setText(currentScore.getNamePlayer());
            mScoreText.setText(currentScore.getScore());
        }

        @Override
        public void onClick(View view) {

        }
    }

}
