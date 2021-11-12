package com.anandmali.pagingcontacts.data

import android.content.ContentResolver
import android.provider.ContactsContract
import androidx.paging.PagingSource
import androidx.paging.PagingState
import javax.inject.Inject

private const val PAGING_INDEX = 1

class ContactPagingSource @Inject constructor(
    private val contentResolver: ContentResolver
) : PagingSource<Int, Contact>() {

    private val contactsProjection = arrayOf(
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
    )

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Contact> {

        //Current paging index position
        val position = params.key ?: PAGING_INDEX

        /**
         * Contacts source : ContactsContract; source for pagination data
         * Thi can be source of Network, Room DB, or any source providing the paginated data
         */
        val contacts = getContacts(params.loadSize, position)

        //Create next paging index
        val nextKey = if (contacts.size < PAGING_SIZE) {
            null
        } else {
            /**
             * By default initial pageSize = 3 * [PAGING_SIZE]
             * If needed this can be changed using : PagingConfig.initialLoadSize
             */
            position + (params.loadSize / PAGING_SIZE)
        }

        //Create a load paging result for PagingData
        return LoadResult.Page(
            data = contacts, //Set adapter data
            prevKey = if (position == 1) null else position - 1, //Previous index
            nextKey = nextKey //Next index
        )
    }

    private fun getContacts(loadSize: Int, position: Int): List<Contact> {
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            contactsProjection,
            null,
            null,
            ContactsContract.Contacts.PHONETIC_NAME + " ASC LIMIT "
                    + loadSize + " OFFSET " + position
        )

        //Read contacts from : ContactsContract
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
        return contacts
    }

    override fun getRefreshKey(state: PagingState<Int, Contact>): Int? {
        /**
         * This refresh key is required for next new paging source,
         * whenever current existing paging source is invalidated,
         * helpful for refresh mechanism on data update
         */
        TODO("Not yet implemented")
    }
}