package com.anandmali.pagingcontacts.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import androidx.paging.rxjava2.observable
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

const val PAGING_SIZE = 10

class ContactRepository @Inject constructor(
    private val contactPagingSource: ContactPagingSource
) {

    //Using live data
    fun getContactsLiveDat(): LiveData<PagingData<Contact>> {
        return Pager(
            config = PagingConfig(pageSize = PAGING_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { contactPagingSource }
        ).liveData
    }

    //Using RxJava observable
    @ExperimentalCoroutinesApi
    fun getContactsObservable(): Observable<PagingData<Contact>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { contactPagingSource }
        ).observable
    }

    //Using kotlin flow
    fun getContactsFlow(): Flow<PagingData<Contact>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { contactPagingSource }
        ).flow
    }
}