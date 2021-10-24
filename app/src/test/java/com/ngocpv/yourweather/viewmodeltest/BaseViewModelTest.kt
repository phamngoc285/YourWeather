package com.ngocpv.yourweather.viewmodeltest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ngocpv.yourweather.TestCoroutineRule
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

open class BaseViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Support main looper

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
    }
}