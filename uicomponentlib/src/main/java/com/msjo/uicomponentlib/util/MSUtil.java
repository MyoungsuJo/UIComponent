package com.msjo.uicomponentlib.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


public final class MSUtil {

    private static final String TAG = "MSUtil";

    public final static void clearApplicationCache(final Context c, final File file){
        File dir = null;

        if(c == null){
            return;
        }

        if(file == null){
            dir = c.getCacheDir();
        }else{
            dir = file;
        }

        if(dir == null){
            return;
        }

        File[] children = dir.listFiles();
        try{
            for(int i=0 ; i<children.length;i++){
                if(children[i].isDirectory()){
                    clearApplicationCache(c, children[i]);
                }else{
                    children[i].delete();
                }
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    public final static String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }


    /**
     * <pre>
     * 설명 : 내장 브라우저 실행(앱 버전체크 동작등으로 인해 앱스토어 업데이트 페이지 유도를 할 경우 등)
     * </pre>
     * <p>
     * msjo
     * 2018. 12. 14.
     */
    public final static void startBrowser(final Activity a, final String url) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // 특정단말..실행할 앱을 찾지 못해 Exception이 발생하는 경우도 있어 추가
            i.setComponent(getDefaultBrowserComponent(a));
            // 특정단말..실행할 앱을 찾지 못해 Exception이 발생하는 경우도 있어 추가
            a.startActivity(i);
        }catch (ActivityNotFoundException e){
            Log.e(TAG, e.getMessage());
        }
    }

    private final static ComponentName getDefaultBrowserComponent(final Context c) {
        Intent i = new Intent().setAction(Intent.ACTION_VIEW).setData(new Uri.Builder().scheme("http").authority("x.y.z").appendQueryParameter("q", "x").build());
        PackageManager pm = c.getPackageManager();
        ResolveInfo default_ri = pm.resolveActivity(i, 0); // may be a chooser
        ResolveInfo browser_ri = null;
        List<ResolveInfo> rList = pm.queryIntentActivities(i, 0);
        for (ResolveInfo ri : rList) {
            if (ri.activityInfo.packageName.equals(default_ri.activityInfo.packageName) && ri.activityInfo.name.equals(default_ri.activityInfo.name)) {
                return ri2cn(default_ri);
            } else if ("com.android.browser".equals(ri.activityInfo.packageName)) {
                browser_ri = ri;
            }
        }
        if (browser_ri != null) {
            return ri2cn(browser_ri);
        } else if (rList.size() > 0) {
            return ri2cn(rList.get(0));
        } else if (default_ri == null) {
            return null;
        } else {
            return ri2cn(default_ri);
        }
    }

    private final static ComponentName ri2cn(ResolveInfo ri) {
        return new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name);
    }

    public final static boolean hasString(final String str){
        if(str == null || str.length() <= 0){
            return false;
        }else{
            return true;
        }
    }


    public final static boolean isYes(final String str){
        if(str == null || str.length() <= 0){
            return false;
        }else{
            if(str.compareToIgnoreCase("Y") == 0) {
                return true;
            }else{
                return false;
            }
        }
    }

    public final static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            // noinspection deprecation
            return Html.fromHtml(source);
        }
        return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
    }


    /**
     *
     * <pre>
     * 설명 : dp 값을 px 값으로 변환
     * </pre>
     * <p>
     * msjo
     * 2018. 12. 28.
     */
    public final static int convertDpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }



    public final static SpannableString createDoubleLineSpannableTitle(final Context c, final String str1, final String str2, final int secondLineTxtDP){

        String prevStr = str1 + "\n";
        String str = str1 + "\n" + str2;

        SpannableString ss=  new SpannableString(str);
        ss.setSpan(new StyleSpan(Typeface.BOLD) ,0, prevStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new AbsoluteSizeSpan(MSUtil.convertDpToPx(c, secondLineTxtDP)), prevStr.length(), str.length(), 0);


        return ss;
    }


    /**
     *
     * <pre>
     * 설명 : 비행기 모드 상태인지 체크
     * </pre>
     * <p>
     * msjo
     *
     * 2019. 01. 25.
     */
    public final static boolean isAirplaneModeOn(Context context){
        boolean isAirplaneMode;

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) == 1;
        }else{
            isAirplaneMode = Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        }

        return isAirplaneMode;
    }

    /**
     *
     * <pre>
     * 설명 : 랜덤한 자리수의 스트링 반환
     * </pre>
     * <p>
     * msjo
     *
     * 2019. 01. 28.
     */
    public final static String getRandomString(int length)
    {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();

        String chars[] = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",");

        for (int i=0 ; i<length ; i++)
        {
            buffer.append(chars[random.nextInt(chars.length)]);
        }


        return buffer.toString();
    }


    public static final boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            return true;
        }
        return false;
    }


    /**
     * 
     * <pre>
     * 설명 : 색상을 반환
     *       - 특정 SDK 버전 이후 getColor 에 추가 파라메터를 필요로 하기때문에 생성해둠
     * </pre>
     * <p>
     * msjo
     *
     * @param c
     * @param id 컬러 아이디 값(defined in colors.xml)
     * 
     * 2019. 02. 25.
     */
    public static int getColor(final Context c, final int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return c.getResources().getColor(id, c.getTheme());
        }else {
            return c.getResources().getColor(id);
        }
    }

    public static String getDate(final String dateFormat){
        Calendar calendar  = Calendar.getInstance();
        DateFormat d = new SimpleDateFormat(dateFormat);

        d.setCalendar(calendar);

        return d.format(calendar.getTime());
    }
}
