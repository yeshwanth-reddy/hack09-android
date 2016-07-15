package com.stayzilla.postbooking.network;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestServiceGenerator {
    private static int HTTP_CONNECTION_TIMEOUT = 15; // Seconds
    private static int HTTP_READ_TIMEOUT = 15; // Seconds

    private RestServiceGenerator() {
    }

    public static <T> T createRestApiService(Class<T> serviceClass, String baseUrl) {

        // Create a custom Gson object with exclusion strategy to exclude the ModelAdapter.class
        // present in the BaseModel class in DBFlow library. We do not need to serialize/deserialize
        // ModelAdapter objects, including this will lead to StackOverflow errors in Gson library
        // https://github.com/Raizlabs/DBFlow/issues/121
        Gson gson = new GsonBuilder().setLenient()
                //To parse null string values as ""
//                .registerTypeAdapterFactory(new JSONParseHelper.NullStringToEmptyAdapterFactory())
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return false; // f.getDeclaredClass().equals(ModelAdapter.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        // Add StethoInterceptor for OkHttpClient for intercepting network calls
        /*if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());

            // Show log for debug build
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            okHttpBuilder.addInterceptor(logging);
        }*/

//        // Add custom cookie handler
//        SZCookieManager szCookieManager = new SZCookieManager();

        okHttpBuilder.connectTimeout(HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                /*.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        String cookieData = "szsso=" + AppUtil.getToken() + ";";
                        cookieData += AppUtil.getCookieData(SharedPreferencesHelper.KEY_INASRA_STCMIN);
                        cookieData += ";";

                        String value = SharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_INASRA_STCMIN);
                        if (!value.isEmpty()) {
                            cookieData += SharedPreferencesHelper.KEY_INASRA_USESSION + "=";
                            cookieData += value;
                        }
                        //cookieData += AppUtil.getCookieData(SharedPreferencesHelper.KEY_INASRA_USESSION);

                        Request request = chain.request();
                        Request newRequest;
                        newRequest = request.newBuilder().addHeader("Cookie", cookieData).build();
                        return chain.proceed(newRequest);
                    }
                }*/;
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpBuilder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return builder.create(serviceClass);
    }


}