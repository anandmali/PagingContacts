package com.anandmali.pagingcontacts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.anandmali.pagingcontacts.data.Contact
import com.anandmali.pagingcontacts.data.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    lateinit var contactsList: LiveData<PagingData<Contact>>

    fun loadContacts() {
        contactsList = contactRepository.getContactStream()
    }
}
