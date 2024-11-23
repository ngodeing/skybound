package com.skybound.di

import android.content.Context
import com.skybound.data.user.UserPreference
import com.skybound.data.user.UserRepository
import com.skybound.data.user.sessiondataStore
import com.skybound.ui.settings.SettingPreferences
import com.skybound.ui.settings.settingDataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val usPref = UserPreference.getInstance(context.sessiondataStore)
        val setPref = SettingPreferences.getInstance(context.settingDataStore)
        return UserRepository.getInstance(usPref, setPref)
    }

    fun provideSettingPreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.settingDataStore)
    }
}
