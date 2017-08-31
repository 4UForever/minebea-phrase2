package com.devsenses.minebea.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.devsenses.minebea.R;
import com.devsenses.minebea.adapter.ListDocumentAdapter;
import com.devsenses.minebea.model.documentmodel.DocumentData;

/**
 * Created by Administrator on 5/11/2015.
 */
public class ListDocumentFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static String KEY_DOCUMENT = "kayDocument";
    private static String KEY_EMPLOYEE_NUMBER = "keyEmployeeNo";

    private ListView listDocument;
    private String employeeNumber;
    private DocumentData dataDocument;
    private Button btBack;

    public static ListDocumentFragment newInstance(DocumentData dataDocument, String employeeNo) {
        ListDocumentFragment instance = new ListDocumentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_DOCUMENT, dataDocument);
        bundle.putString(KEY_EMPLOYEE_NUMBER, employeeNo);
        instance.setArguments(bundle);

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_list_document, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        dataDocument = getArguments().getParcelable(KEY_DOCUMENT);
        employeeNumber = getArguments().getString(KEY_EMPLOYEE_NUMBER);
        initUI(view);
        initEvent();
        setAdapter();
    }

    private void initUI(View view) {
        btBack = (Button) view.findViewById(R.id.back);
        listDocument = (ListView) view.findViewById(R.id.listDocument);
    }

    private void initEvent() {
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        listDocument.setOnItemClickListener(this);
    }

    private void setAdapter() {
        ListDocumentAdapter listDocumentAdapter = new ListDocumentAdapter(getActivity(), dataDocument.getDocuments());
        listDocument.setAdapter(listDocumentAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String urlLoadPdfFile = dataDocument.getDocuments().get(position).getDownloadUrl();
        String pdfName = dataDocument.getDocuments().get(position).getTitle();

        if (!urlLoadPdfFile.trim().isEmpty() && !pdfName.trim().isEmpty()) {
             ((OnListDocumentListener) getActivity()).onDocumentSelected(urlLoadPdfFile, pdfName);
        } else {
            Toast.makeText(getActivity(), "File not available", Toast.LENGTH_SHORT).show();
        }
    }
    public interface OnListDocumentListener {
        void onDocumentSelected(String url, String fileName);
    }

}
