package com.example.bfaa_submission_1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class ProfileViewModel : ViewModel() {

    private val detailUser =  MutableLiveData<User>()
    private val dataFollowers = MutableLiveData<ArrayList<User>>()
    private val dataFollowing = MutableLiveData<ArrayList<User>>()

    fun setDetailUser(username: String) {

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"

        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {

                /*Log.d(TAG, result)*/
                val result = String(responseBody)
                Log.d("ViewModel", result)
                try {
                    val jsonObject = JSONObject(result)
                    val gitUserDetail = User()
                    gitUserDetail.name = jsonObject.getString("login")
                    gitUserDetail.html = jsonObject.getString("html_url")
                    gitUserDetail.photo = jsonObject.getString("avatar_url")
                    gitUserDetail.repository = jsonObject.getString("public_repos")
                    gitUserDetail.followers_num = jsonObject.getString("followers")
                    gitUserDetail.following_num = jsonObject.getString("following")
//                        detail.add(gitUserDetail)
                    detailUser.postValue(gitUserDetail)

                } catch (e:Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getDetailUser() : LiveData<User> {
        return detailUser
    }


    fun setFollowers(username: String?) {
        val followers = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"

        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object :AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {

                try {
                    val result = String(responseBody)
                    Log.d("ViewModel", result)
                    val responseObject= JSONArray(result)
                    for (i in 0 until responseObject.length()) {
                        val jsonObject = responseObject.getJSONObject(i)
                        val userFollowers = User()
                        userFollowers.name = jsonObject.getString("login")
                        userFollowers.photo = jsonObject.getString("avatar_url")
                        userFollowers.html = jsonObject.getString("html_url")

                        followers.add(userFollowers)
                    }
                    dataFollowers.postValue(followers)
                } catch (e:Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })

    }

    fun getFollowers(): LiveData<ArrayList<User>> {
        return dataFollowers
    }

    fun setFollowing(username: String?) {

        val following = ArrayList<User>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"

        client.addHeader("Authorization", "token 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")
        client.addHeader("User-Agent", "request")

        client.get(url, object :AsyncHttpResponseHandler(){
            override fun onSuccess(statusCode: Int, headers: Array<out Header>, responseBody: ByteArray) {

                try {
                    val result = String(responseBody)
                    Log.d("ViewModel", result)
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userFollowing = User()
                        userFollowing.name = jsonObject.getString("login")
                        userFollowing.photo = jsonObject.getString("avatar_url")
                        userFollowing.html = jsonObject.getString("html_url")

                        following.add(userFollowing)
                    }
                    dataFollowing.postValue(following)
                } catch (e:Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
                Log.d("onFailure", error.message.toString())
            }
        })

    }

    fun getFollowing(): LiveData<ArrayList<User>> {
        return dataFollowing
    }
}