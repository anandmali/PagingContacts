package com.anandmali.pagingcontacts.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactPagingSource: ContactPagingSource
) {
    fun getContactStream(): LiveData<PagingData<Contact>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { contactPagingSource }
        ).liveData
    }
}