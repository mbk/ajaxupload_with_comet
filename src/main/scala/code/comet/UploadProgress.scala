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
import net.liftweb.common._

object StatusHolder extends SessionVar[Box[(Long, Long)]](Empty)  

class UploadProgress extends CometActor {
	
  		override def defaultPrefix = Full("progress")

		
    	def render = bind("main" -> <span id="message">Upload progress</span>)
		
    	ActorPing.schedule(this, Message, 1000L)
		
		def sessionProgessListener =
	    S.session.foreach(s => { 
	      s.progressListener = Full((pBytesRead: Long, pBytesTotal: Long, pItem: Int) => {
	        StatusHolder(Full((pBytesRead, pBytesTotal)))
	      })
	    })
		
		override def localSetup() {
			sessionProgessListener
		}

		def getUploadProgress() = {

		    val received: Double = StatusHolder.is.map(_._1.toDouble).openOr(0D)
		    val size: Double = StatusHolder.is.map(_._2.toDouble).openOr(0D)

			Console println("!!!!!"+this+"progress: "+ received+" from "+size)
		    "" + received + "/" + size
			if ((received == 0D) && (size == 0D)) {""}
			else {
				if (received != size) {"Uploaded " + ((received/size) * 100).toInt +"%"}
				else {"Upload completed."}
			} 
		}
		
	    override def lowPriority : PartialFunction[Any,Unit] = {
	      case Message => {
	        partialUpdate(SetHtml("message", Text(getUploadProgress())))
	        ActorPing.schedule(this, Message, 1000L)
	     }
	   }
}
case object Message

}}
