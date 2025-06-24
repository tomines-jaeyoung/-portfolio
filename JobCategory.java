package project;

import java.util.HashMap;
import java.util.Map;

// 새로운 JobCategory 클래스 정의
public class JobCategory {
    private String name;
    private Map<Integer, Job> jobsInSubCategory; // 이 카테고리에 속한 직업들

    public JobCategory(String name) {
        this.name = name;
        this.jobsInSubCategory = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<Integer, Job> getJobsInSubCategory() {
        return jobsInSubCategory;
    }

    public void addJob(int num, Job job) {
        jobsInSubCategory.put(num, job);
    }
}