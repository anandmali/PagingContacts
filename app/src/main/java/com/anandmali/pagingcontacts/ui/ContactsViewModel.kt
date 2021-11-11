package com.anandmali.pagingcontacts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.rxjava2.cachedIn
import com.anandmali.pagingcontacts.data.Contact
import com.anandmali.pagingcontacts.data.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    fun loadContactsLiveData(): LiveData<PagingData<Contact>> {
        return contactRepository.getContactsLiveDat()
            .cachedIn(viewModelScope)
    }

    @ExperimentalCoroutinesApi
    fun loadContactsObservable(): Observable<PagingData<Contact>> {
        return contactRepository.getContactsObservable()
            .cachedIn(viewModelScope)
    }

    fun loadContactsFlow(): Flow<PagingData<Contact>> {
        return contactRepository.getContactsFlow()
            .cachedIn(viewModelScope)
    }
}
