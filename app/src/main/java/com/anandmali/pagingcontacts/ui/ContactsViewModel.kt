package com.anandmali.pagingcontacts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anandmali.pagingcontacts.data.Contact
import com.anandmali.pagingcontacts.data.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    fun loadContacts(): LiveData<PagingData<Contact>> {
        return contactRepository.getContacts()
            .cachedIn(viewModelScope)
    }
}
