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

import com.gojek.krith.contacts.R;
import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.application.App;
import com.gojek.krith.contacts.contact_details.ContactDetailsActivity;
import com.gojek.krith.contacts.models.Contact;
import com.gojek.krith.contacts.utils.SnackbarUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllContactsActivity extends AppCompatActivity implements AllContactsContract.View {

    @BindView(R.id.rv_all_contacts)
    RecyclerView rvAllContacts;

    @BindView(R.id.btn_add_contact)
    FloatingActionButton btnAddContact;

    @BindView(R.id.cl_main_layout)
    CoordinatorLayout mainLayout;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    AllContactsContract.Presenter mPresenter;

    @Inject
    ContactRepository mContactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contacts);
        ButterKnife.bind(this);
        setupComponents();
        setupRecylerView();
        setupTaskAdapter();
    }

    public void setupTaskAdapter() {
        taskAdapter.SetOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Long id) {
                startActivity(new Intent(getApplicationContext(), ContactDetailsActivity.class)
                        .putExtra("id", id));
            }
        });
    }

    public void initializeRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvAllContacts.setLayoutManager(mLayoutManager);
        rvAllContacts.setItemAnimator(new DefaultItemAnimator());
        rvAllContacts.setAdapter(taskAdapter);
    }

    private void setupRecylerView() {
        // Setup the recycler view
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
        progressBar.setVisibility(showLoader ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showContacts(List<Contact> contacts) {

    }

    @Override
    public void setupComponents() {
        ((App) getApplication()).getComponent().inject(this);
        new AllContactsPresenter(this, mContactRepository);
    }

    @Override
    public void showNetworkError() {
        SnackbarUtils.showSnackbar(mainLayout, "Not able to fetch the data");
    }

    @Override
    public void showNoContactsAvailable() {
        SnackbarUtils.showSnackbar(mainLayout, "Contacts not available");
    }
}
