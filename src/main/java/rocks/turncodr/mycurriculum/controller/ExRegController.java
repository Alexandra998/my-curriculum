package rocks.turncodr.mycurriculum.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import rocks.turncodr.mycurriculum.model.ExReg;
import rocks.turncodr.mycurriculum.model.Handbook;
import rocks.turncodr.mycurriculum.model.Handbook.Semester;
import rocks.turncodr.mycurriculum.model.Module;
import rocks.turncodr.mycurriculum.services.ExRegJpaRepository;
import rocks.turncodr.mycurriculum.services.ModuleJpaRepository;

/**
 * Controller for the Examination Regulations sites.
 */
@Controller
public class ExRegController {

    @Autowired
    private ModuleJpaRepository moduleJpaRepository;

    @Autowired
    private ExRegJpaRepository exRegJpaRepository;

    @GetMapping("/exreg/create")
    public String getExRegCreate(Model model) {
        // fetching not mapped modules in a moduleList by selecting only modules, where
        // exReg-attribute = null
        List<Module> moduleList = moduleJpaRepository.findByExReg(null);
        model.addAttribute("moduleList", moduleList);

        return "exregCreate";
    }

    @GetMapping("/exreg/handbook")
    public String getExRegHandbook(@RequestParam("id") Integer id, Model model) {
        Optional<ExReg> exRegresult = exRegJpaRepository.findById(id);
        if (exRegresult.isPresent()) {
            ExReg exReg = exRegresult.get();
            List<Module> moduleList = moduleJpaRepository.findByExReg(exReg);
            model.addAttribute("exReg", exReg);

            Map<Integer, List<Module>> SemesterMap = new HashMap<>();

            for (Module module : moduleList) {
                int semester = module.getSemester();
                List<Module> modules = SemesterMap.get(semester);

                if (modules == null) {
                    modules = new ArrayList<>();
                }
                modules.add(module);
            }
            List<Integer> keys = new ArrayList<>(SemesterMap.keySet());
            Collections.sort(keys);
            Handbook handbook = new Handbook();

            for (Integer key : keys) {
                List<Module> modules = SemesterMap.get(key);
                Collections.sort(modules, Module.ALPHABETICAL_ORDER);
                Semester semester = new Semester(modules, key);
                handbook.getSemesters().add(semester);
            }
            model.addAttribute("handbook", handbook);
        } else {
            model.addAttribute("error", "exregHandbook.exregDoesntExist");

        }
        return "exregHandbook";

    }

}
