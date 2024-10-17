package com.aos.floney.view.subscribe

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.QueryProductDetailsParams
import com.aos.floney.view.common.ErrorToastDialog
import timber.log.Timber

class BillingManager(private val activity: Activity) {
    private lateinit var billingClient : BillingClient

    init {
        billingClient = BillingClient.newBuilder(activity)
            .enablePendingPurchases()
            .setListener { billingResult, purchases ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                    for (purchase in purchases) {
                        handlePurchase(purchase) // 구매 처리
                    }
                } else {
                    // 결제 실패 처리
                    Timber.e("Purchase failed: ${billingResult.debugMessage}")
                }
            }
            .build()
    }

    // 구매 정보 처리
    private fun handlePurchase(purchase: Purchase) {
        // 구매 토큰을 서버로 보내기 전에 구매 상태를 확인해야 함
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            // 구매가 완료된 상태에서만 처리
            val purchaseToken = purchase.purchaseToken
            Timber.i("Purchase successful, token: $purchaseToken")

            // 토큰을 서버로 전송하여 검증
            sendTokenToServer(purchaseToken)

            // 구매가 성공했음을 사용자에게 알림
            if (!purchase.isAcknowledged) {
                acknowledgePurchase(purchase)
            }
        }
    }

    // 서버로 토큰 전송
    private fun sendTokenToServer(purchaseToken: String) {
        // 서버로 토큰 전송을 위한 네트워크 요청
        Timber.i("Sending token to server: $purchaseToken")
    }

    // 구매 확인 (Acknowledgement)
    private fun acknowledgePurchase(purchase: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()

        billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Timber.i("Purchase acknowledged")
            } else {
                Timber.e("Failed to acknowledge purchase: ${billingResult.debugMessage}")
            }
        }
    }
    fun startConnection() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {
                // Google Play 서비스 연결이 끊어진 경우 처리
                Timber.e("checking 1")
            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // 구독 상품 로드 또는 구매 가능 처리
                    Timber.e("checking 2")
                    querySubscriptionDetails()
                }
            }
        })
    }

    fun querySubscriptionDetails() {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("floney_plus")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                )
            ).build()

        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && !productDetailsList.isNullOrEmpty()) {
                // SKU 세부 사항 로드 완료
                val productDetails = productDetailsList[0] // 첫 번째 상품 정보 사용
                // 구매 플로우 실행
                Timber.e("checking 33 Error code: ${billingResult.responseCode}, message: ${productDetailsList}")
                launchPurchaseFlow(productDetails)

            } else {
                // 오류 처리
                Timber.e("checking 3")
                Timber.e("checking Error code: ${billingResult.responseCode}, message: ${productDetailsList}")
            }
        }
    }

    fun launchPurchaseFlow(productDetails: ProductDetails) {
        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                listOf(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .setOfferToken(productDetails.subscriptionOfferDetails?.get(0)?.offerToken!!)
                        .build()
                )
            ).build()

        billingClient.launchBillingFlow(activity, billingFlowParams) // Activity로 구매 플로우 실행
    }
}
