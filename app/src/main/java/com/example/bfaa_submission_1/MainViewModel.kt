package com.example.bfaa_submission_1

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class MainViewModel: ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val listGitUser = MutableLiveData<ArrayList<User>>()
    private val listGitUserSearch = MutableLiveData<ArrayList<User>>()


    fun setUserList(username: String) {
        val list = ArrayList<User>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$username"

        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object :AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {

                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")
                    for (i in 0 until items.length()) {
                        val jsonObject = items.getJSONObject(i)
                        val gitUser = User()
                        gitUser.name = jsonObject.getString("login")
                        gitUser.html = jsonObject.getString("html_url")
                        gitUser.photo = jsonObject.getString("avatar_url")
                        list.add(gitUser)
                    }
                    listGitUser.postValue(list)
                } catch (e:Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getUserList() : LiveData<ArrayList<User>> {
        return listGitUser
    }

//    fun setUserListSearch(username: String) {
//        val listSearch = ArrayList<User>()
//
//
//        val client = AsyncHttpClient()
//        val url = "https://api.github.com/users/$username"
//
//        client.addHeader("Authorization", "token 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")
//        client.addHeader("User-Agent", "request")
//
//        client.get(url, object :AsyncHttpResponseHandler(){
//            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
//
//                /*Log.d(TAG, result)*/
//                try {
//                    val result = String(responseBody)
//                    val jsonArray = JSONArray(result)
//
//                    for (i in 0 until jsonArray.length()) {
//                        val jsonObject = jsonArray.getJSONObject(i)
//                        val gitUser = User()
//                        gitUser.name = jsonObject.getString("login")
//                        gitUser.html = jsonObject.getString("html_url")
//                        gitUser.photo = jsonObject.getString("avatar_url")
//
//                        listSearch.add(gitUser)
//                    }
//                } catch (e:Exception) {
//                    Log.d("Exception", e.message.toString())
//                }
//                listGitUserSearch.postValue(listSearch)
//            }
//
//            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
//                Log.d("onFailure", error.message.toString())
//            }
//
//        })
//    }
//
//    fun getUserListSearch() : MutableLiveData<ArrayList<User>> {
//        return listGitUserSearch
//    }
}