package mir.amirparsa.server_side.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

import mir.amirparsa.server_side.Global;

/**
 * Created by Amir Parsa Mir
 * Call me : amirparsa.mir@gmail.com
 */

public class WebService {

  private String url;
  private String jsonPost;
  private Listener listener;
  private RequestQueue queue = null;

  public interface Listener {

    void onDataReceive(String data);

  }

  public WebService url(String value) {
    url = value;
    return this;
  }

  public WebService jsonPost(String value) {
    jsonPost = value;
    return this;
  }

  public WebService listener(Listener value) {
    listener = value;
    return this;
  }

  private RequestQueue getRequestQueue(Context context) {
    if (queue == null) {
      queue = Volley.newRequestQueue(context);
    }
    return queue;
  }

  private boolean checkNetState(Context context) {

    return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null &&
      ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo().isConnected();
  }

  public void getData(Context context) {

    if (checkNetState(context)) {

      queue = getRequestQueue(context);
      StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String s) {

          if (listener != null) {
            listener.onDataReceive(s);
          } else {
            Log.d("UNI", "WebServicesModule - listener -> null");
          }

        }
      }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
          Log.d("UNI", "Volley Error ->\n" + volleyError.toString());
        }
      }) {

        @Override
        public byte[] getBody() throws AuthFailureError {
          try {
            return jsonPost.getBytes("utf-8");
          } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
          }
          return null;
        }

        @Override
        public String getBodyContentType() {
          return "application/json; charset=utf-8";
        }


      };
      queue.add(request);
      queue.start();

    } else {
      listener.onDataReceive(Global.TAG_NO_NET);
    }

  }

}
