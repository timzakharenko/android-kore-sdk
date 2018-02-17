package kore.botssdk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import kore.botssdk.R;
import kore.botssdk.fragment.ComposeFooterFragment;
import kore.botssdk.models.FormActionTemplate;
import kore.botssdk.models.QuickReplyTemplate;
import kore.botssdk.utils.BundleConstants;
import kore.botssdk.utils.BundleUtils;
import kore.botssdk.view.viewHolder.FormActionViewHolder;

/**
 * Created by Ramachandra Pradeep on 09-Feb-18.
 */

public class FormActionsAdapter extends RecyclerView.Adapter<FormActionViewHolder> {
    private ArrayList<FormActionTemplate> formActionTemplateArrayList;
    Context context;
    LayoutInflater layoutInflater;
    RecyclerView parentRecyclerView;

    ComposeFooterFragment.ComposeFooterInterface composeFooterInterface;

    public FormActionsAdapter(Context context, RecyclerView parentRecyclerView) {
        this.context = context;
        this.parentRecyclerView = parentRecyclerView;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public FormActionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = layoutInflater.inflate(R.layout.form_action_item_layout, null);
        return new FormActionViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(FormActionViewHolder holder, int position) {
        final FormActionTemplate formActionTemplate = formActionTemplateArrayList.get(position);

        holder.getFormActionTitle().setText(formActionTemplate.getTitle());

        holder.getFormActionRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =  parentRecyclerView.getChildPosition(v);
                FormActionTemplate formTemplate = formActionTemplateArrayList.get(position);
                if(formTemplate != null){
                    composeFooterInterface.onFormActionButtonClicked(formTemplate);
                }
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {

        if (formActionTemplateArrayList == null) {
            return 0;
        } else {
            return formActionTemplateArrayList.size();
        }
    }
    public void setQuickReplyTemplateArrayList(ArrayList<FormActionTemplate> formActionTemplateArrayList) {
        this.formActionTemplateArrayList = formActionTemplateArrayList;
    }

    public void setComposeFooterInterface(ComposeFooterFragment.ComposeFooterInterface composeFooterInterface) {
        this.composeFooterInterface = composeFooterInterface;
    }

}