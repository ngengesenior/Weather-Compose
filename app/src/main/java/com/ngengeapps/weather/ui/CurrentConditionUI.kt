import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ngengeapps.weather.R
import com.ngengeapps.weather.data.models.Weather
import com.ngengeapps.weather.data.models.WeatherData
import com.ngengeapps.weather.ui.BoldText
import com.ngengeapps.weather.ui.WeatherIcon
import kotlin.math.roundToInt


@Composable
fun CurrentConditionUI(weatherData: WeatherData){
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        MainConditionUI(weather = weatherData.weather[0],modifier = Modifier.fillMaxWidth())
        Text(text = stringResource(R.string.temperature,weatherData.temperature.roundToInt()) ,fontWeight = FontWeight.Bold,textAlign = TextAlign.Center,fontSize = 40.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = stringResource(R.string.feels_like,weatherData.feelsLike.roundToInt()) ,style = MaterialTheme.typography.caption,fontSize = 10.sp)
        Spacer(modifier = Modifier.height(45.dp))
        GeneralWeatherInfoUI(currentWeather = weatherData)
    }

}

@Composable
fun MainConditionUI(weather: Weather, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.Top) {
        WeatherIcon(icon = weather.icon, size = 60.dp )
        Column(modifier = Modifier.padding(top = 10.dp)) {
            Text(text = weather.main,fontWeight = FontWeight.SemiBold)
            Text(text = weather.description,style = MaterialTheme.typography.caption)
        }
    }
}




@Composable
fun GeneralWeatherInfoUI(currentWeather: WeatherData) {
    Surface(
        shape = RoundedCornerShape(8.dp), color = Color.LightGray,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 8.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BoldText(
                    text = stringResource(
                        R.string.wind_text,
                        currentWeather.windSPeed,
                        currentWeather.windDegree
                    )
                )
                BoldText(text = stringResource(R.string.humidity, currentWeather.humidity) + "%")
                BoldText(text = stringResource(R.string.uv_index, currentWeather.uvIndex))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BoldText(text = stringResource(R.string.pressure_pascal, currentWeather.pressure))
                BoldText(
                    text = stringResource(
                        R.string.visibility_km,
                        currentWeather.visibility.div(1000)
                    )
                )
                BoldText(text = stringResource(R.string.dew_point_celcius, currentWeather.dewPoint))
            }


        }
    }
}