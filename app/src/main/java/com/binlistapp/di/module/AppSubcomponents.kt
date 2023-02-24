package com.binlistapp.di.module

import com.binlistapp.di.component.BinCheckComponent
import dagger.Module

@Module(
    subcomponents = [BinCheckComponent::class]
)
class AppSubcomponents