package com.gojek.krith.contacts.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gojek.krith.contacts.R;
import com.gojek.krith.contacts.models.Contact;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by krith on 02/02/17.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> {
    private List<Contact> contacts;
    OnItemClickListener mItemClickListener;

    public ContactListAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_row, parent, false);
        //TODO: Create a row layout.
        return new ContactListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactListViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        //TODO: Declare the values in holder.
    }

    @Override
    public int getItemCount() {
        if (contacts == null)
            return 0;
        return contacts.size();
    }

    public void updateContactList(List<Contact> contacts) {
        this.contacts = contacts;
        //TODO: Condition for fav and non list.
        notifyDataSetChanged();
    }

    public class ContactListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView taskName;
        public TextView reminderTime;

        public ContactListViewHolder(View view) {
            super(view);
            taskName = (TextView) view.findViewById(R.id.task_name);
            reminderTime = (TextView) view.findViewById(R.id.reminder_time);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition(), contacts.get(getAdapterPosition()).getId());
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, int id);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
