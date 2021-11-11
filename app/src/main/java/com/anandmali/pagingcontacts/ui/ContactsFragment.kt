package com.anandmali.pagingcontacts.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.anandmali.pagingcontacts.databinding.FragmentContactsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactsFragment : Fragment() {

    private val viewModel: ContactsViewModel by viewModels()
    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!
    private val contactsAdapter = ContactsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentContactsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvContactList.adapter = contactsAdapter
        loadContactsFlow()
    }

    /**
     * Load paging contacts using LiveData
     */
    private fun loadContactsLiveData() {
        viewModel.loadContactsLiveData().observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                contactsAdapter.submitData(it)
            }
        })
    }

    /**
     * Load paging contacts using RxJava Observable
     */
    @SuppressLint("CheckResult")
    private fun loadContactsObservable() {
        viewModel.loadContactsObservable().subscribe {
            lifecycleScope.launch {
                contactsAdapter.submitData(it)
            }
        }
    }

    /**
     * Load contacts using Kotlin Flow
     */
    private fun loadContactsFlow() {
        lifecycleScope.launch {
            viewModel.loadContactsFlow().collectLatest {
                contactsAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}