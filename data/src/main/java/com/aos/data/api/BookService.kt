package com.aos.data.api

import com.aos.data.entity.request.book.DeleteBookCategoryBody
import com.aos.data.entity.request.book.PostBooksCategoryAddBody
import com.aos.data.entity.request.book.PostBooksChangeBody
import com.aos.data.entity.response.home.GetBookDaysEntity
import com.aos.data.entity.response.home.GetBookInfoEntity
import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.request.book.PostBooksCreateBody
import com.aos.data.entity.request.book.PostBooksExcelBody
import com.aos.data.entity.request.book.PostBooksInfoAssetBody
import com.aos.data.entity.request.book.PostBooksInfoBudgetBody
import com.aos.data.entity.request.book.PostBooksInfoCarryOverBody
import com.aos.data.entity.request.book.PostBooksInfoCurrencyBody
import com.aos.data.entity.request.book.PostBooksInfoSeeProfileBody
import com.aos.data.entity.request.book.PostBooksJoinBody
import com.aos.data.entity.request.book.PostBooksLinesBody
import com.aos.data.entity.request.book.PostBooksLinesEntity
import com.aos.data.entity.request.book.PostBooksNameBody
import com.aos.data.entity.response.book.GetBookCategoryEntity
import com.aos.data.entity.response.book.PostBooksChangeEntity
import com.aos.data.entity.request.settlement.PostBooksOutcomesBody
import com.aos.data.entity.request.settlement.PostSettlementAddBody
import com.aos.data.entity.response.book.GetBookRepeatEntity
import com.aos.data.entity.response.book.GetBooksBudgetEntity
import com.aos.data.entity.response.book.GetBooksCodeEntity
import com.aos.data.entity.response.book.GetBooksInfoCurrencyEntity
import com.aos.data.entity.response.book.GetBooksInfoEntity
import com.aos.data.entity.response.book.PostBooksCategoryAddEntity
import com.aos.data.entity.response.book.PostBooksCreateEntity
import com.aos.data.entity.response.book.PostBooksInfoCurrencyEntity
import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.data.entity.response.settlement.GetBooksUsersEntity
import com.aos.data.entity.response.settlement.GetSettleUpLastEntity
import com.aos.data.entity.response.settlement.GetSettlementSeeEntity
import com.aos.data.entity.response.settlement.PostBooksOutcomesEntity
import com.aos.data.entity.response.settlement.PostSettlementAddEntity
import com.aos.util.NetworkState
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File

interface BookService {

    // 유저 가계부 유효 확인
    @GET("books/users/check")
    @Headers("Auth: true")
    suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity>

    // 캘린더 조회 (월별)
    @GET("books/month")
    @Headers("Auth: true")
    suspend fun getBookMonth(
        @Query("bookKey") bookKey: String,
        @Query("date") date: String
    ): NetworkState<GetBookMonthEntity>


    // 일별 조회
    @GET("books/days")
    @Headers("Auth: true")
    suspend fun getBookDays(
        @Query("bookKey") bookKey: String,
        @Query("date") date: String
    ): NetworkState<GetBookDaysEntity>

    @GET("books/info")
    @Headers("Auth: true")
    suspend fun getBookInfo(
        @Query("bookKey") bookKey: String,
    ): NetworkState<GetBookInfoEntity>

    // 가계부 참여
    @POST("books/join")
    @Headers("Auth: true")
    suspend fun postBooksJoin(
        @Body postBooksJoinBody: PostBooksJoinBody
    ): NetworkState<PostBooksJoinEntity>

    // 가계부 생성 - 최초 가입 후 가계부 생성
    @POST("books")
    @Headers("Auth: true")
    suspend fun postBooksCreate(
        @Body postBooksCreateBody: PostBooksCreateBody
    ): NetworkState<PostBooksCreateEntity>

    // 카테고리 조회하기
    @GET("books/{bookKey}/categories")
    @Headers("Auth: true")
    suspend fun getBooksCategory(
        @Path("bookKey") bookKey: String,
        @Query("parent") parent: String
    ): NetworkState<List<GetBookCategoryEntity>>

    // 가계부 내역 추가
    @POST("books/lines")
    @Headers("Auth: true")
    suspend fun postBooksLines(
        @Body moneyData: PostBooksLinesBody
    ): NetworkState<PostBooksLinesEntity>

    // 가계부 내역 수정
    @POST("books/lines/change")
    @Headers("Auth: true")
    suspend fun postBooksLinesChange(
        @Body moneyData: PostBooksChangeBody
    ): NetworkState<PostBooksChangeEntity>

    // 가계부의 마지막 정산일 조회
    @GET("books/settlement/last")
    @Headers("Auth: true")
    suspend fun getSettlementLast(
        @Query("bookKey") bookKey: String
    ): NetworkState<GetSettleUpLastEntity>

    // 특정 가계부의 유저들 조회
    @GET("books/users")
    @Headers("Auth: true")
    suspend fun getBooksUsers(
        @Query("bookKey") bookKey: String
    ): NetworkState<List<GetBooksUsersEntity>>

    // 정산 지출 내역 조회
    @POST("books/outcomes")
    @Headers("Auth: true")
    suspend fun postBooksOutcomes(
        @Body postBooksOutcomesBody: PostBooksOutcomesBody
    ): NetworkState<List<PostBooksOutcomesEntity>>

    // 정산 추가
    @POST("settlement")
    @Headers("Auth: true")
    suspend fun postSettlementAdd(
        @Body postSettlementAddBody: PostSettlementAddBody
    ): NetworkState<PostSettlementAddEntity>

    // 가계부의 정산 내역 조회
    @GET("settlement")
    @Headers("Auth: true")
    suspend fun getSettlementSee(
        @Query("bookKey") bookKey: String
    ): NetworkState<List<GetSettlementSeeEntity>>

    // 정산 세부 내역 조회
    @GET("settlement/{id}")
    @Headers("Auth: true")
    suspend fun getSettlementDetailSee(
        @Path("id") id: Long
    ): NetworkState<PostSettlementAddEntity>

    // 가계부 설정 조회하기
    @GET("books/info")
    @Headers("Auth: true")
    suspend fun getBooksInfo(
        @Query("bookKey") bookKey: String
    ): NetworkState<GetBooksInfoEntity>

    // 가계부 이름 변경
    @POST("books/name")
    @Headers("Auth: true")
    suspend fun postBooksName(
        @Body postBooksNameBody : PostBooksNameBody
    ): NetworkState<Void>

    // 가계부 삭제
    @HTTP(method = "DELETE", path="books/delete", hasBody = true)
    @Headers("Auth: true")
    suspend fun deleteBooks(
        @Query("bookKey") bookKey: String
    ): NetworkState<Void>

    // 내역 프로필 보기 설정
    @POST("books/info/seeProfile")
    @Headers("Auth: true")
    suspend fun postBooksInfoSeeProfile(
        @Body postBooksInfoSeeProfile : PostBooksInfoSeeProfileBody
    ): NetworkState<Void>

    // 가계부 초기화
    @HTTP(method = "DELETE", path="books/info/delete/all", hasBody = true)
    @Headers("Auth: true")
    suspend fun deleteBooksInfoAll(
        @Query("bookKey") bookKey: String
    ): NetworkState<Void>

    // 화폐설정 변경
    @POST("books/info/currency")
    @Headers("Auth: true")
    suspend fun postBooksInfoCurrency(
        @Body postBooksInfoCurrencyBody : PostBooksInfoCurrencyBody
    ): NetworkState<PostBooksInfoCurrencyEntity>

    // 화폐설정 조회
    @GET("books/info/currency")
    @Headers("Auth: true")
    suspend fun getBooksInfoCurrency(
        @Query("bookKey") bookKey : String
    ): NetworkState<GetBooksInfoCurrencyEntity>

    // 가계부 자산 설정하기
    @POST("books/info/asset")
    @Headers("Auth: true")
    suspend fun postBooksInfoAsset(
        @Body postBooksInfoAssetBody : PostBooksInfoAssetBody
    ): NetworkState<Void>

    // 가계부 이월 설정 on/off
    @POST("books/info/carryOver")
    @Headers("Auth: true")
    suspend fun postBooksInfoCarryOver(
        @Body postBooksInfoCarryOverBody: PostBooksInfoCarryOverBody
    ): NetworkState<Void>

    // 예산조회하기
    @GET("books/budget")
    @Headers("Auth: true")
    suspend fun getBooksBudget(
        @Query("bookKey") bookKey : String,
        @Query("startYear") date : String
    ): NetworkState<GetBooksBudgetEntity>

    // 가계부 예산 설정하기
    @POST("books/info/budget")
    @Headers("Auth: true")
    suspend fun postBooksInfoBudget(
        @Body postBooksInfoBudgetBody : PostBooksInfoBudgetBody
    ): NetworkState<Void>

    // 하위 카테고리 삭제
    @HTTP(method = "DELETE", path="books/{bookKey}/categories", hasBody = true)
    @Headers("Auth: true")
    suspend fun deleteBookCategory(
        @Path("bookKey") bookKey: String,
        @Body deleteBookCategoryBody: DeleteBookCategoryBody
    ): NetworkState<Void>

    // 카테고리 추가하기
    @POST("books/{bookKey}/categories")
    @Headers("Auth: true")
    suspend fun postBooksCategoryAdd(
        @Path("bookKey") bookKey: String,
        @Body postBooksCategoryAddBody: PostBooksCategoryAddBody
    ): NetworkState<PostBooksCategoryAddEntity>

    // 가계부 코드 조회
    @GET("books/code")
    @Headers("Auth: true")
    suspend fun getBooksCode(
        @Query("bookKey") bookKey : String
    ): NetworkState<GetBooksCodeEntity>

    // 가계부 엑셀 다운로드
    @POST("books/excel")
    @Headers("Auth: true")
    suspend fun postBooksExcel(
        @Body postBooksExcelBody: PostBooksExcelBody
    ): NetworkState<ResponseBody>

    // 반복 내역 조회
    @GET("books/repeat")
    @Headers("Auth: true")
    suspend fun getBooksRepeat(
        @Query("bookKey") bookKey: String,
        @Query("categoryType") categoryType: String
    ): NetworkState<List<GetBookRepeatEntity>>

    // 반복 내역 삭제
    @HTTP(method = "DELETE", path="books/repeat", hasBody = true)
    @Headers("Auth: true")
    suspend fun deleteBooksRepeat(
        @Query("repeatLineId") repeatLineId: Int
    ): NetworkState<Void>

}