package taki.eddine.premier.league.modelviewintent.MvvmPackage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import taki.eddine.premier.league.modelviewintent.ApiHelper.ApiHelper

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Repository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
