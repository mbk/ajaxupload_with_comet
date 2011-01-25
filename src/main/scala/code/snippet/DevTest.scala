package code.snippet {
	
  import _root_.net.liftweb.mapper.MappedEmail
  import net.liftweb.common.{ Box, Full,Empty }
  import net.liftweb.http.{S, SHtml,FileParamHolder }
  import net.liftweb.http.js._  
  import net.liftweb.http.js.JsCmds._
  import scala.xml._
  import net.liftweb.util.Helpers._

	
	class Test {
		
		var fileHolder : Box[FileParamHolder] = Empty
	
		def tester(xhtml: NodeSeq): NodeSeq = {
			bind("upload",xhtml,
			"loader" -> SHtml.fileUpload((f) => {fileHolder = new Full(f)}),
			"submit" -> SHtml.submit(S ? "Upload",() => {Console println("####file uploaded, NOOP####")})
			)
		}
	}
}