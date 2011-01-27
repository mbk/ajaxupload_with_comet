package code {
package comet {

import net.liftweb._
import http._
import SHtml._ 
import net.liftweb.common.{Box, Full}
import net.liftweb.util._
import net.liftweb.actor._
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds.{SetHtml}
import scala.xml._

class Clock extends CometActor {
	
  override def defaultPrefix = Full("clock")
		
  def render = bind("main" -> <span id="message">Whatever you feel like returning</span>)
		
  ActorPing.schedule(this, Message, 1000L)
		
  override def lowPriority : PartialFunction[Any,Unit] = {
    case Message => {
      partialUpdate(SetHtml("message", Text("updated: " + timeNow.toString)))
      ActorPing.schedule(this, Message, 1000L)
    }
  }
}
case object Message

}}
