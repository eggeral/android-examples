package ese.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// RecyclerView is a more efficient implementation of a "list"
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ese.recyclerview";

    private static class Flight {
        public String number;
        public String from;
        public String to;

        public Flight(String number, String from, String to) {
            this.number = number;
            this.from = from;
            this.to = to;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // == 01 add recycler view to Layout
        // == 02 add items to the list
        List<Flight> flights = Arrays.asList(
                new Flight("LH3454", "GRZ", "FRA"),
                new Flight("OS101", "GRZ", "DUS"),
                new Flight("BM2001", "MUC", "RST")
        );

        // == 03 create flight row layout
        // == 04 create adapter

        // == 05 put it all together

        FlightsAdapter flightsAdapter = new FlightsAdapter();

        RecyclerView flightList = findViewById(R.id.recycler_view);
        flightList.setLayoutManager(new LinearLayoutManager(this));
        flightList.setAdapter(flightsAdapter);
        flightsAdapter.displayFlights(flights);


    }

    private static class FlightsAdapter extends RecyclerView.Adapter<FlightsAdapter.ViewHolder> {

        static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView numberTextView;
            private TextView fromTextView;
            private TextView toTextView;

            private Flight flight;

            ViewHolder(final View itemView) {
                super(itemView);

                numberTextView = itemView.findViewById(R.id.flight_row_numer);
                fromTextView = itemView.findViewById(R.id.flight_row_from);
                toTextView = itemView.findViewById(R.id.flight_row_to);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i(TAG, "onClick: " + flight.number);
                    }
                });

            }

            void updateView(final Flight flight) {
                numberTextView.setText(flight.number);
                fromTextView.setText(flight.from);
                toTextView.setText(flight.to);
                this.flight = flight;
            }

        }

        private List<Flight> flights = new ArrayList<>();

        void displayFlights(List<Flight> flights) {

            this.flights.clear();
            if (flights != null) {
                this.flights.addAll(flights);
            }

            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_list_row, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.updateView(flights.get(position));
        }

        @Override
        public int getItemCount() {
            return flights.size();
        }
    }
}
