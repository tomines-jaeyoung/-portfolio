package project;

import java.util.Map;
import java.util.HashMap;

public class Job {
    private String name;
    private String description;
    private Map<String, Integer> requiredAbilities;

    public Job(String name, String description, Map<String, Integer> requiredAbilities) {
        this.name = name;
        this.description = description;
        this.requiredAbilities = requiredAbilities;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getRequiredAbilities() {
        return requiredAbilities;
    }

    public void displayJobInfo() {
        System.out.println("\n--- 직업 정보: " + name + " ---");
        System.out.println("설명: " + description);
    }
}