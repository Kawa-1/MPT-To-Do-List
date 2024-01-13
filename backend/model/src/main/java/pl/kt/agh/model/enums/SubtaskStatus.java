package pl.kt.agh.model.enums;

import lombok.Getter;

@Getter
public enum SubtaskStatus {
    TODO("todo"),
    DOING("doing"),
    DONE("done");
    private final String name;

    SubtaskStatus(String name) {
        this.name = name;
    }

    public static SubtaskStatus fromString(String str){
        for (SubtaskStatus subtaskStatus : SubtaskStatus.values()){
            if (subtaskStatus.name.equalsIgnoreCase(str)){
                return subtaskStatus;
            }
        }
        throw new IllegalArgumentException("Cannot resolve subtask status for: " + str);
    }


}
