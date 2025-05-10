package com.omooooori.feature.userlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omooooori.data.mapper.toModel
import com.omooooori.domain.FetchUsersUseCase
import com.omooooori.model.GithubUser

class GithubUserPagingSource(
    private val fetchUsersUseCase: FetchUsersUseCase,
) : PagingSource<Int, GithubUser>() {
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        return try {
            val page = params.key ?: 1
            val responseDto = fetchUsersUseCase.execute(page)
            val response = responseDto.map { it.toModel() }

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
