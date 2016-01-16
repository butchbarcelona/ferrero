package proj.ferrero;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import proj.ferrero.models.LogData;

/**
 * Created by tonnyquintos on 1/16/16.
 */
public class LogsFragment extends Fragment {

    ListView lvLogs;
    LogListAdapter adapter;

    public LogsFragment(){
        //logs = new ArrayList<LogData>();

        adapter = new LogListAdapter(new ArrayList<LogData>());
    }

    public void setData(ArrayList<LogData> logs){
        //this.logs = logs;
        if(adapter != null) {
            adapter.setData(logs);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main_nav, container, false);
        lvLogs = (ListView) rootView.findViewById(R.id.lv_logs);
        lvLogs.setAdapter(adapter);

        return rootView;
    }


    public class LogListAdapter extends BaseAdapter{

        ArrayList<LogData> logs;
        public LogListAdapter(ArrayList<LogData> logs){
            this.logs = logs;
        }

        public void setData(ArrayList<LogData> logs){
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

            View view = LogsFragment.this.getActivity().getLayoutInflater().inflate(R.layout.listitem_log, null);

            TextView tvTimeIn, tvTimeOut, tvStart, tvEnd;
            tvTimeIn = (TextView) view.findViewById(R.id.tvTimeIn);
            tvTimeOut = (TextView) view.findViewById(R.id.tvTimeOut);
            tvStart = (TextView) view.findViewById(R.id.tvStationStart);
            tvEnd = (TextView) view.findViewById(R.id.tvStationEnd);


            tvTimeIn.setText(Util.epochToStringFormat(logs.get(position).getTimeIn(),"hh:mm:ss"));
            tvTimeOut.setText(Util.epochToStringFormat(logs.get(position).getTimeOut(),"hh:mm:ss"));
            tvStart.setText(logs.get(position).getStationStart()+"");
            tvEnd.setText(logs.get(position).getStationEnd()+"");




            return view;
        }
    }

}
