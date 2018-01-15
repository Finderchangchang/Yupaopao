package com.longxiang.woniuke.customwheelview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * Created by Administrator on 2016/3/29 0029.
 */
public class BitmapDownLoad {
    public static Bitmap getBitmap(final String url, final Context context) {

        final Bitmap[] bitmap = {null};

        String fileName=context.getExternalCacheDir()+ File.separator+url.substring(url.lastIndexOf("/")+1);
        byte[] bytes=SDCardHelper.loadFileFromSDCard(fileName);

        if (bytes==null) {
            RequestParams params = new RequestParams(url);
            x.http().get(params, new Callback.CommonCallback<byte[]>() {
                @Override
                public void onSuccess(byte[] result) {
                    //  Log.e("12345", "onSuccess: "+result.length );
                     bitmap[0] =getDownDimensionBitmap(result);
                    String Name = url.substring(url.lastIndexOf("/")+1);
                    SDCardHelper.saveBitmapToSDCardCacheDir(bitmap[0], Name, context);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    //  Log.e("12345", "onError:error " );
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }else {
             bitmap[0] =BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }




//        x.image().bind(view, url, new Callback.CommonCallback<Drawable>() {
//            @Override
//            public void onSuccess(Drawable result) {
//                BitmapDrawable bd=(BitmapDrawable)result;
//                Bitmap bitmap=bd.getBitmap();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
        return bitmap[0];

    }
    /**
     * 计算图片二次采样的采样率，使用获取图片宽高之后的Options作为第一个参数
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     *
     * --by Google
     */

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        //只有当请求的宽度、高度 > 0时，进行缩放
        //否则，图片不进行缩放
        if(reqHeight > 0 && reqWidth > 0){
            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }
        }
        return inSampleSize;
    }

    /**
     * 获取二次采样图片
     * @param data
//     * @param requestWidth
//     * @param requestHeight
     * @return
     */


    public static Bitmap getDownDimensionBitmap(byte[] data) {
        Bitmap bitmap = null;
        if (data != null) {
            //按照原始的图片尺寸，进行Bitmap的生存
            //按照Bitmap生成，是按照图片的原始宽高，进行生成，并且每一个像素占用4个字节 ARGB
//                ret = BitmapFactory.decodeByteArray(data, 0, data.length);

            //采用二次采样（缩小图片尺寸的方式）
            //1.步骤1 获取原始图片的宽高信息，用于进行采样的计算

            //1.1创建Options ，给BitmapFactory的内部解码器设置参数
            BitmapFactory.Options options = new BitmapFactory.Options();
            //1.2设置inJustDecodeBounds 来控制解码器，只会进行图片宽高的获取，不会获取图片
            //不占用内存，当使用这个参数，代表BitmapFactory.decodexxx()不会返回bitmap
            options.inJustDecodeBounds = true;

            //解码，使用options参数 设置解码方式
            BitmapFactory.decodeByteArray(data, 0, data.length, options);

            //2.步骤2 根据图片的真实尺寸，与当前需要显示的尺寸，进行计算，生成采样率

            //2.1
            //int picW = options.outWidth;
            //int picH = options.outHeight;

            //2.2准备 显示在手机上 256x128 128x64
            //尺寸是根据程序需要来设置的

//                int reqW = 256;
//                int reqH = 128;

            //2.3计算 设置 图片采样率
           // options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);//宽度的1/32  高度的1/32
           //变为原来的四分之一
			options.inSampleSize=1;

            //2.4开放 解码，实际生成Bitmap
            options.inJustDecodeBounds = false;

            //2.4.1 Bitmap.Config的说明
            //要求解码器对于每一个采样的像素，使用RGB_565 存储方式
            //一个像素，占用两个字节，比ARGB_8888笑了一半
            //如果解码器不能使用指定配置，就自动使用ARGB_8888
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            //2.4.2是一个过时的设置,可以及时清除内存
            options.inPurgeable = true;

            //2.5使用设置采样的参数，来进行 解码，获取bitmap
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        }
        return bitmap;
    }
}
