package com.cafe.DAO;

import com.cafe.DTO.CustomerDTO;
import com.cafe.DTO.ReceivedNote;
import com.cafe.DTO.Supplier;
import com.cafe.utils.Day;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReceivedNoteDAO extends  Manager{
    public ReceivedNoteDAO() throws SQLException {
        super("received_note", new ArrayList<>(
            List.of("RECEIPT_ID",
                "STAFF_ID",
                "DOR",
                "GRAND_TOTAL",
                "DELETED")
        ));
    }

    public List<ReceivedNote> convertToReceivedNote(List<List<String>> data){
        List<ReceivedNote> receivedNoteList = new ArrayList<ReceivedNote>();
        try{
            String receipt_ID, staff_ID;
            Day DOR;
            double grand_Total;
            boolean deleted;
            for (List<String> row : data){
                receipt_ID = row.get(0);
                staff_ID = row.get(1);
                DOR = Day.parseDay(row.get(2));
                grand_Total = Double.parseDouble(row.get(3));
                deleted = !row.get(4).contains("0");
                ReceivedNote receivedNote = new ReceivedNote(receipt_ID, staff_ID, DOR, grand_Total, deleted);
                receivedNoteList.add(receivedNote);
            }
        } catch(Exception ignored){

        }
        return receivedNoteList;
    }

    public List<ReceivedNote> readReceivedNotes(String[] conditions){
        List<ReceivedNote> receivedNoteList = new ArrayList<>();
        try {
            receivedNoteList = convertToReceivedNote(read(conditions));
        } catch (Exception ignored){

        }
        return receivedNoteList;
    }

    public int createReceivedNote(String receipt_ID, String staff_ID, Day DOR){
        if (readReceivedNotes(new String[]{"RECEIPT_ID = '" + receipt_ID + "'"}).size() != 0){
            return 0;
        }
        try {
            return super.create(receipt_ID, staff_ID, DOR, 0, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int updateReceivedNote(String receipt_ID, String staff_ID, Day DOR){
        // Những giá trị là _ID sẽ được combobox cho user chọn
        try {
            Map<String, Object> updateValues = new HashMap<>();
            if (staff_ID != null){
                updateValues.put("STAFF_ID", staff_ID);
            }
            if (DOR != null){
                updateValues.put("DOR", DOR);
            }
            return super.update(updateValues, "RECEIPT_ID = '" + receipt_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }

    public int deleteReceivedNote(String receipt_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "RECEIPT_ID = '" + receipt_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
