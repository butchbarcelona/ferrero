package proj.ferrero;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
        //users = new ArrayList<LogData>();

        adapter = new LogListAdapter(new ArrayList<LogData>());
    }

    public void setData(ArrayList<LogData> logs){
        //this.users = users;
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

            TextView tvTimeIn, tvTimeOut, tvStart, tvEnd, tvName;
            tvTimeIn = (TextView) view.findViewById(R.id.tvTimeIn);
            tvTimeOut = (TextView) view.findViewById(R.id.tvTimeOut);
            tvStart = (TextView) view.findViewById(R.id.tvStationStart);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvName.setText(logs.get(position).getUserName());

            tvEnd = (TextView) view.findViewById(R.id.tvStationEnd);

            LinearLayout llStart = (LinearLayout) view.findViewById(R.id.ll_in);
            LinearLayout llEnd = (LinearLayout) view.findViewById(R.id.ll_out);

            View lineView = (View) view.findViewById(R.id.line_view);


            if(logs.get(position).getStationEnd() == null || logs.get(position).getStationEnd().isEmpty()){

                tvEnd.setText("");
                tvTimeOut.setText("--:--:--");
                lineView.setBackgroundResource(R.drawable.red_line_dotted);
            } else {

                lineView.setBackgroundColor(getResources().getColor(R.color.blue_dark));
                llStart.setBackgroundResource(R.drawable.blue_circle);
                llEnd.setBackgroundResource(R.drawable.blue_circle);
                tvEnd.setText(logs.get(position).getStationEnd()+"");
                tvTimeOut.setText(Util.epochToStringFormat(logs.get(position).getTimeOut(),"HH:mm"));

                TextView tvDuration = (TextView) view.findViewById(R.id.tvDuration);
                TextView tvFare = (TextView) view.findViewById(R.id.tvFare);

                tvDuration.setText(Util.getStringDuration(logs.get(position).getDuration()));
                tvFare.setText("P"+logs.get(position).getFare());
            }

            tvStart.setText(logs.get(position).getStationStart()+"");
            tvTimeIn.setText(Util.epochToStringFormat(logs.get(position).getTimeIn(),"HH:mm"));

            return view;
        }
    }

}
