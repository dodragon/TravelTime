package com.dod.data.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dod.data.api.UserApi
import com.dod.data.model.UserModel
import retrofit2.HttpException
import java.io.IOException

class UserPagingSource(
    private val groupId: Long,
    private val userApiService: UserApi
) : PagingSource<Int, UserModel>() {

    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {
        return try{
            val page = params.key ?: 0

            val response = userApiService.groupUserList(
                groupId,
                page,
                30
            )

            val groupList = response.body() ?: listOf()

            val prevKey = if(page == 0) null else page - 1
            val nextKey = if(groupList.isEmpty()) null else page + 1

            LoadResult.Page(
                data = groupList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }catch (e: IOException) {
            LoadResult.Error(e)
        }catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}