package com.example.bfaa_submission_1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    companion object {
        private val TAG = MainViewModel::class.java.simpleName
    }

    private val listGitUser = MutableLiveData<ArrayList<User>>()

    fun setUserList(username: String) {
        ApiConfig.getApiService()
            .getSearch(username)
            .enqueue(object :Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        listGitUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }

            })
    }

    fun getUserList() : LiveData<ArrayList<User>> {
        return listGitUser
    }



    /*fun setUserList(username: String) {
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
                        gitUser.username = jsonObject.getString("login")
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
    }*/
}