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

    //this is the list options listview adapter's constructor. It takes in the context of the
    //activity and a String array of options and then initializes private variables with them

    public OptionsAdapter(Context c, String [] options) {
        this.options = options;
        this.mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //returns how many items in list

    @Override
    public int getCount() {
        return options.length;
    }


    //returns the item at position i

    @Override
    public Object getItem(int i) {
        return options[i];
    }

    // returns the id of an item at position i

    @Override
    public long getItemId(int i) {
        return i;
    }

    //returns the view desired for each list item

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.list_view_format, null);
        TextView optionTextView = (TextView) v.findViewById(R.id.optionTextView);
        optionTextView.setText(options[i]);

        return v;
    }
}
