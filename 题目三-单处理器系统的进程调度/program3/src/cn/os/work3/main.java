package cn.os.work3;

import java.util.Scanner;

public class main {
     public static void main(String[] args){
         PCB head = null;
         head = new Create().createPCB(head,"a",3,10,2,0,0,0,0);
         head = new Create().createPCB(head,"b",1,1,4,0,0,0,0);
         head = new Create().createPCB(head,"c",4,2,1,0,0,0,0);
         head = new Create().createPCB(head,"d",5,1,0,0,0,0,0);
         head = new Create().createPCB(head,"e",2,5,3,0,0,0,0);
         main.mainMenu(head);
     }
     public static void mainMenu(PCB head){
         Create create = new Create();
         Scanner sc=new Scanner(System.in);
         int count=1;
         while (count==1){
             System.out.println("+++++++++++++++++++++++++++++++++++++++");
             System.out.println("1.添加新进程");
             System.out.println("2.选择调度算法");
             System.out.println("3.查看当前进程信息");
             System.out.println("0.退出");
             System.out.println("+++++++++++++++++++++++++++++++++++++++");
             int num = sc.nextInt();
             switch (num){
                 case 1:
                     String name;
                     int priority;
                     int runtime;
                     int arrivaltime;
                     System.out.println("请输入进程名：");
                     name = sc.next();
                     System.out.println("请输入进程优先级：");
                     priority=sc.nextInt();
                     System.out.println("请输入进程运行时间：");
                     runtime=sc.nextInt();
                     System.out.println("请输入进程到达时间：");
                     arrivaltime=sc.nextInt();
                     head = create.createPCB(head,name,priority,runtime,arrivaltime,0,0,0,0);
                     break;
                 case 2:main.dispatchMenu(head);break;
                 case 3:create.check(head);break;
                 case 0:count=0;break;
                 default:
                     System.out.println("Error:输入错误，请重新输入");
             }
         }
     }
     public static void dispatchMenu(PCB head){
         Scanner sc=new Scanner(System.in);
         Algorithm al = new Algorithm();
         int count =1;
         while(count==1){
             System.out.println("+++++++++++++++++++++++++++++++++++++++");
             System.out.println("请选择调度算法：");
             System.out.println("1.短作业优先级算法");
             System.out.println("2.高优先级优先算法");
             System.out.println("3.时间片轮转算法");
             System.out.println("4.最短剩余时间优先算法");
             System.out.println("0.退出");
             System.out.println("+++++++++++++++++++++++++++++++++++++++");
             int input = sc.nextInt();
             switch (input){
                 case 1:
                     al.ShortProcess(head);
                     break;
                 case 2:
                     al.Priority(head);
                     break;
                 case 3:
                     System.out.println("请输入时间片：");
                     int timeRound = sc.nextInt();
                     al.Roundrobin(head,timeRound);
                     break;
                 case 4:
                     al.ShortTime(head);
                     break;
                 case 0:
                     count=0;
                     break;
                 default:
                     System.out.println("Error:输入错误，请重新输入");
             }
         }
     }
}
