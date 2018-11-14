package measure.jjxx.com.baselibrary.adapter;

/**
 * @author lx
 * @Created by: 2018/10/16 0016.
 * @description:
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import measure.jjxx.com.baselibrary.R;
import measure.jjxx.com.baselibrary.utils.WindowUtils;
import measure.jjxx.com.baselibrary.view.SquareItemLayout;

/**
 * @author lx
 *         Created by donglua on 15/5/31.
 *         添加图片
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private ArrayList<String> photoPaths;
    private LayoutInflater inflater;
    private Context mContext;

    public final static int TYPE_ADD = 1;
    final static int TYPE_PHOTO = 2;
    final static int MAX = 100;
    private boolean status = false;
    private boolean lean=false;
    public PhotosAdapter(Context mContext, ArrayList<String> photoPaths,boolean status) {
        this.photoPaths = photoPaths;
        this.mContext = mContext;
        this.status = status;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        switch (viewType) {
            case TYPE_ADD:
                itemView = inflater.inflate(R.layout.base_item_add, parent, false);
                break;
            case TYPE_PHOTO:
                itemView = inflater.inflate(R.layout.base_picker_item_photo, parent, false);
                break;
            default:
                break;
        }
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (getItemViewType(position) == TYPE_PHOTO) {
            Glide.with(mContext)
                    .load(photoPaths.get(position))
                    .thumbnail(0.1f)
                    .into(holder.ivPhoto);
            //控制删除按钮
            if (status) {
                holder.vSelected.setVisibility(View.VISIBLE);
            } else {
                holder.vSelected.setVisibility(View.GONE);
            }
            //删除
            holder.vSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1
                    int position = holder.getLayoutPosition();
                    // 2
                    mOnItemClickListener.deleteClick(holder.itemView, position);
                }
            });
            //点击图片
            holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 1
                    int position = holder.getLayoutPosition();
                    // 2
                    mOnItemClickListener.photoClick(holder.itemView, position);
                }
            });
        } else {
            holder.addview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lean){
                        // 1
                        int position = holder.getLayoutPosition();
                        // 2
                        mOnItemClickListener.addlick(holder.itemView, position);
                    }

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        int count = photoPaths.size() + 1;
        if (count > MAX) {
            count = MAX;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == photoPaths.size() && position != MAX) ? TYPE_ADD : TYPE_PHOTO;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;
        private ImageView vSelected;
        private ImageView img_add;
        private SquareItemLayout addview;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            vSelected = itemView.findViewById(R.id.v_selected);
            img_add = itemView.findViewById(R.id.bt_add);
            addview = itemView.findViewById(R.id.addview);
        }
    }

    public void getData(ArrayList<String> photoPaths) {
        this.photoPaths = photoPaths;
        notifyDataSetChanged();
    }

    /**
     * 内部接口
     */

    public interface OnItemClickListener {
        void addlick(View view, int position);
        void photoClick(View view,int position);
        void deleteClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void status(boolean lean) {
        this.status = lean;
        notifyDataSetChanged();
    }
    public void addview(boolean lean) {
        this.lean = lean;
        notifyDataSetChanged();
    }
}
