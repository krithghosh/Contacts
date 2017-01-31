package com.gojek.krith.contacts.contact_details;

import com.gojek.krith.contacts.add_contact.AddContactContract;
import com.gojek.krith.contacts.models.Contact;

import java.util.List;

/**
 * Created by krith on 31/01/17.
 */

public interface ContactDetailsContract {
    public interface View {
        void setUpPresenter(AddContactContract.Presenter presenter);

        void showLoader(boolean showLoader);

        void showContact(Contact contact);

        void setUpComponents();
    }

    public interface Presenter {
        void subscribe();

        void unSubscribe();

        void fetchContact(int serverId);
    }
}
