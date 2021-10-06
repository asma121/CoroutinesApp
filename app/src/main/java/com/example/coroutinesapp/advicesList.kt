package com.example.coroutinesapp

import com.google.gson.annotations.SerializedName
class advicesList{
    @SerializedName("slip")
    var slip1:slip? = null
    class slip{
        @SerializedName("advice")
        var advice: String? = null
    }

}


