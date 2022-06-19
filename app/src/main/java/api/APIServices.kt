package api

import model.LoginResponse
import model.Plans
import model.UserItem
import retrofit2.Call
import retrofit2.http.*

interface APIServices {

    // authenticate user for login
    @FormUrlEncoded
    @POST("verifyUser")
    fun verifyUser(@Field("username") username: String,
                   @Field("password") password: String
    ):Call<LoginResponse>

    // fetching user profile from database
    @GET("user/{name}")
    fun getUser(@Path("name") name: String):Call<UserItem>

    // fetching all the available plans
    @GET("getPlans")
    fun getPlans():Call<Plans>


}