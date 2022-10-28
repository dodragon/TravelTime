package com.dod.data.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dod.data.api.GroupApi
import com.dod.data.model.GroupModel
import retrofit2.HttpException
import java.io.IOException

class GroupPagingSource(
    private val userIdx: Long,
    private val groupApiService: GroupApi
) : PagingSource<Int, GroupModel>() {

    override fun getRefreshKey(state: PagingState<Int, GroupModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GroupModel> {
        return try{
            val page = params.key ?: 0

            val response = groupApiService.getMainGroups(
                userIdx,
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