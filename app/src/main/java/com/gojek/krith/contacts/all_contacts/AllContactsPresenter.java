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

    private final AllContactsContract.View mView;
    private final ContactRepository mContactRepository;
    private static Subscription mSubscription;
    private final SharedPreferenceManager mSharedPreferenceManager;
    private final boolean FORCE_UPDATE = Boolean.TRUE;
    private final boolean SHOW_LOADER = Boolean.TRUE;

    public AllContactsPresenter(AllContactsContract.View mView, ContactRepository mContactRepository,
                                SharedPreferenceManager mSharedPreferenceManager) {
        this.mView = mView;
        this.mContactRepository = mContactRepository;
        this.mSharedPreferenceManager = mSharedPreferenceManager;
        mView.setUpPresenter(this);
    }

    @Override
    public void subscribe() {
        if (mSharedPreferenceManager.getBoolean(IS_CONTACTS_FETCHED_FROM_SERVER))
            fetchContacts(!FORCE_UPDATE);
        else
            fetchContacts(FORCE_UPDATE);
    }

    @Override
    public void unSubscribe() {
        if (!(mSubscription == null || mSubscription.isUnsubscribed()))
            mSubscription.unsubscribe();
    }

    @Override
    public void fetchContacts(boolean shouldForceUpdate) {
        mView.showLoader(SHOW_LOADER);
        mSubscription = mContactRepository.getAllContacts(shouldForceUpdate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Contact>>() {
                    @Override
                    public void onCompleted() {
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
                        mSharedPreferenceManager.putBoolean(IS_CONTACTS_FETCHED_FROM_SERVER, Boolean.TRUE);
                        mView.showLoader(!SHOW_LOADER);
                        mView.showContacts(contacts);
                    }
                });
    }
}
