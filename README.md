# Storm Course
Repository for a Apache Storm course I'll give to my co-workers

## Chapter 1: Topologies and bolts
In this chapter we'll create a simple topology that counts words using 
one of storm's predefined spouts and a bolt that we'll create in this
chapter.

## Chapter 2: Custom Spouts
In this chapter we'll create our own spout that emits tweets and a bolt 
that sends the text to the Wordcount bolt.

## Chapter 3: Streams
Now we'll create a new bolt that is able to identify the location based 
on the coordinates of the tweet metadata and we'l modify the bolt that
receives info from the spout to send the tweet text and metadata to two 
different streams.

## Chapter 4: Join bolts
After crating two different stream to process the data in parallel, we
are going to merge this information in another bolt so we can get the
_Trending Topics_ by location. In this case we'll use a RichBolt instead
of a BasicBolt so we'll have to manually ack and fail tuples. We'll have 
another bolt calculate the world trending topics.

## Chapter 5: Tuning our topology
Now that we have our topology fully working we'll learn about the 
different parameters we can use to modify the performance of our topology
and we'll try to get the best possible performance.
