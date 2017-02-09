package com.gojek.krith.contacts.all_contacts;

import com.gojek.krith.contacts.models.Contact;

import java.util.List;

/**
 * Created by krith on 31/01/17.
 */

public interface AllContactsContract {
    interface View {
        void setUpPresenter(Presenter presenter);

        void showLoader(boolean showLoader);

        void showContacts(List<Contact> contacts);

        void setupComponents();

        void showNetworkError();

        void showNoContactsAvailable();
    }

    interface Presenter {
        void subscribe();

        void unSubscribe();

        void fetchContacts(boolean forceUpdate);
    }
}
