package com.gojek.krith.contacts.add_contact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.gojek.krith.contacts.R;
import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.all_contacts.AllContactsActivity;
import com.gojek.krith.contacts.application.App;
import com.gojek.krith.contacts.models.Contact;
import com.gojek.krith.contacts.utils.AppUtils;
import com.gojek.krith.contacts.utils.SnackbarUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity extends AppCompatActivity implements AddContactContract.View, View.OnClickListener {

    @BindView(R.id.iv_profile_image)
    ImageView profileImage;

    @BindView(R.id.et_first_name)
    EditText firstName;

    @BindView(R.id.et_last_name)
    EditText lastName;

    @BindView(R.id.et_email)
    EditText email;

    @BindView(R.id.et_phone_number)
    EditText phoneNumber;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.rv_main_layout)
    RelativeLayout mainLayout;

    @BindView(R.id.btn_done)
    Button doneBtn;

    private AddContactContract.Presenter mPresenter;

    @Inject
    ContactRepository mContactRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        setUpComponents();
        doneBtn.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void setUpPresenter(AddContactContract.Presenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void showLoader(boolean showLoader) {
        progressBar.setVisibility(showLoader ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setUpComponents() {
        App.getComponent().inject(this);
        new AddContactPresenter(this, mContactRepository);
    }

    @Override
    public void showNetworkError() {
        SnackbarUtils.showSnackbar(mainLayout, getString(R.string.not_able_to_reach_server));
    }

    @Override
    public void contactSaved() {
        startActivity(new Intent(this, AllContactsActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        contactSaved();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:
                if (!AppUtils.isValidName(firstName.getText().toString())) {
                    SnackbarUtils.showSnackbar(mainLayout, getString(R.string.first_name_not_valid));
                    return;
                } else if (!AppUtils.isValidName(lastName.getText().toString())) {
                    SnackbarUtils.showSnackbar(mainLayout, getString(R.string.last_name_not_valid));
                    return;
                } else if (!AppUtils.validateEmail(email.getText().toString())) {
                    SnackbarUtils.showSnackbar(mainLayout, getString(R.string.email_not_valid));
                    return;
                } else if (!AppUtils.validatePhoneNumber(phoneNumber.getText().toString())) {
                    SnackbarUtils.showSnackbar(mainLayout, getString(R.string.phone_number_not_valid));
                    return;
                }

                Contact contact = new Contact();
                contact.setFirstName(firstName.getText().toString());
                contact.setLastName(lastName.getText().toString());
                contact.setEmail(email.getText().toString());
                contact.setPhoneNumber(phoneNumber.getText().toString());
                mPresenter.subscribe(contact);
                break;
        }
    }
}
