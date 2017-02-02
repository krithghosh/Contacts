package com.gojek.krith.contacts.all_contacts;

import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.application.SharedPreferenceManager;
import com.gojek.krith.contacts.models.Contact;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.gojek.krith.contacts.application.SharedPreferenceManager.IS_CONTACTS_FETCHED_FROM_SERVER;

/**
 * Created by krith on 31/01/17.
 */

public class AllContactsPresenter implements AllContactsContract.Presenter {

    AllContactsContract.View mView;
    ContactRepository mContactRepository;
    Subscription mSubscription;
    private final boolean FORCE_UPDATE = Boolean.TRUE;
    private final boolean SHOW_LOADER = Boolean.TRUE;

    public AllContactsPresenter(AllContactsContract.View mView, ContactRepository mContactRepository) {
        this.mView = mView;
        this.mContactRepository = mContactRepository;
        mView.setUpPresenter(this);
    }

    @Override
    public void subscribe() {
        if (SharedPreferenceManager.getBoolean(IS_CONTACTS_FETCHED_FROM_SERVER))
            fetchContacts(!FORCE_UPDATE);
        else
            fetchContacts(FORCE_UPDATE);
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null || !mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

    @Override
    public void fetchContacts(boolean shouldForceUpdate) {
        mView.showLoader(SHOW_LOADER);
        mContactRepository.getAllContacts(shouldForceUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Contact>>() {
                    @Override
                    public void onCompleted() {
                        mView.showLoader(!SHOW_LOADER);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoader(!SHOW_LOADER);
                        mView.showNetworkError();
                    }

                    @Override
                    public void onNext(List<Contact> contacts) {
                        if (contacts == null || contacts.size() < 1) {
                            mView.showNoContactsAvailable();
                            return;
                        }
                        SharedPreferenceManager.putBoolean(IS_CONTACTS_FETCHED_FROM_SERVER, Boolean.TRUE);
                        mView.showContacts(contacts);
                    }
                });
    }
}
