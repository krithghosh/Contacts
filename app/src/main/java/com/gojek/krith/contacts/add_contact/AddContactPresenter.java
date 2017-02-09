package com.gojek.krith.contacts.add_contact;

import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.models.Contact;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by krith on 31/01/17.
 */

public class AddContactPresenter implements AddContactContract.Presenter {

    private final AddContactContract.View mView;
    private final ContactRepository mContactRepository;
    private static Subscription mSubscription = null;
    private final boolean SHOW_LOADER = Boolean.TRUE;

    public AddContactPresenter(AddContactContract.View mView, ContactRepository mContactRepository) {
        this.mView = mView;
        this.mContactRepository = mContactRepository;
        mView.setUpPresenter(this);
    }

    @Override
    public void subscribe(Contact contact) {
        addContact(contact);
    }

    @Override
    public void unSubscribe() {
        if (!(mSubscription == null || mSubscription.isUnsubscribed()))
            mSubscription.unsubscribe();
    }

    @Override
    public void addContact(Contact contact) {
        mView.showLoader(SHOW_LOADER);
        mSubscription = mContactRepository.addContact(contact)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Contact>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoader(!SHOW_LOADER);
                        mView.showNetworkError();
                    }

                    @Override
                    public void onNext(Contact contact) {
                        mView.showLoader(!SHOW_LOADER);
                        mView.contactSaved();
                    }
                });
    }
}
