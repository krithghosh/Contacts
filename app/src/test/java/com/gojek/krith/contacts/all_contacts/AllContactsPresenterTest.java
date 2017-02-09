package com.gojek.krith.contacts.all_contacts;

import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.application.SharedPreferenceManager;
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
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by krith on 09/02/17.
 */
public class AllContactsPresenterTest {

    private static List<Contact> CONTACTS;

    @Mock
    private ContactRepository mContactRepository;

    @Mock
    private SharedPreferenceManager mSharedPreferenceManager;

    @Mock
    private AllContactsContract.View mView;

    @Captor
    private ArgumentCaptor<List<Contact>> mLoadContacts;
    private AllContactsPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new AllContactsPresenter(mView, mContactRepository, mSharedPreferenceManager);
        Contact contact1 = new Contact(1, "wayne", "rooney", "919831234567", "wayne@example.com", false);
        Contact contact2 = new Contact(2, "zlatan", "ibra", "919831234567", "ibra@example.com", false);
        CONTACTS = Lists.newArrayList(contact1, contact2);

        // Mocking the AndroidSchedulers.MainThread().
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void fetchContacts() throws Exception {
        when(mContactRepository.getAllContacts(true)).thenReturn(Observable.just(CONTACTS));
        mPresenter.fetchContacts(true);
        InOrder inOrder = inOrder(mView);
        inOrder.verify(mView).showLoader(true);
        inOrder.verify(mView).showLoader(false);
        ArgumentCaptor<List> showContactsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(mView).showContacts(showContactsArgumentCaptor.capture());
        assertTrue(showContactsArgumentCaptor.getValue().size() == 2);
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
    }
}