package com.example.program;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class HelloController implements Initializable {

    @FXML
    private TextField initPos;

    @FXML
    private VBox vbox1;

    @FXML
    private HBox hbox1;

    @FXML
    private HBox hbox2;

    @FXML
    private HBox hbox3;

    @FXML
    private HBox hbox4;
    @FXML
    private HBox hbox5;
    private TextArea textArea = new TextArea();
    int[] array=new int[400];
    int start = 0;
    int fcfsPath=0;
    int[] fcfsArray = new int[400];
    int sstfPath=0;
    int[] sstfArray = new int[400];
    int lookPath=0;
    int[] lookArray = new int[400];
    int cscanPath=0;
    int[] cscanArray = new int[400];
    @FXML
    void start() {
        if (isNumeric2(initPos.getText())){
            start=Integer.parseInt(initPos.getText());
            if(start>=0&&start<1500){
                series1.getData().clear();
                series2.getData().clear();
                series3.getData().clear();
                series4.getData().clear();
                init();
                fcfs();
                sstf();
                look();
                cscan();
                for (int i =0;i<400;i++){
                    series1.getData().add(new XYChart.Data(i,fcfsArray[i]));
                    series2.getData().add(new XYChart.Data(i,sstfArray[i]));
                    series3.getData().add(new XYChart.Data(i,lookArray[i]));
                    series4.getData().add(new XYChart.Data(i,cscanArray[i]));
                }
                fcfsData.setText("FCFS总磁头移动数："+fcfsPath+"\n平均寻道长度："+((double)fcfsPath/400.0));
                sstfData.setText("SSTF总磁头移动数："+sstfPath+"\n平均寻道长度："+((double)sstfPath/400.0));
                lookData.setText("LOOK总磁头移动数："+lookPath+"\n平均寻道长度："+((double)lookPath/400.0));
                cscanData.setText("C-SCAN总磁头移动数："+cscanPath+"\n平均寻道长度："+((double)cscanPath/400.0));
            }
            else {
                textArea.appendText("\n磁头初始位置越界！请重新输入磁头初始位置（0-1500）");
            }
        }else{
            textArea.appendText("\n输入磁头初始位置输入格式不正确，请重新输入，再开始模拟！");
        }
    }
    public void init(){//磁盘磁道数为1500

        int[] array0 = new int[200];
        int[] array1 = new int[100];
        int[] array2 = new int[100];
        for(int i=0; i<200; i++){
            array0[i] = (int)(Math.random()*500);
        }
        for(int i=0; i<100; i++){
            array1[i] = 500+(int)(Math.random()*500);
        }
        for(int i=0; i<100; i++){
            array2[i] = 1000+(int)(Math.random()*500);
        }
        for (int i=0; i<400; i++){
            if (i<200){
                array[i] = array0[i];
            }
            if (i>=200&&i<300){
                array[i] = array1[i-200];
            }
            if (i>=300){
                array[i] = array2[i-300];
            }
        }
        textArea.appendText("已创建新的磁盘请求序列号队列分别如下:\n");
        for (int i=0;i<400;i++){
            if(array[i]/10==0&&array[i]!=10){
                textArea.appendText(array[i] +"       ");
            }else if((array[i]/10>0&&array[i]/10<10)||array[i]==10){
                textArea.appendText(array[i] +"     ");
            }else if((array[i]/100>0&&array[i]/100<10)||array[i]==100){
                textArea.appendText(array[i] +"   ");
            }else {
                textArea.appendText(array[i] +" ");
            }
            if(i%10==9){
                textArea.appendText("\n");
            }
        }
    }

    public void fcfs(){
        int b=start;
        fcfsPath=Math.abs(b-array[0]);
        b=array[0];
        fcfsArray[0]=array[0];
        for(int i=1;i<400;i++){
            fcfsArray[i]=array[i];
            fcfsPath+=Math.abs(fcfsArray[i-1]-fcfsArray[i]);
        }
    }
    int Smin(int b,int[] re){
        int min = Math.abs(b-re[0]);
        int j=0;
        for(int i=1;i<400;i++){
            if(re[i]<1500) {
                if (Math.abs(b - re[i]) < min) {
                    min = Math.abs(b - re[i]);
                    j = i;
                }
            }
        }
        return j;
    }
    public void sstf(){
        int c=0;
        int b=start;
        int[] re=new int[400];
        for(int i=0;i<400;i++){
            re[i]=array[i];
        }
        for(int i=0;i<400;i++){
            c=Smin(b,re);
            b=re[c];
            re[c]=99999999;
            sstfArray[i]=b;
        }
        sstfPath=Math.abs(start-sstfArray[0]);
        for(int i=1;i<400;i++){
            sstfPath+=Math.abs(sstfArray[i-1]-sstfArray[i]);
        }
    }
    public void look(){
        int c=0,b=start;
        int[] re=new int[400];
        for(int i=0;i<400;i++){
            re[i]=array[i];
        }
        for(int i=0;i<399;i++){
            for(int j=0;j<400-i-1;j++){
                if(re[j]>re[j+1]){
                    re[j]=re[j]+re[j+1];
                    re[j+1]=re[j]-re[j+1];
                    re[j]=re[j]-re[j+1];
                }
            }
        }
        for(int i=0;i<400;i++) {
            if (re[i] > b) {
                lookArray[c++] = re[i];
            }
        }
        for(int i=399;i>=0;i--) {

            if (re[i] < b) {
                lookArray[c++] = re[i];
            }
        }
        lookPath=Math.abs(start-lookArray[0]);
        for(int i=1;i<400;i++){
            lookPath+=Math.abs(lookArray[i-1]-lookArray[i]);
        }
    }
    public void cscan(){
        int c=0,b=start;
        int[] re=new int[400];
        for(int i=0;i<400;i++){
            re[i]=array[i];
        }
        for(int i=0;i<399;i++){
            for(int j=0;j<400-i-1;j++){
                if(re[j]>re[j+1]){
                    re[j]=re[j]+re[j+1];
                    re[j+1]=re[j]-re[j+1];
                    re[j]=re[j]-re[j+1];
                }
            }
        }
        for(int i=0;i<400;i++) {
            if (re[i] > b) {
                cscanArray[c++] = re[i];
            }
        }
        for(int i=0;i<400;i++) {

            if (re[i] < b) {
                cscanArray[c++] = re[i];
            }
        }
        cscanPath=Math.abs(start-cscanArray[0]);
        for(int i=1;i<400;i++){
            cscanPath+=Math.abs(cscanArray[i-1]-cscanArray[i]);
        }
    }
    NumberAxis xAxis1 = new NumberAxis();
    NumberAxis yAxis1 =new NumberAxis(0,2000,1000);
    NumberAxis xAxis2 = new NumberAxis();
    NumberAxis yAxis2 =new NumberAxis();
    NumberAxis xAxis3 = new NumberAxis();
    NumberAxis yAxis3 =new NumberAxis();
    NumberAxis xAxis4 = new NumberAxis();
    NumberAxis yAxis4 =new NumberAxis();
    LineChart<Number,Number> fcfsLineChart = new LineChart<>(xAxis1,yAxis1);
    LineChart<Number,Number> sstfLineChart = new LineChart<>(xAxis2,yAxis2);
    LineChart<Number,Number> lookLineChart = new LineChart<>(xAxis3,yAxis3);
    LineChart<Number,Number> cscanLineChart = new LineChart<>(xAxis4,yAxis4);

    XYChart.Series series1 = new XYChart.Series<>();
    XYChart.Series series2 = new XYChart.Series<>();
    XYChart.Series series3 = new XYChart.Series<>();
    XYChart.Series series4 = new XYChart.Series<>();
    Label fcfsData = new Label("FCFS总磁头移动数：0\n平均寻道长度：0");
    Label sstfData = new Label("SSTF总磁头移动数：0\n平均寻道长度：0");
    Label lookData = new Label("LOOK总磁头移动数：0\n平均寻道长度：0");
    Label cscanData = new Label("C-SCAN总磁头移动数：0\n平均寻道长度：0");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        xAxis1 = new NumberAxis();
        yAxis1 =new NumberAxis(0,1600,100);
        xAxis2 = new NumberAxis();
        yAxis2 =new NumberAxis(0,1600,100);
        xAxis3 = new NumberAxis();
        yAxis3 =new NumberAxis(0,1600,100);
        xAxis4= new NumberAxis();
        yAxis4 =new NumberAxis(0,1600,100);
        fcfsLineChart = new LineChart<>(xAxis1,yAxis1);
        sstfLineChart= new LineChart<>(xAxis2,yAxis2);
        lookLineChart= new LineChart<>(xAxis3,yAxis3);
        cscanLineChart= new LineChart<>(xAxis4,yAxis4);
        series1 = new XYChart.Series<>();
        series2 = new XYChart.Series<>();
        series3 = new XYChart.Series<>();
        series4 = new XYChart.Series<>();
        fcfsLineChart.setTitle("FCFS");
        fcfsLineChart.setTitleSide(Side.BOTTOM);
        sstfLineChart.setTitle("SSFT");
        sstfLineChart.setTitleSide(Side.BOTTOM);
        lookLineChart.setTitle("LOOK");
        lookLineChart.setTitleSide(Side.BOTTOM);
        cscanLineChart.setTitle("C-SCAN");
        cscanLineChart.setTitleSide(Side.BOTTOM);
        fcfsLineChart.getData().addAll(series1);
        sstfLineChart.getData().addAll(series2);
        lookLineChart.getData().addAll(series3);
        cscanLineChart.getData().addAll(series4);
        // xAxis.setLabel("磁盘调度顺序");
        // yAxis.setLabel("当前位置");
        hbox1.getChildren().addAll(fcfsLineChart,fcfsData);
        hbox1.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(sstfLineChart,sstfData);
        hbox2.setAlignment(Pos.CENTER);
        hbox3.getChildren().addAll(lookLineChart,lookData);
        hbox3.setAlignment(Pos.CENTER);
        hbox4.getChildren().addAll(cscanLineChart,cscanData);
        hbox4.setAlignment(Pos.CENTER);
        hbox5.getChildren().addAll(textArea);
        hbox5.setAlignment(Pos.CENTER);
    }
    public static boolean isNumeric2(String str) {
        if (!str.matches("^[0-9]+[0-9]*$")) {
            return false;
        }
        return true;
    }
}

