package com.waarstreamt.het.data.repository

import com.waarstreamt.het.data.api.WaarStreamtApi
import com.waarstreamt.het.data.model.ContentDetail
import com.waarstreamt.het.data.model.MockData
import com.waarstreamt.het.data.model.SearchResult
import javax.inject.Inject
import javax.inject.Singleton

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String, val cause: Throwable? = null) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}

// TODO: Replace CSV-based mock data with real API calls
// API endpoints (when backend is live):
//   GET /search?q={query} → List<SearchResult>
//   GET /content/{id}     → ContentDetail
// Set useMockData = false and update BASE_URL in build.gradle.kts

@Singleton
class ContentRepository @Inject constructor(
    private val api: WaarStreamtApi,
    private val useMockData: Boolean = false, // flip to true for offline demo
) {

    suspend fun search(query: String): Result<List<SearchResult>> {
        if (query.length < 2) return Result.Success(emptyList())
        return try {
            if (useMockData) {
                val filtered = MockData.searchResults.filter {
                    it.title.contains(query, ignoreCase = true)
                }
                Result.Success(filtered)
            } else {
                Result.Success(api.search(query))
            }
        } catch (e: Exception) {
            Result.Error("Zoeken mislukt. Controleer je verbinding.", e)
        }
    }

    suspend fun getDetail(id: String): Result<ContentDetail> {
        return try {
            if (useMockData) {
                val detail = MockData.details[id]
                    ?: return Result.Error("Niet gevonden")
                Result.Success(detail)
            } else {
                Result.Success(api.getDetail(id))
            }
        } catch (e: Exception) {
            Result.Error("Kon details niet laden.", e)
        }
    }
}
