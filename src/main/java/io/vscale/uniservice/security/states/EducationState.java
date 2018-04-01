package io.vscale.uniservice.security.states;

/**
 * 26.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public enum EducationState {

    BACCALAUREATE("Бакалавриат"), MAGISTRACY("Магистратура");

    private String type;

    EducationState(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return this.type;
    }

}
