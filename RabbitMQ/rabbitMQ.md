# RabbitMQ

## 介绍

RabbitMQ 是一个消息代理，接收、缓存并发出消息。

- 三个角色：Producer、Queue、Consumer。三者大多数情况下不在一个主机。
- 发送过程：
    - 引入链接工厂并配置（ConnectionFactory）
    - 工厂生产链接（Connection）
    - 从链接中获取频道（Channel）
    - 频道定义一个队列
    - 将消息发送（publish）至队列

- 接收过程：
    - 引入链接工厂并配置（ConnectionFactory）
    - 工厂生产链接（Connection）
    - 从链接中获取频道（Channel）
    - 频道定义一个同名队列
    - 定义一个发送回调（DeliverCallback），该回调将缓冲消息，直到我们准备好使用它们

## Work Queues

- 作用：避免等待需要长时间、消耗大量资源的任务。规划任务稍后完成，我们将任务封装为消息并将其发送到队列。后台运行的 worker 逐个 pop 并最终将任务完成。多个 worker 将一起协作完成 tasks。(console job)导出。

- Round-robin dispatching（循环调度），多个 worker 的情况下，RabbitMQ 会将消息循环分配给每个 worker，每个 worker 分配的任务一样多。
- Message acknowledgment：消费者接收、执行完毕消息后告诉 RabbitMQ 这条消息可以随便删了。如果消费者发送确认前去世了，消息则会被转交给其他消费者。
- Message durability：消息在 RabbitMQ 服务重启期间保留，将消息和队列声明为持久化的。

## Publish/Subscribe

- 核心是一个消息发送给多个消费者，生产者将消息发送给 exchange，exchange 转发给队列。
- 四种 exchange：`direct`, `topic`, `headers` and `fanout`。

## Routing

- 消费者选择性的接收信息。队列绑定 exchange 时，增加一个 routing key，只接受符合 key 值的 message。

## Topics

- Routing 只能通过一种要素来判断是否接收，Topic exchange 来实现多种要素。

## Remote procedure call（RPC）

- 远程调用注意点：
    - 分清哪些是本地方法，哪些是远程方法。
    - 记录系统组件之间的依赖关系。
    - 错误处理机制。
- 回调队列：在请求中加入回调队列地址以接收响应。
- AMQP 0-9-1 协议定义了 14个伴随着消息的属性。四个主要的有：
    - `deliveryMode`：标记消息是否持久。
    - `contentType`。
    - `replyTo`：命名回调队列。
    - `correlationId`： 将请求与 RPC 响应关联。
- 每一个客户端一个回调队列 -> `replyTo`，每一个请求标记一个唯一值 `correlationId`。

## Publisher Confirms

- 开启 Publisher Confirms，broker 会确认消息是否安全的经过了服务端的处理。
