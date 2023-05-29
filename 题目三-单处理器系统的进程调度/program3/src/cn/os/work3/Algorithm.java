package cn.os.work3;

import java.util.HashSet;

public class Algorithm {
    private PCB pre=null;
    private PCB prev=null;
    private PCB min=null;
    private int num=0;

    private int start=0;
    private double nums=0;
    private int count=0;
    private static Create create = new Create();
    public void ShortProcess(PCB head){
        PCB head2= CreateHead(head);
        while (head2!=null){
            min=null;
            pre=null;
            PCB cur = head2;
            int mintime=10000;
            while (cur!=null){
                if(cur.runtime<=mintime && cur.arrival_time<=start){
                    mintime=cur.runtime;
                    prev=pre;
                    min=cur;
                }
                pre=cur;
                cur=cur.nextpcb;
            }
            if(min==null){
                min = findMin(head2);
            }
            if(min.arrival_time>start){
                start=min.arrival_time;
            }
            head2 = toolMethod(min,prev,start,head2);
            start+=min.runtime;
            num+=min.turn_time;
            nums+=min.dturn_time;
            count++;
        }
        this.endFun();
    }
    public void endFun(){
        System.out.println("平均周转时间："+(double)this.num/(double)this.count+"平均带权周转时间："+
                this.nums/this.count );
        this.start=0;
        this.nums=0;
        this.num=0;
        this.count=0;
    }
    public PCB toolMethod(PCB min,PCB prev,int start,PCB head){
        min.start_time=start;
        min.end_time=min.start_time+min.runtime;
        min.turn_time=min.end_time-min.arrival_time;
        min.dturn_time=(double) min.turn_time/(double) min.runtime;
        System.out.println("进程名："+min.name+"    优先级："+min.priority+"    运行时间："+
                min.runtime+"   到达时间："+min.arrival_time+"   开始时间："+min.start_time+"   结束时间："+
                min.end_time+"  周转时间："+min.turn_time+"  带权周转时间："+min.dturn_time);
        if(prev ==null){
            if(min.nextpcb==null){
                return null;
            }
            return min.nextpcb;
        }else {
            prev.nextpcb=min.nextpcb;
        }
        return head;
    }
    public PCB findMin(PCB head){
        PCB cur = head;
        PCB real = null;
        int mintime =cur.arrival_time;
        while (cur!=null){
            if(cur.arrival_time<=mintime){
                mintime = cur.arrival_time;
                real=cur;
            }
            cur = cur.nextpcb;
        }
        return real;
    }
    public PCB CreateHead(PCB head){
        PCB head2 = null;
        PCB cur =head;
        while (cur!=null){
            head2=create.createPCB(head2, cur.name, cur.priority, cur.runtime, cur.arrival_time, cur.start_time,cur.end_time,cur.turn_time,cur.dturn_time);
            cur = cur.nextpcb;
        }
        return head2;
    }

    public void Priority(PCB head) {
        PCB head2 = CreateHead(head);
        while (head2!=null){
            min = null;
            pre = null;
            PCB cur = head2;
            int mintime = 0;
            while (cur!=null){
                if (cur.priority>=mintime&&cur.arrival_time<=start){
                    mintime=cur.priority;
                    prev=pre;
                    min=cur;
                }
                pre=cur;
                cur=cur.nextpcb;
            }
            if(min==null){
                min = findMin(head2);
            }
            if(min.arrival_time>start){
                start=min.arrival_time;
            }
            head2 = toolMethod(min,prev,start,head2);
            start+=min.runtime;
            num+=min.turn_time;
            nums+=min.dturn_time;
            count++;
        }
        this.endFun();
    }

    public void Roundrobin(PCB head, int timeR) {
        PCB newpcb=null;
        HashSet<String> s = new HashSet<String>();
        PCB head2 = QueueHead(head);
        while (head2!=null){
            min=head2;
            if(!s.contains(min.name)){
                s.add(min.name);
                min.start_time = min.start_time+timeR*(s.size()-1);
            }
            if(min.arrival_time>start){
                start = min.arrival_time;
            }
            if(min.new_runtime>timeR){
                min.new_runtime -= timeR;
                start+=timeR;
                min.new_arrival+=timeR;
                newpcb = new PCB(min.name, min.priority, min.runtime, min.arrival_time,
                        min.start_time, min.end_time, min.turn_time, min.dturn_time);
                newpcb.new_arrival = min.new_arrival;
                newpcb.new_runtime=min.new_runtime;
                insert(head2,newpcb);
                head2 = min.nextpcb;
            }else {
                start+=min.new_runtime;
                min.end_time = start;
                min.turn_time=min.end_time-min.arrival_time;
                min.dturn_time=(double) min.turn_time/(double) min.runtime;
                head2 = min.nextpcb;
                num+=min.turn_time;
                nums+=min.dturn_time;
                count++;
                System.out.println("进程名："+min.name+"    优先级："+min.priority+"    运行时间："+
                        min.runtime+"   到达时间："+min.arrival_time+"   开始时间："+min.start_time+"   结束时间："+
                        min.end_time+"  周转时间："+min.turn_time+"  带权周转时间："+min.dturn_time);
            }
        }
        this.endFun();
    }

    public void insert(PCB head, PCB min) {
        PCB cur = head;
        PCB pre = null;
        while (cur!=null){
            if(cur.arrival_time>min.new_arrival){
                pre.nextpcb = min;
                min.nextpcb = cur;
                return;
            }
            pre = cur;
            cur = cur.nextpcb;
        }
        pre.nextpcb = min;
        min.nextpcb = cur;
    }

    private PCB QueueHead(PCB head) {
        PCB cur = head;
        PCB nodemin = null;
        PCB head2 = null;
        int min=1000;
        int count=0;
        while (cur!=null){
            count++;
            cur = cur.nextpcb;
        }
        while (count!=0){
            min=1000;
            cur=head;
            while (cur!=null){
                if(cur.arrival_time<min&&cur.status==0){
                    nodemin = cur;
                    min = cur.arrival_time;
                }
                cur = cur.nextpcb;
            }
            nodemin.status=1;
            count--;
            head2 = create.createPCB(head2, nodemin.name, nodemin.priority, nodemin.runtime,
                    nodemin.arrival_time, nodemin.start_time, nodemin.end_time,
                    nodemin.turn_time, nodemin.dturn_time);
        }
        return head2;
    }

    public void ShortTime(PCB head) {
        PCB head2 = CreateHead(head);
        int time = 0;
        HashSet<String> s = new HashSet<String>();
        while (head2!=null){
            min=null;
            pre=null;
            PCB cur =head2;
            int mintime = 1000;
            while (cur!=null){
                if(cur.runtime-cur.used_time<=mintime&&cur.arrival_time<=start){
                    mintime = cur.runtime-cur.used_time;
                    prev = pre;
                    min = cur;
                }
                pre = cur;
                cur = cur.nextpcb;
            }
            if(!s.contains(min.name)){
                s.add(min.name);
                min.start_time = time;
            }
            if(min==null){
                min=findMin(head2);
            }
            if(min.arrival_time>start){
                start=min.arrival_time;
            }
            PCB p=null;
            PCB p1=null;
            while (true){
                min.used_time++;
                time++;
                if(min.used_time==min.runtime){
                    min.end_time=time;
                    min.turn_time=min.end_time-min.arrival_time;
                    min.dturn_time=(double)min.turn_time/(double) min.runtime;
                    System.out.println("进程名："+min.name+"    优先级："+min.priority+"    运行时间："+
                            min.runtime+"   到达时间："+min.arrival_time+"   开始时间："+min.start_time+"   结束时间："+
                            min.end_time+"  周转时间："+min.turn_time+"  带权周转时间："+min.dturn_time);
                    if(prev==null){
                        if(min.nextpcb==null){
                            head2=null;
                        }
                        head2 = min.nextpcb;
                    }else{
                        prev.nextpcb=min.nextpcb;
                    }
                    start = time;
                    num+=min.turn_time;
                    nums+=min.dturn_time;
                    count++;
                    break;
                }
                p = head2;
                p1=head2;
                while (p!=null){
                    if(p.arrival_time<=time){
                        if(p.runtime<min.runtime-min.used_time){
                            while (p1!=null){
                                if(p1.name==min.name){
                                    p1.used_time=min.used_time;
                                    break;
                                }
                                p1 = p1.nextpcb;
                            }
                            prev=pre;
                            min=p;
                        }
                    }
                    pre=p;
                    p=p.nextpcb;
                }
                if(!s.contains(min.name)){
                    s.add(min.name);
                    min.start_time=time;
                }
            }
        }
        this.endFun();
    }
}
