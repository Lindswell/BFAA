package com.example.bfaa_submission_1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class User (
    var name: String? = null,
    var html: String? = null,
    var username: String? = null,
    var location: String? = null,
    var repository: String? = null,
    var company: String? = null,
    var follower: String? = null,
    var following: String? = null,
    var photo: String? = null,
    var following_num: String? = null,
    var followers_num: String? = null
): Parcelable