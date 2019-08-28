##多个资源的事务同步方式
+   XA与最后资源博弈
    +   1 Start messaging transaction
    +   2 Receive message
    +   3 Start JTA transaction on DB
    +   4 Update database
    +   5 Phase-1 Commit onDB transaction
    +   6 Commit messaging transaction
    +   7 Phase-1 Commit onDB transaction
+   共享资源
    +   两个数据源共享同一个底层资源
    +   ActiveMQ使用DB作为存储
    +   使用DB上的connection控制事务提交
    +   需要数据源支持
+   最大努力一次提交
    +   依次提交事务
    +   可能出错
    +   通过AOP或Listener实现事务直接的同步
+   链式事务
    +   定义一个事务链
    +   多个事务在一个事务管理器里面依次提交
    +   可能出错
+   如何选择（根据一致性要求）
    +   强一致性事务：JTA（性能最差，只适合于单个服务内）
    +   弱，最终一致性：最大努力一次提交，链式事务（设计相应的错误处理机制）
+   如何选择（根据场景）
    +   MQ-DB：最大努力一次提交—+重试
    +   多个DB：链式事务管理
    +   多个数据源：链式事务，或者其他事务同步
+   比较
JTA优缺点

JTA它的缺点是二次提交，事务时间长，数据锁的时间太长，性能比较低。

优点是强一致性，对于数据一致性要求很强的业务很有利，而且可以用于微服务。

链式/同步事务优缺点

优点： 比JTA轻量，能满足大部分事务需求，也是强一致性。

缺点： 只能单机玩，不能用于微服务，事务依次提交后提交的事务若出错不能回滚。

它两的比较

JTA重，Chained轻。

JTA能用于微服务分布式事务，Chained只能用于单机分布式事务。

事实上我们处理分布式事务都要求做到最终一致性。就是你刚开始我不需要保持你的数据一致，你中间可以出错，但是我能保证最终数据是一致的。
##分布式系统唯一性ID生成
+   数据库自增
+   UUID：唯一ID标准，128位，几种版本
+   Mongodb的ObjectId：时间戳+机器ID+进程ID+序号
+   Redis的INCR操作，Zookeeper节点的版本号
##使用何种方式
+   自增ID：考虑安全性，部署
+   时间序列：便利通过ID判断创建时间
+   长度，是否数字类型：时候建索引
##分布式对象
+   Redis：Redisson库，RLock，RMap，RQueue等对象
+   Zookeeper：Netflix Curator等：Lock，Queue对象
##分布式事务实现模式与技术
+   内容
    +   分布式事务实现的几种模式
        +   消息驱动模式：Message Driver
        +   事件溯源模式：Event Sourcing
        +   TCC模式：Try-Confirm-Cancel
+   幂等性,唯一性ID
    +   幂等操作：任意多次执行所产生的影响与一次执行的影响相同
    +   方法的幂等性：使用同样的参数调用一个方法多次与调用一次结果相同
    +   接口的幂等性：接口被重复调用，结果一致
    +   微服务接口的幂等性
        +   重要性：经常需要通过重试实现分布式事务的最终一致性
        +   GET方法不会对系统产生副作用，具有幂等性
        +   POST，PUT，DELETE方法实现需要满足幂等性
+   分布式锁，对象
+   幂等性实现方案
    +   方法中：存储已经处理过的id，下次判断已经处理了就不再处理
    +   sql中控制：在条件中加上判断是否已经处理了
