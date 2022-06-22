package api

import model.*
import retrofit2.Call
import retrofit2.http.*

interface APIServices {

    // authenticate user for login
    @FormUrlEncoded
    @POST("verifyUser")
    fun verifyUser(@Field("email") email: String,
                   @Field("password") password: String):Call<LoginResponse>

    @FormUrlEncoded
    @POST("registerUser")
    fun registerUser(@Field("name") name: String,
                     @Field("email") email: String,
                     @Field("password") password: String,
                     @Field("phoneNumber") phoneNumber: String,
                     @Field("city") city: String,
                     @Field("state") state: String,
                     @Field("street") street: String,
                     @Field("pin") pin :String,
                     @Field("access") access: Boolean):Call<RegisterResponse>


    // fetching user profile from database
    @FormUrlEncoded
    @POST("user")
    fun getUser(@Field("email") email: String):Call<UserItem>

    @FormUrlEncoded
    @POST("cardDetails")
    fun getCardDetails(@Field("email") email: String):Call<CardDetails>

    // fetching all the available plans
    @POST("getPlans")
    fun getPlans():Call<List<Plans>>

    @FormUrlEncoded
    @POST("getTransactions")
    fun getTransactions(@Field("cardNumber") cardNumber: String): Call<List<Transactions>>

}