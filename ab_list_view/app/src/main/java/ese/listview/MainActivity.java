package ese.listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ese.listview";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 add list view to Layout
        // == 02 add items to the list
        ListView listView = (ListView) findViewById(R.id.list_view);

        List<String> items = Arrays.asList("item1", "item2", "item3");
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
//                final String item = (String) parent.getItemAtPosition(position);
//                Log.i(TAG, "onItemClick: item clicked: " + item);
//            }
//
//        });

        // == 03 custom item text views
        // R.layout.bold_large_item points to a layout xml defining a TextView
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.bold_large_item, items);
//        listView.setAdapter(adapter);

        // == 04 custom item layouts
        // R.layout.row_item defines the layout
        // R.id.row_text id of the text view
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row_item, R.id.row_text, items);
//        listView.setAdapter(adapter);

        // == 05 custom adapter
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, items);
        listView.setAdapter(adapter);

        // Exercise
        // Create a list view of strings. Open a child activity on key press
        // Use a list of class Person. (name, address). Show the name of the first person
        // bold large in the first row. Show the address in a smaller font in a second row.

    }

    private static class CustomArrayAdapter extends ArrayAdapter<String> {

        public CustomArrayAdapter(Context context, List<String> values) {
            super(context, -1, values);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.row_item, null);
            TextView textView = rowView.findViewById(R.id.row_text);
            textView.setText("--- " + getItem(position) + " ---");
            return rowView;
        }
    }

}
