package com.androiddevs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Student> studentList;
    private Button btnSelection;
    private TextView thankyou;
    ArrayList<Boolean> chkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chkStatus = new ArrayList<Boolean>();
        for (int i = 1; i <= 15; i++) {
            chkStatus.add(false);
        }

        thankyou = (TextView) findViewById(R.id.tvThankyou);
        thankyou.setVisibility(View.GONE);
        btnSelection = (Button) findViewById(R.id.btnShow);
        studentList = new ArrayList<Student>();
        for (int i = 1; i <= 15; i++) {
            Student st = new Student("Android " + i, "Androiddevs" + i
                    + "@gmail.com", false);
            studentList.add(st);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // create an Object for Adapter
        mAdapter = new CardViewDataAdapter(studentList);
        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        btnSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                List<Student> stList = ((CardViewDataAdapter) mAdapter)
                        .getStudentist();
                for (int i = 0; i < stList.size(); i++) {
                    Student singleStudent = stList.get(i);
                    if (singleStudent.isSelected() == true) {

                        data = data + ", " + singleStudent.getName().toString();
      /*
       * Toast.makeText( CardViewActivity.this, " " +
       * singleStudent.getName() + " " +
       * singleStudent.getEmailId() + " " +
       * singleStudent.isSelected(),
       * Toast.LENGTH_SHORT).show();
       */
                    }
                }
                Toast.makeText(MainActivity.this,
                        "Selected Students: \n" + data, Toast.LENGTH_LONG)
                        .show();
            }
        });

    }

    private void getSelectedChkBox() {
        String data = "";
        List<Student> stList = ((CardViewDataAdapter) mAdapter)
                .getStudentist();
        for (int i = 0; i < stList.size(); i++) {
            Student singleStudent = stList.get(i);
            if (singleStudent.isSelected() == true) {

                data = data + ", " + singleStudent.getName().toString();
      /*
       * Toast.makeText( CardViewActivity.this, " " +
       * singleStudent.getName() + " " +
       * singleStudent.getEmailId() + " " +
       * singleStudent.isSelected(),
       * Toast.LENGTH_SHORT).show();
       */
            }
        }
      /*  Toast.makeText(MainActivity.this,
                "Selected Students: \n" + data, Toast.LENGTH_LONG)
                .show();*/
        thankyou.setText(removeFirstChar(data));
    }

    public String removeFirstChar(String s) {
        return s.substring(1);
    }

    public class CardViewDataAdapter extends
            RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {

        private List<Student> stList;

        public CardViewDataAdapter(List<Student> students) {
            this.stList = students;
        }

        // Create new views
        @Override
        public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
            // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.cardview_row, null);
            // create ViewHolder
            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final int pos = position;
            viewHolder.tvName.setText(stList.get(position).getName());
            viewHolder.tvEmailId.setText(stList.get(position).getEmailId());
            viewHolder.chkSelected.setChecked(stList.get(position).isSelected());
            viewHolder.chkSelected.setTag(stList.get(position));
            viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Student contact = (Student) cb.getTag();
                    contact.setSelected(cb.isChecked());
                    stList.get(pos).setSelected(cb.isChecked());

                    chkStatus.set(pos, cb.isChecked());
                    boolean contains = chkStatus.contains(true);
                    if (contains) {
                        thankyou.setVisibility(View.VISIBLE);
                        getSelectedChkBox();
                    } else {
                        thankyou.setVisibility(View.GONE);
                    }
                  /*  Toast.makeText(
                            v.getContext(),
                            "Clicked on Checkbox: " + cb.getText() + " is "
                                    + cb.isChecked() + " Positon " + pos, Toast.LENGTH_LONG).show();*/
                }
            });

        }

        // Return the size arraylist
        @Override
        public int getItemCount() {
            return stList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvName;
            public TextView tvEmailId;

            public CheckBox chkSelected;

            public Student singlestudent;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);

                tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);

                tvEmailId = (TextView) itemLayoutView.findViewById(R.id.tvEmailId);
                chkSelected = (CheckBox) itemLayoutView
                        .findViewById(R.id.chkSelected);

            }

        }

        // method to access in activity after updating selection
        public List<Student> getStudentist() {
            return stList;
        }

    }

}