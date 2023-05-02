package api.service

const val API_KEY = "4522d8bc-6718-4f11-b449-7b37cdff4577"
object DBClient {

    fun getClient() : KPApiService = KPApiService(API_KEY)
}