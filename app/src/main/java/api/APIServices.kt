package api

import androidx.annotation.FractionRes
import model.*
import retrofit2.Call
import retrofit2.http.*

interface APIServices {

    // authenticate user for login
    @FormUrlEncoded
    @POST("verifyUser")
    fun verifyUser(@Field("email") email: String,
                   @Field("password") password: String):Call<LoginResponse>

    //register new user
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
                     @Field("access") access: Boolean): Call<RegisterResponse>


    // fetching user profile from database
    @FormUrlEncoded
    @POST("user")
    fun getUser(@Field("email") email: String): Call<UserItem>

    // fetch card details
    @FormUrlEncoded
    @POST("cardDetails")
    fun getCardDetails(@Field("email") email: String): Call<CardDetails>

    // fetching all the available plans
    @POST("getPlans")
    fun getPlans():Call<List<Plans>>

    //get all transactions
    @FormUrlEncoded
    @POST("getTransactions")
    fun getTransactions(@Field("cardNumber") cardNumber: String): Call<List<Transactions>>

    // create a new transactions
    @FormUrlEncoded
    @POST("newTransaction")
    fun createTransaction(@Field("amount") amount: String?,
                          @Field("email") email: String,
                          @Field("cardNumber") cardNumber: String): Call<TransactionResponse>


    //create a new card
    @FormUrlEncoded
    @POST("createCard")
    fun createCard(@Field("cardNumber") cardNumber: String,
                   @Field("email") email: String): Call<CardResponse>


}