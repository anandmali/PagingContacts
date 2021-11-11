package com.anandmali.pagingcontacts.data

import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

class ContactPagingSource @Inject constructor(
    private val contentResolver: ContentResolver
) : PagingSource<Int, Contact>() {

    private val contactsProjection = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    )

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {
        val position = params.key ?: 1
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            contactsProjection,
            null,
            null,
            ContactsContract.Contacts.PHONETIC_NAME + " ASC LIMIT " + params.loadSize + " OFFSET " + position
        )

        val contacts: MutableList<Contact> = mutableListOf()

        cursor?.let {
            it.moveToFirst()
            while (!it.isAfterLast) {
                val id = it.getColumnIndex(contactsProjection[0])
                val name = it.getString(it.getColumnIndexOrThrow(contactsProjection[1]))
                contacts.add(Contact(id.toLong(), name.toString()))
                it.moveToNext()
            }
            it.close()
        }

        val nextKey = if (contacts.size < 4) {
            null
        } else {
            position + (params.loadSize / 4)
        }

        return LoadResult.Page(
            data = contacts,
            prevKey = if (position == 1) null else position - 1,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? {
        TODO("Not yet implemented")
    }
}