package com.gojek.krith.contacts.contact_details;

import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.add_contact.AddContactContract;
import com.gojek.krith.contacts.add_contact.AddContactPresenter;
import com.gojek.krith.contacts.all_contacts.AllContactsPresenter;
import com.gojek.krith.contacts.models.Contact;
import com.google.common.collect.Lists;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by krith on 10/02/17.
 */
public class ContactDetailsPresenterTest {

    @Mock
    private ContactRepository mContactRepository;

    @Mock
    private ContactDetailsContract.View mView;

    @Captor
    private ArgumentCaptor<Contact> mLoadContact;
    private ContactDetailsPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new ContactDetailsPresenter(mView, mContactRepository);

        // Mocking the AndroidSchedulers.MainThread().
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void markFavourite() throws Exception {
        Contact contact = new Contact(1, "wayne", "rooney", "919831234567", "wayne@example.com", true);
        when(mContactRepository.markFavorite(1)).thenReturn(Observable.just(contact));
        mPresenter.markFavourite(1);
        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showLoader(true);
        inOrder.verify(mView).showLoader(false);
        verify(mView).markFav(contact.getFavorite());
    }

    @Test
    public void getContact() throws Exception {
        Contact contact = new Contact(1, "wayne", "rooney", "919831234567", "wayne@example.com", false);
        when(mContactRepository.getContact(1)).thenReturn(Observable.just(contact));
        mPresenter.getContact(1);
        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showLoader(true);
        inOrder.verify(mView).showLoader(false);
        ArgumentCaptor<Contact> showContactsArgumentCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(mView).showContact(showContactsArgumentCaptor.capture());
        assertEquals("wayne", showContactsArgumentCaptor.getValue().getFirstName());
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
    }
}