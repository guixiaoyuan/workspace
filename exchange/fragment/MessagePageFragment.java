package com.deeal.exchange.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.deeal.exchange.Bean.MessageItemBean;
import com.deeal.exchange.R;
import com.deeal.exchange.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;


public class MessagePageFragment extends Fragment {

    private View rootview;
    private ListView lvMessage;
    private MessageAdapter adapter;
    private List<MessageItemBean> items = new ArrayList<MessageItemBean>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootview == null){
            rootview = inflater.inflate(R.layout.fragment_message_page,null);
            initList();
        }
        ViewGroup parent = (ViewGroup) rootview.getParent();
        if(parent != null){
            parent.removeView(rootview);
        }
        return rootview;
    }

    private void initList(){
        lvMessage = (ListView) rootview.findViewById(R.id.lvMessage);
        adapter = new MessageAdapter(getActivity(),items);
        if(items == null){

        }else{
            lvMessage.setAdapter(adapter);
        }
    }
}