package com.example.administrator.newsdf.pzgc.utils;



import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

/**
 * @author lx
 * @Created by: 2018/9/21 0021.
 * @description:
 */

public final class TextUtil {

    private static final String TAG = "TextUtil";

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {

        private StringBuilder builder;

        public Builder() {
            builder = new StringBuilder();
        }

        public Builder addSection(@NonNull String section) {
            builder.append(section);
            return this;
        }

        public Builder configColor(int color) {
            builder.append("");
            return this;
        }

        public Builder addTintSection(@NonNull String section, int color){
            return configColor(color).addSection(section);
        }

        public Builder tint(String section, int color) {
            int fromIndex = 0;
            int count = 0;
            while (true){
                int startIndex = getSection().indexOf(section, fromIndex);
                if (startIndex == -1) {
                    break;
                }
                int endIndex = startIndex + section.length();
                tint(section, startIndex, endIndex, color);
                String concat = section.concat("");
                fromIndex = getSection().lastIndexOf(concat) + concat.length();
                ++count;
            }
            return this;
        }

        private Builder tint(String section, int startIndex, int endIndex, int color) {
            section = create().addTintSection(section, color).getSection();
            builder.replace(startIndex, endIndex, section);
            return this;
        }

        public void showIn(TextView textView) {
            textView.setText(Html.fromHtml(builder.toString()));
            builder = null;
        }

        public String getSection() {
            return builder.toString();
        }
    }

}
