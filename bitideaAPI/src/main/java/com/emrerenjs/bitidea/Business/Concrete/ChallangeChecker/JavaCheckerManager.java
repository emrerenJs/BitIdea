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

    private int runProcess(String[] command) throws InterruptedException, IOException {
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
            }else if(ioModels[i].getDimension() == 1){
                parameternames += "param" + i +(i != ioModels.length - 1 ? ", " : "");
                code += "\t\tString[] elements"+i+" = new String[0];\n";
                code += "\t\tString input"+i+" = args["+(i)+"];\n";
                code += "\t\tString element"+i+" = input"+i+".substring(1,input"+i+".length()-1);\n";
                code += "\t\telements"+i+"= element"+i+".split(\",\");\n";
                code += "\t\t"+javaTypes.get(ioModels[i].getType()) + "[] param"+i+" = new "+javaTypes.get(ioModels[i].getType())+"[elements"+i+".length];\n";
                code += "\t\tfor(int i"+i+" = 0; i"+i+" < elements"+i+".length; i"+i+"++){\n";
                if(ioModels[i].getType().equals("Char")){
                    code += "\t\t\tparam"+i+"[i"+i+"] = (elements"+i+"[i"+i+"]).charAt(0);\n";
                }else{
                    code += "\t\t\tparam"+i+"[i"+i+"] = " + javaCasts.get(ioModels[i].getType()) + "(elements"+i+"[i"+i+"]);\n";
                }

                code += "\t\t}\n";
            }else if(ioModels[i].getDimension() == 2){
                parameternames += "param2d" + i +(i != ioModels.length - 1 ? ", " : "");

                code += "\t\tString[] elements2d"+i+" = new String[0];\n";
                code += "\t\tString input2d"+i+" = args["+(i)+"];\n";
                code += "\t\tString element2d"+i+" = input2d"+i+".substring(1,input2d"+i+".length()-1);\n";
                code += "\t\telements2d"+i+"= element2d"+i+".split(\"],\");\n";

                code += "\t\t"+javaTypes.get(ioModels[i].getType()) + "[][] param2d"+i+" = new "+javaTypes.get(ioModels[i].getType())+"[elements2d"+i+".length][0];\n";
                code += "\t\tfor(int i2d"+i+" = 0; i2d"+i+" < elements2d"+i+".length; i2d"+i+"++){\n";
                code += "\t\t\tif(i2d"+i+" != elements2d"+i+".length - 1){\n";
                code += "\t\t\t\telements2d"+i+"[i2d"+i+"] = elements2d"+i+"[i2d"+i+"] + \"]\";\n";
                code += "\t\t\t}\n";

                code += "\t\t\tString[] elements"+i+" = new String[0];\n";
                code += "\t\t\tString input"+i+" = elements2d"+i+"[i2d"+i+"];\n";
                code += "\t\t\tString element"+i+" = input"+i+".substring(1,input"+i+".length()-1);\n";
                code += "\t\t\telements"+i+"= element"+i+".split(\",\");\n";
                code += "\t\t\t"+javaTypes.get(ioModels[i].getType()) + "[] param"+i+" = new "+javaTypes.get(ioModels[i].getType())+"[elements"+i+".length];\n";
                code += "\t\t\tfor(int i"+i+" = 0; i"+i+" < elements"+i+".length; i"+i+"++){\n";
                if(ioModels[i].getType().equals("Char")){
                    code += "\t\t\tparam"+i+"[i"+i+"] = (elements"+i+"[i"+i+"]).charAt(0);\n";
                }else{
                    code += "\t\t\tparam"+i+"[i"+i+"] = " + javaCasts.get(ioModels[i].getType()) + "(elements"+i+"[i"+i+"]);\n";
                }
                code += "\t\t\t}\n";
                code += "\t\t\tparam2d"+i+"[i2d"+i+"] = param"+i+";\n";
                code += "\t\t}\n";

            }else if(ioModels[i].getDimension() == 3){
                parameternames += "param3d" + i +(i != ioModels.length - 1 ? ", " : "");
                code += "\t\tString[] elements3d"+i+" = new String[0];\n";
                code += "\t\tString input3d"+i+" = args["+i+"];\n";
                code += "\t\tString element3d"+i+" = input3d"+i+".substring(1,input3d"+i+".length()-1);\n";
                code += "\t\telements3d"+i+" = element3d"+i+".split(\"]],\");\n";
                code += "\t\t"+javaTypes.get(ioModels[i].getType()) + "[][][] param3d"+i+" = new "+javaTypes.get(ioModels[i].getType())+"[elements3d"+i+".length][0][0];\n";
                code += "\t\tfor(int i3d"+i+" = 0; i3d"+i+" < elements3d"+i+".length; i3d"+i+"++){\n";
                code += "\t\t\tif(i3d"+i+" != elements3d"+i+".length - 1){\n";
                code += "\t\t\t\telements3d"+i+"[i3d"+i+"] = elements3d"+i+"[i3d"+i+"] + \"]]\";\n";
                code += "\t\t\t}\n";

                code += "\t\t\tString[] elements2d"+i+" = new String[0];\n";
                code += "\t\t\tString input2d"+i+" = elements3d"+i+"["+(i)+"];\n";
                code += "\t\t\tString element2d"+i+" = input2d"+i+".substring(1,input2d"+i+".length()-1);\n";
                code += "\t\t\telements2d"+i+"= element2d"+i+".split(\"],\");\n";
                code += "\t\t\t"+javaTypes.get(ioModels[i].getType()) + "[][] param2d"+i+" = new "+javaTypes.get(ioModels[i].getType())+"[elements2d"+i+".length][0];\n";
                code += "\t\t\tfor(int i2d"+i+" = 0; i2d"+i+" < elements2d"+i+".length; i2d"+i+"++){\n";
                code += "\t\t\t\tif(i2d"+i+" != elements2d"+i+".length - 1){\n";
                code += "\t\t\t\t\telements2d"+i+"[i2d"+i+"] = elements2d"+i+"[i2d"+i+"] + \"]\";\n";
                code += "\t\t\t\t}\n";

                code += "\t\t\t\tString[] elements"+i+" = new String[0];\n";
                code += "\t\t\t\tString input"+i+" = elements2d"+i+"[i2d"+i+"];\n";
                code += "\t\t\t\tString element"+i+" = input"+i+".substring(1,input"+i+".length()-1);\n";
                code += "\t\t\t\telements"+i+"= element"+i+".split(\",\");\n";
                code += "\t\t\t\t"+javaTypes.get(ioModels[i].getType()) + "[] param"+i+" = new "+javaTypes.get(ioModels[i].getType())+"[elements"+i+".length];\n";
                code += "\t\t\t\tfor(int i"+i+" = 0; i"+i+" < elements"+i+".length; i"+i+"++){\n";
                if(ioModels[i].getType().equals("Char")){
                    code += "\t\t\tparam"+i+"[i"+i+"] = (elements"+i+"[i"+i+"]).charAt(0);\n";
                }else{
                    code += "\t\t\tparam"+i+"[i"+i+"] = " + javaCasts.get(ioModels[i].getType()) + "(elements"+i+"[i"+i+"]);\n";
                }
                code += "\t\t\t\t}\n";

                code += "\t\t\t\tparam2d"+i+"[i2d"+i+"] = param"+i+";\n";
                code += "\t\t\t}\n";
                code += "\t\t\tparam3d"+i+"[i3d"+i+"] = param2d"+i+";\n";
                code += "\t\t}\n";
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
        if(testScenarioModels[0].getOutput().getType().equals("String")){
            code += "\t\tif(result.equals(expected)){\n";
        }else{
            code += "\t\tif(result == expected){\n";
        }
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
                String[] command = new String[inputModels.length + 5];
                command[0] = "java";
                command[1] = "-cp";
                command[2] = directorypath;
                command[3] = classpath;
                int k = 0;
                for(k = 0; k < inputModels.length; k++){
                    if(inputModels[k].getType().equals("String")){
                        command[k + 4] = inputModels[k].getValue().toString();
                    }else{
                        command[k + 4] = inputModels[k].getValue().toString().replace(" ","");
                    }
                }
                command[k + 4] = outputModel.getValue().toString();
                int runProgramProcess = runProcess(command);
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
