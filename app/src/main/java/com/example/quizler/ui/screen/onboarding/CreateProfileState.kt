package com.example.quizler.ui.screen.onboarding

import com.example.quizler.R
import com.example.quizler.model.InfoBannerData

data class CreateProfileState(
    val username: String = "",
    val choosenAvatar: Avatar? = null,
    val infoBannerData: InfoBannerData? = null
) {
    val avatarList = listOf(
        Avatar(R.mipmap.bear, name = "bear"),
        Avatar(R.mipmap.bed_bug, name = "bed_bug"),
        Avatar(R.mipmap.cat, name = "cat"),
        Avatar(R.mipmap.cow, name = "cow"),
        Avatar(R.mipmap.dinosaur, name = "dinosaur"),
        Avatar(R.mipmap.dog, name = "dog"),
        Avatar(R.mipmap.ganesha, name = "ganesha"),
        Avatar(R.mipmap.gorilla, name = "gorilla"),
        Avatar(R.mipmap.graduation, name = "graduation"),
        Avatar(R.mipmap.ladybug, name = "ladybug"),
        Avatar(R.mipmap.lion, name = "lion"),
        Avatar(R.mipmap.monkey, name = "monkey"),
        Avatar(R.mipmap.panda_bear, name = "panda_bear"),
        Avatar(R.mipmap.polar_bear, name = "polar_bear"),
        Avatar(R.mipmap.rabbit, name = "rabbit"),
        Avatar(R.mipmap.snowy_owl, name = "snowy_owl"),
        Avatar(R.mipmap.turtle, name = "turtle"),
    )
}
