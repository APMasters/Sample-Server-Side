package mir.amirparsa.server_side.request;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import mir.amirparsa.server_side.Global;
import mir.amirparsa.server_side.utils.WebService;

/**
 * Created by Amir Parsa Mir
 * Call me : amirparsa.mir@gmail.com
 */

public class GetMusicsRequest {

  private static GetMusicsRequest request;
  private WebService webService;
  private Gson gson;
  private Context context;
  private String response;

  public static GetMusicsRequest getInstance(Context context) {

    if (request == null) {
      request = new GetMusicsRequest(context);
    }
    return request;

  }

  private GetMusicsRequest(Context context) {
    this.context = context;
  }


  public void sendRequest() {

    webService = getWebService();

    WebService.Listener listener = new WebService.Listener() {

      @Override
      public void onDataReceive(String serverResponse) {

        Log.d(Global.LOG, "Get Music Request -> Response :\n" + serverResponse);

        if (!serverResponse.equals(Global.TAG_NO_NET)) {

          gson = getGson();

          Response data = gson.fromJson(serverResponse, Response.class);

          if (data.code.equals("OK")) {

            Global.musics = data.musics;

            setResponse("OK");

          } else {
            setResponse(data.code);
          }
        } else {
          setResponse(Global.TAG_NO_NET);
        }

      }
    };

    gson = getGson();

    PostParams params = new PostParams(Global.TOKEN);

    String json = gson.toJson(params);

    Log.d(Global.LOG, "Get Music Request -> Post params :\n" + json);

    webService
      .url(Global.URL_GET_MUSICS)
      .listener(listener)
      .jsonPost(json)
      .getData(context);

  }

  public String getResponse() {
    return this.response;
  }

  private void setResponse(String str) {
    this.response = str;
  }

  private class PostParams {

    private String token;

    PostParams(String token) {
      this.token = token;
    }

  }

  private class Response {

    String code;
    List<Global.Music> musics;

  }

  private WebService getWebService() {
    if (webService == null) {
      webService = new WebService();
    }
    return webService;
  }

  private Gson getGson() {
    if (gson == null) {
      gson = new Gson();
    }
    return gson;
  }


}
