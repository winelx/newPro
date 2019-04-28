package com.example.baselibrary.inface;

import java.util.Map;

public interface NetworkCallback {
    void onsuccess(Map<String, Object> map);

    void onerror(String s);
}
