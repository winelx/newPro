package com.example.administrator.newsdf.pzgc.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.administrator.newsdf.R;
import com.example.administrator.newsdf.pzgc.bean.Inface_all_item;
import com.example.administrator.newsdf.pzgc.utils.Dates;
import com.example.administrator.newsdf.pzgc.utils.SlantedTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.administrator.newsdf.R.id.inface_item_message;
import static com.example.administrator.newsdf.R.id.inface_status_true;

/**
 * 全部任务列表界面的listview 的adapter
 * Created by Administrator on 2018/1/22 0022.
 */

public class Imageloaders extends BaseAdapter {
    private Context context;
    private List<Inface_all_item> list;
    private ListView listview;
    private LruCache<String, BitmapDrawable> mImageCache;
    private ImageLoader imageLoader;
    private RequestQueue queue;

    public Imageloaders(Context context, List<Inface_all_item> list) {
        super();
        this.context = context;
        this.list = list;
        queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue, new BitmapCache());
        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, BitmapDrawable>(cacheSize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (listview == null) {
            listview = (ListView) parent;
        }
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.interface_item, null);
            holder = new ViewHolder();
            holder.inface_image = convertView.findViewById(R.id.inface_image);
            holder.inface_image3 = convertView.findViewById(R.id.inface_image3);
            holder.inface_image2 = convertView.findViewById(R.id.inface_image2);
            holder.inface_imag1 = convertView.findViewById(R.id.inface_image1);
            holder.user_auathor = convertView.findViewById(R.id.inface_avatar);
            holder.inter_title = convertView.findViewById(R.id.inter_title);
            holder.inter_time = convertView.findViewById(R.id.inter_time);
            holder.inface_wbs_path = convertView.findViewById(R.id.inface_wbs_path);
            holder.inter_content = convertView.findViewById(R.id.inter_content);
            holder.inface_username = convertView.findViewById(R.id.inface_username);
            holder.inface_uptime = convertView.findViewById(R.id.inface_uptime);
            holder.inface_pcontent = convertView.findViewById(R.id.inface_pcontent);
            holder.inface_loation = convertView.findViewById(R.id.inface_loation);
            holder.inface_imgae_text = convertView.findViewById(R.id.inface_imgae_text);
            holder.inface_status_true = convertView.findViewById(inface_status_true);
            holder.inface_item_message = convertView.findViewById(inface_item_message);
            holder.textView4 = convertView.findViewById(R.id.textView4);
            //文档
            holder.inface_relat1 = convertView.findViewById(R.id.inface_relat1);
            holder.inface_relat2 = convertView.findViewById(R.id.inface_relat2);
            holder.inface_relat3 = convertView.findViewById(R.id.inface_relat3);
            //文档文字
            holder.inface_relat1_name = convertView.findViewById(R.id.inface_relat1_name);
            holder.inface_relat2_name = convertView.findViewById(R.id.inface_relat2_name);
            holder.inface_relat3_name = convertView.findViewById(R.id.inface_relat3_name);
            //文档icon
            holder.inface_relat1_icon = convertView.findViewById(R.id.inface_relat1_icon);
            holder.inface_relat2_icon = convertView.findViewById(R.id.inface_relat2_icon);
            holder.inface_relat3_icon = convertView.findViewById(R.id.inface_relat3_icon);
            holder.view = convertView.findViewById(R.id.view);
            holder.inface_no_image=convertView.findViewById(R.id.inface_no_image);
            //多图片时显示图片数量
            holder.ic_loading_bg = convertView.findViewById(R.id.ic_loading_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //标题
        if (list.get(position).getGroupName().length() != 0) {
            holder.inter_title.setText(list.get(position).getGroupName());
        } else {
            holder.inter_title.setText("主动上传任务 ");
        }
        holder.inface_wbs_path.setText(list.get(position).getWbsPath());
        holder.inter_content.setText(list.get(position).getContent());
        holder.inface_username.setText(list.get(position).getUploador());
        holder.inface_uptime.setText(list.get(position).getUpload_time());
        holder.inface_loation.setText(list.get(position).getUpload_addr());
        holder.inface_pcontent.setText(list.get(position).getUpload_content());
        ArrayList<String> path = new ArrayList<>();
        ArrayList<String> pathname = new ArrayList<>();
        path.addAll(list.get(position).getUpload());
        pathname.addAll(list.get(position).getFilename());
        Dates.setback(context, list.get(position).getPortrait(), holder.user_auathor);
        switch (list.get(position).getIsFinish() + "") {
            //未完成
            case "0":
                holder.inface_item_message.setTextString("未完成");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.unfinish_gray);
                holder.inface_status_true.setVisibility(View.GONE);
                holder.view.setVisibility(View.GONE);
                String str = null;
                try {
                    str = Dates.datato(list.get(position).getCreateTime());
                    holder.inter_time.setText("已推送：" + str);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case "1":
                holder.view.setVisibility(View.VISIBLE);
                holder.inface_status_true.setVisibility(View.VISIBLE);
                holder.inface_item_message.setTextString("已完成");
                try {
                    String strtime = dateToStamp(list.get(position).getUpdateDate());
                    holder.inter_time.setText(Dates.stampToDates(strtime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.textView4.setText("(" + list.get(position).getComments() + ")");
                holder.inface_item_message.setSlantedBackgroundColor(R.color.finish_green);
                // 预设一个图片
                if (path.size() >= 1) {
                    holder.inface_image.setVisibility(View.VISIBLE);
                    String imgUrl = path.get(0);
                    if (imgUrl != null && !imgUrl.equals("")) {
                        int doc = imgUrl.lastIndexOf(".");
                        String strs = imgUrl.substring(doc + 1);
                        holder.inface_imgae_text.setVisibility(View.GONE);
                        holder.ic_loading_bg.setVisibility(View.GONE);
                        holder.inface_image2.setVisibility(View.INVISIBLE);
                        holder.inface_image3.setVisibility(View.INVISIBLE);
                        if (strs.equals("pdf")) {
                            //隐藏图片
                            holder.inface_imag1.setVisibility(View.GONE);
                            //显示文档布局
                            holder.inface_relat1.setVisibility(View.VISIBLE);
                            //设置文档布局背景色
                            holder.inface_relat1.setBackgroundColor(Color.parseColor("#f8f5f6"));
                            //pdf的icon显示图
                            holder.inface_relat1_icon.setText("P");
                            //设置文件名
                            holder.inface_relat1_name.setText(pathname.get(0) + strs);
                            //设置文字的颜色
                            holder.inface_relat1_icon.setTextColor(Color.parseColor("#FFFFFF"));
                            //设置文字背景色
                            holder.inface_relat1_icon.setBackgroundColor(Color.parseColor("#e98e90"));
                        } else if (strs.equals("doc") || strs.equals("docx")) {
                            //隐藏图片
                            holder.inface_imag1.setVisibility(View.GONE);
                            //显示文档布局
                            holder.inface_relat1.setVisibility(View.VISIBLE);
                            //设置文档布局背景色
                            holder.inface_relat1.setBackgroundColor(Color.parseColor("#f1f6f7"));
                            //pdf的icon显示图
                            holder.inface_relat1_icon.setText("W");
                            //设置文件名
                            holder.inface_relat1_name.setText(pathname.get(0) + strs);
                            //设置文字的颜色
                            holder.inface_relat1_icon.setTextColor(Color.parseColor("#FFFFFF"));
                            //设置文字背景色
                            holder.inface_relat1_icon.setBackgroundColor(Color.parseColor("#5e8ed3"));

                        } else if (strs.equals("xlsx") || strs.equals("xls")) {
                            //隐藏图片
                            holder.inface_imag1.setVisibility(View.GONE);
                            //显示文档布局
                            holder.inface_relat1.setVisibility(View.VISIBLE);
                            //设置文档布局背景色
                            holder.inface_relat1.setBackgroundColor(Color.parseColor("#f5f8f7"));
                            //pdf的icon显示图
                            holder.inface_relat1_icon.setText("X");
                            //设置文件名
                            holder.inface_relat1_name.setText(pathname.get(0) + strs);
                            //设置文字的颜色
                            holder.inface_relat1_icon.setTextColor(Color.parseColor("#FFFFFF"));
                            //设置文字背景色
                            holder.inface_relat1_icon.setBackgroundColor(Color.parseColor("#67cf95"));
                        } else {
                            holder.inface_relat1.setVisibility(View.GONE);
                            holder.inface_imag1.setVisibility(View.VISIBLE);
                            holder.inface_imag1.setDefaultImageResId(R.mipmap.image_loading);
                            holder.inface_imag1.setErrorImageResId(R.mipmap.image_error);
                            //截取出后缀
                            String pas = imgUrl.substring(imgUrl.length() - 4, imgUrl.length());
                            //拿到截取后缀后的字段
                            imgUrl = imgUrl.replace(pas, "");
                            //在字段后面添加_min后再拼接后缀
                            imgUrl = imgUrl + "_min" + pas;
                            holder.inface_imag1.setImageUrl(imgUrl, imageLoader);
                        }
                        if (path.size() >= 2) {
                            String imgUrl1 = path.get(1);
                            if (imgUrl1 != null && !imgUrl1.equals("")) {
                                int doc1 = imgUrl1.lastIndexOf(".");
                                String strs1 = imgUrl1.substring(doc1 + 1);
                                holder.inface_image2.setVisibility(View.VISIBLE);
                                holder.inface_image3.setVisibility(View.INVISIBLE);
                                if (strs1.equals("pdf")) {
                                    //隐藏图片
                                    holder.inface_image2.setVisibility(View.GONE);
                                    //显示文档布局
                                    holder.inface_relat2.setVisibility(View.VISIBLE);
                                    //设置文档布局背景色
                                    holder.inface_relat2.setBackgroundColor(Color.parseColor("#f8f5f6"));
                                    //pdf的icon显示图
                                    holder.inface_relat2_icon.setText("P");
                                    //设置文件名
                                    holder.inface_relat2_name.setText(pathname.get(1) + strs1);
                                    //设置文字的颜色
                                    holder.inface_relat2_icon.setTextColor(Color.parseColor("#FFFFFF"));
                                    //设置文字背景色
                                    holder.inface_relat2_icon.setBackgroundColor(Color.parseColor("#e98e90"));
                                } else if (strs1.equals("doc") || strs1.equals("docx")) {
                                    //隐藏图片
                                    holder.inface_image2.setVisibility(View.GONE);
                                    //显示文档布局
                                    holder.inface_relat2.setVisibility(View.VISIBLE);
                                    //设置文档布局背景色
                                    holder.inface_relat2.setBackgroundColor(Color.parseColor("#f1f6f7"));
                                    //pdf的icon显示图
                                    holder.inface_relat2_icon.setText("W");
                                    //设置文件名
                                    holder.inface_relat2_name.setText(pathname.get(1) + strs1);
                                    //设置文字的颜色
                                    holder.inface_relat2_icon.setTextColor(Color.parseColor("#FFFFFF"));
                                    //设置文字背景色
                                    holder.inface_relat2_icon.setBackgroundColor(Color.parseColor("#5e8ed3"));
                                } else if (strs1.equals("xlsx") || strs1.equals("xls")) {
                                    //隐藏图片
                                    holder.inface_image2.setVisibility(View.GONE);
                                    //显示文档布局
                                    holder.inface_relat2.setVisibility(View.VISIBLE);
                                    //设置文档布局背景色
                                    holder.inface_relat2.setBackgroundColor(Color.parseColor("#f5f8f7"));
                                    //pdf的icon显示图
                                    holder.inface_relat2_icon.setText("X");
                                    //设置文件名
                                    holder.inface_relat2_name.setText(pathname.get(1) + strs1);
                                    //设置文字的颜色
                                    holder.inface_relat2_icon.setTextColor(Color.parseColor("#FFFFFF"));
                                    //设置文字背景色
                                    holder.inface_relat2_icon.setBackgroundColor(Color.parseColor("#67cf95"));
                                } else {
                                    holder.inface_image2.setVisibility(View.VISIBLE);
                                    //显示文档布局
                                    holder.inface_relat2.setVisibility(View.GONE);
                                    holder.inface_image2.setDefaultImageResId(R.mipmap.image_loading);
                                    holder.inface_image2.setErrorImageResId(R.mipmap.image_error);
                                    //截取出后缀
                                    String pas = imgUrl1.substring(imgUrl1.length() - 4, imgUrl1.length());
                                    //拿到截取后缀后的字段
                                    imgUrl1 = imgUrl1.replace(pas, "");
                                    //在字段后面添加_min后再拼接后缀
                                    imgUrl1 = imgUrl1 + "_min" + pas;
                                    holder.inface_image2.setImageUrl(imgUrl1, imageLoader);
                                }
                                if (path.size() >= 3) {
                                    String imgUrl2 = path.get(2);
                                    if (imgUrl2 != null && !imgUrl2.equals("")) {
                                        int doc2 = imgUrl2.lastIndexOf(".");
                                        String strs2 = imgUrl2.substring(doc2 + 1);
                                        if (strs2.equals("pdf")) {
                                            //隐藏图片
                                            holder.inface_image3.setVisibility(View.GONE);
                                            //显示文档布局
                                            holder.inface_relat3.setVisibility(View.VISIBLE);
                                            //设置文档布局背景色
                                            holder.inface_relat3.setBackgroundColor(Color.parseColor("#f8f5f6"));
                                            //pdf的icon显示图
                                            holder.inface_relat3_icon.setText("P");
                                            //设置文件名
                                            holder.inface_relat3_name.setText(pathname.get(2) + strs2);
                                            //设置文字的颜色
                                            holder.inface_relat3_icon.setTextColor(Color.parseColor("#FFFFFF"));
                                            //设置文字背景色
                                            holder.inface_relat3_icon.setBackgroundColor(Color.parseColor("#e98e90"));
                                        } else if (strs2.equals("doc") || strs2.equals("docx")) {
                                            //隐藏图片
                                            holder.inface_image3.setVisibility(View.GONE);
                                            //显示文档布局
                                            holder.inface_relat3.setVisibility(View.VISIBLE);
                                            //设置文档布局背景色
                                            holder.inface_relat3.setBackgroundColor(Color.parseColor("#f1f6f7"));
                                            //pdf的icon显示图
                                            holder.inface_relat3_icon.setText("W");
                                            //设置文件名
                                            holder.inface_relat3_name.setText(pathname.get(2) + strs2);
                                            //设置文字的颜色
                                            holder.inface_relat3_icon.setTextColor(Color.parseColor("#FFFFFF"));
                                            //设置文字背景色
                                            holder.inface_relat3_icon.setBackgroundColor(Color.parseColor("#5e8ed3"));
                                        } else if (strs2.equals("xlsx") || strs2.equals("xls")) {
                                            //隐藏图片
                                            holder.inface_image3.setVisibility(View.GONE);
                                            //显示文档布局
                                            holder.inface_relat3.setVisibility(View.VISIBLE);
                                            //设置文档布局背景色
                                            holder.inface_relat3.setBackgroundColor(Color.parseColor("#f5f8f7"));
                                            //pdf的icon显示图
                                            holder.inface_relat3_icon.setText("X");
                                            //设置文件名
                                            holder.inface_relat3_name.setText(pathname.get(2) + strs2);
                                            //设置文字的颜色
                                            holder.inface_relat3_icon.setTextColor(Color.parseColor("#FFFFFF"));
                                            //设置文字背景色
                                            holder.inface_relat3_icon.setBackgroundColor(Color.parseColor("#67cf95"));
                                        } else {
                                            holder.inface_image3.setVisibility(View.VISIBLE);
                                            holder.inface_relat3.setVisibility(View.GONE);
                                            if (path.size() > 3) {
                                                holder.ic_loading_bg.setVisibility(View.VISIBLE);
                                                holder.inface_imgae_text.setVisibility(View.VISIBLE);
                                                int num = path.size() - 3;
                                                holder.inface_imgae_text.setText("+" + num);
                                            }
                                            holder.inface_image3.setDefaultImageResId(R.mipmap.image_loading);
                                            holder.inface_image3.setErrorImageResId(R.mipmap.image_error);
                                            //截取出后缀
                                            String pas = imgUrl2.substring(imgUrl2.length() - 4, imgUrl2.length());
                                            //拿到截取后缀后的字段
                                            imgUrl2 = imgUrl2.replace(pas, "");
                                            //在字段后面添加_min后再拼接后缀
                                            imgUrl2 = imgUrl2 + "_min" + pas;
                                            holder.inface_image3.setImageUrl(imgUrl2, imageLoader);
                                        }
                                    }
                                }

                            }
                        }
                    }
                } else {
                    holder.inface_no_image.setVisibility(View.VISIBLE);
                    holder.inface_image.setVisibility(View.GONE);
                }
            default:
                break;
        }
        return convertView;
    }

    class ViewHolder {
        NetworkImageView inface_image3, inface_image2, inface_imag1,inface_no_image;
        CircleImageView user_auathor;
        RelativeLayout inface_status_true;
        TextView inter_title;
        TextView inter_time;
        TextView inface_wbs_path;
        TextView inter_content;
        TextView inface_username;
        TextView inface_uptime;
        TextView inface_pcontent;
        TextView inface_loation;
        TextView inface_imgae_text;
        TextView textView4;
        View view;
        LinearLayout inface_image;
        RelativeLayout inface_relat1, inface_relat2, inface_relat3;
        TextView inface_relat1_icon, inface_relat2_icon, inface_relat3_icon;
        TextView inface_relat1_name, inface_relat2_name, inface_relat3_name;
        LinearLayout ic_loading_bg;
        private SlantedTextView inface_item_message;
    }

    public void getData(List<Inface_all_item> mdata) {
        this.list = mdata;
        notifyDataSetChanged();
    }

    /**
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
}

