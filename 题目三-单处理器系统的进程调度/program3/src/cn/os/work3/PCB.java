package cn.os.work3;

public class PCB {
    String name;
    int priority;
    int runtime;
    int arrival_time;
    int start_time;
    int end_time;
    int turn_time;
    double dturn_time;
    PCB nextpcb;
    int status;
    int used_time=0;
    int new_arrival;
    int new_runtime;

    public PCB(String name,int priority,int runtime,int arrival_time,int start_time,
               int end_time,int turn_time,double dturn_time){
        this.name=name;
        this.priority=priority;
        this.arrival_time=arrival_time;
        this.runtime=runtime;
        this.nextpcb=null;
        this.start_time=start_time;
        this.end_time=end_time;
        this.turn_time=turn_time;
        this.used_time=0;
        this.dturn_time=dturn_time;
        this.new_arrival=arrival_time;
        this.new_runtime=runtime;
    }
}
