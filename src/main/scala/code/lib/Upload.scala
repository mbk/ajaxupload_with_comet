package code {
	package lib {

        import net.liftweb.common.{ Box, Full, Empty }
	    import net.liftweb.http._
		import net.liftweb.util._
		import net.liftweb.http.js.JsCmds._
	    import net.liftweb.util.Helpers._

		object StatusHolder extends SessionVar[Box[(Long, Long)]](Empty)  
			
		
		object UploadManager {	
			
			private var prog_init = false;
			
			def sessionProgessListener = {
				S.session.open_!.progressListener = Full({
					(p: Long,q: Long,r: Int) => {
						//Console println("in progress listener"+p+"..."+q)
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
			    val size: Long = StatusHolder.is.map(_._2).openOr(0L)
				Console println("progress: "+ received+" from "+size)
			    "" + received + "/" + size
			}					
						
			def receiveUpload: LiftRules.DispatchPF = { 
			  case Req("upload" :: Nil, _, PostRequest) => () => {
					val files = S.request.open_!.uploadedFiles
					Console println(files)
					Full(new OkResponse)
			  }
			}
			
			def progress: LiftRules.DispatchPF = { 
			  case Req("progress" :: Nil, _, _) => () => {
					Full(PlainTextResponse(getUploadProgress))
			  }
			}
		}
	}
}