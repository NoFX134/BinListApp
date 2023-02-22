package com.binlistapp.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.binlistapp.presentation.main.App
import com.example.binlistapp.databinding.FragmentBinCheckBinding

class BinCheckFragment : Fragment() {

    companion object {

        fun newInstance() = BinCheckFragment()
    }

    private lateinit var binding: FragmentBinCheckBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as App).appComponent.binCheckFragmentComponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =FragmentBinCheckBinding.inflate(inflater, container, false)
        return binding.root
    }
}