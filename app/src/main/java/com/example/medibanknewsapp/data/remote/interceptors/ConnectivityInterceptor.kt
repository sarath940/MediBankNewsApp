
import android.content.Context
import com.example.medibanknewsapp.R
import com.example.medibanknewsapp.util.Utilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ConnectivityInterceptor @Inject constructor(val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Utilities.hasInternetConnection(context)) {
            throw IOException(context.getString(R.string.check_ur_internet_connection))
        }
        return chain.proceed(chain.request())
    }
}