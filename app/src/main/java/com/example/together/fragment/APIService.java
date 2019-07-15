package com.example.together.fragment;

import com.example.together.notification.MyResponse;
import com.example.together.notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAjMWWtVI:APA91bFVgt1CuhihHU_ErDkpq24MiC7SN4ERW5UurfLLHoa938CRaWzl9Y6zNWGWtSVwuWXosQ-oMTbuMGiD66sn5r0JYgU033VT_Es8MbxPOItpt6BwjiKrU14bfkX6XbiS4Hfb33iH"
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
