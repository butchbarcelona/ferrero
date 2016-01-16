package proj.ferrero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import proj.ferrero.ble.BlunoLibrary;
import proj.ferrero.models.LogData;
import proj.ferrero.models.Stations;
import proj.ferrero.models.User;

public class MainNavActivity extends BlunoLibrary
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = "FERRERO";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    ImageButton btnScan;

    LogsFragment logsFragment;
    ArrayList<LogData> logs;

    SqlDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nav);

        onCreateProcess();
        serialBegin(115200);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        btnScan = (ImageButton) this.findViewById(R.id.connect);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonScanOnClickProcess();
            }
        });
        dbHelper = new SqlDatabaseHelper(this);

        //tempdata
        logs = new ArrayList<LogData>();
        logs.add(new LogData(1,"EFAWF12312", 1452918693, "Station B"));
        logs.add(new LogData(2,"EFAWF12312", 1452918693, "Station A", 1452918693, "Station B",12323,30));

        ArrayList<User> users = new ArrayList<User>();
        users.add(new User("EFAWF12312","Tons",1000));


        //add temp data to db
        for(LogData log: logs){
            dbHelper.createLog(log);
        }

        for(User user: users){
            dbHelper.createUser(user);
        }


        refreshData();

    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        if(logsFragment == null){
            logsFragment = new LogsFragment();
            logsFragment.setData(logs);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container,logsFragment)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_nav, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConectionStateChange(BlunoLibrary.connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {											//Four connection state
            case isConnected:
                btnScan.setBackgroundResource(R.drawable.btn_fab);
                //btnScan.setText("Connected");
                break;
            case isConnecting:
                btnScan.setBackgroundResource(R.drawable.btn_fab_light_green);
                //btnScan.setText("Connecting");
                break;
            case isToScan:
                btnScan.setBackgroundResource(R.drawable.btn_fab_gray);
                //btnScan.setText("Scan");
                break;
            case isScanning:
                btnScan.setBackgroundResource(R.drawable.btn_fab_light_green);
                //btnScan.setText("Scanning");
                break;
            case isDisconnecting:
                btnScan.setBackgroundResource(R.drawable.btn_fab_gray);
                //btnScan.setText("isDisconnecting");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSerialReceived(String idTag) {

        Log.e("onMessageReceive", idTag);
        idTag = idTag.replaceAll("(\\r|\\n)", "");

        //EFAWF12312

        try {
            checkUserTag(idTag.split(":")[1], idTag.split(":")[0]);
        }catch(Exception e){

        }

    }


    public boolean checkUserTag(String userId, String tappedAt){
        ArrayList<LogData> userLogs = dbHelper.getAllLogsOfUserWithoutTimeout(userId);
        User user = dbHelper.getUser(userId);


        if(user != null) {
            if (userLogs.size() > 0) {
                //exit state
                Log.e(MainNavActivity.TAG,"exit state");

                LogData currLog = userLogs.get(0);
                //compute duration
                long duration = System.currentTimeMillis() - currLog.getTimeIn();
                currLog.setDuration(duration);

                //compute fare
                int fare = Math.abs(Stations.getStation(tappedAt).getIndex()
                        - Stations.getStation(currLog.getStationStart()).getIndex())*10+10;

                currLog.setFare(fare);

                user.setLoad(user.getLoad() - fare);

                dbHelper.updateLogExit(currLog);
                dbHelper.updateUser(user);

                refreshData();
            } else {
                //entrance state
                Log.e(MainNavActivity.TAG,"enter state");

                if (user.getLoad() >= 30) {
                    return false;
                }

                dbHelper.createLog(new LogData(0, userId, System.currentTimeMillis(), tappedAt));
                refreshData();
            }
        }else{
            //TODO: if user is not in database
            Log.e(MainNavActivity.TAG,"no user in db "+userId);
        }

        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();														//onStop Process by BlunoLibrary
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

    protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();														//onResume Process by BlunoLibrary
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void refreshData(){
        logs = dbHelper.getAllLogs();

        if(logsFragment != null) {
            logsFragment.setData(logs);
        }
    }
}
