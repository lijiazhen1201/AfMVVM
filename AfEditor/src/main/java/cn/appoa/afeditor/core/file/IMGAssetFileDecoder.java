package cn.appoa.afeditor.core.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;


public class IMGAssetFileDecoder extends IMGDecoder {

    private Context mContext;

    public IMGAssetFileDecoder(Context context, Uri uri) {
        super(uri);
        mContext = context;
    }

    @Override
    public Bitmap decode(BitmapFactory.Options options) {
        Uri uri = getUri();
        if (uri == null) {
            return null;
        }

        String path = uri.getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }

        path = path.substring(1);

        try {
            InputStream iStream = mContext.getAssets().open(path);
            return BitmapFactory.decodeStream(iStream, null, options);
        } catch (IOException ignore) {

        }

        return null;
    }
}
