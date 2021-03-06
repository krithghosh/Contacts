package com.gojek.krith.contacts.all_contacts;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.gojek.krith.contacts.adapter.ContactListAdapter;
import com.gojek.krith.contacts.R;
import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.add_contact.AddContactActivity;
import com.gojek.krith.contacts.application.App;
import com.gojek.krith.contacts.application.SharedPreferenceManager;
import com.gojek.krith.contacts.contact_details.ContactDetailsActivity;
import com.gojek.krith.contacts.models.Contact;
import com.gojek.krith.contacts.utils.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.gojek.krith.contacts.utils.AppUtils.SERVER_ID;

public class AllContactsActivity extends AppCompatActivity implements AllContactsContract.View, View.OnClickListener {

    @BindView(R.id.rv_all_contacts)
    RecyclerView rvAllContacts;

    @BindView(R.id.btn_add_contact)
    FloatingActionButton btnAddContact;

    @BindView(R.id.cl_main_layout)
    CoordinatorLayout mainLayout;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private AllContactsContract.Presenter mPresenter;
    private ContactListAdapter mContactListAdapter;

    @Inject
    ContactRepository mContactRepository;

    @Inject
    SharedPreferenceManager mSharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        ButterKnife.bind(this);
        setupComponents();
        setupTaskAdapter();
        setupRecyclerView();
        btnAddContact.setOnClickListener(this);
    }

    private void setupTaskAdapter() {
        mContactListAdapter = new ContactListAdapter(new ArrayList<Contact>(), this);
        mContactListAdapter.SetOnItemClickListener(new ContactListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int id) {
                startActivity(new Intent(getApplicationContext(), ContactDetailsActivity.class)
                        .putExtra(SERVER_ID, id));
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAllContacts.setLayoutManager(mLayoutManager);
        rvAllContacts.setItemAnimator(new DefaultItemAnimator());
        rvAllContacts.setAdapter(mContactListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void setUpPresenter(AllContactsContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showLoader(boolean showLoader) {
        progressBar.setVisibility(showLoader ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showContacts(List<Contact> contacts) {
        mContactListAdapter.updateContactList(contacts);
    }

    @Override
    public void setupComponents() {
        App.getComponent().inject(this);
        new AllContactsPresenter(this, mContactRepository, mSharedPreferenceManager);
    }

    @Override
    public void showNetworkError() {
        SnackbarUtils.showSnackbar(mainLayout, getString(R.string.not_able_to_reach_server));
    }

    @Override
    public void showNoContactsAvailable() {
        SnackbarUtils.showSnackbar(mainLayout, getString(R.string.contacts_not_available));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_contact:
                startActivity(new Intent(this, AddContactActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}
