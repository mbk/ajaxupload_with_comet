package code {
	package lib {

        import net.liftweb.common.{ Box, Full, Empty }
	    import net.liftweb.http._
		import net.liftweb.util._
		import net.liftweb.http.js.JsCmds._
	    import net.liftweb.util.Helpers._

			
		
		object UploadManager {	
			
											
			def receiveUpload: LiftRules.DispatchPF = { 
			  case Req("upload" :: Nil, _, PostRequest) => () => {
					val files = S.request.open_!.uploadedFiles
					Console println(files)
					Full(new OkResponse)
			  }
			}
		}
	}
}