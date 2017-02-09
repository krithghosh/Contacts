package com.gojek.krith.contacts.contact_details;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gojek.krith.contacts.R;
import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.all_contacts.AllContactsActivity;
import com.gojek.krith.contacts.application.App;
import com.gojek.krith.contacts.models.Contact;
import com.gojek.krith.contacts.utils.SnackbarUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.gojek.krith.contacts.utils.AppUtils.SERVER_ID;

public class ContactDetailsActivity extends AppCompatActivity implements ContactDetailsContract.View,
        View.OnClickListener {

    @BindView(R.id.iv_profile_image)
    CircleImageView profilePic;

    @BindView(R.id.tv_full_name)
    TextView fullName;

    @BindView(R.id.tv_phone_number)
    TextView phoneNumber;

    @BindView(R.id.tv_email)
    TextView email;

    @BindView(R.id.iv_fav)
    ImageView fav;

    @BindView(R.id.rv_main_layout)
    RelativeLayout mainLayout;

    @BindView(R.id.rv_inner_layout)
    RelativeLayout innerLayout;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private ContactDetailsContract.Presenter mPresenter;
    private int serverId = 0;

    @Inject
    ContactRepository mContactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        if (getIntent() != null)
            serverId = getIntent().getIntExtra(SERVER_ID, 0);
        ButterKnife.bind(this);
        setUpComponents();
        fav.setOnClickListener(this);
    }

    @Override
    public void setUpComponents() {
        App.getComponent().inject(this);
        new ContactDetailsPresenter(this, mContactRepository);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe(serverId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void setUpPresenter(ContactDetailsContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showLoader(boolean showLoader) {
        progressBar.setVisibility(showLoader ? View.VISIBLE : View.GONE);
    }

    @Override
    public void markFav(boolean markFav) {
        fav.setImageResource(markFav ? R.drawable.ic_fav : R.drawable.ic_not_fav);
    }

    @Override
    public void showNetworkError() {
        SnackbarUtils.showSnackbar(mainLayout, getString(R.string.not_able_to_reach_server));
    }

    @Override
    public void showContact(Contact contact) {
        fullName.setText(contact.getFirstName().substring(0, 1)
                .toUpperCase().concat(contact.getFirstName().substring(1))
                .concat(" ")
                .concat(contact.getLastName().substring(0, 1)
                        .toUpperCase().concat(contact.getLastName().substring(1))));
        phoneNumber.setText(contact.getPhoneNumber());
        email.setText(contact.getEmail());
        Glide.with(this)
                .load(contact.getProfilePic())
                .placeholder(ContextCompat.getDrawable(this, R.drawable.ic_placeholder))
                .into(profilePic);
        fav.setImageResource(contact.getFavorite() ? R.drawable.ic_fav : R.drawable.ic_not_fav);
        innerLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AllContactsActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fav:
                mPresenter.markFavourite(serverId);
                break;
        }
    }
}
