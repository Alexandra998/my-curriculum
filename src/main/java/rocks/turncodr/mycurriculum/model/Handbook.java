package rocks.turncodr.mycurriculum.model;

import java.util.ArrayList;
import java.util.List;

public class Handbook {
    
    private List <Semester> semesters;
    public Handbook () {
        semesters = new ArrayList<>(); 
    }
    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public static class Semester {
        private List <Module> modules;
        private int number; 
        public Semester(List<Module> modules, int number) {
            super();
            this.modules = modules;
            this.number = number;
        }
        public List<Module> getModules() {
            return modules;
        }
        public void setModules(List<Module> modules) {
            this.modules = modules;
        }
        public int getNumber() {
            return number;
        }
        public void setNumber(int number) {
            this.number = number;
        }
    }

}
