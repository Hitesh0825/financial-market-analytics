package com.market
import java.util.Properties
import java.util.concurrent.TimeUnit
import org.apache.kafka.clients.producer._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
object MarketEventProducer extends App {
  val a = args.sliding(2,2).collect { case Array(k,v) => k -> v }.toMap
  val bootstrap=a.getOrElse("--bootstrap", sys.env.getOrElse("KAFKA_BOOTSTRAP_SERVERS","localhost:9092")); val topic=a.getOrElse("--topic","market-events")
  val rate=a.getOrElse("--rate","10").toInt; val count=a.getOrElse("--count","0").toLong
  val invalid=a.getOrElse("--invalid-rate","0.02").toDouble; val duplicate=a.getOrElse("--duplicate-rate","0.02").toDouble; val late=a.getOrElse("--late-rate","0.02").toDouble
  val props=new Properties; props.put("bootstrap.servers",bootstrap); props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer"); props.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer"); props.put("acks","all"); props.put("enable.idempotence","true")
  val producer=new KafkaProducer[String,String](props); val mapper=new ObjectMapper().registerModule(DefaultScalaModule); sys.addShutdownHook(producer.close())
  var n=1L; var last: Option[MarketEvent]=None
  while(count==0 || n<=count) { val e=if(last.nonEmpty && scala.util.Random.nextDouble<duplicate) last.get else MarketEventGenerator.next(n,invalid,late); producer.send(new ProducerRecord(topic,e.symbol,mapper.writeValueAsString(e))).get(); last=Some(e); n+=1; TimeUnit.MILLISECONDS.sleep(math.max(1,1000/rate)) }
  producer.flush(); producer.close()
}
