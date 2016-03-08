package de.hdodenhof.circleimageview.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends Activity {

    private RequestQueue volleyRequestQueue;
    private ImageLoader volleyImageLoader;
    private CircleImageView image1, image2;

    private String remoteImageUrl = "http://assets.rollingstone.com/assets/2013/article/behind-the-crazy-anchorman-newscaster-brawl-20131218/18377/_original/1035x690-anchorman-1800-1387407909.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image1 = (CircleImageView) findViewById(R.id.image1);
        image2 = (CircleImageView) findViewById(R.id.image2);
        volleyRequestQueue = Volley.newRequestQueue(this);
        volleyImageLoader = new ImageLoader(volleyRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        image1.setImageUrl(remoteImageUrl, volleyImageLoader);
        image2.setImageUrl(remoteImageUrl, volleyImageLoader);
    }

}
