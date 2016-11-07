package lps.com.br.find_it;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private ArrayList<Item> items;
    private Context context;

    ListItemAdapter(Context context, ArrayList<Item> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        @SuppressLint("InflateParams") View rootView = LayoutInflater.from(context).inflate(R.layout.list_item, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ListItemAdapter.ViewHolder viewHolder, int i) {
        viewHolder.lbNome.setText(items.get(i).getNomeItem());
        viewHolder.lblData.setText(items.get(i).getData());
        viewHolder.lblCategoria.setText(context.getString(R.string.lbl_category) + items.get(i).getCategoria());
        viewHolder.lblStatus.setText(context.getString(R.string.lbl_status) + items.get(i).getStatus());
        viewHolder.lblDescricao.setText(context.getString(R.string.lbl_description) + items.get(i).getDescricao());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView lbNome;
        private TextView lblData;
        private TextView lblCategoria;
        private TextView lblStatus;
        private TextView lblDescricao;
        ViewHolder(View view) {
            super(view);
            lbNome = (TextView)view.findViewById(R.id.lbl_titulo);
            lblData = (TextView)view.findViewById(R.id.lbl_data);
            lblCategoria = (TextView)view.findViewById(R.id.lbl_categoria);
            lblStatus = (TextView)view.findViewById(R.id.lbl_status);
            lblDescricao = (TextView)view.findViewById(R.id.lbl_desc);
        }
    }

}
