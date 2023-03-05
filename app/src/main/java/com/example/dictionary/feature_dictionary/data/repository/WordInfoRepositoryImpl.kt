package com.example.dictionary.feature_dictionary.data.repository

import com.example.dictionary.core.util.Resource
import com.example.dictionary.feature_dictionary.data.local.WordInfoDao
import com.example.dictionary.feature_dictionary.data.remote.DictionaryApi
import com.example.dictionary.feature_dictionary.domain.model.WordInfo
import com.example.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class WordInfoRepositoryImpl(
    private val api: DictionaryApi,
    private val dao: WordInfoDao
) :
    WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfo = dao.getWordInfo(word = word).map { it.toWordInfo() }

        emit(Resource.Loading(data = wordInfo))

        try {
            val remoteWordInfo = api.getWordInfo(word)

            dao.deleteWordInfo(remoteWordInfo.map { it.word })

            dao.insertWordInfo(remoteWordInfo.map { it.toWordInfoEntity() })

        } catch (e: HttpException) { // If we got an invalid response
            emit(
                Resource.Error(
                    message = "Oops, something went wrong!", data = wordInfo
                )
            )

        } catch (e: IOException) { // If the parsing went wrong
            emit(
                Resource.Error(
                    message = "Couldn't reach server, check your internet connection.",
                    data = wordInfo
                )
            )
        }

        val newWordInfo = dao.getWordInfo(word).map { it.toWordInfo() }

        emit(Resource.Success((newWordInfo)))
    }
}