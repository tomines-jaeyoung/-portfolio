package project;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int remainingPoints;
    private Map<String, Integer> abilities;
    private static final int MAX_ABILITY_SCORE = 100;
    private static final int MAX_USER_ALLOCATABLE_POINTS = 250;

    public User(int initialPoints, String[] abilityNames) {
        this.remainingPoints = initialPoints;
        this.abilities = new HashMap<>();
        for (String ability : abilityNames) {
            this.abilities.put(ability, 0);
        }
    }

    public int getRemainingPoints() {
        return remainingPoints;
    }

    public void setRemainingPoints(int remainingPoints) {
        this.remainingPoints = remainingPoints;
    }

    public Map<String, Integer> getAbilities() {
        return abilities;
    }

    public void setAbilities(Map<String, Integer> abilities) {
        this.abilities = new HashMap<>(abilities);
    }

    public boolean allocateAbilityPoint(String abilityName, int pointsToAllocate) {
        if (pointsToAllocate <= 0 || pointsToAllocate > remainingPoints) {
            System.out.println("[알림] 유효하지 않은 포인트입니다. 남은 포인트 (" + remainingPoints + "점) 내에서 1점 이상으로 입력해주세요.");
            return false;
        }

        int currentAbilityScore = abilities.get(abilityName);
        if (currentAbilityScore + pointsToAllocate > MAX_ABILITY_SCORE) {
            System.out.println("[알림] " + abilityName + "는(은) 최대 " + MAX_ABILITY_SCORE + "점까지 배분할 수 있습니다. 현재 " + currentAbilityScore + "점입니다.");
            return false;
        }

        abilities.put(abilityName, currentAbilityScore + pointsToAllocate);
        remainingPoints -= pointsToAllocate;
        System.out.println("-> " + abilityName + "에 " + pointsToAllocate + "점 배분 완료. 남은 포인트: " + remainingPoints + "점");
        return true;
    }

    public void displayCurrentAbilities() {
        System.out.println("\n--- 현재 나의 능력치 ---");
        abilities.forEach((ability, value) -> System.out.println(ability + ": " + value + "점"));
        System.out.println("남은 자유 포인트: " + remainingPoints + "점");
        System.out.println("------------------------");
    }

    public boolean meetsJobRequirements(Job job) {
        System.out.println("\n--- " + job.getName() + " 직업 요구 조건 검사 ---");

        int totalRequiredPoints = 0;
        for (int requiredScore : job.getRequiredAbilities().values()) {
            totalRequiredPoints += requiredScore;
        }
        if (totalRequiredPoints > MAX_USER_ALLOCATABLE_POINTS) {
            System.out.println("[불충족] 이 직업은 총 " + totalRequiredPoints + "점의 능력치를 요구합니다. " +
                    "당신이 배분할 수 있는 최대 능력치 총합은 " + MAX_USER_ALLOCATABLE_POINTS + "점입니다.");
            return false;
        }

        boolean allRequirementsMet = true;
        for (Map.Entry<String, Integer> entry : job.getRequiredAbilities().entrySet()) {
            String requiredAbilityName = entry.getKey();
            int requiredScore = entry.getValue();
            int userScore = abilities.getOrDefault(requiredAbilityName, 0);

            if (userScore < requiredScore) {
                System.out.println("[불충족] " + requiredAbilityName + ": 요구 " + requiredScore + "점 / 현재 " + userScore + "점 (부족: " + (requiredScore - userScore) + "점)");
                allRequirementsMet = false;
            } else {
                System.out.println("[충족] " + requiredAbilityName + ": 요구 " + requiredScore + "점 / 현재 " + userScore + "점");
            }
        }
        return allRequirementsMet;
    }
}