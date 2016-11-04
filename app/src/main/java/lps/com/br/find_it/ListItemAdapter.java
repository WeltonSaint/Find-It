package lps.com.br.find_it;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PC.RW on 11/09/2016.
 */
public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private ArrayList<Item> items;
    private Context context;

    public ListItemAdapter(Context context,ArrayList<Item> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ListItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.lbNome.setText(items.get(i).getNomeItem());
        viewHolder.lblData.setText(items.get(i).getData());
        viewHolder.lblCategoria.setText(viewHolder.lblCategoria.getText() + items.get(i).getCategoria());
        viewHolder.lblStatus.setText(viewHolder.lblStatus.getText() + items.get(i).getStatus());
        viewHolder.lblDescricao.setText(viewHolder.lblDescricao.getText() + items.get(i).getDescricao());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView lbNome;
        private TextView lblData;
        private TextView lblCategoria;
        private TextView lblStatus;
        private TextView lblDescricao;
        public ViewHolder(View view) {
            super(view);
            lbNome = (TextView)view.findViewById(R.id.lbl_titulo);
            lblData = (TextView)view.findViewById(R.id.lbl_data);
            lblCategoria = (TextView)view.findViewById(R.id.lbl_categoria);
            lblStatus = (TextView)view.findViewById(R.id.lbl_status);
            lblDescricao = (TextView)view.findViewById(R.id.lbl_desc);
        }
    }

}
