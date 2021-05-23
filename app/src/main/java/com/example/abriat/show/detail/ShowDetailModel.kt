package com.example.abriat.show.detail

import com.example.abriat.show.api.ShowApiDetailResponse
import retrofit2.Response

sealed class ShowDetailModel

data class ShowDetailSuccess(val response: Response<ShowApiDetailResponse>): ShowDetailModel()
object ShowDetailError : ShowDetailModel()
object ShowDetailLoader : ShowDetailModel()
object ShowDetailNotFound : ShowDetailModel()