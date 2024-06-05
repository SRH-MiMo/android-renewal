import com.example.mimo.data.RequestData
import com.example.mimo.data.ResponseData
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("model")
    suspend fun sendDream(@Body requestData: RequestData): ResponseData
}
