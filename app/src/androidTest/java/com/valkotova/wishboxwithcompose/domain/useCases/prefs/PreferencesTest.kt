package com.valkotova.wishboxwithcompose.domain.useCases.prefs

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.valkotova.wishboxwithcompose.HiltTestRunner
import com.valkotova.wishboxwithcompose.data.repositories.PrefsRepositoryImpl
import com.valkotova.wishboxwithcompose.domain.Repositories.PrefsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.components.SingletonComponent
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton


@HiltAndroidTest
//@Config(application = HiltTestRunner::class)
class PreferencesTest{

//    @Module
//    @InstallIn(SingletonComponent::class)
//    interface TestModule {
//
//        @Singleton
//        @Binds
//        fun bindsPreference(pref: PrefsRepositoryImpl): PrefsRepository
//    }

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var getTokenUseCase: GetTokenUseCase
    @Inject
    lateinit var getMyIdUseCase: GetMyIdUseCase
    @Inject
    lateinit var setMyIdUseCase: SetMyIdUseCase
    @Inject
    lateinit var setTokenUseCase: SetTokenUseCase
    @Inject
    lateinit var logoutUseCase: LogoutUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun getSetToken(){
        val token = "12345"
        setTokenUseCase.execute(token)
        assertEquals(token, getTokenUseCase.execute())
    }

    @Test
    fun getSetId(){
        val id : Int = 12345
        setMyIdUseCase.execute(id)
        assertEquals(id, getMyIdUseCase.execute())
    }

    @Test
    fun logout(){
        val token = "12345"
        setTokenUseCase.execute(token)
        logoutUseCase.execute()
        assertEquals("", getTokenUseCase.execute())
    }
}