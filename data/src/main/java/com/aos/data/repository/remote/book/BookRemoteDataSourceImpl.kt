package com.aos.data.repository.remote.book

import com.aos.data.api.BookService
import com.aos.data.api.NaverShortenUrlService
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
import javax.inject.Inject

class BookRemoteDataSourceImpl @Inject constructor(private val bookService: BookService, private val naverShortenUrlService: NaverShortenUrlService): BookRemoteDataSource {

    override suspend fun getCheckUserBook(): NetworkState<GetCheckUserBookEntity> {
        return bookService.getCheckUserBook()
    }

    override suspend fun getBooksMonth(bookKey: String, date: String): NetworkState<GetBookMonthEntity> {
        return bookService.getBookMonth(bookKey, date)
    }

    override suspend fun getBooksDays(
        bookKey: String,
        date: String,
    ): NetworkState<GetBookDaysEntity> {
        return bookService.getBookDays(bookKey, date)
    }

    override suspend fun getBookInfo(bookKey: String): NetworkState<GetBookInfoEntity> {
        return bookService.getBookInfo(bookKey)
    }
    override suspend fun postBooksJoin(
        postBooksJoinBody : PostBooksJoinBody
    ): NetworkState<PostBooksJoinEntity> {
        return bookService.postBooksJoin(postBooksJoinBody)
    }
    override suspend fun postBooksCreate(
        postBooksCreateBody : PostBooksCreateBody
    ): NetworkState<PostBooksCreateEntity> {
        return bookService.postBooksCreate(postBooksCreateBody)
    }

    override suspend fun getBookCategory(
        bookKey: String,
        parent: String,
    ): NetworkState<List<GetBookCategoryEntity>> {
        return bookService.getBooksCategory(bookKey, parent)
    }

    override suspend fun postBooksLines(moneyData: PostBooksLinesBody): NetworkState<PostBooksLinesEntity> {
        return bookService.postBooksLines(moneyData)
    }

    override suspend fun postBooksLinesChange(moneyData: PostBooksChangeBody): NetworkState<PostBooksChangeEntity> {
        return bookService.postBooksLinesChange(moneyData)
    }

    override suspend fun getSettlementLast(
        bookKey: String
    ): NetworkState<GetSettleUpLastEntity> {
        return bookService.getSettlementLast(bookKey)
    }
    override suspend fun getBooksUsers(
        bookKey: String
    ): NetworkState<List<GetBooksUsersEntity>> {
        return bookService.getBooksUsers(bookKey)
    }
    override suspend fun postBooksOutcomes(
        postBooksOutcomesBody : PostBooksOutcomesBody
    ): NetworkState<List<PostBooksOutcomesEntity>> {
        return bookService.postBooksOutcomes(postBooksOutcomesBody)
    }
    override suspend fun postSettlementAdd(
        postSettlementAddBody : PostSettlementAddBody
    ): NetworkState<PostSettlementAddEntity> {
        return bookService.postSettlementAdd(postSettlementAddBody)
    }
    override suspend fun getSettlementSee(
        bookKey: String
    ): NetworkState<List<GetSettlementSeeEntity>> {
        return bookService.getSettlementSee(bookKey)
    }
    override suspend fun getSettlementDetailSee(
        id : Long
    ): NetworkState<PostSettlementAddEntity> {
        return bookService.getSettlementDetailSee(id)
    }
    override suspend fun getBooksInfo(
        bookKey: String
    ): NetworkState<GetBooksInfoEntity> {
        return bookService.getBooksInfo(bookKey)
    }
    override suspend fun postBooksName(
        postBooksNameBody: PostBooksNameBody
    ): NetworkState<Void> {
        return bookService.postBooksName(postBooksNameBody)
    }
    override suspend fun deleteBooks(
        bookKey: String
    ): NetworkState<Void> {
        return bookService.deleteBooks(bookKey)
    }

    override suspend fun postBooksInfoSeeProfile(
        postBooksInfoSeeProfileBody : PostBooksInfoSeeProfileBody
    ): NetworkState<Void> {
        return bookService.postBooksInfoSeeProfile(postBooksInfoSeeProfileBody)
    }

    override suspend fun deleteBooksInfoAll(
        bookKey: String
    ): NetworkState<Void> {
        return bookService.deleteBooksInfoAll(bookKey)
    }

    override suspend fun postBooksInfoCurrency(
        postBooksInfoCurrency : PostBooksInfoCurrencyBody
    ): NetworkState<PostBooksInfoCurrencyEntity> {
        return bookService.postBooksInfoCurrency(postBooksInfoCurrency)
    }

    override suspend fun getBooksInfoCurrency(
        bookKey : String
    ): NetworkState<GetBooksInfoCurrencyEntity> {
        return bookService.getBooksInfoCurrency(bookKey)
    }

    override suspend fun postBooksInfoAsset(
        postBooksInfoAssetBody: PostBooksInfoAssetBody
    ): NetworkState<Void> {
        return bookService.postBooksInfoAsset(postBooksInfoAssetBody)
    }

    override suspend fun postBooksInfoCarryOver(
        postBooksInfoCarryOverBody: PostBooksInfoCarryOverBody
    ): NetworkState<Void> {
        return bookService.postBooksInfoCarryOver(postBooksInfoCarryOverBody)
    }

    override suspend fun getBooksBudget(
        bookKey : String,
        date : String
    ): NetworkState<GetBooksBudgetEntity> {
        return bookService.getBooksBudget(bookKey, date)
    }

    override suspend fun postBooksInfoBudget(
        postBooksInfoBudgetBody: PostBooksInfoBudgetBody
    ): NetworkState<Void> {
        return bookService.postBooksInfoBudget(postBooksInfoBudgetBody)
    }

    override suspend fun deleteBookCategory(
        bookKey: String,
        deleteBookCategoryBody: DeleteBookCategoryBody
    ): NetworkState<Void> {
        return bookService.deleteBookCategory(bookKey, deleteBookCategoryBody)
    }

    override suspend fun postBooksCategoryAdd(
        bookKey: String,
        postBooksCategoryAddBody: PostBooksCategoryAddBody
    ): NetworkState<PostBooksCategoryAddEntity> {
        return bookService.postBooksCategoryAdd(bookKey, postBooksCategoryAddBody)
    }

    override suspend fun getBooksCode(
        bookKey: String
    ): NetworkState<GetBooksCodeEntity> {
        return bookService.getBooksCode(bookKey)
    }

    override suspend fun getBooksRepeat(
        bookKey: String,
        categoryType: String
    ): NetworkState<List<GetBookRepeatEntity>> {
        return bookService.getBooksRepeat(bookKey, categoryType)
    }

    override suspend fun deleteBooksRepeat(
        repeatLineId: Int
    ): NetworkState<Void> {
        return bookService.deleteBooksRepeat(repeatLineId)
    }

    override suspend fun postBooksExcel(postBooksExcelBody: PostBooksExcelBody): NetworkState<ResponseBody> {
        return bookService.postBooksExcel(postBooksExcelBody)
    }

    override suspend fun postBooksOut(
        postBooksOutBody: PostBooksOutBody
    ): NetworkState<Void> {
        return bookService.postBooksOut(postBooksOutBody)
    }

    override suspend fun deleteBookLines(bookLineKey: String): NetworkState<Void> {
        return bookService.deleteBookLines(bookLineKey)
    }

    override suspend fun getBooks(code: String): NetworkState<GetBooksEntity> {
        return bookService.getBooks(code)
    }

    override suspend fun postShortenUrl(
        id: String,
        secretKey: String,
        url: String
    ): NetworkState<PostNaverShortenUrlEntity> {
        return naverShortenUrlService.postShortenUrl(id, secretKey, url)
    }
        
    override suspend fun deleteBookLinesAll(bookLineKey: Int): NetworkState<Void> {
        return bookService.deleteBooksLineAll(bookLineKey)
    }

    override suspend fun getBookFavorite(
        bookKey: String,
        categoryType: String
    ): NetworkState<List<GetBookFavoriteEntity>> {
        return bookService.getBookFavorite(bookKey, categoryType)
    }

    override suspend fun deleteBookFavorite(
        bookKey: String,
        favoriteId : Int
    ): NetworkState<Void> {
        return bookService.deleteBookFavorite(bookKey, favoriteId)
    }

    override suspend fun postBooksFavorites(
        bookKey: String,
        postBooksFavoritesBody : PostBooksFavoritesBody
    ): NetworkState<PostBookFavoriteEntity> {
        return bookService.postBooksFavorites(bookKey, postBooksFavoritesBody)
    }

    override suspend fun postChangeBookImg(postChangeBookImgBody: PostChangeBookImgBody): NetworkState<Void> {
        return bookService.postChangeBookImg(postChangeBookImgBody)
    }
}