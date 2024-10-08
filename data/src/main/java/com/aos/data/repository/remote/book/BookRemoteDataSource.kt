package com.aos.data.repository.remote.book

import com.aos.data.entity.request.book.DeleteBookCategoryBody
import com.aos.data.entity.request.book.PostBooksCategoryAddBody
import com.aos.data.entity.request.book.PostBooksChangeBody
import com.aos.data.entity.request.book.PostBooksCreateBody
import com.aos.data.entity.request.book.PostBooksExcelBody
import com.aos.data.entity.request.book.PostBooksFavoritesBody
import com.aos.data.entity.request.book.PostBooksInfoAssetBody
import com.aos.data.entity.request.book.PostBooksInfoBudgetBody
import com.aos.data.entity.request.book.PostBooksInfoCarryOverBody
import com.aos.data.entity.request.book.PostBooksInfoCurrencyBody
import com.aos.data.entity.request.book.PostBooksInfoSeeProfileBody
import com.aos.data.entity.request.book.PostBooksJoinBody
import com.aos.data.entity.request.book.PostBooksLinesBody
import com.aos.data.entity.request.book.PostBooksLinesEntity
import com.aos.data.entity.request.book.PostBooksNameBody
import com.aos.data.entity.request.book.PostBooksOutBody
import com.aos.data.entity.request.book.PostChangeBookImgBody
import com.aos.data.entity.response.book.GetBookCategoryEntity
import com.aos.data.entity.response.book.PostBooksChangeEntity
import com.aos.data.entity.request.settlement.PostBooksOutcomesBody
import com.aos.data.entity.request.settlement.PostSettlementAddBody
import com.aos.data.entity.response.book.GetBookFavoriteEntity
import com.aos.data.entity.response.book.GetBookRepeatEntity
import com.aos.data.entity.response.book.GetBooksBudgetEntity
import com.aos.data.entity.response.book.GetBooksCodeEntity
import com.aos.data.entity.response.book.GetBooksEntity
import com.aos.data.entity.response.book.GetBooksInfoCurrencyEntity
import com.aos.data.entity.response.book.GetBooksInfoEntity
import com.aos.data.entity.response.book.PostBookFavoriteEntity
import com.aos.data.entity.response.book.PostBooksCategoryAddEntity
import com.aos.data.entity.response.book.PostBooksCreateEntity
import com.aos.data.entity.response.book.PostBooksInfoCurrencyEntity
import com.aos.data.entity.response.book.PostBooksJoinEntity
import com.aos.data.entity.response.home.GetBookDaysEntity
import com.aos.data.entity.response.home.GetBookInfoEntity
import com.aos.data.entity.response.home.GetBookMonthEntity
import com.aos.data.entity.response.home.GetCheckUserBookEntity
import com.aos.data.entity.response.settlement.GetBooksUsersEntity
import com.aos.data.entity.response.settlement.GetSettleUpLastEntity
import com.aos.data.entity.response.settlement.GetSettlementSeeEntity
import com.aos.data.entity.response.settlement.PostBooksOutcomesEntity
import com.aos.data.entity.response.settlement.PostNaverShortenUrlEntity
import com.aos.data.entity.response.settlement.PostSettlementAddEntity
import com.aos.util.NetworkState
import okhttp3.ResponseBody

interface BookRemoteDataSource {

    suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity>
    suspend fun getBooksMonth(bookKey: String, date: String): NetworkState<GetBookMonthEntity>
    suspend fun getBooksDays(bookKey: String, date: String): NetworkState<GetBookDaysEntity>
    suspend fun getBookInfo(bookKey: String): NetworkState<GetBookInfoEntity>
    suspend fun postBooksJoin(postBooksJoinBody : PostBooksJoinBody): NetworkState<PostBooksJoinEntity>
    suspend fun postBooksCreate(postBooksCreateBody : PostBooksCreateBody): NetworkState<PostBooksCreateEntity>
    suspend fun getBookCategory(bookKey: String, parent: String): NetworkState<List<GetBookCategoryEntity>>
    suspend fun postBooksLines(moneyData: PostBooksLinesBody): NetworkState<PostBooksLinesEntity>
    suspend fun postBooksLinesChange(moneyData: PostBooksChangeBody): NetworkState<PostBooksChangeEntity>

    suspend fun getSettlementLast(bookKey: String): NetworkState<GetSettleUpLastEntity>
    suspend fun getBooksUsers(bookKey: String): NetworkState<List<GetBooksUsersEntity>>
    suspend fun postBooksOutcomes(postBooksOutcomesBody : PostBooksOutcomesBody): NetworkState<List<PostBooksOutcomesEntity>>
    suspend fun postSettlementAdd(postSettlementAddBody : PostSettlementAddBody): NetworkState<PostSettlementAddEntity>
    suspend fun getSettlementSee(bookKey: String): NetworkState<List<GetSettlementSeeEntity>>
    suspend fun getSettlementDetailSee(id: Long): NetworkState<PostSettlementAddEntity>
    suspend fun getBooksInfo(bookKey: String): NetworkState<GetBooksInfoEntity>
    suspend fun postBooksName(postBooksNameBody: PostBooksNameBody): NetworkState<Void>
    suspend fun deleteBooks(bookKey: String): NetworkState<Void>
    suspend fun postBooksInfoSeeProfile(postBooksInfoSeeProfileBody : PostBooksInfoSeeProfileBody) : NetworkState<Void>
    suspend fun deleteBooksInfoAll(bookKey: String): NetworkState<Void>
    suspend fun postBooksInfoCurrency(postBooksInfoCurrency : PostBooksInfoCurrencyBody): NetworkState<PostBooksInfoCurrencyEntity>
    suspend fun getBooksInfoCurrency(bookKey : String): NetworkState<GetBooksInfoCurrencyEntity>
    suspend fun postBooksInfoAsset(postBooksInfoAssetBody: PostBooksInfoAssetBody): NetworkState<Void>
    suspend fun postBooksInfoCarryOver(postBooksInfoCarryOverBody: PostBooksInfoCarryOverBody): NetworkState<Void>
    suspend fun getBooksBudget(bookKey : String, date: String): NetworkState<GetBooksBudgetEntity>
    suspend fun postBooksInfoBudget(postBooksInfoBudgetBody: PostBooksInfoBudgetBody): NetworkState<Void>
    suspend fun deleteBookCategory(bookKey:String, deleteBookCategoryBody: DeleteBookCategoryBody): NetworkState<Void>
    suspend fun postBooksCategoryAdd(bookKey:String, postBooksCategoryAddBody: PostBooksCategoryAddBody): NetworkState<PostBooksCategoryAddEntity>
    suspend fun getBooksCode(bookKey:String): NetworkState<GetBooksCodeEntity>
    suspend fun getBooksRepeat(bookKey : String, categoryType : String): NetworkState<List<GetBookRepeatEntity>>
    suspend fun deleteBooksRepeat(repeatLineId:Int): NetworkState<Void>
    suspend fun postBooksExcel(postBooksExcelBody: PostBooksExcelBody): NetworkState<ResponseBody>
    suspend fun postBooksOut(postBooksOutBody: PostBooksOutBody): NetworkState<Void>
    suspend fun deleteBookLines(bookLineKey: String): NetworkState<Void>
    suspend fun getBooks(code: String): NetworkState<GetBooksEntity>
    suspend fun postShortenUrl(id: String, secretKey: String,url:String): NetworkState<PostNaverShortenUrlEntity>
    suspend fun deleteBookLinesAll(bookLineKey: Int): NetworkState<Void>
    suspend fun getBookFavorite(bookKey: String, categoryType: String): NetworkState<List<GetBookFavoriteEntity>>
    suspend fun deleteBookFavorite(bookKey:String, favoriteId: Int): NetworkState<Void>
    suspend fun postBooksFavorites(bookKey:String, postBooksFavoritesBody: PostBooksFavoritesBody): NetworkState<PostBookFavoriteEntity>
    suspend fun postChangeBookImg(postChangeBookImgBody: PostChangeBookImgBody): NetworkState<Void>
}