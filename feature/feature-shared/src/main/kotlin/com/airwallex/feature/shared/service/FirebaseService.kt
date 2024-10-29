package com.airwallex.feature.shared.service

interface FirebaseService {
    fun init()

    fun setupUser(userId: String)

    fun clearUser()
}
