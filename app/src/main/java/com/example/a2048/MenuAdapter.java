package com.example.a2048;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.bumptech.glide.Glide;

class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private ArrayList<MenuItem> mMenuData;
    private Context mContext;
    private ImageView mMenuImage;


    MenuAdapter(Context context, ArrayList<MenuItem> mMenuData) {
        this.mMenuData = mMenuData;
        this.mContext = context;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.activity_menu_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder,
                                 int position) {
        MenuItem currentItem = mMenuData.get(position);
        Glide.with(mContext).load(currentItem.getImageResource()).into(mMenuImage);
        holder.bindTo(currentItem);

    }

    @Override
    public int getItemCount() {
        return mMenuData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleText;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleText = itemView.findViewById(R.id.title2);
            mMenuImage = itemView.findViewById(R.id.menuImage);

        }

        void bindTo(MenuItem currentSport) {
            mTitleText.setText(currentSport.getTitle());

        }

        @Override
        public void onClick(View v) {
            MenuItem currentItem = mMenuData.get(getAdapterPosition());
            Intent intent = new Intent(mContext, currentItem.getActivity());
            mContext.startActivity(intent);

        }

    }
}
