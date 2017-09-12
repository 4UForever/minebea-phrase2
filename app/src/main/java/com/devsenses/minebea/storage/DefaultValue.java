package com.devsenses.minebea.storage;

import com.devsenses.minebea.model.breakmodel.BreakReason;
import com.devsenses.minebea.model.breakmodel.BreakReasonData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 12/9/2560.
 */

public final class DefaultValue {
    private DefaultValue() {

    }

    public static BreakReasonData getDefaultBreakReasonData() {
        List<BreakReason> list = new ArrayList<>();
        list.add(createBreakReason(7, "001", "เข้าห้องน้ำ / กินน้ำ from default", 0));
        list.add(createBreakReason(8, "002", "ไปห้องพยาบาล", 1));
        list.add(createBreakReason(9, "003", "พักเบรคใหญ่ / ย่อย", 0));
        list.add(createBreakReason(10, "004", "Stop meeting (เพื่อ Information ข้อมูลต่าง ๆ)", 1));
        list.add(createBreakReason(11, "005", "Change Model", 1));
        list.add(createBreakReason(12, "006", "Wait Car, Wait Part", 0));
        list.add(createBreakReason(13, "007", "Finish Invoice", 0));
        list.add(createBreakReason(14, "008", "QC Reject งาน , Stop Line , Delete data", 1));
        list.add(createBreakReason(15, "009", "M/C Error (No running)", 1));
        list.add(createBreakReason(16, "0010", "Stop all M/C (conveyer stop)", 0));
        list.add(createBreakReason(17, "0011", "Move change brake", 0));
        list.add(createBreakReason(18, "0012", "Big Cleaning", 0));
        list.add(createBreakReason(19, "0013", "Sorting", 0));
        list.add(createBreakReason(20, "0014", "Change Operator", 1));
        list.add(createBreakReason(21, "0015", "Change Shift / เปลี่ยนกะ", 0));

        BreakReasonData data = new BreakReasonData();
        data.setBreakReason(list);

        return data;
    }

    private static BreakReason createBreakReason(int id, String code, String reason, int flag) {
        BreakReason reasonModel = new BreakReason();
        reasonModel.setReason(reason);
        reasonModel.setId(id);
        reasonModel.setCode(code);
        reasonModel.setFlag(flag);
        return reasonModel;
    }
}
