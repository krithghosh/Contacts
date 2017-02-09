package com.gojek.krith.contacts.add_contact;

import com.gojek.krith.contacts.models.Contact;

/**
 * Created by krith on 31/01/17.
 */

public interface AddContactContract {
    interface View {
        void setUpPresenter(Presenter presenter);

        void showLoader(boolean showLoader);

        void setUpComponents();

        void showNetworkError();

        void contactSaved();
    }

    interface Presenter {
        void subscribe(Contact contact);

        void unSubscribe();

        void addContact(Contact contact);
    }
}
