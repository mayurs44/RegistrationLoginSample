package com.vatsaltechnosoft.mani.amritha.registrationloginsample;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.util.List;

/**
 * Created by Amritha on 6/30/18.
 */
public class ListViewRecyclerAdapter extends RecyclerView.Adapter<ListViewRecyclerAdapter.ListRecyclerViewHolder> {

    List<User> listUsers;

    ListViewRecyclerAdapter(List<User> listUsers) {

        this.listUsers = listUsers;

    }

    @Override
    public ListViewRecyclerAdapter.ListRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_recycler, parent, false);

        return new ListViewRecyclerAdapter.ListRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListRecyclerViewHolder holder, final int position) {

        String name = listUsers.get(position).getFirstName() + " " + listUsers.get(position).getLastName();

        holder.textViewFullName.setText(name);

        holder.textViewRadioButton.setText(listUsers.get(position).getGender());

        holder.textViewCheckBox.setText(listUsers.get(position).getHobbies());

    }


    @Override
    public int getItemCount() {

        Log.v(com.vatsaltechnosoft.mani.amritha.registrationloginsample.ListViewRecyclerAdapter.class.getSimpleName(), "" + listUsers.size());
        return listUsers.size();
    }

    class ListRecyclerViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewFullName;
        AppCompatTextView textViewRadioButton;
        AppCompatTextView textViewCheckBox;

        ListRecyclerViewHolder(View itemView) {
            super(itemView);

            textViewFullName = itemView.findViewById(R.id.textViewName);
            textViewRadioButton = itemView.findViewById(R.id.textViewGender);
            textViewCheckBox = itemView.findViewById(R.id.textViewHobbies);
        }
    }

}
