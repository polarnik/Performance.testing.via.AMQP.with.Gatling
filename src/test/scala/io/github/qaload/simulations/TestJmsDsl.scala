package io.github.qaload.simulations

import com.ibm.mq.jms.MQConnectionFactory
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.jms.Predef._
import io.github.qaload.scenariofragment.PutMessage
import javax.jms._

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.Try

class TestJmsDsl extends Simulation {


  val conf = ConfigFactory.load().getConfig("test")

  private val jmsHost = Try(conf.getString("jms.hostname")).getOrElse("")
  private val jmsPort = Try(conf.getString("jms.port")).getOrElse("1415")
  private val jmsQueueManager = Try(conf.getString("jms.queueManager")).getOrElse("")
  private val jmsChannel = Try(conf.getString("jms.channel")).getOrElse("UNKNOWN")
  private val jmsInputName = Try(conf.getString("jms.input.name")).getOrElse("")
  private val jmsOutputName = Try(conf.getString("jms.output.name")).getOrElse("")

  // create a ConnectionFactory for ActiveMQ
  // search the documentation of your JMS broker
  val connectionFactory = new MQConnectionFactory()
  connectionFactory.setTransportType(1)

  connectionFactory.setHostName(jmsHost)
  connectionFactory.setPort(jmsPort.toInt)
  connectionFactory.setChannel(jmsChannel)
  connectionFactory.setQueueManager(jmsQueueManager)
  connectionFactory.setAppName("Gatling TestJmsDsl")

//  connectionFactory.setCCSID(819)
//
//
//  connectionFactory.setSSLResetCount(0)
//  connectionFactory.setIntProperty("XMSC_WMQ_SSL_KEY_RESETCOUNT", 0)
//
//  connectionFactory.setChannel("ELB.MSKV14")
//  connectionFactory.setStringProperty("XMSC_WMQ_CHANNEL", "ELB.MSKV14")
//
//  connectionFactory.setFailIfQuiesce(1)
//
//  connectionFactory.setUseConnectionPooling(true)
//  connectionFactory.setBooleanProperty("XMSC_WMQ_USE_CONNECTION_POOLING", true)
//
//  connectionFactory.setBrokerControlQueue("SYSTEM.BROKER.CONTROL.QUEUE")
//  connectionFactory.setStringProperty("XMSC_WMQ_BROKER_CONTROLQ", "SYSTEM.BROKER.CONTROL.QUEUE")
//
//  connectionFactory.setQueueManager("QMMSKV14")
//  connectionFactory.setStringProperty("XMSC_WMQ_QUEUE_MANAGER", "QMMSKV14")
//
//  connectionFactory.setReceiveIsolation(0)
//  connectionFactory.setIntProperty("XMSC_WMQ_RECEIVE_ISOLATION", 0)
//
//  //connectionFactory.setMsgCompList("NONE RLE ZLIBFAST ZLIBHIGH")
//  connectionFactory.setMsgCompList("NONE")
//  //connectionFactory.setObjectProperty("XMSC_WMQ_MSG_COMP", "[0]")
//
//  connectionFactory.setMessageSelection(0)
//  connectionFactory.setIntProperty("XMSC_WMQ_MESSAGE_SELECTION", 0)
//
//  //val connectionTag = Array.fill(128)(0).map(_.toByte)
//  val connectionTag = Array(0x37, 0x7b, 0xc1, 0x7e).map(_.toByte)
//  connectionFactory.setConnTag(connectionTag)
//  connectionFactory.setBytesProperty("XMSC_WMQ_CONNECTION_TAG", connectionTag)
//
//  connectionFactory.setProcessDuration(0)
//  connectionFactory.setIntProperty("XMSC_WMQ_PROCESS_DURATION", 0)
//
//  connectionFactory.setAsyncExceptions(1)
//  connectionFactory.setIntProperty("XMSC_ASYNC_EXCEPTIONS", 1)
//
//  connectionFactory.setMsgBatchSize(10)
//  connectionFactory.setIntProperty("XMSC_WMQ_MSG_BATCH_SIZE", 10)
//
//  connectionFactory.setOutcomeNotification(true)
//  connectionFactory.setBooleanProperty("XMSC_WMQ_OUTCOME_NOTIFICATION", true)
//
//  connectionFactory.setWildcardFormat(0)
//
  connectionFactory.setShareConvAllowed(1)
  connectionFactory.setIntProperty("XMSC_WMQ_SHARE_CONV_ALLOWED", 1)
//
//  connectionFactory.setBrokerCCSubQueue("SYSTEM.JMS.ND.CC.SUBSCRIBER.QUEUE")
//  connectionFactory.setStringProperty("XMSC_WMQ_BROKER_CC_SUBQ", "SYSTEM.JMS.ND.CC.SUBSCRIBER.QUEUE")
//
//  connectionFactory.setMessageRetention(1)
//  connectionFactory.setIntProperty("XMSC_WMQ_MESSAGE_RETENTION", 1)
//
//  connectionFactory.setRescanInterval(5000)
//  connectionFactory.setIntProperty("XMSC_WMQ_RESCAN_INTERVAL", 5000)
//
//  connectionFactory.setTransportType(1)
//  connectionFactory.setIntProperty("XMSC_WMQ_CONNECTION_MODE", 1)
//
//  connectionFactory.setBrokerPubQueue("SYSTEM.BROKER.DEFAULT.STREAM")
//  connectionFactory.setStringProperty("XMSC_WMQ_BROKER_PUBQ", "SYSTEM.BROKER.DEFAULT.STREAM")
//
//  connectionFactory.setBrokerVersion(-1)
//
//  connectionFactory.setClientReconnectTimeout(1800)
//  connectionFactory.setIntProperty("XMSC_WMQ_CLIENT_RECONNECT_TIMEOUT", 1800)
//
//  connectionFactory.setStringProperty("XMSC_CONNECTION_TYPE_NAME", "com.ibm.msg.client.wmq")
//
//  connectionFactory.setMaxBufferSize(1000)
//  connectionFactory.setIntProperty("XMSC_WMQ_MAX_BUFFER_SIZE", 1000)
//
//  connectionFactory.setBrokerQueueManager("")
//  connectionFactory.setStringProperty("XMSC_WMQ_BROKER_QMGR", "")
//
//  connectionFactory.setTargetClientMatching(true)
//  connectionFactory.setBooleanProperty("XMSC_WMQ_TARGET_CLIENT_MATCHING", true)
//
//  connectionFactory.setSendCheckCount(0)
//  connectionFactory.setIntProperty("XMSC_WMQ_SEND_CHECK_COUNT", 0)
//
//  connectionFactory.setBrokerSubQueue("SYSTEM.JMS.ND.SUBSCRIBER.QUEUE")
//  connectionFactory.setStringProperty("XMSC_WMQ_BROKER_SUBQ", "SYSTEM.JMS.ND.SUBSCRIBER.QUEUE")
//
//  connectionFactory.setClientReconnectOptions(0)
//  connectionFactory.setIntProperty("XMSC_WMQ_CLIENT_RECONNECT_OPTIONS", 0)
//
//  connectionFactory.setReceiveExitInit("")
//  connectionFactory.setStringProperty("XMSC_WMQ_RECEIVE_EXIT_INIT", "")
//
//  connectionFactory.setProxyPort(443)
//  connectionFactory.setIntProperty("XMSC_RTT_PROXY_PORT", 443)
//
//  connectionFactory.setHdrCompList("NONE")
//  //connectionFactory.setHdrCompList("NONE SYSTEM")
//
//  connectionFactory.setCloneSupport(0)
//  connectionFactory.setIntProperty("XMSC_WMQ_CLONE_SUPPORT", 0)
//
//  connectionFactory.setMapNameStyle(true)
//  connectionFactory.setBooleanProperty("XMSC_WMQ_MAP_NAME_STYLE", true)
//
//
//  connectionFactory.setSyncpointAllGets(false)
//  connectionFactory.setBooleanProperty("XMSC_WMQ_SYNCPOINT_ALL_GETS", false)
//
//  connectionFactory.setOptimisticPublication(false)
//  connectionFactory.setBooleanProperty("XMSC_WMQ_OPT_PUB", false)
//
//  connectionFactory.setTempQPrefix("")
//  connectionFactory.setStringProperty("XMSC_WMQ_TEMP_Q_PREFIX", "")
//
//  connectionFactory.setSendExitInit("")
//  connectionFactory.setStringProperty("XMSC_WMQ_SEND_EXIT_INIT", "")
//
//  //78
//  connectionFactory.setTemporaryModel("SYSTEM.DEFAULT.MODEL.QUEUE")
//  connectionFactory.setStringProperty("XMSC_WMQ_TEMPORARY_MODEL", "SYSTEM.DEFAULT.MODEL.QUEUE")
//
//
//  //82
//  connectionFactory.setLocalAddress("")
//  connectionFactory.setStringProperty("XMSC_WMQ_LOCAL_ADDRESS", "")
//
//  //83
//  connectionFactory.setSubscriptionStore(1)
//  connectionFactory.setIntProperty("XMSC_WMQ_SUBSCRIPTION_STORE", 1)
//
//
//  //89
//  connectionFactory.setMQConnectionOptions(0)
//  connectionFactory.setIntProperty("XMSC_WMQ_CONNECT_OPTIONS", 0)
//
//  //90
//  connectionFactory.setShortProperty("XMSC_ADMIN_OBJECT_TYPE", 17.toShort)
//
//
//  connectionFactory.setStringProperty("XMSC_WMQ_CONNECTION_NAME_LIST_INT", "s-msk-v-mq04.raiffeisen.ru(1414)")
//
//  connectionFactory.setIntProperty("XMSC_CONNECTION_TYPE", 1)
//
//  connectionFactory.setBooleanProperty("XMSC_WMQ_SPARSE_SUBSCRIPTIONS", false)
//
//  connectionFactory.setSecurityExit("")
//  connectionFactory.setSendExit("")
//  connectionFactory.setProviderVersion("unspecified")
//  connectionFactory.setMulticast(0)
//
//
//
//  connectionFactory.setDirectAuth(0)
//  connectionFactory.setIntProperty("XMSC_RTT_DIRECT_AUTH", 0)


  val jmsConfig = jms
    .connectionFactory(connectionFactory)
    .usePersistentDeliveryMode
    .replyTimeout(3 * 60 * 1000)
    .matchByCorrelationId
    .listenerThreadCount(1)


  val putMessage = new PutMessage()

  val scnSendSimple = scenario("JMS send simple")
    .exec(
      putMessage.putMessage(jmsInputName, "test")
    )

  val scnSendAndWait = scenario("JMS request/reply")
    .exec(
      putMessage.putMessageAndWait(jmsInputName, jmsOutputName, "req-reply")
    )

  val testDurationMinutes = 15
  val startFactor = 0.5
  val scaleFactor = 3.0

  setUp(
    //scnSendSimple.inject(rampUsersPerSec(10.0 * startFactor) to (scaleFactor * 100.0) during(testDurationMinutes minutes)),
    scnSendAndWait.inject(rampUsersPerSec(1.0 * startFactor) to (scaleFactor * 20.0) during(testDurationMinutes minutes))
  )
  .protocols(jmsConfig)

  def checkBodyTextCorrect(m: Message) = {
    // this assumes that the service just does an "uppercase" transform on the text
    m match {
      case tm: TextMessage => tm.getText == "HELLO FROM GATLING JMS DSL"
      case _               => false
    }
  }
}
