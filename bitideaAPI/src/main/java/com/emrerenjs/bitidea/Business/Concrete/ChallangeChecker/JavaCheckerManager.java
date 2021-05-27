package com.emrerenjs.bitidea.Business.Concrete.ChallangeChecker;

import com.emrerenjs.bitidea.Business.Abstract.ChallangeCheckerService;
import com.emrerenjs.bitidea.Entity.MongoDB.Challange;
import com.emrerenjs.bitidea.Error.RecordNotFoundException;
import com.emrerenjs.bitidea.Model.Challange.ChallangeAnswerModel;
import com.emrerenjs.bitidea.Model.Challange.IOModel;
import com.emrerenjs.bitidea.Model.Challange.TestScenarioModel;
import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;

public class JavaCheckerManager implements ChallangeCheckerService {


    private HashMap<String,String> javaTypes;
    private HashMap<String,String> javaCasts;

    public JavaCheckerManager(){
        this.javaTypes = new HashMap<>();
        javaTypes.put("Integer","int");
        javaTypes.put("Double","double");
        javaTypes.put("String","String");
        javaTypes.put("Boolean","boolean");
        javaTypes.put("Char","char");

        this.javaCasts = new HashMap<>();
        javaCasts.put("Integer","Integer.parseInt");
        javaCasts.put("Double","Double.parseDouble");
        javaCasts.put("String","");
        javaCasts.put("Boolean","Boolean.parseBoolean");

    }

    private void printLines(String name, InputStream ins) throws Exception{
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
    }

    private int runProcess(String command) throws InterruptedException, IOException {
        Process process = Runtime.getRuntime().exec(command);
        try{
            printLines("stdout:", process.getInputStream());
            printLines("stderr:", process.getErrorStream());
        }catch(Exception exception){
            exception.printStackTrace();
        }

        process.waitFor();
        int exit = process.exitValue();
        process.destroy();
        return exit;
    }

    @Override
    public boolean checkAnswer(Challange challange, ChallangeAnswerModel challangeAnswerModel,String username) {
        Gson gson = new Gson();
        TestScenarioModel[] testScenarioModels = gson.fromJson(challange.getTestScenariosString(),TestScenarioModel[].class);
        String directorypath = "./programs/p_"+challange.getId()+"_"+username;
        String classpath = "p_"+challange.getId() + "_" + username;

        String code = "public class "+classpath+" {\n";
        code += "\tpublic static void main(String[] args){\n";

        /*take arguments*/
        IOModel[] ioModels = testScenarioModels[0].getInputs();
        int i = 0;
        String parameternames = "";
        for(i = 0; i < ioModels.length; i++){
            if(ioModels[i].getDimension() == 0){
                parameternames += "param" + i + (i != ioModels.length - 1 ? ", " : "");
                String converterString = "";
                if(ioModels[i].getType().equals("Char")){
                    converterString += "args["+(i)+"].charAt(0)";
                }else if(ioModels[i].getType().equals("String")){
                    converterString += "args["+(i)+"]";
                }else{
                    converterString += javaCasts.get(ioModels[i].getType()) + "(args[" + (i) + "])";
                }
                code += "\t\t"+javaTypes.get(ioModels[i].getType()) + " param"+i+" = " + converterString + ";\n";
            }
        }
        /*take arguments*/
        /*pass converted arguments to program*/
        String className = "p_" + challange.getChallangeHeader().replaceAll("\\s+","") + "_" + username;
        code += "\t\t" + className + " classType = new " + className + "();\n";
        code += "\t\t" + javaTypes.get(testScenarioModels[0].getOutput().getType()) + " result = classType." + challange.getChallangeHeader().replaceAll("\\s+","") + "f(" + parameternames + ");\n";
        String converterString = "";
        if(testScenarioModels[0].getOutput().getType().equals("Char")){
            converterString += "args["+(i)+"].charAt(0)";
        }else if(testScenarioModels[0].getOutput().getType().equals("String")){
            converterString += "args["+(i)+"]";
        }else{
            converterString += javaCasts.get(testScenarioModels[0].getOutput().getType()) + "(args[" + (i) + "])";
        }
        code += "\t\t" + javaTypes.get(testScenarioModels[0].getOutput().getType()) + " expected = " + converterString + ";\n";
        code += "\t\tif(result == expected){\n";
        code += "\t\t\tSystem.exit(0);\n";
        code += "\t\t}else{\n";
        code += "\t\t\tSystem.exit(1);\n\t\t}\n";
        /*pass converted arguments to program*/
        code += "\t}\n";
        code += "}\n";

        code += challangeAnswerModel.getChallangeAnswer();

        File directory = new File(directorypath);
        directory.mkdir();
        File file = new File(directorypath+"/"+classpath + ".java");
        try{
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(directorypath + "/" + classpath + ".java");
            fileWriter.write(code);
            fileWriter.close();
        }catch(Exception exception){
            throw new RuntimeException("Bişey oldu");
        }
        boolean thereIsNoError = true;
        try{
            int compileProcess = runProcess("javac " + directorypath + "/" + classpath + ".java");
            for(int j = 0; j < testScenarioModels.length; j++){
                String arguments = "";
                IOModel[] inputModels = testScenarioModels[j].getInputs();
                IOModel outputModel = testScenarioModels[j].getOutput();
                for(int k = 0; k < inputModels.length; k++){
                    arguments += inputModels[k].getValue() + " ";
                }
                arguments += outputModel.getValue();
                int runProgramProcess = runProcess("java -cp " + directorypath + " " + classpath + " " + arguments);
                if(runProgramProcess == 1){
                    thereIsNoError = false;
                    break;
                }
            }
            String[] files = directory.list();
            for(String cf : files){
                File currentFile = new File(directory.getPath(),cf);
                currentFile.delete();
            }
            directory.delete();
        }catch(Exception exception){
            throw new RuntimeException("Bişey oldu");
        }
        return thereIsNoError;
    }
}
