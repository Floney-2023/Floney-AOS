package com.aos.repository

import com.aos.model.book.PostBooksChangeModel
import com.aos.model.book.PostBooksCreateModel
import com.aos.model.book.PostBooksJoinModel
import com.aos.model.book.PostBooksLinesModel
import com.aos.model.book.UiBookCategory
import com.aos.model.book.UiBookSettingModel
import com.aos.model.home.GetCheckUserBookModel
import com.aos.model.home.UiBookDayModel
import com.aos.model.home.UiBookInfoModel
import com.aos.model.home.UiBookMonthModel
import com.aos.model.settlement.GetSettlementLastModel
import com.aos.model.settlement.UiMemberSelectModel
import com.aos.model.settlement.UiOutcomesSelectModel
import com.aos.model.settlement.UiSettlementAddModel
import com.aos.model.settlement.UiSettlementSeeModel
import com.aos.model.settlement.settleOutcomes
import java.time.Duration

interface BookRepository {

    // 유저 가계부 유효 확인
    suspend fun getCheckUserBook(): Result<GetCheckUserBookModel>

    // 캘린더 조회
    suspend fun getBooksMonth(bookKey: String, date: String): Result<UiBookMonthModel>

    // 일별 내역 조회
    suspend fun getBooksDays(bookKey: String, date: String): Result<UiBookDayModel>

    // 가계부 정보 조회
    suspend fun getBookInfo(bookKey: String): Result<UiBookInfoModel>

    // 가계부 참여
    suspend fun postBooksJoin(code: String): Result<PostBooksJoinModel>

    // 가계부 생성
    suspend fun postBooksCreate(name: String, profileImg: String): Result<PostBooksCreateModel>

    // 가계부 생성
    suspend fun getBookCategory(bookKey: String, parent: String): Result<List<UiBookCategory>>

    // 가계부 내역 추가
    suspend fun postBooksLines(
        bookKey: String,
        money: Int,
        flow: String,
        asset: String,
        line: String,
        lineDate: String,
        description: String,
        except: Boolean,
        nickname: String,
        repeatDuration: String
    ): Result<PostBooksLinesModel>

    // 가계부 내역 수정
    suspend fun postBooksLinesChange(
        lineId: Int,
        bookKey: String,
        money: Int,
        flow: String,
        asset: String,
        line: String,
        lineDate: String,
        description: String,
        except: Boolean,
        nickname: String,
    ): Result<PostBooksChangeModel>

    // 가계부의 마지막 정산일 조회
    suspend fun getSettlementLast(bookKey: String): Result<GetSettlementLastModel>

    // 특정 가계부의 유저들 조회
    suspend fun getBooksUsers(bookKey: String): Result<UiMemberSelectModel>

    // 정산 지출 내역 조회
    suspend fun postBooksOutcomes(usersEmails : List<String>, startDate : String, endDate : String, bookKey: String) : Result<UiOutcomesSelectModel>

    // 정산 추가
    suspend fun postSettlementAdd(bookKey: String, startDate : String, endDate : String, usersEmails : List<String>, outcomes: List<settleOutcomes>) : Result<UiSettlementAddModel>

    // 가계부의 정산 내역 조회
    suspend fun getSettlementSee(bookKey: String): Result<UiSettlementSeeModel>

    // 정산 세부 내역 조회
    suspend fun getSettlementDetailSee(id: Long): Result<UiSettlementAddModel>

    // 가계부 설정 조회하기
    suspend fun getBooksInfo(bookKey: String): Result<UiBookSettingModel>

    // 가계부 이름 변경
    suspend fun postBooksName(name: String, bookKey: String): Result<Void?>

    // 가계부 삭제
    suspend fun deleteBooks(bookKey: String): Result<Void?>

    // 내역 프로필 보기 설정
    suspend fun postBooksInfoSeeProfile(bookKey: String, seeProfileStatus: Boolean): Result<Void?>
}