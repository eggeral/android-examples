package ese.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        if (getArguments() != null) {
            TextView bookText = view.findViewById(R.id.book_text);
            bookText.setText(getArguments().getString("title"));
        }
        return view;
    }

    public void showBook(String title) {
        TextView bookText = getView().findViewById(R.id.book_text);
        bookText.setText(title);
    }
}
