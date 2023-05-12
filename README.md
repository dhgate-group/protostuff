![Protostuff](https://protostuff.github.io/images/protostuff_300x100.png)



###Problem Encountered or Solved


**1，require fast serialization that is also friendly to large objects**

    After multiple comparisons, it was determined that protostuff provided stable performance and was friendly to large objects, with relatively consistent speeds. 
    Alternative options such as JSON and JSON2 were considered, however, due to non-standardized coding practices in our longstanding Internet company history, 
    JSON could not be used for things like unclear generics, including List<Object> or List<Map>. Consequently, protostuff was ultimately selected.

**2，Compatibility**

    Given the selection of protostuff, compatibility problems emerged:
    
    (a) Without tags, developers may add new attributes to classes in the wrong location.
    (b) Adding tags also poses issues. For instance, we are a longstanding Internet company that has already adopted microservices. 
    There are many projects with complex dependency relationships. Suppose Developer A adds an attribute to a DTO field but forgets to add the tag before building. 
    At the same time, Developer B is building and publishing changes to their project, which may only be small, such as a single logging message. 
    It's unreasonable to ask them to run all functional and unit tests again. However, due to A's (illegal) modifications, B's project experiences errors when published.
**3，Reduce Workload**

    Adding tags is a mechanical process that creates an unpleasant user experience.


**4,Solution**

    On top of the existing protostuff codebase, small tweaks were made so that if a tag is present, it takes its corresponding value, and if not, it calculates one instead.

**5,JUnit Test Class**

    NoAnnotatedFieldsTest

**6,Regrets**

    Because an automatically calculated value replaces tags, there is a chance of hash collisions. If this occurs, another tag can be added to the collision property.
    PS: However, after comparing multiple solutions in terms of performance and usability, the current solution remains the best choice.

**7,Conclusion**

    Two modes are available in the version used internally by our company. One is as described above, and the other remains compatible with older versions. 
    This is because it's impossible to upgrade all jar packages across the company simultaneously. If necessary, we can continue modifying our current solution to include this feature.

**8,contact us**
email：wangliguang@163.com
WeChat：wangliguang517
company：jiagou.list <jiagou.list@dhgate.com>






###遇到或解决的问题

**1，需要高速的序列化，且对大对象很友好**

    经过多次比对发现protostuff性能稳定，且对大对象很友好，速度相对稳定。
    中间也考虑过用json，或者json2，但是作为一家老牌互联网公司历史上会有一些，不是很规范的写法，导致json无法使用，
    比如泛型不明确：List<Object> ,List<Map>
    最终选择了protostuff
**2，兼容性**

    选择protostuff后面临兼容性的问题
    a，不加tag的话，可能会有开发的同学将新增的属性发到类的上面，或者中间
    b，加tag，也会面临其他的问题
    比如我们敦煌作为老牌互联网公司，且已经微服务化。会有很多个工程，同时依赖关系也相对复杂。
    A工程的开发同学对dto字段加了属性但是忘记加tag。然后进行构建。
    B工程现在正好构建，发版。但是B工程只改了一小点内容，甚至是一条日志打印，不可能要求他把所有的功能测试，或者单元测试都跑一遍。
    但是由于A的(非法)修改导致B工程上线报错。
**3，减少工作量**

    加tag作为一个机械性动作，带来的使用感觉不是很好


**4,解决思路**

    在原有的protostuff基础上做了小的调整，有tag取tag值，没有tag计算一个值

**5,junit测试类**

    NoAnnotatedFieldsTest

**6,遗憾**

    因为自动计算值来代替tag，会有一定的hash碰撞几率，假如碰撞可以在碰撞的属性上再加一个tag。
    PS：不过在性能，和可用性上面多个方案对比后，综合考虑后还是目前的方案最为适合

**7,写到最后**

    我们公司内部使用的版本，有2个模式一个是上面介绍的。
    另外一个模式是兼容以前老的版本，因为无法做到全公司同一时间升级jar包。
    如果大家需要，我可以在当前基础上继续修改，将这个功能也囊括进来
    
**8,联系我们**
我公司邮箱：wangliguang@163.com
我微信：wangliguang517
公司群组邮箱：jiagou.list <jiagou.list@dhgate.com>
