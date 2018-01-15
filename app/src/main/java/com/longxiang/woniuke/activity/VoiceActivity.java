package com.longxiang.woniuke.activity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.longxiang.woniuke.R;
import com.longxiang.woniuke.utils.CountDownTimerView;
import com.longxiang.woniuke.utils.ExtAudioRecorder;
import com.longxiang.woniuke.utils.MyApp;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VoiceActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {
private ImageView ivBack;
    private Button recordvoice;
    private Button btnting;
    private Button btnbaocun;
    private ExtAudioRecorder extRecorder;
    private MediaPlayer player;
    private CountDownTimerView tvTime;
    private ProgressBar progressBar;
    private TextView title;
    LinearLayout ll_record;
    ImageView iv_volume;
    Handler mHandler = new Handler();
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        setView();
        setListener();
    }

    private void setView() {
        ll_record = (LinearLayout) findViewById(R.id.ll_record);
        iv_volume = (ImageView) findViewById(R.id.iv_volume);
        ivBack= (ImageView) findViewById(R.id.voice_iv_back);
        recordvoice= (Button) findViewById(R.id.voice_luyin);
        btnting= (Button) findViewById(R.id.voice_btn_shiting);
        btnbaocun= (Button) findViewById(R.id.voice_btn_baocun);
        tvTime= (CountDownTimerView) findViewById(R.id.voice_timerview);
        btnting.setTextColor(Color.parseColor("#1EBAF3"));
        btnbaocun.setTextColor(Color.parseColor("#ffffff"));
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        title= (TextView) findViewById(R.id.voice_tv_title_name);
        title.setTextColor(Color.parseColor("#ffffff"));
    }

    private void setListener() {
        ivBack.setOnClickListener(this);
        btnting.setOnClickListener(this);
        btnbaocun.setOnClickListener(this);
        recordvoice.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.voice_iv_back:
                finish();
                break;
            case R.id.voice_btn_shiting:
                btnting.setOnClickListener(null);
                if(player==null) {
                    try {
                        player = new MediaPlayer();
                        player.reset();
                        player.setDataSource(getRawFilePath());
                        player.prepare();
                        player.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.voice_btn_baocun:
                progressBar.setVisibility(View.VISIBLE);
                recordvoice.setOnClickListener(null);
                btnting.setOnClickListener(null);
                btnbaocun.setOnClickListener(null);
                new Thread(){
                    @Override
                    public void run() {
                        uploaddir();
                        super.run();
                    }
                }.start();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        try {
            int action = event.getAction();
            switch (action) {
                // 按下开始录，
                case MotionEvent.ACTION_DOWN:
                    btnting.setOnClickListener(this);
                    if (extRecorder == null) {
                        recordvoice.setText("松开结束");
                        startRecord();
                        break;
                    }
                    // 松开发送语音
                case MotionEvent.ACTION_UP:
                    // 停止
                    recordvoice.setText("按住录音");
                    stopRecord();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void stopRecord() {
        tvTime.stop();
        tvTime.setVisibility(View.INVISIBLE);
        recordvoice.setPressed(false);
        extRecorder.stop();
        extRecorder.release();
        extRecorder = null;
        mHandler.removeCallbacks(mPollTask);
    }

    public void startRecord() {
        tvTime.setVisibility(View.VISIBLE);
        tvTime.setTime(0,0,10);
        tvTime.start();
        extRecorder = ExtAudioRecorder.getInstanse(false);  //设置为false,录制wav
        extRecorder.setOutputFile(getRawFilePath()); //输出SD卡路径
        extRecorder.prepare();
        extRecorder.start();
        ll_record.setVisibility(View.VISIBLE);
        t = new Thread(mPollTask);
        t.start();
    }

    public static String getRawFilePath(){
        try {
            File directory=new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (!directory.exists())
            {
                directory.mkdirs();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.i("lvzhiweilog", "getRawFilePath: "+Environment.getExternalStorageDirectory().getAbsolutePath()+"/voiceintroduce.wav");
        return Environment.getExternalStorageDirectory().getAbsolutePath()+"/voiceintroduce.wav";
    }
    private String uploaddir() {
        String result = null;
        String  BOUNDARY = "*****";  //边界标识   随机生成
        String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data";   //内容类型
        File file=new File(getRawFilePath());
        try {
            URL url = new URL("http://bubblefish.jbserver.cn/api/api/MediaUpload");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);  //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(true);  //不允许使用缓存
            conn.setRequestMethod("POST");  //请求方式
            conn.setRequestProperty("Charset", "utf-8");  //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);

            if(file!=null)
            {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream( conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key   只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的   比如:abc.png
                 */
                Log.i("lvzhiweilog", "uploaddir: "+file.getName());
                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""+file.getName()+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+"utf-8"+LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024*4];
                int len = 0;
                while((len=is.read(bytes))!=-1)
                {
                    dos.write(bytes, 0, len);
                    Log.i("lvzhiewilog", "uploaddir: "+bytes.toString());
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();

                /**
                 * 获取响应码  200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e("lvzhiweilog", "response code:"+res);
                if(res==200)
                {
                    Log.e("lvzhiweilog", "request success");
                    InputStream input =  conn.getInputStream();
                    StringBuffer sb1= new StringBuffer();
                    int ss ;
                    while((ss=input.read())!=-1)
                    {
                        sb1.append((char)ss);
                    }
                    result = sb1.toString();
                    Log.e("lvzhiweilog", "result : "+ result);
                    try {
                        JSONObject obj=new JSONObject(result);
                        if(obj.getString("retcode").equals("2000")){
                            mediaEdit(result);
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(VoiceActivity.this,"网络不佳,稍后重试",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    btnbaocun.setOnClickListener(VoiceActivity.this);
                                }
                            });

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                else{
                    Log.e("lvzhiweilog", "request error");
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }

    private void mediaEdit(String result) {
        try {
            JSONObject obj=new JSONObject(result);
            String voicepath=obj.getString("data");
            Log.i("voiceactivity", "mediaEdit: "+voicepath);
            RequestParams params=new RequestParams("http://bubblefish.jbserver.cn/api/users/mediaEdit");
            params.addBodyParameter("uid", MyApp.uid);
            params.addBodyParameter("media",voicepath);
            x.http().post(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.i("voiceactivity", "onSuccess: "+result);
                    try {
                        JSONObject obj=new JSONObject(result);
                        switch (obj.getString("retcode")){
                            case "2000":
                                Toast.makeText(getApplicationContext(),obj.getString("msg"),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                finish();
                                break;

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // 更新音量图
    private void updateVolume(int volume) {
        switch (volume) {
            case 1:
                iv_volume.setImageResource(R.mipmap.p1);
                break;
            case 2:
                iv_volume.setImageResource(R.mipmap.p2);
                break;
            case 3:
                iv_volume.setImageResource(R.mipmap.p3);
                break;
            case 4:
                iv_volume.setImageResource(R.mipmap.p4);
                break;
            case 5:
                iv_volume.setImageResource(R.mipmap.p5);
                break;
            case 6:
                iv_volume.setImageResource(R.mipmap.p6);
                break;
            case 7:
                iv_volume.setImageResource(R.mipmap.p7);
                break;
            default:
                break;
        }
    }
    private Runnable mPollTask = new Runnable() {
        public void run() {
            int mVolume = getVolume();
            Log.d("volume", mVolume + "");
            updateVolume(mVolume);
            mHandler.postDelayed(mPollTask, 100);
        }
    };
    // 获取音量值，只是针对录音音量
    public int getVolume() {
        int volume = 0;
        // 录音
        if (extRecorder != null) {
            volume = extRecorder.getMaxAmplitude() / 650;
            Log.d("sdfgasd", volume + "");
            if (volume != 0)
                // volume = (int) (10 * Math.log(volume) / Math.log(10)) / 7;
                volume = (int) (10 * Math.log10(volume)) / 3;
            Log.d("db", volume + "");
        }
        return volume;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(player!=null) {
            player.stop();
            player.release();
        }
    }
}
