package com.omooooori.feature.userlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omooooori.data.mapper.toModel
import com.omooooori.domain.FetchUsersUseCase
import com.omooooori.model.GithubUser

class GithubUserPagingSource(
    private val fetchUsersUseCase: FetchUsersUseCase,
    private val query: String,
) : PagingSource<Int, GithubUser>() {
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        val page = params.key ?: 1
        return try {
            val responseDto = fetchUsersUseCase.execute(page)
            val allUsers = responseDto.map { it.toModel() }
            val filteredUsers =
                if (query.isNotBlank()) {
                    allUsers.filter {
                        it.username.contains(query, ignoreCase = true)
                    }
                } else {
                    allUsers
                }
            val nextKey =
                when {
                    responseDto.size < PAGE_SIZE -> null
                    query.isNotEmpty() -> null
                    else -> page + 1
                }
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(
                data = filteredUsers,
                prevKey = prevKey,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val PAGE_SIZE = 30
    }
}
