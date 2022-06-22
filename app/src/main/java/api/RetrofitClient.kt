package api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val URL = "http://stmapi-env-1.eba-ihzgwh9t.us-east-1.elasticbeanstalk.com/"

    fun getRetrofitInstance(): Retrofit{
        return Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val service: APIServices = getRetrofitInstance().create(APIServices::class.java)
}