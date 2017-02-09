package com.gojek.krith.contacts.contact_details;

import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.models.Contact;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by krith on 31/01/17.
 */

public class ContactDetailsPresenter implements ContactDetailsContract.Presenter {

    private ContactDetailsContract.View mView;
    private final ContactRepository mContactRepository;
    private static Subscription mSubscription;
    private final boolean SHOW_LOADER = Boolean.TRUE;
    private Contact mContact = null;

    public ContactDetailsPresenter(ContactDetailsContract.View mView, ContactRepository mContactRepository) {
        this.mView = mView;
        this.mContactRepository = mContactRepository;
        mView.setUpPresenter(this);
    }

    @Override
    public void subscribe(int id) {
        getContact(id);
    }

    @Override
    public void unSubscribe() {
        if (!(mSubscription == null || mSubscription.isUnsubscribed()))
            mSubscription.unsubscribe();
    }

    @Override
    public void getContact(int id) {
        mView.showLoader(SHOW_LOADER);
        mSubscription = mContactRepository.getContact(id)
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
                        mView.showContact(contact);
                        mSubscription.unsubscribe();
                    }
                });
    }

    @Override
    public void markFavourite(int id) {
        mView.showLoader(SHOW_LOADER);
        mSubscription = mContactRepository.markFavorite(id)
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
                        mContact = contact;
                        mView.showLoader(!SHOW_LOADER);
                        mView.markFav(mContact.getFavorite());
                        mSubscription.unsubscribe();
                    }
                });
    }
}
