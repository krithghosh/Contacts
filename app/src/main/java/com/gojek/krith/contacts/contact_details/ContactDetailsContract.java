package com.gojek.krith.contacts.contact_details;

import com.gojek.krith.contacts.models.Contact;


/**
 * Created by krith on 31/01/17.
 */

public interface ContactDetailsContract {
    interface View {
        void setUpPresenter(ContactDetailsContract.Presenter presenter);

        void showLoader(boolean showLoader);

        void markFav(boolean markFav);

        void showNetworkError();

        void setUpComponents();

        void showContact(Contact contact);
    }

    interface Presenter {
        void subscribe(int id);

        void unSubscribe();

        void markFavourite(int id);
    }
}
