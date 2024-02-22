package com.example.a2048;

/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


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

class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>  {

    // Member variables.
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
        // Get current item.
        MenuItem currentItem= mMenuData.get(position);

        // Populate the textviews with data.
        Glide.with(mContext).load(currentItem.getImageResource()).into(mMenuImage);

        holder.bindTo(currentItem);

    }

    @Override
    public int getItemCount() {
        return mMenuData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTitleText;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title2);
            mMenuImage = itemView.findViewById(R.id.menuImage);

        }
        void bindTo(MenuItem currentSport){
            // Populate the textviews with data.
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
