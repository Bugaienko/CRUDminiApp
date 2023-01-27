package org.example.crudProject;

public enum ChoiceEnum {
    CREATE("[c]reate", "c"),
    READ("[r]ead", "r"),
    POSITION("[p]osition", "p"),
    UPDATE("[u]pdate", "u"),
    DELETE("[d]elete", "d"),
    SORT("s[o]rt", "o"),
    SEARCH("[s]earch", "s"),
    FIND("[f]ind", "f"),
    EXIT("e[x]it", "x");


    final String description;
    final String key;

    ChoiceEnum(String description, String key){
        this.description = description;
        this.key = key;
    }

    public static ChoiceEnum getEnumByKey(String key) {
        for (ChoiceEnum choiceEnum: ChoiceEnum.values()){
            if (choiceEnum.key.equalsIgnoreCase(key)) return choiceEnum;
        }
        return null;
    }


    }
