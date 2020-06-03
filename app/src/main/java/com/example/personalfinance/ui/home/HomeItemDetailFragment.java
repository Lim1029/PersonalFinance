package com.example.personalfinance.ui.home;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.personalfinance.R;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

public class HomeItemDetailFragment extends DialogFragment {
    public interface isItemUpdated{
        void sendResult(Boolean result);
    }
    public isItemUpdated mIsItemUpdated;

    private Boolean isUpdated = false;

    private String itemId = "";
    private Button btnBackCancel;
    private Button btnEditSave;
    private ProgressBar progressBarSave;
    private ProgressBar gridItemProgressCircle;
    private ImageView imgItemDetail;
    private ProgressBar imgProgressCircle;

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
        btnBackCancel = root.findViewById(R.id.btnBackGridItem);
        btnEditSave = root.findViewById(R.id.btnEditSaveGridItem);
        progressBarSave = root.findViewById(R.id.progressBarSave);
        gridItemProgressCircle = root.findViewById(R.id.gridItemProgressBar);
        imgItemDetail = root.findViewById(R.id.imgItemDetail);
        imgProgressCircle = root.findViewById(R.id.homeGridItemDetailImgProgressBar);

        final ParseQuery<ParseObject> query = ParseQuery.getQuery("Money");
        query.whereEqualTo("objectId",itemId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(e==null){
                    edtTitle.setText(object.getString("title"));
                    edtAmount.setText(object.getDouble("amount")+"");
                    edtDate.setText(object.getString("date"));
                    edtMoneyType.setText(object.getString("moneyType"));
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
                    gridItemProgressCircle.setVisibility(View.GONE);

                    ParseFile imageFile = (ParseFile)object.get("picture");
                    imageFile.getDataInBackground(new GetDataCallback() {
                        public void done(byte[] data, ParseException e) {
                            if (e == null) {
                                imgItemDetail.setImageBitmap(BitmapFactory.decodeByteArray(data,0,data.length));
                            } else {
                                // something went wrong
                            }
                            imgProgressCircle.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBackCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (btnBackCancel.getTag().toString()){
                    case "Back":
                        mIsItemUpdated.sendResult(isUpdated);
                        dismiss();
                        break;
                    case "Cancel":
                        edtAmount.setEnabled(false);
                        edtDate.setEnabled(false);
                        edtMoneyType.setEnabled(false);
                        edtRemarks.setEnabled(false);
                        edtTitle.setEnabled(false);
                        btnEditSave.setTag("Edit");
                        btnEditSave.setText("Edit");
                        btnBackCancel.setText("Back");
                        btnBackCancel.setTag("Back");
                        break;

                }

            }
        });

        btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (btnEditSave.getTag().toString()){
                    case "Edit":
                        edtAmount.setEnabled(true);
                        edtDate.setEnabled(true);
                        edtMoneyType.setEnabled(true);
                        edtRemarks.setEnabled(true);
                        edtTitle.setEnabled(true);
                        btnEditSave.setTag("Save");
                        btnEditSave.setText("Save");
                        btnBackCancel.setText("Cancel");
                        btnBackCancel.setTag("Cancel");
                        break;
                    case "Save":
                        edtAmount.setEnabled(false);
                        edtDate.setEnabled(false);
                        edtMoneyType.setEnabled(false);
                        edtRemarks.setEnabled(false);
                        edtTitle.setEnabled(false);
                        btnEditSave.setTag("Edit");
                        btnEditSave.setText("Edit");
                        btnBackCancel.setText("Back");
                        btnBackCancel.setTag("Back");

                        progressBarSave.setVisibility(View.VISIBLE);

                        query.getInBackground(itemId, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {

                                if(e==null){

                                    object.put("amount", Double.parseDouble(edtAmount.getText().toString()));
                                    object.put("moneyType", edtMoneyType.getText().toString());
                                    object.put("remarks", edtRemarks.getText().toString());
                                    object.put("title", edtTitle.getText().toString());
//                                    object.put("date",edtDate.getText().toString());
                                    object.saveInBackground();
                                    Toast.makeText(getContext(),"Item updated!",Toast.LENGTH_SHORT).show();

                                    isUpdated = true;



                                } else {
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                progressBarSave.setVisibility(View.GONE);
                            }
                        });

                        break;
                }
            }
        });


        return root;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIsItemUpdated = (isItemUpdated) getTargetFragment();
    }
}
