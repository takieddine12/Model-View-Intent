package taki.eddine.premier.league.modelviewintent.ApiHelper

import taki.eddine.premier.league.modelviewintent.model.User

class ApiHelperImp(var apiHelper: ApiHelper) : ApiHelper{
    override suspend fun getUsers(): List<User> {
        return apiHelper.getUsers()
    }
}