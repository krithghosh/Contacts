package com.gojek.krith.contacts.adapter;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gojek.krith.contacts.R;
import com.gojek.krith.contacts.all_contacts.AllContactsActivity;
import com.gojek.krith.contacts.models.Contact;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by krith on 02/02/17.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> {
    private List<Contact> contacts;
    private OnItemClickListener mItemClickListener;
    private final AllContactsActivity allContactsActivity;

    public ContactListAdapter(List<Contact> contacts, AllContactsActivity allContactsActivity) {
        updateContactList(contacts);
        this.allContactsActivity = allContactsActivity;
    }

    @Override
    public ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);
        return new ContactListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactListViewHolder holder, int position) {
        Contact contact = this.contacts.get(position);
        String fullName = contact.getFirstName().substring(0, 1)
                .toUpperCase().concat(contact.getFirstName().substring(1))
                .concat(" ")
                .concat(contact.getLastName().substring(0, 1)
                        .toUpperCase().concat(contact.getLastName().substring(1)));
        holder.tvName.setText(fullName);
        Glide.with(allContactsActivity)
                .load(contact.getProfilePic())
                .placeholder(ContextCompat.getDrawable(allContactsActivity, R.drawable.ic_placeholder))
                .into(holder.ivProfileImage);
        holder.ivFav.setVisibility(position == 0 && contact.getFavorite() ? View.VISIBLE : View.GONE);
        if (contact.getFavorite())
            holder.tvLetter.setVisibility(View.GONE);
        else {
            if (contact.isShowLetter()) {
                holder.tvLetter.setVisibility(View.VISIBLE);
                holder.tvLetter.setText(Character.toString(contact.getFirstName().charAt(0)).toUpperCase());
            } else {
                holder.tvLetter.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (contacts == null)
            return 0;
        return contacts.size();
    }

    public void updateContactList(List<Contact> _contacts) {
        if (_contacts.size() < 1)
            return;
        contacts = _contacts;
        List<Contact> favList = new ArrayList<Contact>();
        List<Contact> nonFavList = new ArrayList<Contact>();
        int prev = -1;
        for (Contact contact : _contacts) {
            if (contact.getFavorite()) {
                favList.add(contact);
                contact.setShowLetter(false);
            } else {
                nonFavList.add(contact);
                if ((int) contact.getFirstName().charAt(0) != prev) {
                    prev = (int) contact.getFirstName().charAt(0);
                    contact.setShowLetter(true);
                }
            }
        }
        favList.addAll(nonFavList);
        contacts = favList;
        notifyDataSetChanged();
    }

    public class ContactListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView tvLetter;
        public final TextView tvName;
        public final ImageView ivFav;
        public final CircleImageView ivProfileImage;

        public ContactListViewHolder(View view) {
            super(view);
            tvLetter = (TextView) view.findViewById(R.id.tv_letter);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            ivFav = (ImageView) view.findViewById(R.id.iv_fav);
            ivProfileImage = (CircleImageView) view.findViewById(R.id.iv_profile_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClick(view, getAdapterPosition(), contacts.get(getAdapterPosition()).getId());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, int id);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
