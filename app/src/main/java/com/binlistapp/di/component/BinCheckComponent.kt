package com.binlistapp.di.component

import com.binlistapp.presentation.fragments.BinCheckFragment
import dagger.Subcomponent
import ru.myproject.currencyconverter.di.scope.FragmentScope

@Subcomponent
@FragmentScope
interface BinCheckComponent {

    fun inject(fragment: BinCheckFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(): BinCheckComponent
    }
}