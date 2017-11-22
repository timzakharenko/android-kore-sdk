package kore.botssdk.view;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import kore.botssdk.R;
import kore.botssdk.application.AppControl;
import kore.botssdk.models.BotTableDataModel;
import kore.botssdk.view.viewUtils.LayoutUtils;
import kore.botssdk.view.viewUtils.MeasureUtils;

/**
 * Created by Ramachandra Pradeep on 30-Oct-17.
 */

public class TableView extends ViewGroup {
    private Context mContext;
    TableLayout mTable;
    int dp1;
    public TableView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.bot_table_view, this, true);
        dp1 = (int) AppControl.getInstance().getDimensionUtil().dp1;
        mTable = (TableLayout) findViewById(R.id.tableView);
//        setBackgroundColor(Color.BLUE);
    }

    public void populateTableView(BotTableDataModel data){
        if(data != null){
/*            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10,10,10,10);*/
            mTable.removeAllViews();
            int size = data.getHeaders().size();
            int rowSize = data.getRows().size();

            TableRow tr = new TableRow(mContext);
            tr.setBackgroundResource(R.drawable.tableview_cell_shape);
//                if(i%2==0)tr.setBackgroundColor(Color.GRAY);
//                tr.setId(100+i);
//                tr.setBackgroundColor(Color.GRAY);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            //  tr.setPadding(10,10,10,10);

            //Create two columns to add as table data
            // Create a TextView to add date
            for(int i = 0; i < size; i++) {
                TextView txtHeader = new TextView(mContext);
                txtHeader.setId(200 + i);
                //   txtHeader.setLayoutParams(layoutParams);
                txtHeader.setText(data.getHeaders().get(i).getTitle());
                txtHeader.setGravity(Gravity.CENTER);
                txtHeader.setPadding(10, 20, 10, 20);
                txtHeader.setTextColor(Color.BLACK);
                txtHeader.setTextSize(14);
                tr.addView(txtHeader);
            }
            mTable.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            for(int j=0; j< rowSize; j++){
                TableRow trdata = new TableRow(mContext);
                //  trdata.setPadding(10,10,10,10);
//                if(i%2==0)tr.setBackgroundColor(Color.GRAY);
//                tr.setId(100+i);
//                trdata.setBackgroundColor(Color.GRAY);
                trdata.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
                trdata.setBackgroundResource(R.drawable.tableview_cell_shape);
                for(int k=0; k<size;k++){
                    if(data.getRows().get(j).size() != size) {
                        continue;
                    }
                    TextView txtData = new TextView(mContext);
                    txtData.setId(200 + k);
                    // txtData.setLayoutParams(layoutParams);
                    txtData.setText(data.getRows().get(j).get(k) != null?data.getRows().get(j).get(k):"");
                    txtData.setPadding(10, 20, 10, 20);
                    txtData.setTextColor(Color.BLACK);
                    txtData.setGravity(Gravity.CENTER);
                    txtData.setTextSize(14);
                    trdata.addView(txtData);
                }
                mTable.addView(trdata, new TableLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT));
            }
                /*TextView labelWEIGHT = new TextView(mContext);
                labelWEIGHT.setId(200+i);
                labelWEIGHT.setText(data.getRows().get(i).get(0)!=null?data.getRows().get(i).get(0):"");
                labelWEIGHT.setTextColor(Color.BLACK);
                tr.addView(labelWEIGHT);*/





        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int parentWidth = getMeasuredWidth();

        //get the available size of child view
        int childLeft = this.getPaddingLeft();
        int childTop = this.getPaddingTop();

        //walk through each child, and arrange it from left to right
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutUtils.layoutChild(child, childLeft, childTop);
                childTop += child.getMeasuredHeight();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxAllowedWidth = parentWidth;
        int wrapSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        int totalHeight = getPaddingTop();
        int totalWidth = getPaddingLeft();

        int childWidthSpec;
        int childHeightSpec;
        int contentWidth = 0;

        /*
         * For Pie View Layout
         */
        childWidthSpec = MeasureSpec.makeMeasureSpec(maxAllowedWidth, MeasureSpec.EXACTLY);
        int childs = getChildCount();
        int childHeight = 0;
        for(int i= 0; i<childs;i++){
            MeasureUtils.measure(getChildAt(i), childWidthSpec, wrapSpec);
            childHeight+= getChildAt(i).getMeasuredHeight();
        }
        int parentHeightSpec = MeasureSpec.makeMeasureSpec(childHeight != 0 ? childHeight + (int)50 * dp1 : 0, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, parentHeightSpec);
    }
}
