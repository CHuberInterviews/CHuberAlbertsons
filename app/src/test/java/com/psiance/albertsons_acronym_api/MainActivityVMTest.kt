package com.psiance.albertsons_acronym_api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.psiance.albertsons_acronym_api.network.AcronymManager
import com.psiance.albertsons_acronym_api.network.responseDTO.AcronymResponseDTO
import com.psiance.albertsons_acronym_api.network.RetrofitService
import com.psiance.albertsons_acronym_api.views.viewmodel.AcronymViewModel
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

@RunWith(JUnit4::class)
class MainActivityVMTest{
    private var server: MockWebServer? = null

    private var vm : AcronymViewModel = AcronymViewModel()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        server = MockWebServer()
    }

    @Test
    fun testNetworkCallSuccess(){
        startServer(200)
        val latch = CountDownLatch(1)
        val url = server?.url("/").toString()
        RetrofitService.BASE_URL = url

        vm.getResponseTest("ssl")
        assertNotNull(vm.getItemsTest())
        latch.await(20000, TimeUnit.MILLISECONDS)
    }

    private fun startServer(responseCode: Int){

        val mockedResponse = MockResponse()

        mockedResponse.setResponseCode(responseCode)
        val contentBody= MockResponseFileReader("acronym.json")
        contentBody.content?.let { mockedResponse.setBody(it) }
        server?.enqueue(mockedResponse)
        server?.start()
    }
}