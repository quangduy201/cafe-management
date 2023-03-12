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

    public List<ReceivedNote> getReceivedNotes(){
        List<ReceivedNote> receivedNoteList = new ArrayList<ReceivedNote>() {
        };
        try{
            ReceivedNoteDAO receivedNoteDAO = new ReceivedNoteDAO();
            List<List<String>> receivedNotes = receivedNoteDAO.read();
            for (List<String> row : receivedNotes){
                String receipt_ID, staff_ID;
                Day DOR;
                double grand_Total;
                boolean deleted;
                receipt_ID = row.get(0);
                staff_ID = row.get(1);
                DOR = Day.parseDay(row.get(2));
                grand_Total = Double.parseDouble(row.get(3));
                if (row.get(4).indexOf("0") != -1){
                    deleted = false;
                }else {
                    deleted = true;
                }
                ReceivedNote receivedNote = new ReceivedNote(receipt_ID, staff_ID, DOR, grand_Total, deleted);
                receivedNoteList.add(receivedNote);
            }
        } catch(Exception e){

        }
        return receivedNoteList;
    }

    public ReceivedNote readByPrimaryKey(String receipt_ID){
        try {
            for (ReceivedNote receivedNote : getReceivedNotes()){
                if (receivedNote.getReceipt_ID().indexOf(receipt_ID) != -1){
                    return receivedNote;
                }
            }
        } catch(Exception e){
            return null;
        }
        return null;
    }

    public List<ReceivedNote> readByStaff_ID(String staff_ID){
        List<ReceivedNote> result = new ArrayList<ReceivedNote>();
        try {
            for (ReceivedNote receivedNote : getReceivedNotes()){
                if (receivedNote.getStaff_ID().indexOf(staff_ID) != -1){
                    result.add(receivedNote);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<ReceivedNote> readByDOR(Day DOR){
        List<ReceivedNote> result = new ArrayList<ReceivedNote>();
        try {
            for (ReceivedNote receivedNote : getReceivedNotes() ) {
                if (receivedNote.getDOR().equals(DOR)){
                    result.add(receivedNote);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<ReceivedNote> readByDOR(Day DOR1, Day DOR2){
        List<ReceivedNote> result = new ArrayList<ReceivedNote>();
        try {
            for (ReceivedNote receivedNote : getReceivedNotes() ) {
                if (receivedNote.getDOR().compare(DOR1, DOR2)){
                    result.add(receivedNote);
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<ReceivedNote> readByGrand_Total(String compare, double grand_Total){
        List<ReceivedNote> result = new ArrayList<ReceivedNote>();
        try {
            if (compare.indexOf("<") != -1){
                for (ReceivedNote receivedNote : getReceivedNotes() ) {
                    if (receivedNote.getGrand_Total() < grand_Total){
                        result.add(receivedNote);
                    }
                }
            } else if (compare.indexOf("<=") != -1) {
                for (ReceivedNote receivedNote : getReceivedNotes() ) {
                    if (receivedNote.getGrand_Total() <= grand_Total){
                        result.add(receivedNote);
                    }
                }
            } else if (compare.indexOf(">") != -1) {
                for (ReceivedNote receivedNote : getReceivedNotes() ) {
                    if (receivedNote.getGrand_Total() > grand_Total){
                        result.add(receivedNote);
                    }
                }
            } else if (compare.indexOf(">=") != -1) {
                for (ReceivedNote receivedNote : getReceivedNotes() ) {
                    if (receivedNote.getGrand_Total() >= grand_Total){
                        result.add(receivedNote);
                    }
                }
            } else if (compare.indexOf("=") != -1) {
                for (ReceivedNote receivedNote : getReceivedNotes() ) {
                    if (receivedNote.getGrand_Total() == grand_Total){
                        result.add(receivedNote);
                    }
                }
            }
        } catch (Exception e){

        }
        return result;
    }

    public List<ReceivedNote> readByGrand_Total(double grand_Total1, double grand_Total2){
        List<ReceivedNote> result = new ArrayList<ReceivedNote>();
        try {
            for (ReceivedNote receivedNote : getReceivedNotes()){
                if (receivedNote.getGrand_Total() >= grand_Total1 && receivedNote.getGrand_Total() <= grand_Total2){
                    result.add(receivedNote);
                }
            }
        } catch(Exception e){
        }
        return result;
    }

    public List<ReceivedNote> readByDeleted(boolean deleted){
        List<ReceivedNote> result = new ArrayList<ReceivedNote>();
        try {
            for (ReceivedNote receivedNote : getReceivedNotes()){
                if (receivedNote.isDeleted() == deleted){
                    result.add(receivedNote);
                }
            }
        } catch(Exception e){
        }
        return result;
    }

    public int create(String receipt_ID, String staff_ID, Day DOR){
        if (readByPrimaryKey(receipt_ID) != null){
            return 0;
        }
        try {
            return super.create(receipt_ID, staff_ID, DOR, 0, 0);
        } catch (Exception e){
            return 0;
        }
    }

    public int update(String receipt_ID, String staff_ID, Day DOR){
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

    public int delete(String receipt_ID){
        try {
            Map<String, Object> updateValues = new HashMap<>();
            updateValues.put("DELETED", 1);
            return super.update(updateValues, "RECEIPT_ID = '" + receipt_ID + "'");
        } catch (Exception e){
            return 0;
        }
    }
}
