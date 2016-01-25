package proj.ferrero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import proj.ferrero.models.User;

/**
 * Created by tonnyquintos on 1/16/16.
 */
public class UsersFragment extends Fragment {

    ListView lvUsers;
    UserListAdapter adapter;

    SqlDatabaseHelper dbHelper;

    ImageButton btnAdd;

    public UsersFragment(){

        adapter = new UserListAdapter(new ArrayList<User>());
    }

    public void setData(ArrayList<User> users){
        if(adapter != null) {
            adapter.setData(users);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_users, container, false);
        lvUsers = (ListView) rootView.findViewById(R.id.lv_users);
        lvUsers.setAdapter(adapter);

        btnAdd = (ImageButton) rootView.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UsersFragment.this.getContext(),AddUserActivity.class));
            }
        });


        return rootView;
    }





    public class UserListAdapter extends BaseAdapter{

        ArrayList<User> users;
        public UserListAdapter(ArrayList<User> users){
            this.users = users;

            dbHelper = new SqlDatabaseHelper(getActivity());
        }

        public void setData(ArrayList<User> users){
            this.users = users;

        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public Object getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = UsersFragment.this.getActivity().getLayoutInflater().inflate(R.layout.listitem_user, null);

            TextView tvTag, tvName, tvAge, tvBday, tvBlood, tvAllergies, tvMedCond, tvContactPerson, tvContactNum;
            tvName = (TextView)view.findViewById(R.id.tvName);
            tvAge = (TextView)view.findViewById(R.id.tvAge);
            tvBday = (TextView)view.findViewById(R.id.tvBday);
            tvBlood = (TextView)view.findViewById(R.id.tvBlood);
            tvAllergies = (TextView)view.findViewById(R.id.tvAllergies);
            tvMedCond = (TextView)view.findViewById(R.id.tvMedicalConditions);
            tvContactPerson = (TextView)view.findViewById(R.id.tvContactPerson);
            tvContactNum = (TextView)view.findViewById(R.id.tvContactNumber);
            tvTag = (TextView)view.findViewById(R.id.tvTag);

            tvName.setText(users.get(position).getUserName());
            tvAge.setText(users.get(position).getAge()+"");
            tvBday.setText(users.get(position).getBday());
            tvBlood.setText(users.get(position).getBloodType());
            tvAllergies.setText(users.get(position).getAllergies());
            tvMedCond.setText(users.get(position).getMedCond());
            tvContactPerson.setText(users.get(position).getContactPerson());
            tvContactNum.setText(users.get(position).getContactNum());
            tvTag.setText(users.get(position).getUserId());


            return view;
        }
    }

}
