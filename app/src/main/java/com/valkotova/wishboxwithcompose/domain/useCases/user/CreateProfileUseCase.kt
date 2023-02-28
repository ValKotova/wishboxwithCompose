package com.valkotova.wishboxwithcompose.domain.useCases.user

import com.valkotova.wishboxwithcompose.domain.Repositories.UserRepository
import com.valkotova.wishboxwithcompose.domain.model.users.Gender
import javax.inject.Inject

class CreateProfileUseCase @Inject constructor(
    private val userRepository : UserRepository
) {
    suspend fun execute(
        nickname: String?,
        fullName: String,
        gender: Gender,
        birthday: String?,
        about: String?,
        social: List<String>?,
        avatar: Int?
    ){
        userRepository.initProfile(
            nickname = nickname,
            fullName = fullName,
            gender = gender.toString(),
            birthday = birthday,
            about = about,
            social = social,
            avatar = avatar
        )
    }
}