package taroichinose.java_conf.gr.jp.widgetclicker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetClicker extends AppWidgetProvider {

    private static final String COOKIE_CLICK  = "android.appwidget.action.CLICK";
    static int CookieCount = 0;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.clicker_layout);
        remoteViews.setOnClickPendingIntent(R.id.cookieButton, clickCookie(context));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        Log.d("onUpdate", "onUpdate");
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("onReceive", "onReceive");

        super.onReceive(context, intent);

        //RemoteViewって色んな場所に定義していいものなのだろうか
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.clicker_layout);
        //onReceiveの中で定義するようなものではないと思う
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName myWidget = new ComponentName(context, WidgetClicker.class);
        int[] mAppWidgetIds = appWidgetManager.getAppWidgetIds(myWidget);


        switch (intent.getAction()) {
            case COOKIE_CLICK:
                CookieCount++;
                Log.d("クッキーの枚数：", Integer.toString(CookieCount));
                remoteViews.setTextViewText(R.id.nowCookieNum, "枚数： " + CookieCount);
                break;
            default: break;
        }

        appWidgetManager.updateAppWidget(mAppWidgetIds, remoteViews);
    }

    static private PendingIntent clickCookie(Context context) {

        Intent intent = new Intent();
        intent.setAction(COOKIE_CLICK);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
