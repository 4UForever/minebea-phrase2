package com.devsenses.minebea.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.devsenses.minebea.R;
import com.devsenses.minebea.adapter.GridDocumentAdapter;
import com.devsenses.minebea.model.documentmodel.DocumentData;
import com.devsenses.minebea.model.documentmodel.DocumentModel;
import com.devsenses.minebea.permission.Permission;

/**
 * Created by Administrator on 5/11/2015.
 */
public class GridMenuDocumentFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final int GRID_COLUMNS = 2;
    private static final String KEY_DATA = "ketData";
    private int selectedPosition;

    private GridView gridViewDocument;
    private DocumentModel documentModel;

    public static GridMenuDocumentFragment newInstance(DocumentModel documentModel) {
        GridMenuDocumentFragment fragment = new GridMenuDocumentFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DATA, documentModel);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_grid_menu_document, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        documentModel = getArguments().getParcelable(KEY_DATA);

        initUI(view);
        setGridColumns(GRID_COLUMNS);
        setEventListener();
        setGridAdapter(documentModel);
    }

    private void initUI(View view) {
        gridViewDocument = (GridView) view.findViewById(R.id.gridViewDocument);
    }

    private void setGridColumns(int columns){
        gridViewDocument.setNumColumns(columns);
    }

    private void setEventListener(){
        gridViewDocument.setOnItemClickListener(this);
    }

    private void setGridAdapter(DocumentModel documentModel) {
        gridViewDocument.setAdapter(new GridDocumentAdapter(getActivity(), documentModel.getData()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedPosition = position;
        onDocumentSelected(position);
    }

    private void onDocumentSelected(int position) {
        if (documentModel.getData().get(position).getDocuments().size() > 0) {
            ((OnGridMenuDocumentListener) getActivity()).onGridMenuSelected(documentModel.getData().get(position));
        } else {
            Toast.makeText(getActivity(), "Not have Document!", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnGridMenuDocumentListener{
        void onGridMenuSelected(DocumentData documentDocumentData);
    }

}
