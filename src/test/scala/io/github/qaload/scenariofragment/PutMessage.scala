package io.github.qaload.scenariofragment

import java.text.SimpleDateFormat

import io.gatling.core.Predef._
import io.gatling.jms.Predef._

import scala.language.postfixOps

class PutMessage (){

  val dfDateTime = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSSSSS")

  def putMessage(queueInputName: String, messageText: String) =
    repeat(1)
    {
        exec
        {
          session =>
            val now = System.currentTimeMillis()
            session
              .set("createTime", dfDateTime.format(now))
	      .set("messageText", messageText)
        }
      .exec(jms("send")
        .send
        .queue(queueInputName)
        .textMessage("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
          +"<input>${messageText} --- ${createTime}</input>")
      )
    }


  def putMessageAndWait(queueInputName: String, queueOutputName: String, messageText: String) =
      exec{
         session =>
            val now = System.currentTimeMillis()
            session.set("createTime", dfDateTime.format(now))
	                  .set("messageText", messageText)
      }
      .exec(jms("requestReply")
        .requestReply
        .queue(queueInputName)
        .replyQueue(queueOutputName)
        .noJmsReplyTo
        .textMessage("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
          +"<input>${messageText} --- ${createTime}</input>")
      )

}
