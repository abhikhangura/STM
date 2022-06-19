package api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL = "http://stmapi-env-1.eba-ihzgwh9t.us-east-1.elasticbeanstalk.com/"

    private val retrofitBuilder: Retrofit = Retrofit.Builder().
        addConverterFactory(GsonConverterFactory.create()).
        baseUrl(URL).
        build()

    val service: APIServices = retrofitBuilder.create(APIServices::class.java)
}