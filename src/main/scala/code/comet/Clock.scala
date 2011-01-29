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


class Clock extends CometActor {
	
  override def defaultPrefix = Full("clock")

  	private var prog_init = false;
		
    def render = bind("main" -> <span id="message">Upload progress</span>)
		
    ActorPing.schedule(this, Message, 1000L)

		def sessionProgessListener = {
			S.session.open_!.progressListener = Full({
				(p: Long,q: Long,r: Int) => {
					Console println("in progress listener"+p+"..."+q)
					StatusHolder(Full((p,q)))
					()
				}
			})
		}

		def getUploadProgress() = {
			if(true) {
				sessionProgessListener;
				prog_init = true
			}
		    val received: Long = StatusHolder.is.map(_._1).openOr(0L)
		    val size: Long = StatusHolder.is.map(_._2).openOr(1L)
			Console println("progress: "+ received+" from "+size)
		    "" + received + "/" + size
		    "Upload: " + received + "from  a total of"+ size
		}
		
	    override def lowPriority : PartialFunction[Any,Unit] = {
	      case Message => {
	        partialUpdate(SetHtml("message", Text((new java.util.Date()).toString + getUploadProgress())))
	        ActorPing.schedule(this, Message, 1000L)
	     }
	   }
}
case object Message

}}
