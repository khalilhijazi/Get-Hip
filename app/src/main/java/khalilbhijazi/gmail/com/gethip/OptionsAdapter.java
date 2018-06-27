package khalilbhijazi.gmail.com.gethip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OptionsAdapter extends BaseAdapter{

    private String [] options;
    private LayoutInflater mInflater;

    public OptionsAdapter(Context c, String [] options) {
        this.options = options;
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return options.length;
    }

    @Override
    public Object getItem(int i) {
        return options[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.list_view_format, null);
        TextView optionTextView = (TextView) v.findViewById(R.id.optionTextView);
        optionTextView.setText(options[i]);

        return v;
    }
}
