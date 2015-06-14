package com.eleyan.androidpluginunity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import java.util.List;
import com.unity3d.player.UnityPlayerActivity;

public class SharingClass extends UnityPlayerActivity {
    private static final String APP_ID = "1451806085137467";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void shareStatusFacebook(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            //name of the facebook app (to avoid confusion with messenger app)
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        // if facebook app not found, use web explorer and app id to share content
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/dialog/feed?" ;
            sharerUrl += "app_id="+APP_ID+"&";
            sharerUrl+="display=popup&";
            sharerUrl+="redirect_uri=https://www.facebook.com";
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }

    public void shareTwitter(){
        //open a dialog with web explorer and Twitter app choice
        String tweetUrl = String.format("https://twitter.com/intent/tweet?text=%s&url=%s","", "");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));

        // Narrow down to official Twitter app, if available:
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.twitter")) {
                intent.setPackage(info.activityInfo.packageName);
                break;
            }
        }

        startActivity(intent);
    }

}
