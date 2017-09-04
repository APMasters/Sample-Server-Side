package mir.amirparsa.server_side.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mir.amirparsa.server_side.Global;
import mir.amirparsa.server_side.R;
import mir.amirparsa.server_side.adapter.RcvMusicsAdapter;
import mir.amirparsa.server_side.request.GetMusicsRequest;

/**
 * Created by Amir Parsa Mir
 * Call me : amirparsa.mir@gmail.com
 */

public class MainActivity extends AppCompatActivity {

  private GetMusicsRequest request;

  @BindView(R.id.rcvMusics)
  RecyclerView rcvMusics;

  @BindView(R.id.progressBar)
  ProgressBar progressBar;

  @OnClick(R.id.fab)
  public void onFabClick() {
    getMusicList();
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    getSupportActionBar().setTitle("Simple Music Playlist");

  }


  private void getMusicList() {

    showLoading(true);

    request = GetMusicsRequest.getInstance(this);
    request.sendRequest();

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {

        checkResponse(request.getResponse());

      }
    }, 3000);

  }


  private void checkResponse(String response) {

    switch (response) {

      case Global.TAG_NO_NET:
        showLoading(false);
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        break;

      case Global.TAG_OK:
        showLoading(false);
        rcvMusics.setAdapter(new RcvMusicsAdapter(this));
        rcvMusics.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        break;

      default:
        showLoading(false);
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        Log.d(Global.LOG, "Response Error ->\t" + response);

    }

  }


  private void showLoading(boolean show) {

    if (show) {
      progressBar.setVisibility(View.VISIBLE);
      rcvMusics.setVisibility(View.GONE);
      return;
    }

    progressBar.setVisibility(View.GONE);
    rcvMusics.setVisibility(View.VISIBLE);

  }

}
