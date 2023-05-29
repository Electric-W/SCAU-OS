package cn.os.work3;

public class Create {
    public PCB createPCB(PCB head,String name,int priority,int runtime,int arrivaltime,
                         int starttime,int endtime,int turntime,double dturntime){
        if(head==null){
            PCB pcb = new PCB(name,priority,runtime,arrivaltime,starttime,endtime,turntime,dturntime);
            head=pcb;
            return head;
        }

        PCB cur = head;
        while (cur.nextpcb!=null){
            cur=cur.nextpcb;
        }
        PCB pcb = new PCB(name,priority,runtime,arrivaltime,starttime,endtime,turntime,dturntime);
        cur.nextpcb=pcb;
        return head;
    }
    public void check(PCB head){
        if(head==null){
            System.out.println("当前没有节点信息");
            return;
        }
        PCB cur = head;
        while (cur!=null){
            System.out.println("进程名"+cur.name+"、优先级："+cur.priority+"、需要运行时间"+cur.runtime+
                    "、到达时间："+cur.arrival_time);
            cur = cur.nextpcb;
        }
    }
}
