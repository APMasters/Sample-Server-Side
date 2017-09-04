package mir.amirparsa.server_side;

import android.app.Application;

import java.util.List;

/**
 * Created by Amir Parsa Mir
 * Call me : amirparsa.mir@gmail.com
 */

public class Global extends Application {

  public static final String URL_GET_MUSICS = "http://192.168.1.102/uni/music/get-musics.php";
  public static final String TOKEN = "AuthorizedClient";

  public static final String TAG_OK = "OK";
  public static final String TAG_NO_NET = "NO_INTERNET";

  public static final String LOG = "TEST-LOG";

  public static List<Music> musics = null;

  public class Music {

    String name;
    String singer;
    String album;
    String coverImageUrl;
    float rate;

    public String getName() {
      return name;
    }

    public String getSinger() {
      return singer;
    }

    public String getAlbum() {
      return album;
    }

    public String getCoverImgUrl() {
      return coverImageUrl;
    }

    public float getRate() {
      return rate;
    }

  }

}
