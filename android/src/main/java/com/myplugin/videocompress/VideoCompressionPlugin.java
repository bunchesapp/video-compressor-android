package com.myplugin.videocompress;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;


import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.LogCallback;
import com.arthenica.ffmpegkit.ReturnCode;
import com.arthenica.ffmpegkit.SessionState;
import com.arthenica.ffmpegkit.Statistics;
import com.arthenica.ffmpegkit.StatisticsCallback;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.io.File;
import java.util.ArrayList;


@CapacitorPlugin(name = "VideoCompression")
public class VideoCompressionPlugin extends Plugin {

    private VideoCompression implementation = new VideoCompression();
    ArrayList<Uri> mlistUris = new ArrayList<>();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

    @Override
    public void load() {
        super.load();
    }

    @PluginMethod
    public void compressVideoUsingFFmpeg(PluginCall pluginCall){
        String filePath = pluginCall.getString("inputVideoPath");
        String fileName =  pluginCall.getString("outputVideoName");

        File file = new File(getActivity().getExternalFilesDir(
                Environment.DIRECTORY_MOVIES), fileName+".mp4");

        Log.d("Path",file.getAbsolutePath());

        FFmpegSession session = FFmpegKit.executeAsync("-i "+filePath+" -c:v libx264 -crf 27 -preset ultrafast -c:a copy -s 960x540 "+file.getAbsolutePath(), new FFmpegSessionCompleteCallback() {

            @Override
            public void apply(FFmpegSession session) {
                SessionState state = session.getState();
                ReturnCode returnCode = session.getReturnCode();

                Log.d("TAG", "Converted");
                Log.d("TAG", state.toString());

                JSObject ret = new JSObject();
                ret.put("outputVideoPath", file.getAbsolutePath());
                pluginCall.resolve(ret);

            }
        }, new LogCallback() {

            @Override
            public void apply(com.arthenica.ffmpegkit.Log log) {

                //Log.d("log",log.toString());
                // CALLED WHEN SESSION PRINTS LOGS

            }
        }, new StatisticsCallback() {

            @Override
            public void apply(Statistics statistics) {
                JSObject ret = new JSObject();
                ret.put("statistics", statistics.toString());
                notifyListeners("onProgress", ret);
            }
        });
    }
}