package com.dod.travel.base

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, "f40cc4aeca7991aad237d6ef0c11a821")
    }
}