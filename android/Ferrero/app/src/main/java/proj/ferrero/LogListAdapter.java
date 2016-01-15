package proj.ferrero;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import proj.ferrero.models.Log;

/**
 * Created by tonnyquintos on 1/15/16.
 */
public class LogListAdapter extends BaseAdapter {

    ArrayList<Log> logs;

    public LogListAdapter(ArrayList<Log> logs){
        this.logs = logs;
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        return null;
    }
}
