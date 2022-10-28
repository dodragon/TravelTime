package com.dod.data.util

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dod.data.api.TravelApi
import com.dod.data.model.TravelModel
import retrofit2.HttpException
import java.io.IOException

class TravelPagingSource(
    private val groupId: Long,
    private val travelApiService: TravelApi
) : PagingSource<Int, TravelModel>() {

    override fun getRefreshKey(state: PagingState<Int, TravelModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TravelModel> {
        return try{
            val page = params.key ?: 0

            val response = travelApiService.getTravelList(
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