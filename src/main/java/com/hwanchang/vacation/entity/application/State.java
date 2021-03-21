package com.hwanchang.vacation.entity.application;

public enum State {

    RUNNING("진행"),

    FINISHED("완료"),

    REJECTED("반려"),

    CANCELED("취소");

    private final String value;

    State(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static State of(String name) {
        for (State state : State.values()) {
            if (state.name().equalsIgnoreCase(name)) {
                return state;
            }
        }
        return null;
    }

}
