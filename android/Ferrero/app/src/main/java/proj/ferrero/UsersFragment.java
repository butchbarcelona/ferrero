package proj.ferrero;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import proj.ferrero.models.User;

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


        dbHelper = new SqlDatabaseHelper(this.getActivity().getApplicationContext());
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
        public View getView(final int position, View convertView, ViewGroup parent) {

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

            final EditText etLoad = (EditText)view.findViewById(R.id.tv_load);

            tvName.setText(users.get(position).getUserName());
            tvAge.setText(users.get(position).getAge()+"");
            tvBday.setText(users.get(position).getBday());
            tvBlood.setText(users.get(position).getBloodType());
            tvAllergies.setText(users.get(position).getAllergies());
            tvMedCond.setText(users.get(position).getMedCond());
            tvContactPerson.setText(users.get(position).getContactPerson());
            tvContactNum.setText(users.get(position).getContactNum());
            tvTag.setText(users.get(position).getUserId());
            etLoad.setText(users.get(position).getLoad() + "");

            /*etLoad.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int load = 0;
                    if (!s.toString().isEmpty()) {
                        load = Integer.parseInt(etLoad.getText().toString());
                        users.get(position).setLoad(load);
                        //dbHelper.updateUser(users.get(position));
                        ((MainNavActivity) UsersFragment.this.getActivity()).updateUser(users.get(position));
                        refresh();
                        *//*
                        hideKeyboard();*//*

                    }
                }
            });*/
            etLoad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        int load = Integer.parseInt(etLoad.getText().toString());
                        users.get(position).setLoad(load);
                        //dbHelper.updateUser(users.get(position));
                        ((MainNavActivity) UsersFragment.this.getActivity()).updateUser(users.get(position));
                        refresh();
                    }
                }
            });

            ImageButton btnDelete = (ImageButton) view.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(UsersFragment.this.getActivity(), "Do you really want to delete " + users.get(position).getUserName() + "?", "Yes", "Cancel",
                      new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              ((MainNavActivity) UsersFragment.this.getActivity()).deleteUser(users.get(position));
                              //dbHelper.deleteUser(users.get(position));
                              //refresh();
                          }
                      }, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                          }
                      });
                }
            });


            return view;
        }
    }


    public void showDialog(Context ctx, String message, String okButton, String cancelButton
      , DialogInterface.OnClickListener positiveListener
      , DialogInterface.OnClickListener negativeListener){
        new AlertDialog.Builder(ctx)
          .setTitle(MainNavActivity.TAG)
          .setMessage(message)
          .setPositiveButton(okButton, positiveListener)
          .setNegativeButton(cancelButton, negativeListener)
          .setIcon(android.R.drawable.ic_dialog_alert)
          .show();
    }
    public void refresh(){
        adapter.setData(dbHelper.getAllUsers());
        adapter.notifyDataSetChanged();
    }

    private void hideKeyboard(){
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
