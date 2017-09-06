package com.devsenses.minebea;

import com.devsenses.minebea.model.loginmodel.Line;
import com.devsenses.minebea.model.loginmodel.Model;
import com.devsenses.minebea.model.loginmodel.Process;
import com.devsenses.minebea.model.loginmodel.SelectedModel;
import com.devsenses.minebea.model.partmodel.LotNo;
import com.devsenses.minebea.model.partmodel.Part;
import com.devsenses.minebea.model.partmodel.PartData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 6/9/2560.
 */

public final class MockData {

    public static Model getMockModel() {
        List<Line> lines = new ArrayList<>();
        lines.add(getMockLine());

        Model model = new Model();
        model.setCreatedAt("02/30/2560");
        model.setUpdatedAt("02/30/2560");
        model.setId(1234);
        model.setTitle("Mockup model");
        model.setLines(lines);

        return model;
    }

    public static Line getMockLine() {
        List<Process> processes = new ArrayList<>();
        processes.add(getMockProcess());

        Line line = new Line();
        line.setTitle("Mock line");
        line.setId(0);
        line.setProcesses(processes);
        line.setCreatedAt("02/30/2560");
        line.setUpdatedAt("02/30/2560");

        return line;
    }

    public static Process getMockProcess() {
        Process process = new Process();
        process.setTitle("Mock process");
        process.setId(0);
        process.setCreatedAt("02/30/2560");
        process.setUpdatedAt("02/30/2560");
        process.setNumber("12");

        return process;
    }

    public static SelectedModel getMockSelectedModel() {
        SelectedModel selectedModel = new SelectedModel();
        selectedModel.setTitle("Mock selected model");
        selectedModel.setID(0);
        selectedModel.setLineID(5555);
        selectedModel.setLineTitle("Mock line title");
        selectedModel.setLotNumber("mock lot number");
        selectedModel.setOnBreak(0L);
        selectedModel.setProcessID(3333);
        selectedModel.setProcessTitle("mock process title");
        selectedModel.setProcessNumber("9898989");

        return selectedModel;
    }

    public static PartData getMockPartData() {
        List<Part> parts = new ArrayList<>();
        parts.add(getMockPart());

        PartData partData = new PartData();
        partData.setProcessLogID(0);
        partData.setPartList(parts);

        return partData;
    }

    public static Part getMockPart() {
        List<LotNo> lotNos = new ArrayList<>();
        lotNos.add(getMockLotNo());

        Part part = new Part();
        part.setNumber("123456");
        part.setId(0);
        part.setCreateAt("02/30/2560");
        part.setUpdatedAt("02/30/2560");
        part.setName("mock part name");
        part.setProcessId(567);
        part.setProductId(456);
        part.setIQC(lotNos);

        return part;
    }

    public static LotNo getMockLotNo() {
        LotNo lotNo = new LotNo();
        lotNo.setNumber("mock lotNO");
        lotNo.setQuantity(3);

        return lotNo;
    }
}
