package com.cafe.BLL;

import com.cafe.DAL.DecentralizationDetailDAL;
import com.cafe.DAL.ModuleDAL;
import com.cafe.DTO.DecentralizationDetail;
import com.cafe.DTO.Discount;
import com.cafe.DTO.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModuleBLL extends Manager<Module> {
    private ModuleDAL moduleDAL;
    private List<Module> moduleList;

    public ModuleBLL() {
        try {
            moduleDAL = new ModuleDAL();
            moduleList = searchModules("DELETED = 0", "MODULE_ID != 'DE00'");
        } catch (Exception ignored) {

        }
    }

    public List<Module> searchModules(String... conditions) {
        return moduleDAL.searchModules(conditions);
    }

    public ModuleDAL getModuleDAL() {
        return moduleDAL;
    }

    public void setModule(ModuleDAL moduleDAL) {
        this.moduleDAL = moduleDAL;
    }

    public List<Module> findDecentralizationDetails(String key, String value) {
        List<Module> list = new ArrayList<>();
        for (Module module : moduleList) {
            if (getValueByKey(module, key).toString().toLowerCase().contains(value.toLowerCase())) {
                list.add(module);
            }
        }
        return list;
    }

    public List<Module> findModulesBy(Map<String, Object> conditions) {
        List<Module> modules = moduleList;
        for (Map.Entry<String, Object> entry : conditions.entrySet())
            modules = findObjectsBy(entry.getKey(), entry.getValue(), modules);
        return modules;
    }
    @Override
    public Object getValueByKey(Module modules, String key){
        return switch (key) {
            case "MODULE_ID" -> modules.getModuleID();
            case "MODULE_NAME" -> modules.getModuleName();
            default -> null;
        };
    }
}
