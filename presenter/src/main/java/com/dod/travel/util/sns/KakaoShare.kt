package com.dod.travel.util.sns

import android.content.Context
import android.util.Log
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.share.rx
import com.kakao.sdk.template.model.Link
import com.kakao.sdk.template.model.TextTemplate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers

object KakaoShare {

    private val TAG = "KakaoShare"

    fun feed(userName: String, data: Map<String, String>): TextTemplate {
        return TextTemplate(
            text = "${userName}님이 함께 하쟤요!",
            link = Link(
                webUrl = "https://play.google.com/store/apps/details?id=com.dod.travel",
                mobileWebUrl = "https://play.google.com/store/apps/details?id=com.dod.travel",
                data
            )
        )
    }

    fun share(template: TextTemplate, context: Context) {
        val disposable = CompositeDisposable()

        ShareClient.rx.shareDefault(context, template)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ sharingResult ->
                Log.d(TAG, "카카오톡 공유 성공 ${sharingResult.intent}")
                context.startActivity(sharingResult.intent)

                // 카카오톡 공유에 성공했지만 아래 경고 메시지가 존재할 경우 일부 컨텐츠가 정상 동작하지 않을 수 있습니다.
                Log.w(TAG, "Warning Msg: ${sharingResult.warningMsg}")
                Log.w(TAG, "Argument Msg: ${sharingResult.argumentMsg}")
            }, {error ->
                Log.e(TAG, "카카오톡 공유 실패 ", error)
            })
            .addTo(disposable)
    }
}