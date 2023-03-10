package com.binlistapp.presentation.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.binlistapp.data.model.entities.CardInformation
import com.binlistapp.data.model.enums.DebitCredit
import com.binlistapp.presentation.adapters.BinItemAdapter
import com.binlistapp.presentation.main.App
import com.binlistapp.presentation.viewModels.BinCheckViewModel
import com.binlistapp.utils.titleCaseFirstChar
import com.binlistapp.utils.toPhone
import com.binlistapp.utils.toURL
import com.example.binlistapp.R
import com.example.binlistapp.databinding.FragmentBinCheckBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.myproject.currencyconverter.di.scope.FragmentScope
import javax.inject.Inject

@FragmentScope
class BinCheckFragment : Fragment() {

    companion object {

        fun newInstance() = BinCheckFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: BinCheckViewModel by viewModels { viewModelFactory }
    private lateinit var binding: FragmentBinCheckBinding
    private lateinit var binItemAdapter: BinItemAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.binCheckFragmentComponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBinCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        observeCardInformationStateFlow()
        observeBinItemListStateFlow()
        observeLoadingStateFlow()
        observeSharedStateFlow()
        setupListeners()
    }

    private fun setupRecycleView() {
        binItemAdapter = BinItemAdapter()
        binding.historyRecycleView.apply {
            adapter = binItemAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun observeSharedStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.errorSharedFlow.collect {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.refresh_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun observeLoadingStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadingStateFlow.collect { loading ->
                    binding.progressBar.isVisible = loading
                }
            }
        }
    }

    private fun observeBinItemListStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.binItemListStateFlow.collect { binItemList ->
                    binItemAdapter.submitList(binItemList)
                }
            }
        }
    }

    private fun observeCardInformationStateFlow() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cardInformationStateFlow.collect { cardInformation ->
                    if (cardInformation != null) {
                        setupViews(cardInformation)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.binInputField.setOnEditorActionListener { v, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                viewModel.fetchCardInformation(v.text.toString())
                viewModel.insertBinItem(v.text.toString())
            }
            false
        }
        binItemAdapter.setOnItemClickListener { binItem ->
            binding.binInputField.setText(binItem.binNumber)
            viewModel.fetchCardInformation(binItem.binNumber)
        }

        binItemAdapter.setRecycleClickListener { binItem ->
            viewModel.deleteBinItem(binItem)
        }
    }

    private fun setupViews(cardInformation: CardInformation) {
        with(binding) {
            schemeNetworkContent.text = cardInformation.scheme.titleCaseFirstChar()
            brandContent.text = cardInformation.brand
            lengthContent.text = cardInformation.length
            luhnContent.text = cardInformation.luhn?.let { setupYesNo(it) }
            prepaidContent.text = cardInformation.prepaid?.let { setupYesNo(it) }
            typeContent.text = setupDebitCredit(cardInformation.type)
            countryEmoji.text = cardInformation.emoji
            countryName.text = cardInformation.country
            bankNameCity.text = if (cardInformation.bankName.isNotEmpty()) getString(
                R.string.bank_name_and_city,
                cardInformation.bankName,
                cardInformation.bankCity
            ) else cardInformation.bankCity
            bankUrl.text = cardInformation.bankUrl
            bankPhone.text = cardInformation.bankPhone
            coordinates.text =
                getString(R.string.coordinates, cardInformation.latitude, cardInformation.longitude)

            bankUrl.setOnClickListener {
                val urlUri = Uri.parse(cardInformation.bankUrl.toURL())
                val urlIntent = Intent(Intent.ACTION_VIEW, urlUri)
                ContextCompat.startActivity(requireContext(), urlIntent, null)
            }

            bankPhone.setOnClickListener {
                val phoneUri = Uri.parse("tel:${cardInformation.bankPhone.toPhone()}")
                val phoneIntent = Intent(Intent.ACTION_DIAL, phoneUri)
                ContextCompat.startActivity(requireContext(), phoneIntent, null)
            }

            coordinates.setOnClickListener {
                val geoUri =
                    Uri.parse("geo:${cardInformation.latitude},${cardInformation.longitude}")
                val geoIntent = Intent(Intent.ACTION_VIEW, geoUri)
                ContextCompat.startActivity(requireContext(), geoIntent, null)
            }
        }
    }

    private fun setupDebitCredit(debitCredit: DebitCredit): Spannable {
        return when (debitCredit) {
            DebitCredit.Debit -> colorMyText(getString(R.string.debit_credit), 0, 5)
            DebitCredit.Credit -> colorMyText(getString(R.string.debit_credit), 6, 12)
            DebitCredit.Undefined -> colorMyText(getString(R.string.debit_credit), 0, 0)
        }
    }

    private fun colorMyText(
        inputText: String,
        startIndex: Int,
        endIndex: Int,
    ): Spannable {
        val outPutColoredText: Spannable = SpannableString(inputText)
        outPutColoredText.setSpan(
            ForegroundColorSpan(Color.BLACK), startIndex, endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return outPutColoredText
    }

    private fun setupYesNo(flag: Boolean): Spannable {
        return if (flag)
            colorMyText(getString(R.string.yes_no), 0, 3)
        else colorMyText(getString(R.string.yes_no), 4, 6)
    }
}