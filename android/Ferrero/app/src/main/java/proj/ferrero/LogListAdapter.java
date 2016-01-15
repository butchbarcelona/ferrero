package proj.ferrero;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import proj.ferrero.models.LogData;

/**
 * Created by tonnyquintos on 1/15/16.
 */
public class LogListAdapter extends BaseAdapter {

    ArrayList<LogData> logDatas;

    public LogListAdapter(ArrayList<LogData> logDatas){
        this.logDatas = logDatas;
    }

    @Override
    public int getCount() {
        return logDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return logDatas.get(position);
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
