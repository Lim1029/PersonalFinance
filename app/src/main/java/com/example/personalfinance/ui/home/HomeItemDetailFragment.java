package com.example.personalfinance.ui.home;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.personalfinance.R;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class HomeItemDetailFragment extends DialogFragment {
    private String itemId = "";
    private Button btnBack;

    public HomeItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        itemId = getArguments().getString("itemId");
        final View root = inflater.inflate(R.layout.fragment_home_item_detail, container, false);
        final EditText edtTitle = root.findViewById(R.id.edtTitleGridItem);
        final EditText edtAmount = root.findViewById(R.id.edtAmountGridItem);
        final EditText edtRemarks = root.findViewById(R.id.edtRemarksGridItem);
        final EditText edtDate = root.findViewById(R.id.edtDateGridItem);
        final EditText edtMoneyType = root.findViewById(R.id.edtMoneyTypeGridItem);
        btnBack = root.findViewById(R.id.btnBackGridItem);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Money");
        query.whereEqualTo("objectId",itemId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null){
                    edtTitle.setText(object.getString("title"));
                    edtAmount.setText("RM"+object.getDouble("amount"));
                    edtDate.setText("Recorded on " + object.getDate("date"));
                    edtMoneyType.setText("By " + object.getString("moneyType"));
                    edtRemarks.setText(object.getString("remarks"));

                    switch (object.getString("type")){
                        case "spending":
                            root.setBackgroundResource(R.drawable.griditembackground_spending);
                            break;
                        case "earning":
                            root.setBackgroundResource(R.drawable.griditembackground_earning);
                            break;
                        case "lending":
                            root.setBackgroundResource(R.drawable.griditembackground_lending);
                            break;
                        case "borrowing":
                            root.setBackgroundResource(R.drawable.griditembackground_borrowing);
                            break;
                        default:
                            break;
                    }
                } else {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return root;

    }


}
