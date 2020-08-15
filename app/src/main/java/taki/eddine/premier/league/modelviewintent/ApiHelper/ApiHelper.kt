package taki.eddine.premier.league.modelviewintent.ApiHelper

import retrofit2.http.GET
import taki.eddine.premier.league.modelviewintent.model.User

interface ApiHelper {

    @GET("users")
    suspend fun getUsers(): List<User>

}