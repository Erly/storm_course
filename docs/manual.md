# Storm Manual
<!-- TOC depthFrom:1 depthTo:4 withLinks:1 updateOnSave:1 orderedList:0 -->

- [Storm Manual](#storm-manual)
	- [What is storm?](#what-is-storm)
	- [Components](#components)
		- [Nodes](#nodes)
			- [Nimbus](#nimbus)
			- [Supervisor](#supervisor)
			- [Worker process](#worker-process)
			- [Zookeeper](#zookeeper)
		- [Topologies](#topologies)
			- [Spout](#spout)
			- [Bolt](#bolt)
			- [Task](#task)
			- [Stream](#stream)
			- [Tuple](#tuple)

<!-- /TOC -->
## What is storm?
Apache Storm is a distributed real-time processing system that is horizontally scalable. It is fault-tolerant and can optionally guarantee the processing of messages. Last but not least it's open source and free.

Storm can run in 2 different modes:

###### Streaming
Messages will flow continuosly through the system

###### Batching (Trident)
Messages will be grouped and then processed in batchs, this is more or less how Spark-streaming (quite ironic the streaming in the name) works.

## Components
A storm cluster consists on several nodes that run a topology.

### Nodes

![image](./images/storm-cluster.png)

#### Nimbus
A nimbus service is run on the master node of the cluster. This service is responsible for distributing the topology around the cluster, balancing it and monitoring for failures.

#### Supervisor
The supervisor service is run on the worker nodes (it can be run on the master node too along with the nimbus) and is responsible for listening to work assigned to its machine and starting worker processes for it.

#### Worker process
A worker process runs a subset of the topology. In the practice this is a JVM (Java Virtual Machine) running an _"instance"_ of the topology. Since the topology can actually be run through several supervisor and worker processes at the same time.

#### Zookeeper
The zookeeper technically is not part of the storm cluster, but as so many big data technologies it's necessary to run it. The zookeeper cluster is used to coordinate the Nimbus and supervisor processes and it also contains their state<sup id="a1">[1](#pacemaker)</sup> since the services themshelves are stateless. This means that any of these services can be killed and restarted without impacting anything and also the supervisors can continue working normally even if the nimbus service is killed.

### Topologies
A topology is essentially a job that runs forever. This _job_ is a graph of stream transformations that is composed of **spouts**, **bolts** and **streams**.

![image](./images/topology.png)

#### Spout
The spouts are the sources of tuples in a topology. It can read from a queue, an API, or just produce a stream of random stuff. A topology needs to have at least 1 spout but can have up to n.

#### Bolt
The bolts subscribe to any number of streams and receive tuples from them, do some processing and optionally they can emit tuples to other streams. The bolts are responsible for declaring their output streams and the tuples that are sent on them.

#### Task
A task is basically an instance of a spout/bolt. More than one task can run in a single thread but a task can't be executed in more than one thread. We'll be explain tasks, threads and processes in more detail on the [Parallelism](#parallelism) chapter.

#### Stream
The streams are composed of tuples and connect bolts (its tasks actually) with each other and with spouts. Streams have names to be able to differentiate the different output streams of a bolt, if no name is supplied when defining it, it will have **default** as its name.

##### Stream grouping
Stream grouping tells storm how to send tuples between tasks of different bolts. Without stream groupings Storm wouldn't be able to know what task of a bolt should receive the tuples emitted by the tasks of another bolt. There is a **ShuffleGrouping** that can be used when we don't care which tasks receives a tuple, but sometimes we want to group the tuples by a certain field so they get sent to the same task or send a tuple to all the possible tasks and not just to one.

#### Tuple
Tuples are the objects used by storm to pass information between bolts (and from the spouts). A tuple is a named list of values (more or less like a Map) and this values can be of any type. Storm supports all the primitive types, string and byte arrays natively and you can pass any other type as long as you implement a serializer for that type.

### Concepts
#### Acking
Storm can guarantee that messages get processed and for this it uses acking. Basically when a bolt ends working with a tuple it must ack it to acknowledge that it has been correctly processed. On the other side it has to fail it when an error has occurred and it wants it to be retried.

Since most bolts just ack the tuple at the end of the process method there is **IBasicBolt** that makes this automatically so you only have to worry about the process logic. Bear in mind that when using the BasicBolt a _FailedException_ must be thrown to make a tuple fail.

---
<b id="pacemaker"><sup>1</sup></b>: Starting with Storm 1.0 a pacemaker service can be run to process heartbeats from workers to avoid zookeeper from becoming a bottleneck when the storm cluster is scaled too much [↩](#a1)
