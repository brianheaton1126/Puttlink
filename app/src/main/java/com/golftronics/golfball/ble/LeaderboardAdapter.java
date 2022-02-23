package com.golftronics.golfball.ble;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.games.leaderboard.LeaderboardScore;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeaderboardViewHolder> {

    private ArrayList<LeaderboardItem> mLeaderboardList;

    public static class LeaderboardViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;


        public LeaderboardViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView1 = itemView.findViewById(R.id.player_rank);
            mTextView2 = itemView.findViewById(R.id.player_name);
            mTextView3 = itemView.findViewById(R.id.player_score);

        }
    }

    public LeaderboardAdapter(ArrayList<LeaderboardItem> leaderboardList) {
        mLeaderboardList = leaderboardList;

    }


    @NonNull
    @Override
    public LeaderboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_leaderboard_item, parent, false);
        LeaderboardViewHolder lvh = new LeaderboardViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardViewHolder holder, int position) {

        LeaderboardItem currentItem = mLeaderboardList.get(position);

        holder.mTextView1.setText(currentItem.getMplayerRank());
        holder.mTextView2.setText(currentItem.getMplayerName());
        holder.mTextView3.setText(currentItem.getMplayerScore());
    }

    @Override
    public int getItemCount() {
        return mLeaderboardList.size();
    }
}
