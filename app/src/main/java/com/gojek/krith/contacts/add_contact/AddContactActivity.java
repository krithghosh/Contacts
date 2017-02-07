package com.gojek.krith.contacts.add_contact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gojek.krith.contacts.R;
import com.gojek.krith.contacts.Repository.ContactRepository;

import javax.inject.Inject;

public class AddContactActivity extends AppCompatActivity implements AddContactContract.View {

    AddContactContract.Presenter mPresenter;

    @Inject
    ContactRepository mContactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setUpPresenter(AddContactContract.Presenter presenter) {

    }

    @Override
    public void showLoader(boolean showLoader) {

    }

    @Override
    public void setUpComponents() {

    }

    @Override
    public void showNetworkError() {

    }
}
