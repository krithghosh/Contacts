package com.gojek.krith.contacts.add_contact;

import com.gojek.krith.contacts.Repository.ContactRepository;
import com.gojek.krith.contacts.models.Contact;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by krith on 09/02/17.
 */
public class AddContactPresenterTest {

    @Mock
    private ContactRepository mContactRepository;

    @Mock
    private AddContactContract.View mView;
    private AddContactPresenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new AddContactPresenter(mView, mContactRepository);

        // Mocking the AndroidSchedulers.MainThread().
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void addContact() throws Exception {
        Contact contact = new Contact(1, "wayne", "rooney", "919831234567", "wayne@example.com", false);
        when(mContactRepository.addContact(contact)).thenReturn(Observable.just(contact));
        mPresenter.addContact(contact);
        verify(mContactRepository).addContact(any(Contact.class));
        verify(mView).contactSaved();
    }

    @After
    public void tearDown() throws Exception {
        RxAndroidPlugins.getInstance().reset();
    }
}