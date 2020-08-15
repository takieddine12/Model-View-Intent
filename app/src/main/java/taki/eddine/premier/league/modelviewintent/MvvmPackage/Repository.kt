package taki.eddine.premier.league.modelviewintent.MvvmPackage

import taki.eddine.premier.league.modelviewintent.ApiHelper.ApiHelper

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getList() = apiHelper.getUsers()
}