import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StudentAssessment {

    private static final HashMap<String,HashMap<String,Integer>> map = new HashMap<>();

    public static void addStudent(String student){
        if(!map.containsKey(student)){
            map.put(student, new HashMap<>());
        }else{
            throw new NotCorrectDataException("Студент с таким именем уже существует: " + student);
        }
    }

    public static void removeStudent(String student){
        if(map.containsKey(student)){
            map.remove(student);
        }else{
            throw new NotCorrectDataException("Студент с таким именем не существует: " + student);
        }
    }

    public static void printAll(){
        System.out.println(map);
    }

    public static void printStudentAssessment(String student){
        if(map.containsKey(student)){
            System.out.println(map.get(student));
        }else{
            throw new NotCorrectDataException("Студент с таким именем не существует: " + student);
        }
    }

    public static void addStudentAssessment(String student, String subject ,Integer assessment){

        if(map.containsKey(student)) {
            HashMap<String, Integer> subjectAssessment = map.get(student);
            subjectAssessment.put(subject,assessment);
            map.put(student,subjectAssessment);
        } else{
            throw new NotCorrectDataException("Не нашли студента с именем: " + student);
        }

    }

    public static void  saveToFile(){

        StringBuilder stringBuilder = new StringBuilder();
        boolean firstRow = true;

        for (Map.Entry<String, HashMap<String, Integer>> studentAssessment : map.entrySet()) {
            String student = studentAssessment.getKey();
            HashMap<String, Integer> studentAssessmentValue = studentAssessment.getValue();

            if (studentAssessmentValue.isEmpty()) {
                if (firstRow) firstRow = false;
                else stringBuilder.append('\n');
                appendRow(stringBuilder, student, null, null);
            } else {
                for (Map.Entry<String, Integer> assessment : studentAssessmentValue.entrySet()) {
                    if (firstRow) firstRow = false;
                    else stringBuilder.append('\n');
                    appendRow(stringBuilder, student, assessment.getKey(), assessment.getValue());
                }
            }
        }

        try(FileWriter writer = new FileWriter("store.txt", false)) {
            writer.write(stringBuilder.toString());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void appendRow(StringBuilder stringBuilder, String student, String key, Integer value) {

        stringBuilder.append(student)
                .append(" ")
                .append(key != null ? key : "null")
                .append(" ")
                .append(value != null ? value : "null");
    }

    public static void readFile(){

        try {
            FileReader fileReader = new FileReader("store.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                appendLineToMap(line);
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static void appendLineToMap(String line) {
        String [] dataArr = line.split(" ");
        String  student = dataArr[0];
        String  subject = dataArr[1];
        String assessment = dataArr[2];

        if(map.containsKey(student)){
            HashMap<String,Integer> assessments = map.get(student);
            assessments.put(subject,Integer.valueOf(assessment));
        }else{
            if("null".equals(subject)){
                map.put(student,new HashMap<>());
            }else{
                HashMap<String,Integer> assessments = new HashMap<>();
                assessments.put(subject,Integer.valueOf(assessment));
                map.put(student,assessments);
            }
        }
    }

}
