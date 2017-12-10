package aurorayqz.packagecom.myapplication.ui.local;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aurorayqz.packagecom.myapplication.R;
import aurorayqz.packagecom.myapplication.data.CollectionBean;
import aurorayqz.packagecom.myapplication.ui.CollectionCreateActivity;
import aurorayqz.packagecom.myapplication.ui.adapter.CollectionAdapter;
import aurorayqz.packagecom.myapplication.ui.adapter.OnItemClickListener;
import aurorayqz.packagecom.myapplication.ui.cnmusic.LocalMusicActivity;
import aurorayqz.packagecom.myapplication.ui.cnmusic.RecentPlayListActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocalFragment extends Fragment {


    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private CollectionAdapter collectionAdapter;

    public static LocalFragment newInstance() {
        LocalFragment fragment = new LocalFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_local, container, false);
        ButterKnife.bind(this, inflate);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        collectionAdapter = new CollectionAdapter(getActivity());
        recyclerView.setAdapter(collectionAdapter);
        collectionAdapter.setItemClickListener(new OnItemClickListener<CollectionBean>() {
            @Override
            public void onItemClick(CollectionBean item, int position) {
//                CollectionPlayActivity.open(getActivity(), item);
            }

            @Override
            public void onItemSettingClick(View v, CollectionBean item, int position) {
            }
        });

        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.recently_layout, R.id.local_layout, R.id.download_layout, R.id.artist_layout,R.id.add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.recently_layout:
                RecentPlayListActivity.open(getActivity());
                break;
            case R.id.local_layout:
                startActivity(new Intent(getActivity(), LocalMusicActivity.class));
                break;
            case R.id.download_layout:
                break;
            case R.id.artist_layout:
                break;
            case R.id.add:
                CollectionCreateActivity.open(getActivity());
                break;
        }
    }
}
