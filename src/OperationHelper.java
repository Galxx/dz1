public class OperationHelper {

    static int currentOperation = 0;

    public static boolean HandleOperation( String data){
        //Ничего не ввели, продолжаем ждать данные
        if(data.isEmpty()){
            return false;
        }

        try {
            if(currentOperation == 0){
                return handleInitialOperation(data);
            }else{
                handleSubsequentOperation(data);
            }
        }catch (NotCorrectDataException e){
            System.out.println("Ошибка ввода данных");
            System.out.println(e.getMessage());
        }

        showMainMenu();
        currentOperation = 0;

        return false;
    }

    private static void handleSubsequentOperation(String data) {
        if(currentOperation == 1){
            StudentAssessment.addStudent(data);
            System.out.println("Добавлен ученик: " + data);
        } else if (currentOperation == 2) {
            StudentAssessment.removeStudent(data);
            System.out.println("Удален ученик: " + data);
        } else if (currentOperation == 3){
            String [] dataArr = data.split(" ");
            if(dataArr.length != 3){
                throw new NotCorrectDataException("Оценка введена в неправильном фомате: " + data);
            }
            String student = dataArr[0];
            String subject = dataArr[1];
            Integer assessment;
            try {
                assessment =  Integer.parseInt(dataArr[2]);
            } catch (NumberFormatException e) {
                throw new NotCorrectDataException("Оценка введена в неправильном фомате: " + data);
            }
            StudentAssessment.addStudentAssessment(student,subject,assessment);
            System.out.println("Оценка успешно добавлена");
        }else if(currentOperation == 5){
            StudentAssessment.printStudentAssessment(data);
        }
    }

    private static boolean handleInitialOperation(String data) {
        try {
            currentOperation = Integer.parseInt(data);
        } catch (NumberFormatException e) {
            throw new NotCorrectDataException("Не корректно введен номер операции: " + data);
        }

        //Операции, которые не требуют ввода обрабатываем сразу
        if(currentOperation == 6) {
            StudentAssessment.saveToFile();
            return true;
        } else if (currentOperation == 4) {
            StudentAssessment.printAll();
            showMainMenu();
            currentOperation = 0;
            return false;
        } else if (currentOperation > 6 || currentOperation < 1) {
            throw new NotCorrectDataException("Некорректно введен номер операции: " + data);
        }

        showHeaderForOperation(currentOperation);

        return false;
    }

    public static void showMainMenu() {
        System.out.println("Введите номер операции:");
        System.out.println("1. Добавить ученика");
        System.out.println("2. Удалить ученика");
        System.out.println("3. Обновите оценку ученика");
        System.out.println("4. Просмотр оценок всех учащихся");
        System.out.println("5. Просмотр оценок конкретного учащегося");
        System.out.println("6. Сохранить в файл и выйти");
    }

    private static void showHeaderForOperation(int currentOperation){
        if(currentOperation == 1 || currentOperation == 2 || currentOperation == 5 ) {
            System.out.println("Введите имя ученика");
        } if(currentOperation == 3 ){
            System.out.println("Введите оценку ученика в формате: 'Ученик Предмет Оценка'");
        }

    }

    OperationHelper(){}


    public static void readFromFile() {
        StudentAssessment.readFile();
    }
}
