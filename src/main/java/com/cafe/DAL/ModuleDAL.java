package com.cafe.DAL;

import com.cafe.DTO.Module;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModuleDAL extends Manager {
    public ModuleDAL() {
        super("module",
            List.of("MODULE_ID",
                "MODULE_NAME",
                "DELETE")
            );
    }

    public List<Module> covertModule(List<List<String>> data) {
        return convert(data, row -> {
            row.set(row.size() - 1, row.get(row.size() - 1).equals("0") ? "false" : "true");
            return new Module(
                row.get(0), // moduleID
                row.get(1), // moduleName
                Boolean.parseBoolean(row.get(2)) // deleted
            );
        });
    }

    public int addModule(Module Module) {
        try {
            return create(Module.getModuleID(),
                Module.getModuleID(),
                Module.getModuleName(),
                false
            ); // module khi tạo mặc định deleted = 0
        } catch (Exception e) {
            System.out.println("Error occurred in ModuleDAL.addModule(): " + e.getMessage());
        }
        return 0;
    }

    public int updateModule(Module Module) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(Module.getModuleID());;
            updateValues.add(Module.getModuleName());
            updateValues.add(Module.isDeleted());
            return update(updateValues, "MODULE_ID = '" + Module.getModuleID() + "'");
        } catch (Exception e) {
            System.out.println("Error occurred in ModuleDAL.updateModule(): " + e.getMessage());
        }
        return 0;
    }

    public int deleteModule(String... conditions) {
        try {
            List<Object> updateValues = new ArrayList<>();
            updateValues.add(true);
            return update(updateValues, conditions);
        } catch (Exception e) {
            System.out.println("Error occurred in ModuleDAL.deleteModule(): " + e.getMessage());
        }
        return 0;
    }

    public List<Module> searchModules(String... conditions) {
        try {
            return covertModule(read(conditions));
        } catch (Exception e) {
            System.out.println("Error occurred in ModuleDAL.searchModules(): " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
