package project;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;
    private static Map<Integer, JobCategory> categories; // 카테고리 맵
    private static final String[] ABILITY_NAMES = {"논리력", "창의력", "공감력", "끈기", "책임감"};

    public static void main(String[] args) {
        initializeKiosk();
        runKiosk();
    }

    private static void initializeKiosk() {
        System.out.println("===== 직업 키오스크에 오신 것을 환영합니다! =====");
        System.out.println("당신은 250점의 자유 포인트를 가지고 시작합니다.");
        System.out.println("이 포인트로 원하는 직업의 요구 능력치를 채워보세요!\n");

        currentUser = new User(250, ABILITY_NAMES);
        categories = new HashMap<>();

        // --- 1. 전문직 카테고리 ---
        JobCategory professionalCategory = new JobCategory("전문직");
        professionalCategory.addJob(1, new Job("의사", "환자를 치료하고 생명을 다루는 숭고한 직업입니다. 막중한 책임감과 공감 능력이 필수입니다.", new HashMap<String, Integer>() {{
            put("책임감", 70); put("공감력", 60); put("끈기", 50); put("논리력", 40); put("창의력", 30); // Total: 250
        }}));
        professionalCategory.addJob(2, new Job("변호사", "법률 지식을 바탕으로 의뢰인의 권리를 옹호하는 직업입니다. 논리적 사고와 책임감이 중요합니다.", new HashMap<String, Integer>() {{
            put("논리력", 75); put("책임감", 65); put("공감력", 30); put("끈기", 45); put("창의력", 35); // Total: 250
        }}));
        professionalCategory.addJob(3, new Job("교수", "특정 분야를 연구하고 학생들을 가르치는 직업입니다. 깊이 있는 지식과 꾸준한 연구가 필요합니다.", new HashMap<String, Integer>() {{
            put("논리력", 65); put("끈기", 70); put("창의력", 40); put("책임감", 50); put("공감력", 25); // Total: 250
        }}));
        professionalCategory.addJob(4, new Job("회계사", "기업 및 개인의 재무 상태를 분석하고 관리하는 직업입니다. 정확한 논리력과 책임감이 중요합니다.", new HashMap<String, Integer>() {{
            put("논리력", 80); put("책임감", 60); put("끈기", 50); put("창의력", 20); put("공감력", 20); // Total: 230
        }}));
        professionalCategory.addJob(5, new Job("약사", "의약품을 조제하고 환자에게 복약 지도를 하는 직업입니다. 정확성과 책임감이 요구됩니다.", new HashMap<String, Integer>() {{
            put("논리력", 65); put("책임감", 70); put("끈기", 45); put("공감력", 45); put("창의력", 25); // Total: 250
        }}));
        categories.put(1, professionalCategory);

        // --- 2. IT/기술직 카테고리 ---
        JobCategory itTechCategory = new JobCategory("IT/기술직");
        itTechCategory.addJob(1, new Job("소프트웨어 개발자", "새로운 시스템과 프로그램을 만드는 직업입니다. 논리적 사고와 끈기가 중요합니다.", new HashMap<String, Integer>() {{
            put("논리력", 80); put("끈기", 60); put("창의력", 50); put("책임감", 40); put("공감력", 20); // Total: 250
        }}));
        itTechCategory.addJob(2, new Job("데이터 과학자", "대규모 데이터를 분석하여 의미 있는 통찰을 도출하는 직업입니다. 높은 논리력과 분석 능력이 필요합니다.", new HashMap<String, Integer>() {{
            put("논리력", 85); put("끈기", 55); put("창의력", 40); put("책임감", 45); put("공감력", 25); // Total: 250
        }}));
        itTechCategory.addJob(3, new Job("네트워크 엔지니어", "네트워크 시스템을 설계, 구축, 유지보수하는 직업입니다. 안정적인 시스템 구축에 대한 책임감이 요구됩니다.", new HashMap<String, Integer>() {{
            put("논리력", 70); put("책임감", 65); put("끈기", 55); put("창의력", 30); put("공감력", 30); // Total: 250
        }}));
        itTechCategory.addJob(4, new Job("웹 디자이너", "웹사이트의 시각적 요소와 사용자 경험을 디자인하는 직업입니다. 뛰어난 창의력과 공감 능력이 필요합니다.", new HashMap<String, Integer>() {{
            put("창의력", 75); put("공감력", 55); put("논리력", 40); put("끈기", 30); put("책임감", 50); // Total: 250
        }}));
        itTechCategory.addJob(5, new Job("게임 개발자", "재미있고 몰입감 있는 게임을 만드는 직업입니다. 창의력과 기술적 지식이 함께 요구됩니다.", new HashMap<String, Integer>() {{
            put("창의력", 70); put("논리력", 55); put("끈기", 45); put("책임감", 40); put("공감력", 40); // Total: 250
        }}));
        categories.put(2, itTechCategory);

        // --- 3. 예술/창작직 카테고리 ---
        JobCategory artCreativeCategory = new JobCategory("예술/창작직");
        artCreativeCategory.addJob(1, new Job("작가", "글로 이야기를 만들고 감동을 주는 직업입니다. 풍부한 창의력과 끈기가 필요합니다.", new HashMap<String, Integer>() {{
            put("창의력", 80); put("끈기", 50); put("공감력", 60); put("논리력", 30); put("책임감", 30); // Total: 250
        }}));
        artCreativeCategory.addJob(2, new Job("화가", "그림으로 자신의 생각과 감정을 표현하는 직업입니다. 독창적인 창의력이 필수적입니다.", new HashMap<String, Integer>() {{
            put("창의력", 85); put("끈기", 40); put("공감력", 35); put("논리력", 25); put("책임감", 25); // Total: 210
        }}));
        artCreativeCategory.addJob(3, new Job("음악가", "음악을 만들거나 연주하여 사람들에게 기쁨을 주는 직업입니다. 창의적인 재능과 꾸준한 연습이 필요합니다.", new HashMap<String, Integer>() {{
            put("창의력", 80); put("끈기", 45); put("공감력", 40); put("논리력", 25); put("책임감", 20); // Total: 210
        }}));
        artCreativeCategory.addJob(4, new Job("배우", "다양한 역할을 연기하여 관객에게 몰입감을 선사하는 직업입니다. 뛰어난 공감력과 표현력이 요구됩니다.", new HashMap<String, Integer>() {{
            put("공감력", 80); put("창의력", 65); put("책임감", 30); put("끈기", 35); put("논리력", 20); // Total: 230
        }}));
        artCreativeCategory.addJob(5, new Job("사진작가", "렌즈를 통해 세상의 아름다움과 순간을 포착하는 직업입니다. 창의적인 시선과 섬세함이 필요합니다.", new HashMap<String, Integer>() {{
            put("창의력", 75); put("공감력", 45); put("끈기", 35); put("논리력", 30); put("책임감", 35); // Total: 220
        }}));
        categories.put(3, artCreativeCategory);

        // --- 4. 서비스/사회 공헌직 카테고리 ---
        JobCategory serviceSocialCategory = new JobCategory("서비스/사회 공헌직");
        serviceSocialCategory.addJob(1, new Job("사회 복지사", "도움이 필요한 사람들에게 지원과 서비스를 제공하는 직업입니다. 높은 공감 능력과 책임감이 요구됩니다.", new HashMap<String, Integer>() {{
            put("공감력", 85); put("책임감", 70); put("끈기", 50); put("논리력", 25); put("창의력", 20); // Total: 250
        }}));
        serviceSocialCategory.addJob(2, new Job("경찰관", "사회 질서 유지와 범죄 예방을 담당하는 직업입니다. 강한 책임감과 끈기가 필요합니다.", new HashMap<String, Integer>() {{
            put("책임감", 75); put("끈기", 65); put("논리력", 55); put("공감력", 40); put("창의력", 15); // Total: 250
        }}));
        serviceSocialCategory.addJob(3, new Job("소방관", "재난 현장에서 생명과 재산을 구하는 영웅적인 직업입니다. 높은 책임감과 끈기가 요구됩니다.", new HashMap<String, Integer>() {{
            put("책임감", 80); put("끈기", 70); put("공감력", 50); put("논리력", 30); put("창의력", 20); // Total: 250
        }}));
        serviceSocialCategory.addJob(4, new Job("승무원", "항공기 탑승객의 안전과 편의를 책임지는 직업입니다. 뛰어난 공감 능력과 서비스 마인드가 중요합니다.", new HashMap<String, Integer>() {{
            put("공감력", 75); put("책임감", 55); put("끈기", 40); put("논리력", 30); put("창의력", 50); // Total: 250
        }}));
        serviceSocialCategory.addJob(5, new Job("환경운동가", "환경 보호와 지속 가능한 발전을 위해 활동하는 직업입니다. 환경 문제에 대한 책임감과 열정이 중요합니다.", new HashMap<String, Integer>() {{
            put("책임감", 65); put("공감력", 60); put("끈기", 60); put("논리력", 40); put("창의력", 25); // Total: 250
        }}));
        categories.put(4, serviceSocialCategory);
    }

    private static void runKiosk() {
        while (true) {
            displayMainMenu();
            String choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("종료")) {
                System.out.println("직업 키오스크를 종료합니다. 이용해주셔서 감사합니다!");
                break;
            }

            try {
                int categoryNum = Integer.parseInt(choice);
                if (categories.containsKey(categoryNum)) {
                    selectCategory(categories.get(categoryNum)); // 카테고리 선택 로직 호출
                } else {
                    System.out.println("[알림] 유효하지 않은 번호입니다. 다시 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[알림] 잘못된 입력입니다. 번호 또는 '종료'를 입력해주세요.");
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n--- 직업 카테고리 메뉴 ---");
        categories.forEach((num, category) -> System.out.println(num + ". " + category.getName()));
        System.out.println("-------------------------");
        // currentUser.displayCurrentAbilities(); // 이 부분을 제거하여 첫 화면에서 능력치 표시 안 함
        System.out.print("원하는 카테고리 번호를 입력하거나 '종료'를 입력하세요: ");
    }

    // 새로운 카테고리 선택 로직
    private static void selectCategory(JobCategory selectedCategory) {
        boolean backToCategories = false;
        while (!backToCategories) {
            System.out.println("\n--- " + selectedCategory.getName() + " 직업 목록 ---");
            selectedCategory.getJobsInSubCategory().forEach((num, job) -> System.out.println(num + ". " + job.getName()));
            System.out.println("--------------------------------");
            // currentUser.displayCurrentAbilities(); // 이 부분을 제거하여 카테고리 화면에서 능력치 표시 안 함
            System.out.println("('뒤로' 입력: 이전 카테고리 선택으로 | '홈' 입력: 메인 메뉴로)");
            System.out.print("원하는 직업의 번호를 입력하세요: ");
            String choice = scanner.nextLine().trim();

            if (choice.equalsIgnoreCase("홈")) {
                System.out.println("[알림] 메인 카테고리 메뉴로 돌아갑니다.");
                backToCategories = true;
                continue;
            } else if (choice.equalsIgnoreCase("뒤로")) {
                System.out.println("[알림] 이전 카테고리 메뉴로 돌아갑니다.");
                backToCategories = true;
                continue;
            }

            try {
                int jobNum = Integer.parseInt(choice);
                if (selectedCategory.getJobsInSubCategory().containsKey(jobNum)) {
                    selectJob(selectedCategory.getJobsInSubCategory().get(jobNum));
                } else {
                    System.out.println("[알림] 유효하지 않은 번호입니다. 다시 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[알림] 잘못된 입력입니다. 번호, '뒤로' 또는 '홈'을 입력해주세요.");
            }
        }
    }


    // 직업 선택 및 포인트 배분 로직
    private static void selectJob(Job selectedJob) {
        boolean backToCategoryMenu = false;
        User tempUser = new User(currentUser.getRemainingPoints(), ABILITY_NAMES);
        tempUser.getAbilities().putAll(currentUser.getAbilities()); // 기존 능력치 복사

        while (!backToCategoryMenu) {
            selectedJob.displayJobInfo(); // 선택된 직업 정보 출력

            // 요구 능력치와 내 능력치를 옆에 같이 표시하는 부분 (여기서만 출력)
            System.out.println("--- 요구 능력치 (최소) vs 현재 나의 능력치 ---");
            for (String abilityName : ABILITY_NAMES) {
                int required = selectedJob.getRequiredAbilities().getOrDefault(abilityName, 0);
                int current = tempUser.getAbilities().getOrDefault(abilityName, 0);
                System.out.printf("%-10s: 요구 %3d점 | 현재 %3d점\n", abilityName, required, current);
            }
            System.out.println("--------------------------------------------");

            // 여기서는 tempUser.displayCurrentAbilities(); 호출을 삭제하여 중복을 막았습니다.

            System.out.println("\n--- 포인트 배분 모드 ---");
            System.out.println("('뒤로' 입력: 이전 직업 목록으로 돌아가기 | '홈' 입력: 메인 카테고리 메뉴로 돌아가기 | '구매' 입력: 직업 확정 시도)");
            System.out.print("포인트를 배분할 능력치 (논리력, 창의력, 공감력, 끈기, 책임감) 또는 명령어를 입력하세요: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("홈")) {
                System.out.println("[알림] 메인 카테고리 메뉴로 돌아갑니다.");
                backToCategoryMenu = true;
                continue;
            } else if (input.equalsIgnoreCase("뒤로")) {
                System.out.println("[알림] 이전 직업 목록으로 돌아갑니다.");
                backToCategoryMenu = true;
                continue;
            } else if (input.equalsIgnoreCase("구매")) {
                if (tempUser.meetsJobRequirements(selectedJob)) {
                    System.out.println("\n축하합니다! 당신은 이제 " + selectedJob.getName() + "입니다!");
                    currentUser.setRemainingPoints(0);
                    currentUser.setAbilities(tempUser.getAbilities());

                    System.out.println("\n(참고: 직업을 선택하면 모든 남은 자유 포인트가 소모됩니다.)");
                    System.out.print("더 둘러보시겠습니까? (Y/N): ");
                    String exitChoice = scanner.nextLine().trim();
                    if (exitChoice.equalsIgnoreCase("N")) {
                        System.out.println("직업 키오스크를 종료합니다. 이용해주셔서 감사합니다!");
                        System.exit(0);
                    }
                    backToCategoryMenu = true;
                    continue;
                } else {
                    System.out.println("[알림] 요구 조건을 충족하지 못했습니다. 포인트를 더 배분하거나 다른 직업을 고려해보세요.");
                    System.out.println("----------------------------------------------");
                    continue;
                }
            }

            if (isValidAbilityName(input)) {
                System.out.print(input + "에 배분할 포인트를 입력하세요 (남은 포인트: " + tempUser.getRemainingPoints() + "): ");
                try {
                    int pointsToAllocate = Integer.parseInt(scanner.nextLine().trim());
                    tempUser.allocateAbilityPoint(input, pointsToAllocate);
                } catch (NumberFormatException e) {
                    System.out.println("[알림] 유효한 숫자를 입력해주세요.");
                }
            } else {
                System.out.println("[알림] 유효하지 않은 능력치 이름입니다. 다시 입력해주세요.");
            }
        }
    }

    private static boolean isValidAbilityName(String name) {
        for (String ability : ABILITY_NAMES) {
            if (ability.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}