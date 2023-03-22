package com.example.quizler

import com.example.quizler.model.InfoBannerData
import com.example.util.exceptions.ConnectivityException
import com.example.util.mapper.DataMapper

class InfoBannerDataMapper : DataMapper<Throwable, InfoBannerData> {
    override fun map(input: Throwable): InfoBannerData {
        return if (input::class == ConnectivityException::class) {
            InfoBannerData.NoNetwork
        } else {
            InfoBannerData.ErrorLoadingContent
        }
    }
}
