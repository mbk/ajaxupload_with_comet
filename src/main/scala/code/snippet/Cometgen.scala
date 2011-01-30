package code.snippet {
	
	import net.liftweb.common.{ Box, Full,Empty }
	import net.liftweb.http.{S, SHtml,FileParamHolder }
	import net.liftweb.http.js._  
	import net.liftweb.http.js.JsCmds._
	import scala.xml._
	import net.liftweb.util.Helpers._
	
	class Cometgen {
		
		def another(xhtml: NodeSeq): NodeSeq = {
			bind("gen",xhtml,"newcomet" -> Cometgen.newFragment())
		}
		
	}
	
	object Cometgen {
		
		private var counter: Long = 0
		
		def newFragment() = {
			counter += 1
			
			val fragment = <lift:comet type="UploadProgress"  name={"tab" +counter}>
			<progress:main></progress:main> 
			</lift:comet>
			
			Console println(fragment)
			
			fragment
		}
		
	}
	

}