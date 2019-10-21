package uk.ac.newcastle.dre_ip.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import uk.ac.newcastle.dre_ip.R;


/**
 * The fragment of setup server.
 */
public class Server_Fragment extends Fragment {

    private EditText serverNameValue, serverAddressValue;
    private Button serverInformationSubmit;
    private ListView serverListValue;
    private ArrayAdapter<String> mServerListAdapter;
    private ArrayList<Map<String, String>> item;
    private ArrayList<String> mServerList;
    private String serverAddress;


    /**
     * New instance server fragment.
     *
     * @return the server fragment
     */
    public static Server_Fragment newInstance() {
        Server_Fragment fragment = new Server_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_server, container, false);

        serverNameValue = (EditText) view.findViewById(R.id.serverNameValue);
        serverAddressValue = (EditText) view.findViewById(R.id.serverAddressValue);
        serverInformationSubmit = (Button) view.findViewById(R.id.serverInformationSubmit);

        item = new ArrayList<>();
        mServerList = new ArrayList<>();

        mServerListAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mServerList);
        serverListValue = (ListView) view.findViewById(R.id.serverListValue);
        serverListValue.setAdapter(mServerListAdapter);
        getPreferences();

        serverListValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   @Override
                                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                       String temp = serverListValue.getItemAtPosition(position).toString().trim();
                                                       for (Map<String, String> t : item) {
                                                           if (t.get(temp) != null) {
                                                               serverAddress = t.get(temp);
                                                               break;
                                                           }
                                                       }

                                                       SharedPreferences sharedPref = getActivity().getSharedPreferences("selectedconnection", Context.MODE_PRIVATE);
                                                       SharedPreferences.Editor editor = sharedPref.edit();
                                                       editor.putString("serverAddress", serverAddress);
                                                       editor.commit();
                                                       Toast.makeText(getContext(),"The server " +temp+" has been selected.",Toast.LENGTH_SHORT).show();

                                                   }
                                               }
        );

        serverInformationSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String name, address;
                name = serverNameValue.getText().toString();
                address = serverAddressValue.getText().toString();

                SharedPreferences sharedPref = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                Map<String, String> in = new HashMap<>();
                in.put(name, address);
                for (String s : in.keySet()) {
                    editor.putString(s, in.get(s));
                }
                editor.commit();
                getPreferences();
                serverNameValue.setText("");
                serverAddressValue.setText("");
                serverNameValue.requestFocus();
            }
        });


        return view;
    }

    /**
     * Retrieve the server addresses from SharedPreferences.
     */
    private void getPreferences() {
        item.clear();
        mServerList.clear();
        SharedPreferences sharedPref = getActivity().getSharedPreferences("server", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = sharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Map<String, String> z = new HashMap<>();
            z.put(entry.getKey(), entry.getValue().toString());
            item.add(z);
            mServerList.add(entry.getKey());
            mServerListAdapter.notifyDataSetChanged();
        }
    }
}
