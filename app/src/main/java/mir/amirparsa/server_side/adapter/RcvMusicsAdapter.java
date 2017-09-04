package mir.amirparsa.server_side.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import mir.amirparsa.server_side.Global;
import mir.amirparsa.server_side.R;

/**
 * Created by Amir Parsa Mir
 * Call me : amirparsa.mir@gmail.com
 */

public class RcvMusicsAdapter extends RecyclerView.Adapter<RcvMusicsAdapter.MyViewHolder> {

  private Context context;

  public RcvMusicsAdapter(Context context) {
    this.context = context;
  }


  @Override
  public RcvMusicsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(context).inflate(R.layout.item_rcv_main, parent, false);
    return new MyViewHolder(view);

  }


  @Override
  public void onBindViewHolder(RcvMusicsAdapter.MyViewHolder holder, int position) {

    Global.Music music = Global.musics.get(position);

    holder.txtMusic.setText(music.getName());
    holder.txtSinger.setText(music.getSinger());
    holder.txtAlbum.setText(music.getAlbum());
    holder.rtb.setRating(music.getRate());
    holder.rtb.setMax(5);


    Glide.with(context)
      .load(music.getCoverImgUrl())
      .crossFade()
      .placeholder(R.color.colorAccent)
      .into(holder.imgCover);

  }


  @Override
  public int getItemCount() {
    return Global.musics == null ? 0 : Global.musics.size();
  }


  public class MyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txtMusic)
    TextView txtMusic;

    @BindView(R.id.txtSinger)
    TextView txtSinger;

    @BindView(R.id.txtAlbum)
    TextView txtAlbum;

    @BindView(R.id.imgCover)
    ImageView imgCover;

    @BindView(R.id.rtb)
    RatingBar rtb;

    public MyViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);

    }

  }

}
